package com.ejemplo.productos.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.ejemplo.productos.model.Producto;
import com.ejemplo.productos.service.ProductoService;

import jakarta.validation.Valid;

@Controller
public class ProductoGraphqlController {

    private final ProductoService productoService;

    public ProductoGraphqlController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @QueryMapping
    public List<Producto> productos() {
        return productoService.listar();
    }

    @QueryMapping
    public Producto producto(@Argument Long id) {
        return productoService.obtenerPorId(id);
    }

    @MutationMapping
    public Producto crearProducto(@Argument @Valid ProductoInput input) {
        return productoService.crear(convertir(input));
    }

    @MutationMapping
    public Producto actualizarProducto(@Argument Long id, @Argument @Valid ProductoInput input) {
        return productoService.actualizar(id, convertir(input));
    }

    @MutationMapping
    public Boolean eliminarProducto(@Argument Long id) {
        productoService.eliminar(id);
        return true;
    }

    private Producto convertir(ProductoInput input) {
        Producto producto = new Producto();
        producto.setNombre(input.nombre());
        producto.setDescripcion(input.descripcion());
        producto.setPrecio(input.precio());
        return producto;
    }
}
