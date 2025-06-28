package com.unir.missiact1.msbookscatalogue.configuration;

import com.unir.missiact1.msbookscatalogue.commons.enums.*;
import com.unir.missiact1.msbookscatalogue.commons.exceptions.*;
import com.unir.missiact1.msbookscatalogue.commons.responses.*;
import org.slf4j.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<String>> handleCustom(CustomException ex) {
        log.warn("CustomException: {} - {}", ex.getErrorCode(), ex.getMessage());
        HttpStatus status = mapToHttpStatus(ex.getErrorCode());
        ErrorDetail detail = new ErrorDetail(ex.getErrorCode().name(), ex.getMessage());
        ApiResponse<String> rsp = ApiResponseHelper.createErrorResponse(
            ex.getMessage(),
            status.value(),
            detail
        );
        return ResponseEntity.status(status).body(rsp);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidation(MethodArgumentNotValidException ex) {
        log.warn("Validation failed: {}", ex.getMessage());
        List<ErrorDetail> errors = ex.getBindingResult()
                                     .getFieldErrors()
                                     .stream()
                                     .map(fe -> new ErrorDetail(fe.getField(), fe.getDefaultMessage()))
                                     .collect(Collectors.toList());

        // Usa directamente el helper de validación
        ApiResponse<String> rsp = ApiResponseHelper.createValidationErrorResponse(
            errors,
            "Error de validación de campos"
        );
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(rsp);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFound(NoHandlerFoundException ex) {
        ErrorDetail detail = new ErrorDetail(
            ApiErrorCode.NotFound.name(),
            "El endpoint no existe: " + ex.getRequestURL()
        );
        ApiResponse<String> rsp = ApiResponseHelper.createErrorResponse(
            "Recurso no encontrado",
            HttpStatus.NOT_FOUND.value(),
            detail
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rsp);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        ErrorDetail detail = new ErrorDetail(
            ApiErrorCode.Forbidden.name(),
            "Método HTTP no soportado: " + ex.getMethod()
        );
        ApiResponse<String> rsp = ApiResponseHelper.createErrorResponse(
            "Método no permitido",
            HttpStatus.METHOD_NOT_ALLOWED.value(),
            detail
        );
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(rsp);
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.warn("DataIntegrityViolationException: {}", ex.getMessage());
        String rootMsg = ex.getMostSpecificCause() != null
            ? ex.getMostSpecificCause().getMessage()
            : ex.getMessage();

        ErrorDetail detail = new ErrorDetail(
            ApiErrorCode.Conflict.name(),
            rootMsg
        );

        ApiResponse<String> rsp = ApiResponseHelper.createErrorResponse(
            "Violación de integridad de datos",
            HttpStatus.CONFLICT.value(),
            detail
        );
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(rsp);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneral(Exception ex) {
        log.error("Unhandled exception", ex);
        ErrorDetail detail = new ErrorDetail(
            ApiErrorCode.UnknownError.name(),
            "Se produjo un error inesperado."
        );
        ApiResponse<String> rsp = ApiResponseHelper.createErrorResponse(
            "Ocurrió un error interno del servidor",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            detail
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(rsp);
    }

    private HttpStatus mapToHttpStatus(ApiErrorCode code) {
        return switch (code) {
            case ValidationError      -> HttpStatus.BAD_REQUEST;
            case NotFound             -> HttpStatus.NOT_FOUND;
            case Unauthorized         -> HttpStatus.UNAUTHORIZED;
            case Forbidden            -> HttpStatus.FORBIDDEN;
            case Conflict             -> HttpStatus.CONFLICT;
            case InternalServerError,
                 UnknownError         -> HttpStatus.INTERNAL_SERVER_ERROR;
            default                   -> HttpStatus.BAD_REQUEST;
        };
    }
}