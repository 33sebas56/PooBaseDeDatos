package com.example.conexion_base_de_datos.infrastucture.repository;

import com.example.conexion_base_de_datos.infrastucture.model.Venta;
import com.example.conexion_base_de_datos.infrastucture.model.EstadoVenta;
import com.example.conexion_base_de_datos.infrastucture.model.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    Optional<Venta> findByNumeroFactura(String numeroFactura);

    List<Venta> findByEstado(EstadoVenta estado);

    List<Venta> findByMetodoPago(MetodoPago metodoPago);

    List<Venta> findByVendedorId(Long vendedorId);

    List<Venta> findByClienteId(Long clienteId);

    List<Venta> findByFechaVentaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    List<Venta> findByTotalBetween(BigDecimal totalMinimo, BigDecimal totalMaximo);

    @Query("SELECT v FROM Venta v WHERE DATE(v.fechaVenta) = DATE(:fecha)")
    List<Venta> findVentasDelDia(@Param("fecha") LocalDateTime fecha);

    @Query("SELECT v FROM Venta v WHERE YEAR(v.fechaVenta) = :año AND MONTH(v.fechaVenta) = :mes")
    List<Venta> findVentasDelMes(@Param("año") int año, @Param("mes") int mes);

    @Query("SELECT SUM(v.total) FROM Venta v WHERE v.vendedor.id = :vendedorId AND v.fechaVenta BETWEEN :fechaInicio AND :fechaFin")
    BigDecimal findTotalVentasPorVendedorEnPeriodo(@Param("vendedorId") Long vendedorId,
                                                   @Param("fechaInicio") LocalDateTime fechaInicio,
                                                   @Param("fechaFin") LocalDateTime fechaFin);

    @Query("SELECT COUNT(v), SUM(v.total), AVG(v.total) FROM Venta v WHERE v.fechaVenta BETWEEN :fechaInicio AND :fechaFin")
    Object[] findEstadisticasVentas(@Param("fechaInicio") LocalDateTime fechaInicio,
                                    @Param("fechaFin") LocalDateTime fechaFin);

    boolean existsByNumeroFactura(String numeroFactura);

    @Query("SELECT v FROM Venta v ORDER BY v.fechaVenta DESC")
    List<Venta> findUltimasVentas();
}