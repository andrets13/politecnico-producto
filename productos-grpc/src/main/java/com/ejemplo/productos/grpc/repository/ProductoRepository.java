package com.ejemplo.productos.grpc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplo.productos.grpc.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
