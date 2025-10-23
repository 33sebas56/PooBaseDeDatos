package com.example.conexion_base_de_datos.application.service;

import com.example.conexion_base_de_datos.application.dto.VentaDTO;
import com.example.conexion_base_de_datos.application.mapper.VentaMapper;
import com.example.conexion_base_de_datos.infrastucture.model.Venta;
import com.example.conexion_base_de_datos.infrastucture.model.Cliente;
import com.example.conexion_base_de_datos.infrastucture.model.Vendedor;
import com.example.conexion_base_de_datos.infrastucture.model.EstadoVenta;
import com.example.conexion_base_de_datos.infrastucture.model.MetodoPago;
import com.example.conexion_base_de_datos.infrastucture.repository.VentaRepository;
import com.example.conexion_base_de_datos.infrastucture.repository.ClienteRepository;
import com.example.conexion_base_de_datos.infrastucture.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private VentaMapper ventaMapper;

    public List<VentaDTO> getAllVentas() {
        List<Venta> ventas = ventaRepository.findAll();
        return ventaMapper.toDTOList(ventas);
    }

    public Optional<VentaDTO> getVentaById(Long id) {
        return ventaRepository.findById(id)
                .map(ventaMapper::toDTO);
    }

    public VentaDTO createVenta(VentaDTO ventaDTO) {
        Cliente cliente = clienteRepository.findById(ventaDTO.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + ventaDTO.getClienteId()));

        Vendedor vendedor = vendedorRepository.findById(ventaDTO.getVendedorId())
                .orElseThrow(() -> new IllegalArgumentException("Vendedor no encontrado con ID: " + ventaDTO.getVendedorId()));

        Venta venta = ventaMapper.toEntity(ventaDTO);
        venta.setCliente(cliente);
        venta.setVendedor(vendedor);

        Venta savedVenta = ventaRepository.save(venta);
        return ventaMapper.toDTO(savedVenta);
    }

    public Optional<VentaDTO> updateVenta(Long id, VentaDTO ventaDTO) {
        return ventaRepository.findById(id)
                .map(venta -> {
                    ventaMapper.updateEntityFromDTO(ventaDTO, venta);
                    Venta updatedVenta = ventaRepository.save(venta);
                    return ventaMapper.toDTO(updatedVenta);
                });
    }

    public boolean deleteVenta(Long id) {
        if (ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<VentaDTO> getVentasByVendedor(Long vendedorId) {
        List<Venta> ventas = ventaRepository.findByVendedorId(vendedorId);
        return ventaMapper.toDTOList(ventas);
    }

    public List<VentaDTO> getVentasByCliente(Long clienteId) {
        List<Venta> ventas = ventaRepository.findByClienteId(clienteId);
        return ventaMapper.toDTOList(ventas);
    }

    public List<VentaDTO> getVentasHoy() {
        List<Venta> ventas = ventaRepository.findVentasDelDia(LocalDateTime.now());
        return ventaMapper.toDTOList(ventas);
    }

    public Optional<VentaDTO> getVentaByNumeroFactura(String numeroFactura) {
        return ventaRepository.findByNumeroFactura(numeroFactura)
                .map(ventaMapper::toDTO);
    }

    public List<VentaDTO> getVentasByEstado(EstadoVenta estado) {
        List<Venta> ventas = ventaRepository.findByEstado(estado);
        return ventaMapper.toDTOList(ventas);
    }

    public List<VentaDTO> getVentasByMetodoPago(MetodoPago metodoPago) {
        List<Venta> ventas = ventaRepository.findByMetodoPago(metodoPago);
        return ventaMapper.toDTOList(ventas);
    }

    public List<VentaDTO> getVentasByRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Venta> ventas = ventaRepository.findByFechaVentaBetween(fechaInicio, fechaFin);
        return ventaMapper.toDTOList(ventas);
    }

    public List<VentaDTO> getVentasByRangoTotal(BigDecimal totalMinimo, BigDecimal totalMaximo) {
        List<Venta> ventas = ventaRepository.findByTotalBetween(totalMinimo, totalMaximo);
        return ventaMapper.toDTOList(ventas);
    }

    public List<VentaDTO> getVentasDelMes(int año, int mes) {
        List<Venta> ventas = ventaRepository.findVentasDelMes(año, mes);
        return ventaMapper.toDTOList(ventas);
    }

    public BigDecimal getTotalVentasPorVendedorEnPeriodo(Long vendedorId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ventaRepository.findTotalVentasPorVendedorEnPeriodo(vendedorId, fechaInicio, fechaFin);
    }

    public Object[] getEstadisticasVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ventaRepository.findEstadisticasVentas(fechaInicio, fechaFin);
    }

    public List<VentaDTO> getUltimasVentas() {
        List<Venta> ventas = ventaRepository.findUltimasVentas();
        return ventaMapper.toDTOList(ventas);
    }
}