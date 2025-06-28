package com.unir.missiact1.msbookscatalogue.commons.responses;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.unir.missiact1.msbookscatalogue.commons.helpers.DatetimeHelper;

/**
 * Contenedor genérico para respuestas de API.
 *
 * @param <T> Tipo de datos que devuelve la respuesta.
 */
public class ApiResponse<T> {

    /** Indica si la solicitud fue exitosa. */
    private boolean success;

    /** Código de estado HTTP de la respuesta. */
    private int statusCode;

    /** Mensaje informativo o de error relacionado con la solicitud. */
    private String message = "";

    /** Datos devueltos por la solicitud, si corresponde. */
    private T data;

    /** Identificador único de la transacción o solicitud. */
    private String transactionId = UUID.randomUUID().toString();

    /** Timestamp que indica cuándo se generó la respuesta. */
    private ZonedDateTime timestamp = DatetimeHelper.now();

    /** Lista de errores detallados, si existen. */
    private List<ErrorDetail> errors = new ArrayList<>();

    /** Metadatos adicionales que pueden ser relevantes para la respuesta. */
    private Map<String, Object> metadata = new HashMap<>();

    public ApiResponse() { }

    public ApiResponse(
            boolean success,
            int statusCode,
            String message,
            T data,
            String transactionId,
            List<ErrorDetail> errors,
            Map<String, Object> metadata
    ) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message != null ? message : "";
        this.data = data;
        this.transactionId = transactionId != null ? transactionId : UUID.randomUUID().toString();
        this.timestamp = DatetimeHelper.now();
        this.errors = errors != null ? errors : new ArrayList<>();
        this.metadata = metadata != null ? metadata : new HashMap<>();
    }

    // --- Getters & Setters ---

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message != null ? message : ""; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId != null ? transactionId : UUID.randomUUID().toString();
    }

    public ZonedDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp != null ? timestamp : DatetimeHelper.now();
    }

    public List<ErrorDetail> getErrors() { return errors; }
    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors != null ? errors : new ArrayList<>();
    }

    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata != null ? metadata : new HashMap<>();
    }
}


