package com.hybris.core.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base item model - mirrors SAP Hybris GenericItem / ItemModel.
 * All persistent items in Hybris extend this base type.
 * Maps to the hybris items.xml <itemtype> root hierarchy.
 */
@MappedSuperclass
public abstract class ItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk", updatable = false, nullable = false)
    private String pk;

    @Column(name = "created_ts", updatable = false)
    private LocalDateTime createdTs;

    @Column(name = "modified_ts")
    private LocalDateTime modifiedTs;

    @Column(name = "type_code")
    private String typeCode;

    @Version
    @Column(name = "version")
    private Long version;

    @Column(name = "item_type")
    private String itemType;

    @PrePersist
    protected void onCreate() {
        createdTs = LocalDateTime.now();
        modifiedTs = LocalDateTime.now();
        if (pk == null) {
            pk = UUID.randomUUID().toString();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedTs = LocalDateTime.now();
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    public LocalDateTime getModifiedTs() {
        return modifiedTs;
    }

    public void setModifiedTs(LocalDateTime modifiedTs) {
        this.modifiedTs = modifiedTs;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[pk=" + pk + "]";
    }
}
