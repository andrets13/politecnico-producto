package com.ejemplo.productos.grpc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.ejemplo.productos.grpc.exception.ProductoNoEncontradoException;
import com.ejemplo.productos.grpc.model.Producto;
import com.ejemplo.productos.grpc.repository.ProductoRepository;

import jakarta.validation.Valid;

@Service
@Validated
public class ProductoCrudService {

    private final ProductoRepository productoRepository;

    public ProductoCrudService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    public Producto obtener(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));
    }

    public Producto crear(@Valid Producto producto) {
        producto.setId(null);
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, @Valid Producto datos) {
        Producto existente = obtener(id);
        existente.setNombre(datos.getNombre());
        existente.setDescripcion(datos.getDescripcion());
        existente.setPrecio(datos.getPrecio());
        return productoRepository.save(existente);
    }

    public void eliminar(Long id) {
        productoRepository.delete(obtener(id));
    }
}
