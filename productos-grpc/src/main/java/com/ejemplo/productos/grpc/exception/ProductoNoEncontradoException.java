package com.ejemplo.productos.grpc.exception;

public class ProductoNoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProductoNoEncontradoException(Long id) {
        super("No se encontró el producto con id: " + id);
    }
}
