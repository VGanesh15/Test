package com.hybris.core.service;

import com.hybris.core.model.AddressModel;
import com.hybris.core.model.CustomerModel;

import java.util.List;
import java.util.Optional;

/**
 * Hybris OOTB: CustomerService / CustomerAccountService interface.
 * Mirrors de.hybris.platform.commerceservices.customer.CustomerAccountService.
 *
 * Manages customer registration, login, address book, and guest checkout flow.
 */
public interface CustomerService {

    CustomerModel registerCustomer(String uid, String email, String firstName, String lastName, String rawPassword);

    Optional<CustomerModel> getCustomerByUid(String uid);

    Optional<CustomerModel> getCustomerByEmail(String email);

    CustomerModel updateCustomer(CustomerModel customer);

    void changePassword(String uid, String currentPassword, String newPassword);

    void sendPasswordResetEmail(String email);

    CustomerModel resetPassword(String token, String newPassword);

    AddressModel addAddress(CustomerModel customer, AddressModel address);

    void removeAddress(CustomerModel customer, String addressPk);

    List<AddressModel> getAddressesForCustomer(CustomerModel customer);

    CustomerModel setDefaultShippingAddress(CustomerModel customer, String addressPk);

    CustomerModel setDefaultBillingAddress(CustomerModel customer, String addressPk);

    CustomerModel createGuestCustomer(String email);

    void mergeGuestCart(CustomerModel guestCustomer, CustomerModel registeredCustomer);

    boolean isCustomerRegistered(String email);
}
