package com.example.conexion_base_de_datos.application.service;

import com.example.conexion_base_de_datos.application.dto.VendedorDTO;
import com.example.conexion_base_de_datos.application.mapper.VendedorMapper;
import com.example.conexion_base_de_datos.infrastucture.model.Vendedor;
import com.example.conexion_base_de_datos.infrastucture.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private VendedorMapper vendedorMapper;

    public List<VendedorDTO> getAllVendedores() {
        List<Vendedor> vendedores = vendedorRepository.findAll();
        return vendedorMapper.toDTOList(vendedores);
    }

    public Optional<VendedorDTO> getVendedorById(Long id) {
        return vendedorRepository.findById(id)
                .map(vendedorMapper::toDTO);
    }

    public VendedorDTO createVendedor(VendedorDTO vendedorDTO) {
        if (vendedorRepository.existsByDocumento(vendedorDTO.getDocumento())) {
            throw new IllegalArgumentException("Ya existe un vendedor con el documento: " + vendedorDTO.getDocumento());
        }
        if (vendedorRepository.existsByEmail(vendedorDTO.getEmail())) {
            throw new IllegalArgumentException("Ya existe un vendedor con el email: " + vendedorDTO.getEmail());
        }

        Vendedor vendedor = vendedorMapper.toEntity(vendedorDTO);
        Vendedor savedVendedor = vendedorRepository.save(vendedor);
        return vendedorMapper.toDTO(savedVendedor);
    }

    public Optional<VendedorDTO> updateVendedor(Long id, VendedorDTO vendedorDTO) {
        return vendedorRepository.findById(id)
                .map(vendedor -> {
                    vendedorMapper.updateEntityFromDTO(vendedorDTO, vendedor);
                    Vendedor updatedVendedor = vendedorRepository.save(vendedor);
                    return vendedorMapper.toDTO(updatedVendedor);
                });
    }

    public boolean deleteVendedor(Long id) {
        return vendedorRepository.findById(id)
                .map(vendedor -> {
                    vendedor.setActivo(false);
                    vendedorRepository.save(vendedor);
                    return true;
                })
                .orElse(false);
    }

    public List<VendedorDTO> getVendedoresActivos() {
        List<Vendedor> vendedores = vendedorRepository.findByActivoTrue();
        return vendedorMapper.toDTOList(vendedores);
    }

    public Optional<VendedorDTO> getVendedorByDocumento(String documento) {
        return vendedorRepository.findByDocumento(documento)
                .map(vendedorMapper::toDTO);
    }

    public List<VendedorDTO> buscarVendedores(String nombre) {
        List<Vendedor> vendedores = vendedorRepository
                .findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, nombre);
        return vendedorMapper.toDTOList(vendedores);
    }

    public List<VendedorDTO> getVendedoresPorRangoComision(Double minComision, Double maxComision) {
        List<Vendedor> vendedores = vendedorRepository.findByComisionPorcentajeBetween(minComision, maxComision);
        return vendedorMapper.toDTOList(vendedores);
    }

    public List<VendedorDTO> getVendedoresConVentasEnPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Vendedor> vendedores = vendedorRepository.findVendedoresConVentasEnPeriodo(fechaInicio, fechaFin);
        return vendedorMapper.toDTOList(vendedores);
    }

    public List<Object[]> getTopVendedoresPorCantidadVentas() {
        return vendedorRepository.findTopVendedoresPorCantidadVentas();
    }
}