package com.hybris.facades.data;

/**
 * Hybris OOTB: AddressData DTO.
 * Mirrors de.hybris.platform.commercefacades.user.data.AddressData.
 */
public class AddressData {

    private String id;
    private String title;
    private String firstName;
    private String lastName;
    private String companyName;
    private String line1;
    private String line2;
    private String town;
    private String postalCode;
    private String region;
    private CountryData country;
    private String phone;
    private String email;
    private boolean shippingAddress;
    private boolean billingAddress;
    private boolean defaultAddress;
    private boolean visibleInAddressBook;
    private String formattedAddress;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getLine1() { return line1; }
    public void setLine1(String line1) { this.line1 = line1; }
    public String getLine2() { return line2; }
    public void setLine2(String line2) { this.line2 = line2; }
    public String getTown() { return town; }
    public void setTown(String town) { this.town = town; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public CountryData getCountry() { return country; }
    public void setCountry(CountryData country) { this.country = country; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isShippingAddress() { return shippingAddress; }
    public void setShippingAddress(boolean shippingAddress) { this.shippingAddress = shippingAddress; }
    public boolean isBillingAddress() { return billingAddress; }
    public void setBillingAddress(boolean billingAddress) { this.billingAddress = billingAddress; }
    public boolean isDefaultAddress() { return defaultAddress; }
    public void setDefaultAddress(boolean defaultAddress) { this.defaultAddress = defaultAddress; }
    public boolean isVisibleInAddressBook() { return visibleInAddressBook; }
    public void setVisibleInAddressBook(boolean visibleInAddressBook) { this.visibleInAddressBook = visibleInAddressBook; }
    public String getFormattedAddress() { return formattedAddress; }
    public void setFormattedAddress(String formattedAddress) { this.formattedAddress = formattedAddress; }
}
