package com.ejemplo.productos.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        return repository.save(producto);
    }

    public List<Producto> listar() {
        return repository.findAll();
    }

    public Producto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Producto no encontrado con id: " + id));
    }

    public Producto actualizar(Long id, Producto datos) {
        Producto producto = buscarPorId(id);

        producto.setNombre(datos.getNombre());
        producto.setDescripcion(datos.getDescripcion());
        producto.setPrecio(datos.getPrecio());

        return repository.save(producto);
    }

    public void eliminar(Long id) {
        Producto producto = buscarPorId(id);
        repository.delete(producto);
    }
}