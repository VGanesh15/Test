package com.hybris.core.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Hybris OOTB: Customer item type.
 * Corresponds to items.xml <itemtype code="Customer" extends="User">.
 * Extends the base User type with customer-specific attributes like loyalty points,
 * customer type, and session cart management.
 */
@Entity
@Table(name = "customer", indexes = {
    @Index(name = "idx_customer_uid", columnList = "uid", unique = true),
    @Index(name = "idx_customer_email", columnList = "email")
})
public class CustomerModel extends ItemModel {

    public enum CustomerType {
        GUEST, REGISTERED, B2B_CUSTOMER, B2B_ADMIN
    }

    @Column(name = "uid", unique = true, nullable = false)
    private String uid;

    @Column(name = "name")
    private String name;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "encoded_password")
    private String encodedPassword;

    @Column(name = "title")
    private String title;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "language_iso")
    private String languageIso;

    @Column(name = "currency_iso")
    private String currencyIso;

    @Column(name = "default_ship_address_pk")
    private String defaultShipAddressPk;

    @Column(name = "default_pay_address_pk")
    private String defaultPayAddressPk;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints = 0;

    @Column(name = "session_cart_pk")
    private String sessionCartPk;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type")
    private CustomerType customerType = CustomerType.REGISTERED;

    @Column(name = "active_bassite")
    private String activeBaseSite;

    @Column(name = "customer_id")
    private String customerId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AddressModel> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartModel> carts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderModel> orders = new ArrayList<>();

    public CustomerModel() {
        setTypeCode("Customer");
        setItemType("Customer");
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLanguageIso() {
        return languageIso;
    }

    public void setLanguageIso(String languageIso) {
        this.languageIso = languageIso;
    }

    public String getCurrencyIso() {
        return currencyIso;
    }

    public void setCurrencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
    }

    public String getDefaultShipAddressPk() {
        return defaultShipAddressPk;
    }

    public void setDefaultShipAddressPk(String defaultShipAddressPk) {
        this.defaultShipAddressPk = defaultShipAddressPk;
    }

    public String getDefaultPayAddressPk() {
        return defaultPayAddressPk;
    }

    public void setDefaultPayAddressPk(String defaultPayAddressPk) {
        this.defaultPayAddressPk = defaultPayAddressPk;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getSessionCartPk() {
        return sessionCartPk;
    }

    public void setSessionCartPk(String sessionCartPk) {
        this.sessionCartPk = sessionCartPk;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String getActiveBaseSite() {
        return activeBaseSite;
    }

    public void setActiveBaseSite(String activeBaseSite) {
        this.activeBaseSite = activeBaseSite;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<AddressModel> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressModel> addresses) {
        this.addresses = addresses;
    }

    public List<CartModel> getCarts() {
        return carts;
    }

    public void setCarts(List<CartModel> carts) {
        this.carts = carts;
    }

    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }
}
