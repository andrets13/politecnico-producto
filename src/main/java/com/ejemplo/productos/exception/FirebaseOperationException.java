package com.ejemplo.productos.exception;

public class FirebaseOperationException extends RuntimeException {

    public FirebaseOperationException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}