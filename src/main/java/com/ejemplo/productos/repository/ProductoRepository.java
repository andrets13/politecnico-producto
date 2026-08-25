package com.ejemplo.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplo.productos.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}