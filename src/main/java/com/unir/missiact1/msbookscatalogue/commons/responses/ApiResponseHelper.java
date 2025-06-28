package com.unir.missiact1.msbookscatalogue.commons.responses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Métodos auxiliares para construir ApiResponse de forma concisa.
 */
public final class ApiResponseHelper {

    private ApiResponseHelper() { }

    /**
     * Crea una respuesta exitosa con datos.
     */
    public static <T> ApiResponse<T> createSuccessResponse(T data) {
        return createSuccessResponse(data, "Operation completed successfully.");
    }

    /**
     * Crea una respuesta exitosa con datos y mensaje personalizado.
     */
    public static <T> ApiResponse<T> createSuccessResponse(T data, String message) {
        ApiResponse<T> rsp = new ApiResponse<>();
        rsp.setSuccess(true);
        rsp.setStatusCode(200);
        rsp.setMessage(message != null ? message : "Operation completed successfully.");
        rsp.setData(data);
        // El resto de campos (transactionId, timestamp, errors, metadata) se inicializan por defecto
        return rsp;
    }

    /**
     * Crea una respuesta exitosa sin datos (Data = empty string).
     */
    public static ApiResponse<String> createSuccessResponse() {
        return createSuccessResponse("", "Operation completed successfully.");
    }

    /**
     * Crea una respuesta de error genérico con listado de errores.
     */
    public static <T> ApiResponse<T> createErrorResponse(
            String message,
            int statusCode,
            List<ErrorDetail> errors
    ) {
        ApiResponse<T> rsp = new ApiResponse<>();
        rsp.setSuccess(false);
        rsp.setStatusCode(statusCode);
        rsp.setMessage(message);
        rsp.setData(null);
        rsp.setErrors(errors != null ? errors : new ArrayList<>());
        return rsp;
    }

    /**
     * Crea una respuesta de error genérico sin lista previa (crea lista vacía).
     */
    public static <T> ApiResponse<T> createErrorResponse(
            String message,
            int statusCode
    ) {
        return createErrorResponse(message, statusCode, Collections.emptyList());
    }

    /**
     * Crea una respuesta de error con detalle de ErrorDetail varargs.
     */
    public static ApiResponse<String> createErrorResponse(
            String message,
            int statusCode,
            ErrorDetail... errorDetails
    ) {
        List<ErrorDetail> list = Arrays.stream(errorDetails).collect(Collectors.toList());
        return createErrorResponse(message, statusCode, list);
    }

    /**
     * Crea una respuesta totalmente personalizada.
     */
    public static <T> ApiResponse<T> createCustomResponse(
            boolean success,
            int statusCode,
            String message,
            T data,
            List<ErrorDetail> errors,
            Map<String,Object> metadata
    ) {
        ApiResponse<T> rsp = new ApiResponse<>();
        rsp.setSuccess(success);
        rsp.setStatusCode(statusCode);
        rsp.setMessage(message);
        rsp.setData(data);
        rsp.setErrors(errors != null ? errors : new ArrayList<>());
        rsp.setMetadata(metadata != null ? metadata : new HashMap<>());
        return rsp;
    }

    /**
     * Crea una respuesta de validación fallida (400) con lista de errores.
     */
    public static ApiResponse<String> createValidationErrorResponse(
            List<ErrorDetail> validationErrors
    ) {
        return createValidationErrorResponse(validationErrors, "Validation failed.");
    }

    /**
     * Crea una respuesta de validación fallida (400) genérica.
     */
    public static ApiResponse<String> createValidationErrorResponse(
            List<ErrorDetail> validationErrors,
            String message
    ) {
        ApiResponse<String> rsp = new ApiResponse<>();
        rsp.setSuccess(false);
        rsp.setStatusCode(400);
        rsp.setMessage(message);
        rsp.setData("");
        rsp.setErrors(validationErrors != null ? validationErrors : new ArrayList<>());
        return rsp;
    }

    /**
     * Crea una respuesta de validación fallida (400) genérica para tipo T.
     */
    public static <T> ApiResponse<T> createValidationErrorResponseForType(
            List<ErrorDetail> validationErrors,
            String message
    ) {
        ApiResponse<T> rsp = new ApiResponse<>();
        rsp.setSuccess(false);
        rsp.setStatusCode(400);
        rsp.setMessage(message);
        rsp.setData(null);
        rsp.setErrors(validationErrors != null ? validationErrors : new ArrayList<>());
        return rsp;
    }
}