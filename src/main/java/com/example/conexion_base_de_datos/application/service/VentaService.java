package com.example.conexion_base_de_datos.application.service;

import com.example.conexion_base_de_datos.application.dto.VentaDTO;
import com.example.conexion_base_de_datos.application.mapper.VentaMapper;
import com.example.conexion_base_de_datos.application.port.ClientePort;
import com.example.conexion_base_de_datos.application.port.VendedorPort;
import com.example.conexion_base_de_datos.application.port.VentaPort;
import com.example.conexion_base_de_datos.infrastucture.model.Venta;
import com.example.conexion_base_de_datos.infrastucture.model.Cliente;
import com.example.conexion_base_de_datos.infrastucture.model.Vendedor;
import com.example.conexion_base_de_datos.infrastucture.model.EstadoVenta;
import com.example.conexion_base_de_datos.infrastucture.model.MetodoPago;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService {

    private final VentaPort ventaPort;
    private final ClientePort clientePort;
    private final VendedorPort vendedorPort;
    private final VentaMapper ventaMapper;

    public VentaService(VentaPort ventaPort, ClientePort clientePort, VendedorPort vendedorPort, VentaMapper ventaMapper) {
        this.ventaPort = ventaPort;
        this.clientePort = clientePort;
        this.vendedorPort = vendedorPort;
        this.ventaMapper = ventaMapper;
    }

    public List<VentaDTO> getAllVentas() {
        List<Venta> ventas = ventaPort.findAll();
        return ventaMapper.toDTOList(ventas);
    }

    public Optional<VentaDTO> getVentaById(Long id) {
        return ventaPort.findById(id)
                .map(ventaMapper::toDTO);
    }

    public VentaDTO createVenta(VentaDTO ventaDTO) {
        Cliente cliente = clientePort.findById(ventaDTO.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + ventaDTO.getClienteId()));

        Vendedor vendedor = vendedorPort.findById(ventaDTO.getVendedorId())
                .orElseThrow(() -> new IllegalArgumentException("Vendedor no encontrado con ID: " + ventaDTO.getVendedorId()));

        Venta venta = ventaMapper.toEntity(ventaDTO);
        venta.setCliente(cliente);
        venta.setVendedor(vendedor);

        Venta savedVenta = ventaPort.save(venta);
        return ventaMapper.toDTO(savedVenta);
    }

    public Optional<VentaDTO> updateVenta(Long id, VentaDTO ventaDTO) {
        return ventaPort.findById(id)
                .map(venta -> {
                    ventaMapper.updateEntityFromDTO(ventaDTO, venta);
                    Venta updatedVenta = ventaPort.save(venta);
                    return ventaMapper.toDTO(updatedVenta);
                });
    }

    public boolean deleteVenta(Long id) {
        if (ventaPort.existsById(id)) {
            ventaPort.deleteById(id);
            return true;
        }
        return false;
    }

    public List<VentaDTO> getVentasByVendedor(Long vendedorId) {
        List<Venta> ventas = ventaPort.findByVendedorId(vendedorId);
        return ventaMapper.toDTOList(ventas);
    }

    public List<VentaDTO> getVentasByCliente(Long clienteId) {
        List<Venta> ventas = ventaPort.findByClienteId(clienteId);
        return ventaMapper.toDTOList(ventas);
    }

    public List<VentaDTO> getVentasHoy() {
        List<Venta> ventas = ventaPort.findVentasDelDia(LocalDateTime.now());
        return ventaMapper.toDTOList(ventas);
    }

    public Optional<VentaDTO> getVentaByNumeroFactura(String numeroFactura) {
        return ventaPort.findByNumeroFactura(numeroFactura)
                .map(ventaMapper::toDTO);
    }

    public List<VentaDTO> getVentasByEstado(EstadoVenta estado) {
        List<Venta> ventas = ventaPort.findByEstado(estado);
        return ventaMapper.toDTOList(ventas);
    }

    public List<VentaDTO> getVentasByMetodoPago(MetodoPago metodoPago) {
        List<Venta> ventas = ventaPort.findByMetodoPago(metodoPago);
        return ventaMapper.toDTOList(ventas);
    }

    public List<VentaDTO> getVentasByRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Venta> ventas = ventaPort.findByFechaVentaBetween(fechaInicio, fechaFin);
        return ventaMapper.toDTOList(ventas);
    }

    public List<VentaDTO> getVentasByRangoTotal(BigDecimal totalMinimo, BigDecimal totalMaximo) {
        List<Venta> ventas = ventaPort.findByTotalBetween(totalMinimo, totalMaximo);
        return ventaMapper.toDTOList(ventas);
    }

    public List<VentaDTO> getVentasDelMes(int año, int mes) {
        List<Venta> ventas = ventaPort.findVentasDelMes(año, mes);
        return ventaMapper.toDTOList(ventas);
    }

    public BigDecimal getTotalVentasPorVendedorEnPeriodo(Long vendedorId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ventaPort.findTotalVentasPorVendedorEnPeriodo(vendedorId, fechaInicio, fechaFin);
    }

    public Object[] getEstadisticasVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ventaPort.findEstadisticasVentas(fechaInicio, fechaFin);
    }

    public List<VentaDTO> getUltimasVentas() {
        List<Venta> ventas = ventaPort.findUltimasVentas();
        return ventaMapper.toDTOList(ventas);
    }
}