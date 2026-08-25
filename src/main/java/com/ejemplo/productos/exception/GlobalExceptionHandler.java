package com.ejemplo.productos.exception;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<ApiError> manejarProductoNoEncontrado(
            ProductoNoEncontradoException ex,
            HttpServletRequest request) {

        return construirRespuesta(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI(),
                Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> manejarValidaciones(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> detalles = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                detalles.putIfAbsent(
                        error.getField(),
                        error.getDefaultMessage()));

        return construirRespuesta(
                HttpStatus.BAD_REQUEST,
                "Los datos enviados no son válidos",
                request.getRequestURI(),
                detalles);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> manejarTipoInvalido(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        return construirRespuesta(
                HttpStatus.BAD_REQUEST,
                "El parámetro '" + ex.getName()
                        + "' tiene un valor inválido",
                request.getRequestURI(),
                Map.of());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> manejarJsonInvalido(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        return construirRespuesta(
                HttpStatus.BAD_REQUEST,
                "El cuerpo JSON está vacío o tiene un formato incorrecto",
                request.getRequestURI(),
                Map.of());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> manejarIntegridadDatos(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        return construirRespuesta(
                HttpStatus.CONFLICT,
                "La operación incumple una restricción de la base de datos",
                request.getRequestURI(),
                Map.of());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiError> manejarBaseDeDatos(
            DataAccessException ex,
            HttpServletRequest request) {

        return construirRespuesta(
                HttpStatus.SERVICE_UNAVAILABLE,
                "No fue posible acceder a la base de datos",
                request.getRequestURI(),
                Map.of());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> manejarErrorGeneral(
            Exception ex,
            HttpServletRequest request) {

        return construirRespuesta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error interno en el servidor",
                request.getRequestURI(),
                Map.of());
    }

    private ResponseEntity<ApiError> construirRespuesta(
            HttpStatus estado,
            String mensaje,
            String ruta,
            Map<String, String> detalles) {

        ApiError apiError = new ApiError(
                OffsetDateTime.now(),
                estado.value(),
                estado.getReasonPhrase(),
                mensaje,
                ruta,
                detalles);

        return ResponseEntity.status(estado).body(apiError);
    }
}