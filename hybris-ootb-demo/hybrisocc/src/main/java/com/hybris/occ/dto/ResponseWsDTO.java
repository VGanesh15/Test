package com.hybris.occ.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Hybris OCC: Generic API response envelope.
 * Wraps all OCC API responses in a consistent structure.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWsDTO<T> {

    private boolean success;
    private T data;
    private String errorType;
    private String errorMessage;

    public static <T> ResponseWsDTO<T> success(final T data) {
        final ResponseWsDTO<T> response = new ResponseWsDTO<>();
        response.success = true;
        response.data = data;
        return response;
    }

    public static <T> ResponseWsDTO<T> error(final String errorType, final String message) {
        final ResponseWsDTO<T> response = new ResponseWsDTO<>();
        response.success = false;
        response.errorType = errorType;
        response.errorMessage = message;
        return response;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public String getErrorType() { return errorType; }
    public void setErrorType(String errorType) { this.errorType = errorType; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
