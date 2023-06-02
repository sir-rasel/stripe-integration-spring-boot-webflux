package org.sir.stripeintegration.core.shared;

import lombok.Data;

import java.time.Instant;

@Data
public class EntityAuditFields {
    public Instant createdOn;
    public Instant lastModifiedOn;

    public EntityAuditFields() {
        onCreate();
        onUpdate();
    }

    public void onCreate() {
        this.setCreatedOn(Instant.now());
        this.setLastModifiedOn(getCreatedOn());
    }

    public void onUpdate() {
        this.setLastModifiedOn(Instant.now());
    }
}
