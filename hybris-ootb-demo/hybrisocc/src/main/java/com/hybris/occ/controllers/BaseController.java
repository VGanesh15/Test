package com.hybris.occ.controllers;

import com.hybris.occ.dto.ErrorWsDTO;
import com.hybris.occ.dto.ResponseWsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

/**
 * Hybris OOTB: Base OCC (Omni-Commerce Connect) REST controller.
 * Mirrors de.hybris.platform.webservicescommons.controller.BaseController.
 *
 * OCC v2 API specification follows the SAP Commerce OCC REST API standard,
 * providing a uniform JSON API across all commerce operations.
 * Base URL pattern: /occ/v2/{baseSiteId}/
 *
 * Error responses follow the SAP Commerce error format with error type and reason.
 */
public abstract class BaseController {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    protected <T> ResponseEntity<ResponseWsDTO<T>> success(final T data) {
        return ResponseEntity.ok(ResponseWsDTO.success(data));
    }

    protected <T> ResponseEntity<ResponseWsDTO<T>> created(final T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWsDTO.success(data));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseWsDTO<Void>> handleNotFound(final NoSuchElementException ex) {
        LOG.warn("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ResponseWsDTO.error("not_found", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseWsDTO<Void>> handleBadRequest(final IllegalArgumentException ex) {
        LOG.warn("Bad request: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ResponseWsDTO.error("invalid_argument", ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ResponseWsDTO<Void>> handleConflict(final IllegalStateException ex) {
        LOG.warn("Business rule violation: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ResponseWsDTO.error("business_error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWsDTO<Void>> handleGenericError(final Exception ex) {
        LOG.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseWsDTO.error("internal_error", "An unexpected error occurred"));
    }
}
