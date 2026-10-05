package com.hybris.storefront.controllers;

import com.hybris.facades.data.AddressData;
import com.hybris.facades.data.CartData;
import com.hybris.facades.data.CountryData;
import com.hybris.facades.data.OrderData;
import com.hybris.facades.data.ProductData;
import com.hybris.facades.data.SearchResultData;
import com.hybris.facades.facades.CartFacade;
import com.hybris.facades.facades.CheckoutFacade;
import com.hybris.facades.facades.ProductFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Hybris OOTB: Storefront page controllers.
 * Mirrors yacceleratorstorefront page controllers:
 * - HomePageController, CategoryPageController, ProductPageController
 * - CartPageController, CheckoutController, OrderConfirmationPageController
 *
 * In SAP Hybris, controllers extend AbstractPageController and resolve
 * CMS page templates via CMSPageService. This Spring MVC equivalent
 * delegates directly to facade layer (same pattern, simplified CMS tier).
 */
@Controller
public class StorefrontController {

    private static final Logger LOG = LoggerFactory.getLogger(StorefrontController.class);

    private final ProductFacade productFacade;
    private final CartFacade cartFacade;
    private final CheckoutFacade checkoutFacade;

    @Autowired
    public StorefrontController(final ProductFacade productFacade,
                                 final CartFacade cartFacade,
                                 final CheckoutFacade checkoutFacade) {
        this.productFacade = productFacade;
        this.cartFacade = cartFacade;
        this.checkoutFacade = checkoutFacade;
    }

    @GetMapping("/login")
    public String loginPage(final Model model) {
        model.addAttribute("pageTitle", "My Account | Login");
        model.addAttribute("cartItemCount", 0);
        return "login";
    }

    /** Immutable sale item DTO: product + discount metadata. */
    public static final class SaleItem {
        private final ProductData product;
        private final int discountPct;
        private final String salePriceFormatted;
        private final String originalPriceFormatted;

        public SaleItem(final ProductData product, final int discountPct) {
            this.product = product;
            this.discountPct = discountPct;
            final NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);
            final BigDecimal orig = product.getPrice() != null && product.getPrice().getValue() != null
                    ? product.getPrice().getValue() : BigDecimal.ZERO;
            final BigDecimal sale = orig.multiply(BigDecimal.valueOf(100 - discountPct))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            this.originalPriceFormatted = fmt.format(orig);
            this.salePriceFormatted = fmt.format(sale);
        }

        public ProductData getProduct() { return product; }
        public int getDiscountPct() { return discountPct; }
        public String getSalePriceFormatted() { return salePriceFormatted; }
        public String getOriginalPriceFormatted() { return originalPriceFormatted; }
    }

    /**
     * Galaxy Days Sale page — curated sale products with real-time discounts.
     */
    @GetMapping("/sale")
    public String salePage(final Model model) {
        final Map<String, Integer> saleConfig = new LinkedHashMap<>();
        saleConfig.put("SAM_S24U", 20);
        saleConfig.put("SAM_S24P", 20);
        saleConfig.put("SAM_ZFOLD6", 20);
        saleConfig.put("SAM_ZFLIP6", 25);
        saleConfig.put("TAB_S10U", 25);
        saleConfig.put("TAB_S10P", 25);
        saleConfig.put("WATCH_ULTRA", 20);
        saleConfig.put("WATCH7_44", 15);
        saleConfig.put("BUDS3_PRO", 28);
        saleConfig.put("BOOK4_ULTRA", 20);
        saleConfig.put("QN90D_65", 25);
        saleConfig.put("OD_OLED_G9", 20);

        final List<SaleItem> saleItems = new ArrayList<>();
        for (final Map.Entry<String, Integer> entry : saleConfig.entrySet()) {
            try {
                final ProductData product = productFacade.getProductForCode(entry.getKey());
                saleItems.add(new SaleItem(product, entry.getValue()));
            } catch (Exception e) {
                LOG.warn("Sale product not found: {}", entry.getKey());
            }
        }

        model.addAttribute("saleItems", saleItems);
        model.addAttribute("pageTitle", "Galaxy Days Sale");
        addCartCount(model);
        return "sale";
    }

    /**
     * Homepage — mirrors HomePageController.
     * Renders hero banner + featured product carousel (ProductCarouselComponent).
     */
    @GetMapping({"/", "/index", "/home"})
    public String homepage(final Model model) {
        final List<ProductData> featured = productFacade.getFeaturedProducts();
        model.addAttribute("featuredProducts", featured);
        model.addAttribute("pageTitle", "Electronics Store - Home");
        addCartCount(model);
        return "homepage";
    }

    /**
     * Product search — mirrors SearchPageController with SOLR-backed results.
     * In Hybris: SolrSearchService → SearchResultData via ProductSearchFacade.
     */
    @GetMapping("/search")
    public String searchPage(@RequestParam(defaultValue = "") final String q,
                              @RequestParam(defaultValue = "0") final int page,
                              @RequestParam(defaultValue = "12") final int size,
                              @RequestParam(defaultValue = "") final String sort,
                              final Model model) {
        LOG.debug("Search: q='{}', page={}, sort={}", q, page, sort);
        final SearchResultData<ProductData> results = productFacade.textSearch(q, page, size);
        sortResults(results, sort);
        model.addAttribute("searchResults", results);
        model.addAttribute("query", q);
        model.addAttribute("sort", sort);
        model.addAttribute("pageTitle", "Search: " + q);
        addCartCount(model);
        return "search-results";
    }

    /**
     * Category page (PLP) — mirrors CategoryPageController.
     * In Hybris: resolves to CategoryPage CMS template with ProductListComponent.
     */
    @GetMapping("/c/{categoryCode}")
    public String categoryPage(@PathVariable final String categoryCode,
                                @RequestParam(defaultValue = "0") final int page,
                                @RequestParam(defaultValue = "12") final int size,
                                @RequestParam(defaultValue = "") final String sort,
                                final Model model) {
        LOG.debug("Category page: {}, sort={}", categoryCode, sort);
        final SearchResultData<ProductData> results = productFacade.categorySearch(categoryCode, page, size);
        sortResults(results, sort);
        model.addAttribute("searchResults", results);
        model.addAttribute("categoryCode", categoryCode);
        model.addAttribute("sort", sort);
        model.addAttribute("pageTitle", results.getCurrentCategory() != null
                ? results.getCurrentCategory().getName() : categoryCode);
        addCartCount(model);
        return "category-page";
    }

    /**
     * Product detail page (PDP) — mirrors ProductPageController.
     * In Hybris: resolves to ProductPage CMS template with AddToCartComponent + TabsComponent.
     */
    @GetMapping("/p/{productCode}")
    public String productPage(@PathVariable final String productCode, final Model model) {
        LOG.debug("Product page: {}", productCode);
        final ProductData product = productFacade.getProductForCode(productCode);
        final List<ProductData> similar = productFacade.getSimilarProducts(productCode, 4);
        model.addAttribute("product", product);
        model.addAttribute("similarProducts", similar);
        model.addAttribute("pageTitle", product.getName());
        addCartCount(model);
        return "product-detail";
    }

    /**
     * Cart page — mirrors CartPageController.
     */
    @GetMapping("/cart")
    public String cartPage(final Model model) {
        final CartData cart = cartFacade.getSessionCart();
        model.addAttribute("cart", cart);
        model.addAttribute("pageTitle", "Shopping Cart");
        model.addAttribute("cartItemCount", cartFacade.getCartTotalItems());
        return "cart";
    }

    /**
     * Add to cart — mirrors AddToCartController.
     * In Hybris, this can be AJAX or form POST returning CartModificationData.
     */
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam final String productCode,
                             @RequestParam(defaultValue = "1") final long quantity) {
        LOG.info("Add to cart: product={}, qty={}", productCode, quantity);
        cartFacade.addToCart(productCode, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam final int entryNumber,
                              @RequestParam final long quantity) {
        cartFacade.updateCartEntry(entryNumber, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam final int entryNumber) {
        cartFacade.removeCartEntry(entryNumber);
        return "redirect:/cart";
    }

    /**
     * Checkout GET — mirrors CheckoutController (multi-step entry point).
     */
    @GetMapping("/checkout")
    public String checkoutPage(final Model model) {
        final CartData cart = cartFacade.getSessionCart();
        model.addAttribute("cart", cart);
        model.addAttribute("pageTitle", "Checkout");
        model.addAttribute("deliveryModes", checkoutFacade.getSupportedDeliveryModes());
        model.addAttribute("paymentModes", checkoutFacade.getSupportedPaymentModes());
        model.addAttribute("cartItemCount", cartFacade.getCartTotalItems());
        return "checkout";
    }

    /**
     * Place order POST — mirrors PlaceOrderController.
     * In Hybris: validates checkout → calls CheckoutFacade.placeOrder()
     * → redirects to OrderConfirmationPageController.
     */
    @PostMapping("/checkout/placeOrder")
    public String placeOrder(@RequestParam(defaultValue = "standard") final String deliveryMode,
                              @RequestParam(defaultValue = "creditCard") final String paymentMode,
                              @RequestParam(defaultValue = "Guest") final String firstName,
                              @RequestParam(defaultValue = "Customer") final String lastName,
                              @RequestParam(defaultValue = "123 Demo Street") final String line1,
                              @RequestParam(defaultValue = "London") final String town,
                              @RequestParam(defaultValue = "EC1A 1BB") final String postalCode,
                              @RequestParam(defaultValue = "GB") final String countryIso,
                              final Model model) {
        LOG.info("Place order: delivery={}, payment={}", deliveryMode, paymentMode);
        try {
            final AddressData addressData = buildAddressData(firstName, lastName, line1, town, postalCode, countryIso);
            checkoutFacade.setDeliveryAddress(addressData);
            checkoutFacade.setDeliveryMode(deliveryMode);
            checkoutFacade.setPaymentMode(paymentMode);

            final OrderData order = checkoutFacade.placeOrder();
            model.addAttribute("orderCode", order.getCode());
            model.addAttribute("orderTotal", order.getTotalPrice() != null ? order.getTotalPrice().getValue() : null);
            model.addAttribute("currencyIso", order.getTotalPrice() != null ? order.getTotalPrice().getCurrencyIso() : "USD");
            return "order-confirmation";
        } catch (final Exception e) {
            LOG.error("Order placement failed: {}", e.getMessage(), e);
            model.addAttribute("error", "Order placement failed: " + e.getMessage());
            model.addAttribute("cart", cartFacade.getSessionCart());
            model.addAttribute("deliveryModes", checkoutFacade.getSupportedDeliveryModes());
            model.addAttribute("paymentModes", checkoutFacade.getSupportedPaymentModes());
            return "checkout";
        }
    }

    private AddressData buildAddressData(final String firstName, final String lastName,
                                          final String line1, final String town,
                                          final String postalCode, final String countryIso) {
        final AddressData addr = new AddressData();
        addr.setFirstName(firstName);
        addr.setLastName(lastName);
        addr.setLine1(line1);
        addr.setTown(town);
        addr.setPostalCode(postalCode);
        final CountryData country = new CountryData();
        country.setIsocode(countryIso);
        addr.setCountry(country);
        addr.setShippingAddress(true);
        return addr;
    }

    private void sortResults(final SearchResultData<ProductData> results, final String sort) {
        if (results == null || results.getResults() == null || sort == null || sort.isEmpty()) {
            return;
        }
        final List<ProductData> sorted = new ArrayList<>(results.getResults());
        switch (sort) {
            case "price_asc" -> sorted.sort(Comparator.comparing(
                    p -> p.getPrice() != null && p.getPrice().getValue() != null
                            ? p.getPrice().getValue() : BigDecimal.ZERO));
            case "price_desc" -> sorted.sort(Comparator.comparing(
                    (ProductData p) -> p.getPrice() != null && p.getPrice().getValue() != null
                            ? p.getPrice().getValue() : BigDecimal.ZERO).reversed());
            case "name_asc" -> sorted.sort(Comparator.comparing(
                    p -> p.getName() != null ? p.getName() : ""));
            default -> { /* relevance — no change */ }
        }
        results.setResults(sorted);
    }

    private void addCartCount(final Model model) {
        try {
            model.addAttribute("cartItemCount", cartFacade.getCartTotalItems());
        } catch (final Exception e) {
            model.addAttribute("cartItemCount", 0);
        }
    }
}
