package com.hybris.facades.converters;

import com.hybris.core.model.CartEntryModel;
import com.hybris.core.model.CartModel;
import com.hybris.facades.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Hybris OOTB: CartConverter.
 * Mirrors de.hybris.platform.commercefacades.order.converters.populator.CartPopulator.
 */
@Component("cartConverter")
public class CartConverter implements Converter<CartModel, CartData> {

    private final ProductConverter productConverter;

    @Autowired
    public CartConverter(final ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    @Override
    public CartData convert(final CartModel source) {
        return convert(source, new CartData());
    }

    @Override
    public CartData convert(final CartModel source, final CartData target) {
        if (source == null) return target;

        target.setCode(source.getCode());
        target.setGuid(source.getGuid());
        target.setCurrencyIso(source.getCurrencyIso());
        target.setCalculated(source.isCalculated());
        target.setSite(source.getSite());

        if (source.getSubTotal() != null) {
            target.setSubTotal(new PriceData(source.getSubTotal(), source.getCurrencyIso()));
        }
        if (source.getTotalPrice() != null) {
            target.setTotalPrice(new PriceData(source.getTotalPrice(), source.getCurrencyIso()));
        }
        if (source.getTotalTax() != null) {
            target.setTotalTax(new PriceData(source.getTotalTax(), source.getCurrencyIso()));
        }
        if (source.getTotalDiscounts() != null) {
            target.setTotalDiscounts(new PriceData(source.getTotalDiscounts(), source.getCurrencyIso()));
        }
        if (source.getDeliveryCost() != null) {
            target.setDeliveryCost(new PriceData(source.getDeliveryCost(), source.getCurrencyIso()));
        }

        target.setDeliveryMode(source.getDeliveryModeCode());
        target.setPaymentMode(source.getPaymentModeCode());

        // Convert delivery address
        if (source.getDeliveryAddress() != null) {
            target.setDeliveryAddress(convertAddress(source.getDeliveryAddress()));
        }

        // Convert entries
        if (source.getEntries() != null) {
            final List<OrderEntryData> entries = new ArrayList<>();
            for (final CartEntryModel entry : source.getEntries()) {
                entries.add(convertEntry(entry, source.getCurrencyIso()));
            }
            target.setEntries(entries);
            target.setTotalItems(source.getEntries().stream()
                .mapToInt(e -> e.getQuantity() != null ? e.getQuantity().intValue() : 0)
                .sum());
        }

        return target;
    }

    private OrderEntryData convertEntry(final CartEntryModel entry, final String currencyIso) {
        final OrderEntryData data = new OrderEntryData();
        data.setEntryNumber(entry.getEntryNumber());
        data.setQuantity(entry.getQuantity());
        data.setUnit(entry.getUnit());
        data.setUpdateable(true);
        if (entry.getProduct() != null) {
            data.setProduct(productConverter.convert(entry.getProduct()));
        }
        if (entry.getBasePrice() != null) {
            data.setBasePrice(new PriceData(entry.getBasePrice(), currencyIso));
        }
        if (entry.getTotalPrice() != null) {
            data.setTotalPrice(new PriceData(entry.getTotalPrice(), currencyIso));
        }
        return data;
    }

    private AddressData convertAddress(final com.hybris.core.model.AddressModel addr) {
        final AddressData data = new AddressData();
        data.setId(addr.getPk());
        data.setFirstName(addr.getFirstName());
        data.setLastName(addr.getLastName());
        data.setLine1(addr.getLine1());
        data.setLine2(addr.getLine2());
        data.setTown(addr.getTown());
        data.setPostalCode(addr.getPostalCode());
        data.setPhone(addr.getPhone1());
        if (addr.getCountryIso() != null) {
            final CountryData country = new CountryData();
            country.setIsocode(addr.getCountryIso());
            data.setCountry(country);
        }
        return data;
    }
}
