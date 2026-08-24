package com.ejemplo.productos.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.ejemplo.productos.exception.FirebaseOperationException;
import com.ejemplo.productos.model.Producto;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;

@Repository
public class ProductoRepository {

    private static final String COLECCION = "producto";

    private final Firestore firestore;

    public ProductoRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public Producto guardar(Producto producto) {

        DocumentReference referencia =
                firestore.collection(COLECCION).document();

        producto.setId(referencia.getId());
        esperar(referencia.set(producto));

        return producto;
    }

    public List<Producto> buscarTodos() {

        QuerySnapshot resultado =
                esperar(firestore.collection(COLECCION).get());

        return resultado.getDocuments()
                .stream()
                .map(this::convertir)
                .toList();
    }

    public Optional<Producto> buscarPorId(String id) {

        DocumentSnapshot documento = esperar(
                firestore.collection(COLECCION)
                        .document(id)
                        .get()
        );

        if (!documento.exists()) {
            return Optional.empty();
        }

        return Optional.of(convertir(documento));
    }

    public Producto actualizar(String id, Producto producto) {

        producto.setId(id);

        esperar(
                firestore.collection(COLECCION)
                        .document(id)
                        .set(producto)
        );

        return producto;
    }

    public void eliminar(String id) {

        esperar(
                firestore.collection(COLECCION)
                        .document(id)
                        .delete()
        );
    }

    private Producto convertir(DocumentSnapshot documento) {

        Producto producto = documento.toObject(Producto.class);

        if (producto == null) {
            throw new FirebaseOperationException(
                    "No fue posible convertir el documento de Firebase",
                    null
            );
        }

        producto.setId(documento.getId());
        return producto;
    }

    private <T> T esperar(ApiFuture<T> operacion) {
        try {
            return operacion.get();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new FirebaseOperationException(
                    "La operación con Firebase fue interrumpida",
                    e
            );

        } catch (ExecutionException e) {
            throw new FirebaseOperationException(
                    "No fue posible realizar la operación en Firebase",
                    e.getCause()
            );
        }
    }
}