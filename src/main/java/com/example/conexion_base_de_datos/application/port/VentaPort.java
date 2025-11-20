package com.example.conexion_base_de_datos.application.port;

import com.example.conexion_base_de_datos.infrastucture.model.EstadoVenta;
import com.example.conexion_base_de_datos.infrastucture.model.MetodoPago;
import com.example.conexion_base_de_datos.infrastucture.model.Venta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VentaPort {
    List<Venta> findAll();

    Optional<Venta> findById(Long id);

    Venta save(Venta venta);

    boolean existsById(Long id);

    void deleteById(Long id);

    List<Venta> findByVendedorId(Long vendedorId);

    List<Venta> findByClienteId(Long clienteId);

    List<Venta> findVentasDelDia(LocalDateTime fechaActual);

    Optional<Venta> findByNumeroFactura(String numeroFactura);

    List<Venta> findByEstado(EstadoVenta estado);

    List<Venta> findByMetodoPago(MetodoPago metodoPago);

    List<Venta> findByFechaVentaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    List<Venta> findByTotalBetween(BigDecimal totalMinimo, BigDecimal totalMaximo);

    List<Venta> findVentasDelMes(int ano, int mes);

    BigDecimal findTotalVentasPorVendedorEnPeriodo(Long vendedorId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

    Object[] findEstadisticasVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    List<Venta> findUltimasVentas();
}