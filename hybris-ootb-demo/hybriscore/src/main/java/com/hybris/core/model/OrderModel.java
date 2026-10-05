package com.hybris.core.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Hybris OOTB: Order item type.
 * Corresponds to items.xml <itemtype code="Order" extends="AbstractOrder">.
 * Represents a placed order, created from a Cart via the PlaceOrderStrategy.
 * Manages order lifecycle through status transitions.
 */
@Entity
@Table(name = "orders", indexes = {
    @Index(name = "idx_order_code", columnList = "code", unique = true),
    @Index(name = "idx_order_user", columnList = "user_pk"),
    @Index(name = "idx_order_status", columnList = "status")
})
public class OrderModel extends ItemModel {

    public enum OrderStatus {
        CREATED, ON_VALIDATION, FRAUD_CHECKED, PAYMENT_AUTHORIZED,
        PAYMENT_CAPTURED, READY, PAYMENT_NOT_AUTHORIZED, PAYMENT_ERROR,
        PROCESSING_ERROR, CANCELLING, CANCELLED, COMPLETED,
        VERIFIED, SUSPENDED, B2B_PROCESSING_ERROR
    }

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "guid")
    private String guid;

    @Column(name = "currency_iso")
    private String currencyIso;

    @Column(name = "net")
    private boolean net;

    @Column(name = "site")
    private String site;

    @Column(name = "store")
    private String store;

    @Column(name = "subtotal", precision = 19, scale = 5)
    private BigDecimal subTotal = BigDecimal.ZERO;

    @Column(name = "total_price", precision = 19, scale = 5)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Column(name = "total_tax", precision = 19, scale = 5)
    private BigDecimal totalTax = BigDecimal.ZERO;

    @Column(name = "total_discounts", precision = 19, scale = 5)
    private BigDecimal totalDiscounts = BigDecimal.ZERO;

    @Column(name = "delivery_cost", precision = 19, scale = 5)
    private BigDecimal deliveryCost = BigDecimal.ZERO;

    @Column(name = "payment_cost", precision = 19, scale = 5)
    private BigDecimal paymentCost = BigDecimal.ZERO;

    @Column(name = "delivery_mode_code")
    private String deliveryModeCode;

    @Column(name = "payment_mode_code")
    private String paymentModeCode;

    @Column(name = "payment_transaction_id")
    private String paymentTransactionId;

    @Column(name = "fraudulent")
    private boolean fraudulent;

    @Column(name = "placed")
    private LocalDateTime placed;

    @Column(name = "work_flow_action_pk")
    private String workflowActionPk;

    @Column(name = "export_status")
    private String exportStatus;

    @Column(name = "original_version_pk")
    private String originalVersionPk;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status = OrderStatus.CREATED;

    @Column(name = "status_info", length = 500)
    private String statusInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk")
    private CustomerModel user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_pk")
    private AddressModel deliveryAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_address_pk")
    private AddressModel paymentAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("entry_number ASC")
    private List<OrderEntryModel> entries = new ArrayList<>();

    public OrderModel() {
        setTypeCode("Order");
        setItemType("Order");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCurrencyIso() {
        return currencyIso;
    }

    public void setCurrencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
    }

    public boolean isNet() {
        return net;
    }

    public void setNet(boolean net) {
        this.net = net;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getTotalDiscounts() {
        return totalDiscounts;
    }

    public void setTotalDiscounts(BigDecimal totalDiscounts) {
        this.totalDiscounts = totalDiscounts;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public BigDecimal getPaymentCost() {
        return paymentCost;
    }

    public void setPaymentCost(BigDecimal paymentCost) {
        this.paymentCost = paymentCost;
    }

    public String getDeliveryModeCode() {
        return deliveryModeCode;
    }

    public void setDeliveryModeCode(String deliveryModeCode) {
        this.deliveryModeCode = deliveryModeCode;
    }

    public String getPaymentModeCode() {
        return paymentModeCode;
    }

    public void setPaymentModeCode(String paymentModeCode) {
        this.paymentModeCode = paymentModeCode;
    }

    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }

    public boolean isFraudulent() {
        return fraudulent;
    }

    public void setFraudulent(boolean fraudulent) {
        this.fraudulent = fraudulent;
    }

    public LocalDateTime getPlaced() {
        return placed;
    }

    public void setPlaced(LocalDateTime placed) {
        this.placed = placed;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public CustomerModel getUser() {
        return user;
    }

    public void setUser(CustomerModel user) {
        this.user = user;
    }

    public AddressModel getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(AddressModel deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public AddressModel getPaymentAddress() {
        return paymentAddress;
    }

    public void setPaymentAddress(AddressModel paymentAddress) {
        this.paymentAddress = paymentAddress;
    }

    public List<OrderEntryModel> getEntries() {
        return entries;
    }

    public void setEntries(List<OrderEntryModel> entries) {
        this.entries = entries;
    }

    public String getExportStatus() { return exportStatus; }
    public void setExportStatus(String exportStatus) { this.exportStatus = exportStatus; }
    public String getWorkflowActionPk() { return workflowActionPk; }
    public void setWorkflowActionPk(String workflowActionPk) { this.workflowActionPk = workflowActionPk; }
    public String getOriginalVersionPk() { return originalVersionPk; }
    public void setOriginalVersionPk(String originalVersionPk) { this.originalVersionPk = originalVersionPk; }
    public String getPaymentTransId() { return paymentTransactionId; }
}
