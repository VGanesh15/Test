package com.hybris.core.interceptor;

import com.hybris.core.model.OrderModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Hybris OOTB: Order PrepareInterceptor.
 * Mirrors de.hybris.platform.order.interceptors.OrderPrepareInterceptor.
 *
 * PrepareInterceptors fire BEFORE save, used to auto-populate attributes.
 * This one auto-generates the order GUID and sets the placement timestamp.
 *
 * In Hybris, PrepareInterceptors are the counterpart of JPA @PrePersist/@PreUpdate.
 * They run within the ModelService.save() pipeline:
 * PrepareInterceptor → ValidateInterceptor → JPA persist → LoadInterceptor
 */
@Component("orderPrepareInterceptor")
public class OrderPrepareInterceptor {

    public void onPrepare(final OrderModel order, final InterceptorContext context) throws InterceptorException {
        if (context.isNew()) {
            if (order.getGuid() == null || order.getGuid().isEmpty()) {
                order.setGuid(UUID.randomUUID().toString());
            }
            if (order.getPlaced() == null) {
                order.setPlaced(LocalDateTime.now());
            }
            if (order.getStatus() == null) {
                order.setStatus(OrderModel.OrderStatus.CREATED);
            }
        }
    }
}
