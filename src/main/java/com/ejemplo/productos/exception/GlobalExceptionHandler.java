package com.ejemplo.productos.exception;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<ApiError> productoNoEncontrado(
            ProductoNoEncontradoException ex,
            HttpServletRequest request) {

        return construir(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validacion(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errores = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.putIfAbsent(
                        error.getField(),
                        error.getDefaultMessage()
                )
        );

        return construir(
                HttpStatus.BAD_REQUEST,
                errores,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> jsonInvalido(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        return construir(
                HttpStatus.BAD_REQUEST,
                "El contenido JSON enviado no es válido",
                request.getRequestURI()
        );
    }

    @ExceptionHandler(FirebaseOperationException.class)
    public ResponseEntity<ApiError> errorFirebase(
            FirebaseOperationException ex,
            HttpServletRequest request) {

        return construir(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    private ResponseEntity<ApiError> construir(
            HttpStatus estado,
            Object detalle,
            String ruta) {

        ApiError error = new ApiError(
                Instant.now(),
                estado.value(),
                estado.getReasonPhrase(),
                detalle,
                ruta
        );

        return ResponseEntity.status(estado).body(error);
    }
}