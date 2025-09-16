package com.example.conexion_base_de_datos.infrastucture.repository;
import com.example.conexion_base_de_datos.infrastucture.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    Optional<Vendedor> findByDocumento(String documento);

    Optional<Vendedor> findByEmail(String email);

    List<Vendedor> findByActivoTrue();

    List<Vendedor> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);

    List<Vendedor> findByComisionPorcentajeBetween(Double minComision, Double maxComision);

    @Query("SELECT DISTINCT v FROM Vendedor v JOIN v.ventas vt WHERE vt.fechaVenta BETWEEN :fechaInicio AND :fechaFin")
    List<Vendedor> findVendedoresConVentasEnPeriodo(@Param("fechaInicio") LocalDateTime fechaInicio,
                                                    @Param("fechaFin") LocalDateTime fechaFin);

    @Query("SELECT v, COUNT(vt) as totalVentas FROM Vendedor v LEFT JOIN v.ventas vt WHERE v.activo = true GROUP BY v ORDER BY totalVentas DESC")
    List<Object[]> findTopVendedoresPorCantidadVentas();

    boolean existsByDocumento(String documento);

    boolean existsByEmail(String email);
}