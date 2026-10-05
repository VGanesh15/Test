package com.hybris.facades.data;

import java.util.List;

/**
 * Hybris OOTB: CustomerData DTO.
 * Mirrors de.hybris.platform.commercefacades.user.data.CustomerData.
 */
public class CustomerData {

    private String uid;
    private String customerId;
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String title;
    private String titleCode;
    private String currency;
    private String language;
    private boolean displayUid;
    private List<AddressData> addresses;
    private int loyaltyPoints;
    private String customerType;
    private boolean deactivationDate;

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getTitleCode() { return titleCode; }
    public void setTitleCode(String titleCode) { this.titleCode = titleCode; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public boolean isDisplayUid() { return displayUid; }
    public void setDisplayUid(boolean displayUid) { this.displayUid = displayUid; }
    public List<AddressData> getAddresses() { return addresses; }
    public void setAddresses(List<AddressData> addresses) { this.addresses = addresses; }
    public int getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(int loyaltyPoints) { this.loyaltyPoints = loyaltyPoints; }
    public String getCustomerType() { return customerType; }
    public void setCustomerType(String customerType) { this.customerType = customerType; }
}
