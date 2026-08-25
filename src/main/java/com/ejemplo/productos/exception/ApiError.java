package com.ejemplo.productos.exception;

import java.time.OffsetDateTime;
import java.util.Map;

public record ApiError(
        OffsetDateTime fecha,
        int estado,
        String error,
        String mensaje,
        String ruta,
        Map<String, String> detalles) {
}