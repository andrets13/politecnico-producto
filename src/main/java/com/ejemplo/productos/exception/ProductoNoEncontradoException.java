package com.ejemplo.productos.exception;

public class ProductoNoEncontradoException extends RuntimeException {

    public ProductoNoEncontradoException(String id) {
        super("No existe un producto con el id: " + id);
    }
}