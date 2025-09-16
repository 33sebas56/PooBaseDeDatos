package com.example.conexion_base_de_datos.infrastucture.repository;

import com.example.conexion_base_de_datos.infrastucture.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByDocumento(String documento);

    Optional<Cliente> findByEmail(String email);

    List<Cliente> findByActivoTrue();

    List<Cliente> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);

    List<Cliente> findByCiudadContainingIgnoreCase(String ciudad);

    @Query("SELECT DISTINCT c FROM Cliente c JOIN c.ventas v WHERE c.activo = true")
    List<Cliente> findClientesConVentas();

    @Query("SELECT c, COUNT(v) FROM Cliente c LEFT JOIN c.ventas v WHERE c.id = :clienteId GROUP BY c")
    Object findClienteConConteoVentas(@Param("clienteId") Long clienteId);

    boolean existsByDocumento(String documento);

    boolean existsByEmail(String email);
}