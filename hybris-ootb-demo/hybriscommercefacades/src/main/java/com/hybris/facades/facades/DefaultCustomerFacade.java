package com.hybris.facades.facades;

import com.hybris.core.model.AddressModel;
import com.hybris.core.model.CustomerModel;
import com.hybris.core.service.CustomerService;
import com.hybris.facades.data.AddressData;
import com.hybris.facades.data.CountryData;
import com.hybris.facades.data.CustomerData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Hybris OOTB: DefaultCustomerFacade.
 * Mirrors de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade.
 */
@Service("customerFacade")
public class DefaultCustomerFacade implements CustomerFacade {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultCustomerFacade.class);

    private final CustomerService customerService;
    private CustomerModel currentCustomer;

    @Autowired
    public DefaultCustomerFacade(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public CustomerData getCurrentCustomer() {
        if (currentCustomer == null) return null;
        return convertToData(currentCustomer);
    }

    @Override
    public void register(final String email, final String firstName,
                         final String lastName, final String password) {
        currentCustomer = customerService.registerCustomer(email, email, firstName, lastName, password);
        LOG.info("Customer registered and logged in: {}", email);
    }

    @Override
    public void login(final String email, final String password) {
        final Optional<CustomerModel> customer = customerService.getCustomerByEmail(email);
        if (customer.isEmpty()) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        currentCustomer = customer.get();
        LOG.info("Customer logged in: {}", email);
    }

    @Override
    public void logout() {
        LOG.info("Customer logged out: {}", currentCustomer != null ? currentCustomer.getEmail() : "anonymous");
        currentCustomer = null;
    }

    @Override
    public void updateProfile(final CustomerData customerData) {
        if (currentCustomer == null) throw new IllegalStateException("No customer logged in");
        currentCustomer.setFirstName(customerData.getFirstName());
        currentCustomer.setLastName(customerData.getLastName());
        currentCustomer.setName(customerData.getFirstName() + " " + customerData.getLastName());
        customerService.updateCustomer(currentCustomer);
    }

    @Override
    public void changePassword(final String currentPassword, final String newPassword) {
        if (currentCustomer == null) throw new IllegalStateException("No customer logged in");
        customerService.changePassword(currentCustomer.getUid(), currentPassword, newPassword);
    }

    @Override
    public List<AddressData> getAddressBook() {
        if (currentCustomer == null) return List.of();
        return currentCustomer.getAddresses().stream()
            .filter(AddressModel::isVisibleInAddressBook)
            .map(this::convertAddressToData)
            .toList();
    }

    @Override
    public void addAddress(final AddressData addressData) {
        if (currentCustomer == null) throw new IllegalStateException("No customer logged in");
        final AddressModel model = new AddressModel();
        model.setFirstName(addressData.getFirstName());
        model.setLastName(addressData.getLastName());
        model.setLine1(addressData.getLine1());
        model.setLine2(addressData.getLine2());
        model.setTown(addressData.getTown());
        model.setPostalCode(addressData.getPostalCode());
        model.setPhone1(addressData.getPhone());
        model.setShippingAddress(true);
        model.setVisibleInAddressBook(true);
        if (addressData.getCountry() != null) {
            model.setCountryIso(addressData.getCountry().getIsocode());
        }
        customerService.addAddress(currentCustomer, model);
    }

    @Override
    public void removeAddress(final String addressId) {
        if (currentCustomer == null) throw new IllegalStateException("No customer logged in");
        customerService.removeAddress(currentCustomer, addressId);
    }

    @Override
    public void setDefaultShippingAddress(final String addressId) {
        if (currentCustomer == null) throw new IllegalStateException("No customer logged in");
        customerService.setDefaultShippingAddress(currentCustomer, addressId);
    }

    @Override
    public boolean isAnonymousUser() {
        return currentCustomer == null;
    }

    private CustomerData convertToData(final CustomerModel model) {
        final CustomerData data = new CustomerData();
        data.setUid(model.getUid());
        data.setCustomerId(model.getCustomerId());
        data.setFirstName(model.getFirstName());
        data.setLastName(model.getLastName());
        data.setName(model.getName());
        data.setEmail(model.getEmail());
        data.setCurrency(model.getCurrencyIso());
        data.setLanguage(model.getLanguageIso());
        data.setLoyaltyPoints(model.getLoyaltyPoints() != null ? model.getLoyaltyPoints() : 0);
        data.setCustomerType(model.getCustomerType() != null ? model.getCustomerType().name() : null);
        return data;
    }

    private AddressData convertAddressToData(final AddressModel model) {
        final AddressData data = new AddressData();
        data.setId(model.getPk());
        data.setFirstName(model.getFirstName());
        data.setLastName(model.getLastName());
        data.setLine1(model.getLine1());
        data.setLine2(model.getLine2());
        data.setTown(model.getTown());
        data.setPostalCode(model.getPostalCode());
        data.setPhone(model.getPhone1());
        data.setShippingAddress(model.isShippingAddress());
        data.setBillingAddress(model.isBillingAddress());
        if (model.getCountryIso() != null) {
            final CountryData country = new CountryData();
            country.setIsocode(model.getCountryIso());
            data.setCountry(country);
        }
        return data;
    }
}
