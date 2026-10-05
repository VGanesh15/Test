package com.hybris.core.model;

import jakarta.persistence.*;

/**
 * Hybris OOTB: Address item type.
 * Corresponds to items.xml <itemtype code="Address" extends="GenericItem">.
 * Used for both shipping and billing addresses, associated with CustomerModel.
 */
@Entity
@Table(name = "address")
public class AddressModel extends ItemModel {

    @Column(name = "title")
    private String title;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "line1")
    private String line1;

    @Column(name = "line2")
    private String line2;

    @Column(name = "town")
    private String town;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "region")
    private String region;

    @Column(name = "country_iso")
    private String countryIso;

    @Column(name = "phone1")
    private String phone1;

    @Column(name = "phone2")
    private String phone2;

    @Column(name = "email")
    private String email;

    @Column(name = "shipping_address")
    private boolean shippingAddress;

    @Column(name = "billing_address")
    private boolean billingAddress;

    @Column(name = "visib_address")
    private boolean visibleInAddressBook = true;

    @Column(name = "duplicate_address")
    private boolean duplicate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_pk")
    private CustomerModel user;

    public AddressModel() {
        setTypeCode("Address");
        setItemType("Address");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountryIso() {
        return countryIso;
    }

    public void setCountryIso(String countryIso) {
        this.countryIso = countryIso;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(boolean shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public boolean isBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(boolean billingAddress) {
        this.billingAddress = billingAddress;
    }

    public boolean isVisibleInAddressBook() {
        return visibleInAddressBook;
    }

    public void setVisibleInAddressBook(boolean visibleInAddressBook) {
        this.visibleInAddressBook = visibleInAddressBook;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

    public CustomerModel getUser() {
        return user;
    }

    public void setUser(CustomerModel user) {
        this.user = user;
    }
}
