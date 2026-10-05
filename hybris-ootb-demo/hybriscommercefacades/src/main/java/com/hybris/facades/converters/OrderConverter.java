package com.hybris.facades.converters;

import com.hybris.core.model.OrderEntryModel;
import com.hybris.core.model.OrderModel;
import com.hybris.facades.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Hybris OOTB: OrderConverter.
 * Mirrors de.hybris.platform.commercefacades.order.converters.populator.OrderPopulator.
 */
@Component("orderConverter")
public class OrderConverter implements Converter<OrderModel, OrderData> {

    private final ProductConverter productConverter;

    @Autowired
    public OrderConverter(final ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    @Override
    public OrderData convert(final OrderModel source) {
        return convert(source, new OrderData());
    }

    @Override
    public OrderData convert(final OrderModel source, final OrderData target) {
        if (source == null) return target;

        target.setCode(source.getCode());
        target.setGuid(source.getGuid());
        target.setCurrencyIso(source.getCurrencyIso());
        target.setSite(source.getSite());
        target.setCreated(source.getPlaced());

        if (source.getStatus() != null) {
            target.setStatus(source.getStatus().name());
            target.setStatusDisplay(formatStatus(source.getStatus().name()));
        }

        target.setCancellable(source.getStatus() == OrderModel.OrderStatus.CREATED ||
            source.getStatus() == OrderModel.OrderStatus.ON_VALIDATION);

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

        if (source.getDeliveryAddress() != null) {
            final AddressData addr = new AddressData();
            addr.setId(source.getDeliveryAddress().getPk());
            addr.setFirstName(source.getDeliveryAddress().getFirstName());
            addr.setLastName(source.getDeliveryAddress().getLastName());
            addr.setLine1(source.getDeliveryAddress().getLine1());
            addr.setTown(source.getDeliveryAddress().getTown());
            addr.setPostalCode(source.getDeliveryAddress().getPostalCode());
            if (source.getDeliveryAddress().getCountryIso() != null) {
                final CountryData country = new CountryData();
                country.setIsocode(source.getDeliveryAddress().getCountryIso());
                addr.setCountry(country);
            }
            target.setDeliveryAddress(addr);
        }

        if (source.getEntries() != null) {
            final List<OrderEntryData> entries = new ArrayList<>();
            for (final OrderEntryModel entry : source.getEntries()) {
                final OrderEntryData data = new OrderEntryData();
                data.setEntryNumber(entry.getEntryNumber());
                data.setQuantity(entry.getQuantity());
                data.setUnit(entry.getUnit());
                data.setUpdateable(false);
                if (entry.getProduct() != null) {
                    data.setProduct(productConverter.convert(entry.getProduct()));
                }
                if (entry.getBasePrice() != null) {
                    data.setBasePrice(new PriceData(entry.getBasePrice(), source.getCurrencyIso()));
                }
                if (entry.getTotalPrice() != null) {
                    data.setTotalPrice(new PriceData(entry.getTotalPrice(), source.getCurrencyIso()));
                }
                entries.add(data);
            }
            target.setEntries(entries);
        }

        return target;
    }

    private String formatStatus(final String status) {
        return status.replace("_", " ").toLowerCase()
            .substring(0, 1).toUpperCase() +
            status.replace("_", " ").toLowerCase().substring(1);
    }
}
