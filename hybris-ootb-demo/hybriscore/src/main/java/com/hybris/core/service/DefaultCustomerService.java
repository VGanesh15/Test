package com.hybris.core.service;

import com.hybris.core.dao.CustomerDao;
import com.hybris.core.model.AddressModel;
import com.hybris.core.model.CustomerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Hybris OOTB: DefaultCustomerService / DefaultCustomerAccountService.
 * Mirrors de.hybris.platform.commerceservices.customer.impl.DefaultCustomerAccountService.
 *
 * Guest checkout flow: createGuestCustomer → placeOrder → mergeGuestCart
 * Registration flow: registerCustomer → verifyEmail → login → cart restore
 */
@Service("customerService")
@Transactional
public class DefaultCustomerService implements CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultCustomerService.class);

    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultCustomerService(final CustomerDao customerDao, final PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CustomerModel registerCustomer(final String uid, final String email,
                                          final String firstName, final String lastName,
                                          final String rawPassword) {
        Assert.hasText(uid, "UID must not be blank");
        Assert.hasText(email, "Email must not be blank");
        Assert.hasText(rawPassword, "Password must not be blank");

        if (customerDao.existsByEmail(email)) {
            throw new IllegalStateException("Customer with email already exists: " + email);
        }

        final CustomerModel customer = new CustomerModel();
        customer.setUid(uid);
        customer.setEmail(email);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setName(firstName + " " + lastName);
        customer.setEncodedPassword(passwordEncoder.encode(rawPassword));
        customer.setCustomerType(CustomerModel.CustomerType.REGISTERED);
        customer.setCurrencyIso("USD");
        customer.setLanguageIso("en");
        customer.setActiveBaseSite("electronics");
        customer.setCustomerId(UUID.randomUUID().toString());
        customer.setLoyaltyPoints(0);

        LOG.info("Registering new customer: {}", email);
        return customerDao.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerModel> getCustomerByUid(final String uid) {
        return customerDao.findByUid(uid);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerModel> getCustomerByEmail(final String email) {
        return customerDao.findByEmail(email);
    }

    @Override
    public CustomerModel updateCustomer(final CustomerModel customer) {
        Assert.notNull(customer, "Customer must not be null");
        LOG.info("Updating customer: {}", customer.getUid());
        return customerDao.save(customer);
    }

    @Override
    public void changePassword(final String uid, final String currentPassword, final String newPassword) {
        Assert.hasText(uid, "UID must not be blank");
        Assert.hasText(newPassword, "New password must not be blank");
        final CustomerModel customer = customerDao.findByUid(uid)
            .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + uid));
        if (currentPassword != null && !passwordEncoder.matches(currentPassword, customer.getEncodedPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        customer.setEncodedPassword(passwordEncoder.encode(newPassword));
        customerDao.save(customer);
        LOG.info("Password changed for customer: {}", uid);
    }

    @Override
    public void sendPasswordResetEmail(final String email) {
        Assert.hasText(email, "Email must not be blank");
        final Optional<CustomerModel> customer = customerDao.findByEmail(email);
        if (customer.isPresent()) {
            LOG.info("Password reset email would be sent to: {}", email);
        } else {
            LOG.warn("Password reset requested for non-existent email: {}", email);
        }
    }

    @Override
    public CustomerModel resetPassword(final String token, final String newPassword) {
        throw new UnsupportedOperationException("Token-based reset requires TokenService");
    }

    @Override
    public AddressModel addAddress(final CustomerModel customer, final AddressModel address) {
        Assert.notNull(customer, "Customer must not be null");
        Assert.notNull(address, "Address must not be null");
        address.setUser(customer);
        customer.getAddresses().add(address);
        customerDao.save(customer);
        LOG.info("Address added for customer: {}", customer.getUid());
        return address;
    }

    @Override
    public void removeAddress(final CustomerModel customer, final String addressPk) {
        Assert.notNull(customer, "Customer must not be null");
        Assert.hasText(addressPk, "Address PK must not be blank");
        customer.getAddresses().removeIf(a -> addressPk.equals(a.getPk()));
        customerDao.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressModel> getAddressesForCustomer(final CustomerModel customer) {
        Assert.notNull(customer, "Customer must not be null");
        return customer.getAddresses();
    }

    @Override
    public CustomerModel setDefaultShippingAddress(final CustomerModel customer, final String addressPk) {
        customer.setDefaultShipAddressPk(addressPk);
        return customerDao.save(customer);
    }

    @Override
    public CustomerModel setDefaultBillingAddress(final CustomerModel customer, final String addressPk) {
        customer.setDefaultPayAddressPk(addressPk);
        return customerDao.save(customer);
    }

    @Override
    public CustomerModel createGuestCustomer(final String email) {
        Assert.hasText(email, "Email must not be blank");
        final CustomerModel guest = new CustomerModel();
        guest.setUid("guest|" + UUID.randomUUID());
        guest.setEmail(email);
        guest.setCustomerType(CustomerModel.CustomerType.GUEST);
        guest.setCustomerId(UUID.randomUUID().toString());
        guest.setCurrencyIso("USD");
        guest.setActiveBaseSite("electronics");
        LOG.info("Created guest customer for email: {}", email);
        return customerDao.save(guest);
    }

    @Override
    public void mergeGuestCart(final CustomerModel guestCustomer, final CustomerModel registeredCustomer) {
        LOG.info("Merging guest cart from {} to {}", guestCustomer.getUid(), registeredCustomer.getUid());
        registeredCustomer.setSessionCartPk(guestCustomer.getSessionCartPk());
        customerDao.save(registeredCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isCustomerRegistered(final String email) {
        return customerDao.existsByEmail(email);
    }
}
