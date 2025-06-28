package com.unir.missiact1.msbookscatalogue.commons.enums;

public enum ApiErrorCode {
    UnknownError(1000),
    ValidationError(1001),
    Unauthorized(1002),
    Forbidden(1003),
    NotFound(1004),
    Conflict(1005),
    InternalServerError(1006),
    CustomError(2000),
    DatabaseError(2001),
    NetworkError(2002),
    Timeout(2003),
    DependencyFailure(2004), 
    InvalidArgument(2005);

    private final int code;
    ApiErrorCode(int code) { this.code = code; }
    public int getCode() { return code; }
}