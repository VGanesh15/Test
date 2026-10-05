package com.hybris.occ.controllers;

import com.hybris.facades.facades.CustomerFacade;
import com.hybris.facades.data.AddressData;
import com.hybris.facades.data.CustomerData;
import com.hybris.occ.dto.RegisterRequestWsDTO;
import com.hybris.occ.dto.ResponseWsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Hybris OOTB: Users OCC REST Controller.
 * Mirrors de.hybris.platform.commercewebservices.core.v2.controller.UsersController.
 *
 * Maps to URL: /occ/v2/{baseSiteId}/users
 *
 * Customer management:
 * POST   /users                        - Register new customer
 * GET    /users/{userId}               - Get customer profile
 * PATCH  /users/{userId}               - Update profile
 * GET    /users/{userId}/addresses     - Get address book
 * POST   /users/{userId}/addresses     - Add new address
 * DELETE /users/{userId}/addresses/{id} - Remove address
 */
@RestController
@RequestMapping("/occ/v2/{baseSiteId}/users")
@Tag(name = "Users", description = "SAP Commerce OCC v2 Users API")
public class UsersController extends BaseController {

    private final CustomerFacade customerFacade;

    @Autowired
    public UsersController(final CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    @PostMapping
    @Operation(summary = "Register a new customer")
    public ResponseEntity<ResponseWsDTO<CustomerData>> register(
            @PathVariable final String baseSiteId,
            @RequestBody final RegisterRequestWsDTO request) {

        LOG.info("OCC POST /users: register email={}", request.getEmail());
        customerFacade.register(request.getEmail(), request.getFirstName(),
            request.getLastName(), request.getPassword());
        final CustomerData customer = customerFacade.getCurrentCustomer();
        return created(customer);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get customer profile")
    public ResponseEntity<ResponseWsDTO<CustomerData>> getCustomer(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId) {

        LOG.info("OCC GET /users/{}", userId);
        final CustomerData customer = customerFacade.getCurrentCustomer();
        if (customer == null) {
            throw new java.util.NoSuchElementException("Customer not found: " + userId);
        }
        return success(customer);
    }

    @PatchMapping("/{userId}")
    @Operation(summary = "Update customer profile")
    public ResponseEntity<ResponseWsDTO<Void>> updateCustomer(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId,
            @RequestBody final CustomerData customerData) {

        LOG.info("OCC PATCH /users/{}", userId);
        customerFacade.updateProfile(customerData);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/addresses")
    @Operation(summary = "Get customer address book")
    public ResponseEntity<ResponseWsDTO<List<AddressData>>> getAddresses(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId) {

        LOG.info("OCC GET /users/{}/addresses", userId);
        return success(customerFacade.getAddressBook());
    }

    @PostMapping("/{userId}/addresses")
    @Operation(summary = "Add a new address to the address book")
    public ResponseEntity<ResponseWsDTO<Void>> addAddress(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId,
            @RequestBody final AddressData addressData) {

        LOG.info("OCC POST /users/{}/addresses", userId);
        customerFacade.addAddress(addressData);
        return ResponseEntity.ok(ResponseWsDTO.success(null));
    }

    @DeleteMapping("/{userId}/addresses/{addressId}")
    @Operation(summary = "Remove an address from the address book")
    public ResponseEntity<ResponseWsDTO<Void>> removeAddress(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId,
            @PathVariable final String addressId) {

        LOG.info("OCC DELETE /users/{}/addresses/{}", userId, addressId);
        customerFacade.removeAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/password")
    @Operation(summary = "Change customer password")
    public ResponseEntity<ResponseWsDTO<Void>> changePassword(
            @PathVariable final String baseSiteId,
            @PathVariable final String userId,
            @RequestParam final String currentPassword,
            @RequestParam final String newPassword) {

        LOG.info("OCC POST /users/{}/password", userId);
        customerFacade.changePassword(currentPassword, newPassword);
        return ResponseEntity.ok(ResponseWsDTO.success(null));
    }
}
