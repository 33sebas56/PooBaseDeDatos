package com.example.conexion_base_de_datos.application.port;

import com.example.conexion_base_de_datos.infrastucture.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClientePort {
    List<Cliente> findAll();

    Optional<Cliente> findById(Long id);

    boolean existsByDocumento(String documento);

    boolean existsByEmail(String email);

    Cliente save(Cliente cliente);

    List<Cliente> findByActivoTrue();

    Optional<Cliente> findByDocumento(String documento);

    List<Cliente> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);

    List<Cliente> findByCiudadContainingIgnoreCase(String ciudad);

    List<Cliente> findClientesConVentas();
}
