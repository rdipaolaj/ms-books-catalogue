package com.unir.missiact1.msbookscatalogue.commons.exceptions;

import com.unir.missiact1.msbookscatalogue.commons.enums.ApiErrorCode;

public class CustomException extends RuntimeException {
    private final ApiErrorCode errorCode;

    public CustomException(String message, ApiErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApiErrorCode getErrorCode() {
        return errorCode;
    }
}