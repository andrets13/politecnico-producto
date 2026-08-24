package com.ejemplo.productos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ejemplo.productos.exception.ProductoNoEncontradoException;
import com.ejemplo.productos.model.Producto;
import com.ejemplo.productos.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public Producto crear(Producto producto) {
        producto.setId(null);
        return repository.guardar(producto);
    }

    public List<Producto> listar() {
        return repository.buscarTodos();
    }

    public Producto buscarPorId(String id) {
        return repository.buscarPorId(id)
                .orElseThrow(() ->
                        new ProductoNoEncontradoException(id));
    }

    public Producto actualizar(String id, Producto producto) {
        buscarPorId(id);
        producto.setId(id);
        return repository.actualizar(id, producto);
    }

    public void eliminar(String id) {
        buscarPorId(id);
        repository.eliminar(id);
    }
}