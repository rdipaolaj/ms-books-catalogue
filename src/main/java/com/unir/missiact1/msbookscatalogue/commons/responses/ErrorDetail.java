package com.unir.missiact1.msbookscatalogue.commons.responses;

/**
 * Detalle de cada error individual en la respuesta.
 */
public class ErrorDetail {

    /** Código identificador del error. */
    private String code = "";

    /** Descripción o mensaje del error. */
    private String description = "";

    public ErrorDetail() { }

    public ErrorDetail(String code, String description) {
        this.code = code != null ? code : "";
        this.description = description != null ? description : "";
    }

    // --- Getters & Setters ---

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code != null ? code : ""; }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description != null ? description : "";
    }
}