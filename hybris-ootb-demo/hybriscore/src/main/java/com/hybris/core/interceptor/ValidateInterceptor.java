package com.hybris.core.interceptor;

/**
 * Hybris OOTB: ValidateInterceptor marker interface.
 * Mirrors de.hybris.platform.servicelayer.interceptor.ValidateInterceptor.
 *
 * ValidateInterceptors fire during model.save() validation phase, before persist.
 * They are registered in Spring XML using the interceptor mapping:
 *
 * <bean id="productValidateInterceptorMapping"
 *       class="de.hybris.platform.servicelayer.interceptor.impl.InterceptorMapping">
 *   <property name="interceptor" ref="productValidateInterceptor"/>
 *   <property name="typeCode" value="Product"/>
 * </bean>
 */
public interface ValidateInterceptor<T> {

    /**
     * Called during model save validation. Throw InterceptorException to abort.
     */
    void onValidate(T model, InterceptorContext context) throws InterceptorException;
}
