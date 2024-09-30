package com.mascotas.venta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mascotas.venta.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long>{
    
}
