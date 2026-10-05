package com.hybris.core.interceptor;

/**
 * Hybris OOTB: InterceptorContext stub.
 * Mirrors de.hybris.platform.servicelayer.interceptor.InterceptorContext.
 * Provides context information to interceptors about the current save operation.
 */
public class InterceptorContext {

    private final boolean isNew;
    private final boolean isModified;
    private final String operation;

    public InterceptorContext(final boolean isNew, final boolean isModified, final String operation) {
        this.isNew = isNew;
        this.isModified = isModified;
        this.operation = operation;
    }

    public boolean isNew() {
        return isNew;
    }

    public boolean isModified() {
        return isModified;
    }

    public String getOperation() {
        return operation;
    }
}
