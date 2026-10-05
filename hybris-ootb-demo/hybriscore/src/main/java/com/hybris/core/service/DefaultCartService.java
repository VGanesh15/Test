package com.hybris.core.service;

import com.hybris.core.dao.CartDao;
import com.hybris.core.dao.CustomerDao;
import com.hybris.core.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Hybris OOTB: DefaultCartService.
 * Mirrors de.hybris.platform.order.impl.DefaultCartService.
 *
 * Cart recalculation logic mirrors the CalculationService which applies:
 * 1. Europe1PriceFactory for base prices
 * 2. PromotionEngine for discount evaluation
 * 3. TaxCalculationService for tax computation
 * 4. DeliveryModeFinder for delivery cost
 */
@Service("cartService")
@Transactional
public class DefaultCartService implements CartService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultCartService.class);
    private static final BigDecimal TAX_RATE = new BigDecimal("0.20");
    private static final BigDecimal STANDARD_DELIVERY_COST = new BigDecimal("5.99");

    private final CartDao cartDao;
    private final ProductService productService;
    private final CustomerDao customerDao;

    @Autowired
    public DefaultCartService(final CartDao cartDao,
                               final ProductService productService,
                               final CustomerDao customerDao) {
        this.cartDao = cartDao;
        this.productService = productService;
        this.customerDao = customerDao;
    }

    @Override
    public CartModel getSessionCart() {
        final CartModel cart = new CartModel();
        cart.setGuid(UUID.randomUUID().toString());
        cart.setCode("ANON-" + System.currentTimeMillis());
        cart.setCurrencyIso("USD");
        cart.setSite("electronics");
        cart.setStore("electronicsStore");
        return cartDao.save(cart);
    }

    @Override
    public CartModel getOrCreateCartForUser(final CustomerModel customer) {
        Assert.notNull(customer, "Customer must not be null");
        final List<CartModel> carts = cartDao.findActiveCartsByUser(customer.getUid());
        if (!carts.isEmpty()) {
            LOG.debug("Restoring existing cart for user: {}", customer.getUid());
            return carts.get(0);
        }
        LOG.info("Creating new cart for user: {}", customer.getUid());
        final CartModel cart = new CartModel();
        cart.setGuid(UUID.randomUUID().toString());
        cart.setCode("CART-" + System.currentTimeMillis());
        cart.setUser(customer);
        cart.setCurrencyIso(customer.getCurrencyIso() != null ? customer.getCurrencyIso() : "USD");
        cart.setSite("electronics");
        cart.setStore("electronicsStore");
        return cartDao.save(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartModel> getCartForGuid(final String guid) {
        return cartDao.findByGuid(guid);
    }

    @Override
    public CartEntryModel addToCart(final CartModel cart, final String productCode, final long quantity) {
        Assert.notNull(cart, "Cart must not be null");
        Assert.hasText(productCode, "Product code must not be blank");
        Assert.isTrue(quantity > 0, "Quantity must be positive");

        final ProductModel product = productService.getProductForCode(productCode);

        if (!product.isPurchasable()) {
            throw new IllegalStateException("Product " + productCode + " is not purchasable");
        }
        if (product.getStockLevel() != null && product.getStockLevel() < quantity) {
            throw new IllegalStateException("Insufficient stock for product: " + productCode);
        }

        final Optional<CartEntryModel> existingEntry = cart.getEntries().stream()
            .filter(e -> e.getProduct().getCode().equals(productCode))
            .findFirst();

        if (existingEntry.isPresent()) {
            final CartEntryModel entry = existingEntry.get();
            entry.setQuantity(entry.getQuantity() + quantity);
            entry.setTotalPrice(entry.getBasePrice().multiply(BigDecimal.valueOf(entry.getQuantity())));
            LOG.debug("Updated existing cart entry for {}, new qty={}", productCode, entry.getQuantity());
            calculateCart(cart);
            return entry;
        }

        final int nextEntryNumber = cart.getEntries().size();
        final CartEntryModel entry = new CartEntryModel();
        entry.setEntryNumber(nextEntryNumber);
        entry.setProduct(product);
        entry.setQuantity(quantity);
        entry.setBasePrice(product.getPrice() != null ? product.getPrice() : BigDecimal.ZERO);
        entry.setTotalPrice(entry.getBasePrice().multiply(BigDecimal.valueOf(quantity)));
        entry.setCurrencyIso(cart.getCurrencyIso());
        entry.setUnit("pieces");
        entry.setOrder(cart);
        cart.getEntries().add(entry);

        LOG.info("Added product {} to cart, qty={}", productCode, quantity);
        calculateCart(cart);
        return cartDao.save(cart).getEntries().stream()
            .filter(e -> e.getProduct().getCode().equals(productCode))
            .findFirst()
            .orElseThrow();
    }

    @Override
    public CartModel updateCartEntry(final CartModel cart, final int entryNumber, final long newQuantity) {
        Assert.notNull(cart, "Cart must not be null");
        Assert.isTrue(newQuantity >= 0, "Quantity must be non-negative");

        if (newQuantity == 0) {
            return removeCartEntry(cart, entryNumber);
        }

        final CartEntryModel entry = cart.getEntries().stream()
            .filter(e -> e.getEntryNumber() == entryNumber)
            .findFirst()
            .orElseThrow(() -> new NoSuchFieldError("Entry not found: " + entryNumber));

        entry.setQuantity(newQuantity);
        entry.setTotalPrice(entry.getBasePrice().multiply(BigDecimal.valueOf(newQuantity)));
        return calculateCart(cart);
    }

    @Override
    public CartModel removeCartEntry(final CartModel cart, final int entryNumber) {
        Assert.notNull(cart, "Cart must not be null");
        cart.getEntries().removeIf(e -> e.getEntryNumber() == entryNumber);
        renumberEntries(cart);
        return calculateCart(cart);
    }

    @Override
    public CartModel calculateCart(final CartModel cart) {
        Assert.notNull(cart, "Cart must not be null");

        BigDecimal subTotal = BigDecimal.ZERO;
        for (final CartEntryModel entry : cart.getEntries()) {
            if (entry.getTotalPrice() != null) {
                subTotal = subTotal.add(entry.getTotalPrice());
            }
        }

        final BigDecimal tax = subTotal.multiply(TAX_RATE).setScale(2, java.math.RoundingMode.HALF_UP);
        final BigDecimal delivery = cart.getEntries().isEmpty() ? BigDecimal.ZERO : STANDARD_DELIVERY_COST;
        final BigDecimal total = subTotal.add(tax).add(delivery);

        cart.setSubTotal(subTotal.setScale(2, java.math.RoundingMode.HALF_UP));
        cart.setTotalTax(tax);
        cart.setDeliveryCost(delivery);
        cart.setTotalPrice(total.setScale(2, java.math.RoundingMode.HALF_UP));
        cart.setCalculated(true);

        LOG.debug("Cart calculated: subTotal={}, tax={}, delivery={}, total={}",
            subTotal, tax, delivery, total);

        return cartDao.save(cart);
    }

    @Override
    public CartModel setDeliveryAddress(final CartModel cart, final String addressPk) {
        Assert.notNull(cart, "Cart must not be null");
        Assert.hasText(addressPk, "Address PK must not be blank");
        final AddressModel address = new AddressModel();
        address.setPk(addressPk);
        cart.setDeliveryAddress(address);
        return cartDao.save(cart);
    }

    @Override
    public CartModel setPaymentAddress(final CartModel cart, final String addressPk) {
        Assert.notNull(cart, "Cart must not be null");
        Assert.hasText(addressPk, "Address PK must not be blank");
        final AddressModel address = new AddressModel();
        address.setPk(addressPk);
        cart.setPaymentAddress(address);
        return cartDao.save(cart);
    }

    @Override
    public CartModel setDeliveryMode(final CartModel cart, final String deliveryModeCode) {
        Assert.notNull(cart, "Cart must not be null");
        cart.setDeliveryModeCode(deliveryModeCode);
        if ("premium".equalsIgnoreCase(deliveryModeCode)) {
            cart.setDeliveryCost(new BigDecimal("12.99"));
        } else if ("express".equalsIgnoreCase(deliveryModeCode)) {
            cart.setDeliveryCost(new BigDecimal("9.99"));
        } else {
            cart.setDeliveryCost(STANDARD_DELIVERY_COST);
        }
        return cartDao.save(calculateCart(cart));
    }

    @Override
    public CartModel setPaymentMode(final CartModel cart, final String paymentModeCode) {
        Assert.notNull(cart, "Cart must not be null");
        cart.setPaymentModeCode(paymentModeCode);
        return cartDao.save(cart);
    }

    @Override
    public CartModel applyPromoCode(final CartModel cart, final String promoCode) {
        Assert.notNull(cart, "Cart must not be null");
        Assert.hasText(promoCode, "Promo code must not be blank");
        final String existing = cart.getAppliedPromoCodes();
        cart.setAppliedPromoCodes(existing != null ? existing + "," + promoCode : promoCode);
        if ("SAVE10".equalsIgnoreCase(promoCode)) {
            cart.setTotalDiscounts(cart.getSubTotal().multiply(new BigDecimal("0.10")));
            cart.setTotalPrice(cart.getTotalPrice().subtract(cart.getTotalDiscounts()));
        }
        return cartDao.save(cart);
    }

    @Override
    public void removePromoCode(final CartModel cart, final String promoCode) {
        Assert.notNull(cart, "Cart must not be null");
        if (cart.getAppliedPromoCodes() != null) {
            cart.setAppliedPromoCodes(cart.getAppliedPromoCodes().replace(promoCode, "").replace(",,", ","));
        }
        cart.setTotalDiscounts(BigDecimal.ZERO);
        calculateCart(cart);
    }

    @Override
    public void clearCart(final CartModel cart) {
        Assert.notNull(cart, "Cart must not be null");
        cart.getEntries().clear();
        cart.setSubTotal(BigDecimal.ZERO);
        cart.setTotalPrice(BigDecimal.ZERO);
        cart.setTotalTax(BigDecimal.ZERO);
        cart.setDeliveryCost(BigDecimal.ZERO);
        cart.setTotalDiscounts(BigDecimal.ZERO);
        cart.setCalculated(false);
        cartDao.save(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public int getCartTotalItems(final CartModel cart) {
        return cart.getEntries().stream()
            .mapToInt(e -> e.getQuantity() != null ? e.getQuantity().intValue() : 0)
            .sum();
    }

    private void renumberEntries(final CartModel cart) {
        for (int i = 0; i < cart.getEntries().size(); i++) {
            cart.getEntries().get(i).setEntryNumber(i);
        }
    }
}
