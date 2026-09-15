package com.ejemplo.productos.graphql;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductoInput(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @NotBlank(message = "La descripción es obligatoria") String descripcion,
        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser mayor que cero") BigDecimal precio) {
}
