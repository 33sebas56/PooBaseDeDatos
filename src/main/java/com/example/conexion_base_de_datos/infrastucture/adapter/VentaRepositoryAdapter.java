package com.example.conexion_base_de_datos.infrastucture.adapter;

import com.example.conexion_base_de_datos.application.port.VentaPort;
import com.example.conexion_base_de_datos.infrastucture.model.EstadoVenta;
import com.example.conexion_base_de_datos.infrastucture.model.MetodoPago;
import com.example.conexion_base_de_datos.infrastucture.model.Venta;
import com.example.conexion_base_de_datos.infrastucture.repository.VentaRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class VentaRepositoryAdapter implements VentaPort {

    private final VentaRepository ventaRepository;

    public VentaRepositoryAdapter(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    @Override
    public Optional<Venta> findById(Long id) {
        return ventaRepository.findById(id);
    }

    @Override
    public Venta save(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Override
    public boolean existsById(Long id) {
        return ventaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        ventaRepository.deleteById(id);
    }

    @Override
    public List<Venta> findByVendedorId(Long vendedorId) {
        return ventaRepository.findByVendedorId(vendedorId);
    }

    @Override
    public List<Venta> findByClienteId(Long clienteId) {
        return ventaRepository.findByClienteId(clienteId);
    }

    @Override
    public List<Venta> findVentasDelDia(LocalDateTime fechaActual) {
        return ventaRepository.findVentasDelDia(fechaActual);
    }

    @Override
    public Optional<Venta> findByNumeroFactura(String numeroFactura) {
        return ventaRepository.findByNumeroFactura(numeroFactura);
    }

    @Override
    public List<Venta> findByEstado(EstadoVenta estado) {
        return ventaRepository.findByEstado(estado);
    }

    @Override
    public List<Venta> findByMetodoPago(MetodoPago metodoPago) {
        return ventaRepository.findByMetodoPago(metodoPago);
    }

    @Override
    public List<Venta> findByFechaVentaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ventaRepository.findByFechaVentaBetween(fechaInicio, fechaFin);
    }

    @Override
    public List<Venta> findByTotalBetween(BigDecimal totalMinimo, BigDecimal totalMaximo) {
        return ventaRepository.findByTotalBetween(totalMinimo, totalMaximo);
    }

    @Override
    public List<Venta> findVentasDelMes(int ano, int mes) {
        return ventaRepository.findVentasDelMes(ano, mes);
    }

    @Override
    public BigDecimal findTotalVentasPorVendedorEnPeriodo(Long vendedorId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ventaRepository.findTotalVentasPorVendedorEnPeriodo(vendedorId, fechaInicio, fechaFin);
    }

    @Override
    public Object[] findEstadisticasVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ventaRepository.findEstadisticasVentas(fechaInicio, fechaFin);
    }

    @Override
    public List<Venta> findUltimasVentas() {
        return ventaRepository.findUltimasVentas();
    }
}