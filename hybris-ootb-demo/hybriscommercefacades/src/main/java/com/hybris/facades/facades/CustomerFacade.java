package com.hybris.facades.facades;

import com.hybris.facades.data.AddressData;
import com.hybris.facades.data.CustomerData;

import java.util.List;

/**
 * Hybris OOTB: CustomerFacade interface.
 * Mirrors de.hybris.platform.commercefacades.customer.CustomerFacade.
 */
public interface CustomerFacade {

    CustomerData getCurrentCustomer();

    void register(String email, String firstName, String lastName, String password);

    void login(String email, String password);

    void logout();

    void updateProfile(CustomerData customerData);

    void changePassword(String currentPassword, String newPassword);

    List<AddressData> getAddressBook();

    void addAddress(AddressData addressData);

    void removeAddress(String addressId);

    void setDefaultShippingAddress(String addressId);

    boolean isAnonymousUser();
}
