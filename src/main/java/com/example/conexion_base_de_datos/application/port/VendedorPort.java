package com.example.conexion_base_de_datos.application.port;

import com.example.conexion_base_de_datos.infrastucture.model.Vendedor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VendedorPort {
    List<Vendedor> findAll();

    Optional<Vendedor> findById(Long id);

    boolean existsByDocumento(String documento);

    boolean existsByEmail(String email);

    Vendedor save(Vendedor vendedor);

    List<Vendedor> findByActivoTrue();

    Optional<Vendedor> findByDocumento(String documento);

    List<Vendedor> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);

    List<Vendedor> findByComisionPorcentajeBetween(Double minComision, Double maxComision);

    List<Vendedor> findVendedoresConVentasEnPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    List<Object[]> findTopVendedoresPorCantidadVentas();
}
