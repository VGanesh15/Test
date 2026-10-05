package com.hybris.core.interceptor;

/**
 * Hybris OOTB: InterceptorException.
 * Mirrors de.hybris.platform.servicelayer.interceptor.InterceptorException.
 * Thrown by interceptors to abort a save operation with a user-readable message.
 */
public class InterceptorException extends RuntimeException {

    private final Object interceptor;
    private final String reason;

    public InterceptorException(final String reason, final Object interceptor) {
        super(reason);
        this.interceptor = interceptor;
        this.reason = reason;
    }

    public InterceptorException(final String reason) {
        super(reason);
        this.interceptor = null;
        this.reason = reason;
    }

    public Object getInterceptor() {
        return interceptor;
    }

    public String getReason() {
        return reason;
    }
}
