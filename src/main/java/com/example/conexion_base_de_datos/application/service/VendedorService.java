package com.example.conexion_base_de_datos.application.service;

import com.example.conexion_base_de_datos.application.dto.VendedorDTO;
import com.example.conexion_base_de_datos.application.mapper.VendedorMapper;
import com.example.conexion_base_de_datos.application.port.VendedorPort;
import com.example.conexion_base_de_datos.infrastucture.model.Vendedor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VendedorService {

    private final VendedorPort vendedorPort;
    private final VendedorMapper vendedorMapper;

    public VendedorService(VendedorPort vendedorPort, VendedorMapper vendedorMapper) {
        this.vendedorPort = vendedorPort;
        this.vendedorMapper = vendedorMapper;
    }

    public List<VendedorDTO> getAllVendedores() {
        List<Vendedor> vendedores = vendedorPort.findAll();
        return vendedorMapper.toDTOList(vendedores);
    }

    public Optional<VendedorDTO> getVendedorById(Long id) {
        return vendedorPort.findById(id)
                .map(vendedorMapper::toDTO);
    }

    public VendedorDTO createVendedor(VendedorDTO vendedorDTO) {
        if (vendedorPort.existsByDocumento(vendedorDTO.getDocumento())) {
            throw new IllegalArgumentException("Ya existe un vendedor con el documento: " + vendedorDTO.getDocumento());
        }
        if (vendedorPort.existsByEmail(vendedorDTO.getEmail())) {
            throw new IllegalArgumentException("Ya existe un vendedor con el email: " + vendedorDTO.getEmail());
        }

        Vendedor vendedor = vendedorMapper.toEntity(vendedorDTO);
        Vendedor savedVendedor = vendedorPort.save(vendedor);
        return vendedorMapper.toDTO(savedVendedor);
    }

    public Optional<VendedorDTO> updateVendedor(Long id, VendedorDTO vendedorDTO) {
        return vendedorPort.findById(id)
                .map(vendedor -> {
                    vendedorMapper.updateEntityFromDTO(vendedorDTO, vendedor);
                    Vendedor updatedVendedor = vendedorPort.save(vendedor);
                    return vendedorMapper.toDTO(updatedVendedor);
                });
    }

    public boolean deleteVendedor(Long id) {
        return vendedorPort.findById(id)
                .map(vendedor -> {
                    vendedor.setActivo(false);
                    vendedorPort.save(vendedor);
                    return true;
                })
                .orElse(false);
    }

    public List<VendedorDTO> getVendedoresActivos() {
        List<Vendedor> vendedores = vendedorPort.findByActivoTrue();
        return vendedorMapper.toDTOList(vendedores);
    }

    public Optional<VendedorDTO> getVendedorByDocumento(String documento) {
        return vendedorPort.findByDocumento(documento)
                .map(vendedorMapper::toDTO);
    }

    public List<VendedorDTO> buscarVendedores(String nombre) {
        List<Vendedor> vendedores = vendedorPort
                .findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, nombre);
        return vendedorMapper.toDTOList(vendedores);
    }

    public List<VendedorDTO> getVendedoresPorRangoComision(Double minComision, Double maxComision) {
        List<Vendedor> vendedores = vendedorPort.findByComisionPorcentajeBetween(minComision, maxComision);
        return vendedorMapper.toDTOList(vendedores);
    }

    public List<VendedorDTO> getVendedoresConVentasEnPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Vendedor> vendedores = vendedorPort.findVendedoresConVentasEnPeriodo(fechaInicio, fechaFin);
        return vendedorMapper.toDTOList(vendedores);
    }

    public List<Object[]> getTopVendedoresPorCantidadVentas() {
        return vendedorPort.findTopVendedoresPorCantidadVentas();
    }
}