package com.hybris.occ.controllers;

import com.hybris.facades.facades.CheckoutFacade;
import com.hybris.core.service.OrderService;
import com.hybris.facades.converters.OrderConverter;
import com.hybris.facades.data.OrderData;
import com.hybris.occ.dto.ResponseWsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Hybris OOTB: Orders OCC REST Controller.
 * Mirrors de.hybris.platform.commercewebservices.core.v2.controller.OrdersController.
 *
 * Maps to URL: /occ/v2/{baseSiteId}/users/{userId}/orders
 */
@RestController
@RequestMapping("/occ/v2/{baseSiteId}/users/{userId}/orders")
@Tag(name = "Orders", description = "SAP Commerce OCC v2 Orders API")
public class OrdersController extends BaseController {

    private final CheckoutFacade checkoutFacade;
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @Autowired
    public OrdersController(final CheckoutFacade checkoutFacade,
                             final OrderService orderService,
                             final OrderConverter orderConverter) {
        this.checkoutFacade = checkoutFacade;
        this.orderService = orderService;
        this.orderConverter = orderConverter;
    }

    @PostMapping
    @Operation(summary = "Place an order from the current cart")
    public ResponseEntity<ResponseWsDTO<OrderData>> placeOrder(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId,
            @RequestParam(required = false) final String cartId,
            @RequestParam(required = false, defaultValue = "false") final boolean termsChecked) {

        LOG.info("OCC POST /orders: baseSite={}, user={}, termsChecked={}", baseSiteId, userId, termsChecked);
        final OrderData order = checkoutFacade.placeOrder();
        return created(order);
    }

    @GetMapping
    @Operation(summary = "Get order history for a customer")
    public ResponseEntity<ResponseWsDTO<List<OrderData>>> getOrderHistory(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId,
            @RequestParam(defaultValue = "0") final int currentPage,
            @RequestParam(defaultValue = "10") final int pageSize,
            @RequestParam(required = false) final String statuses) {

        LOG.info("OCC GET /orders: baseSite={}, user={}", baseSiteId, userId);
        final List<OrderData> orders = orderService.getOrdersForCustomer(userId)
            .stream()
            .map(orderConverter::convert)
            .collect(Collectors.toList());
        return success(orders);
    }

    @GetMapping("/{orderCode}")
    @Operation(summary = "Get order details by order code")
    public ResponseEntity<ResponseWsDTO<OrderData>> getOrder(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId,
            @PathVariable final String orderCode) {

        LOG.info("OCC GET /orders/{}: baseSite={}", orderCode, baseSiteId);
        final OrderData order = orderService.getOrderByCode(orderCode)
            .map(orderConverter::convert)
            .orElseThrow(() -> new java.util.NoSuchElementException("Order not found: " + orderCode));
        return success(order);
    }

    @PostMapping("/{orderCode}/cancellation")
    @Operation(summary = "Cancel an order")
    public ResponseEntity<ResponseWsDTO<Void>> cancelOrder(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId,
            @PathVariable final String orderCode,
            @RequestParam(required = false, defaultValue = "Customer requested cancellation") final String reason) {

        LOG.info("OCC POST /orders/{}/cancellation: reason={}", orderCode, reason);
        orderService.cancelOrder(orderCode, reason);
        return ResponseEntity.ok(ResponseWsDTO.success(null));
    }
}
