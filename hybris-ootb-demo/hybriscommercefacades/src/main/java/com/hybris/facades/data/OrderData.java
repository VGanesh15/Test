package com.hybris.facades.data;

import java.time.LocalDateTime;

/**
 * Hybris OOTB: OrderData DTO.
 * Mirrors de.hybris.platform.commercefacades.order.data.OrderData.
 */
public class OrderData extends AbstractOrderData {

    private String status;
    private String statusDisplay;
    private LocalDateTime created;
    private boolean guestCustomer;
    private boolean returnable;
    private boolean cancellable;
    private String guid;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getStatusDisplay() { return statusDisplay; }
    public void setStatusDisplay(String statusDisplay) { this.statusDisplay = statusDisplay; }
    public LocalDateTime getCreated() { return created; }
    public void setCreated(LocalDateTime created) { this.created = created; }
    public boolean isGuestCustomer() { return guestCustomer; }
    public void setGuestCustomer(boolean guestCustomer) { this.guestCustomer = guestCustomer; }
    public boolean isReturnable() { return returnable; }
    public void setReturnable(boolean returnable) { this.returnable = returnable; }
    public boolean isCancellable() { return cancellable; }
    public void setCancellable(boolean cancellable) { this.cancellable = cancellable; }
    public String getGuid() { return guid; }
    public void setGuid(String guid) { this.guid = guid; }
}
