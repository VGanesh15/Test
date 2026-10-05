package com.hybris.occ.controllers;

import com.hybris.facades.facades.CartFacade;
import com.hybris.facades.data.CartData;
import com.hybris.occ.dto.AddToCartRequestWsDTO;
import com.hybris.occ.dto.ResponseWsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Hybris OOTB: Carts OCC REST Controller.
 * Mirrors de.hybris.platform.commercewebservices.core.v2.controller.CartsController.
 *
 * Maps to URL: /occ/v2/{baseSiteId}/users/{userId}/carts
 *
 * Cart lifecycle in Hybris OCC:
 * POST   /carts                            - Create new cart
 * GET    /carts/{cartId}                   - Get cart details
 * DELETE /carts/{cartId}                   - Delete cart
 * POST   /carts/{cartId}/entries           - Add entry to cart
 * PUT    /carts/{cartId}/entries/{entryNum} - Update entry quantity
 * DELETE /carts/{cartId}/entries/{entryNum} - Remove entry
 * POST   /carts/{cartId}/promotions        - Apply promo code
 * DELETE /carts/{cartId}/promotions/{code} - Remove promo code
 */
@RestController
@RequestMapping("/occ/v2/{baseSiteId}/users/{userId}/carts")
@Tag(name = "Carts", description = "SAP Commerce OCC v2 Carts API")
public class CartsController extends BaseController {

    private final CartFacade cartFacade;

    @Autowired
    public CartsController(final CartFacade cartFacade) {
        this.cartFacade = cartFacade;
    }

    @PostMapping
    @Operation(summary = "Create a new cart for the user")
    public ResponseEntity<ResponseWsDTO<CartData>> createCart(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId) {

        LOG.info("OCC POST /carts: baseSite={}, user={}", baseSiteId, userId);
        final CartData cart = cartFacade.getSessionCart();
        return created(cart);
    }

    @GetMapping("/current")
    @Operation(summary = "Get the current session cart")
    public ResponseEntity<ResponseWsDTO<CartData>> getCurrentCart(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId) {

        LOG.info("OCC GET /carts/current: baseSite={}, user={}", baseSiteId, userId);
        final CartData cart = cartFacade.getSessionCart();
        return success(cart);
    }

    @PostMapping("/current/entries")
    @Operation(summary = "Add a product to the cart")
    public ResponseEntity<ResponseWsDTO<CartData>> addToCart(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId,
            @RequestBody final AddToCartRequestWsDTO request) {

        LOG.info("OCC POST /carts/current/entries: product={}, qty={}",
            request.getProductCode(), request.getQuantity());

        final CartData cart = cartFacade.addToCart(request.getProductCode(), request.getQuantity());
        return created(cart);
    }

    @PutMapping("/current/entries/{entryNumber}")
    @Operation(summary = "Update quantity of a cart entry")
    public ResponseEntity<ResponseWsDTO<CartData>> updateCartEntry(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId,
            @PathVariable final int entryNumber,
            @RequestBody final AddToCartRequestWsDTO request) {

        LOG.info("OCC PUT /carts/current/entries/{}: qty={}", entryNumber, request.getQuantity());
        final CartData cart = cartFacade.updateCartEntry(entryNumber, request.getQuantity());
        return success(cart);
    }

    @DeleteMapping("/current/entries/{entryNumber}")
    @Operation(summary = "Remove a cart entry")
    public ResponseEntity<ResponseWsDTO<CartData>> removeCartEntry(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId,
            @PathVariable final int entryNumber) {

        LOG.info("OCC DELETE /carts/current/entries/{}", entryNumber);
        final CartData cart = cartFacade.removeCartEntry(entryNumber);
        return success(cart);
    }

    @PostMapping("/current/promotions")
    @Operation(summary = "Apply a promotion voucher to the cart")
    public ResponseEntity<ResponseWsDTO<CartData>> applyPromoCode(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId,
            @RequestParam final String voucherId) {

        LOG.info("OCC POST /carts/current/promotions: voucher={}", voucherId);
        final CartData cart = cartFacade.applyPromoCode(voucherId);
        return success(cart);
    }

    @DeleteMapping("/current/promotions/{voucherId}")
    @Operation(summary = "Remove a promotion voucher from the cart")
    public ResponseEntity<ResponseWsDTO<Void>> removePromoCode(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId,
            @PathVariable final String voucherId) {

        LOG.info("OCC DELETE /carts/current/promotions/{}", voucherId);
        cartFacade.removePromoCode(voucherId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/current/deliveryaddress")
    @Operation(summary = "Set the delivery address on the cart")
    public ResponseEntity<ResponseWsDTO<CartData>> setDeliveryAddress(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId,
            @RequestParam final String addressId) {

        LOG.info("OCC POST /carts/current/deliveryaddress: addressId={}", addressId);
        final CartData cart = cartFacade.setDeliveryAddress(addressId);
        return success(cart);
    }

    @PostMapping("/current/deliverymode")
    @Operation(summary = "Set the delivery mode on the cart")
    public ResponseEntity<ResponseWsDTO<CartData>> setDeliveryMode(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId,
            @RequestParam final String deliveryModeId) {

        LOG.info("OCC POST /carts/current/deliverymode: mode={}", deliveryModeId);
        final CartData cart = cartFacade.setDeliveryMode(deliveryModeId);
        return success(cart);
    }
}
