package com.ejemplo.productos.exception;

import java.time.Instant;

public record ApiError(
        Instant fecha,
        int estado,
        String error,
        Object detalle,
        String ruta) {
}