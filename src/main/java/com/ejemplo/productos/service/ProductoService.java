package com.ejemplo.productos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ejemplo.productos.exception.ProductoNoEncontradoException;
import com.ejemplo.productos.model.Producto;
import com.ejemplo.productos.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }


    public List<Producto> listar() {
        return productoRepository.findAll();
    }


    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() ->
                        new ProductoNoEncontradoException(id));
    }


    public Producto crear(Producto producto) {
        producto.setId(null);
        return productoRepository.save(producto);
    }


    public Producto actualizar(Long id, Producto datos) {
        Producto productoExistente = obtenerPorId(id);

        productoExistente.setNombre(datos.getNombre());
        productoExistente.setDescripcion(datos.getDescripcion());
        productoExistente.setPrecio(datos.getPrecio());

        return productoRepository.save(productoExistente);
    }


    public void eliminar(Long id) {
        Producto producto = obtenerPorId(id);
        productoRepository.delete(producto);
    }
}