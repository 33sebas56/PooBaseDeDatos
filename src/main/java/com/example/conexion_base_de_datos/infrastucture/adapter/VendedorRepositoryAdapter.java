package com.example.conexion_base_de_datos.infrastucture.adapter;

import com.example.conexion_base_de_datos.application.port.VendedorPort;
import com.example.conexion_base_de_datos.infrastucture.model.Vendedor;
import com.example.conexion_base_de_datos.infrastucture.repository.VendedorRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class VendedorRepositoryAdapter implements VendedorPort {

    private final VendedorRepository vendedorRepository;

    public VendedorRepositoryAdapter(VendedorRepository vendedorRepository) {
        this.vendedorRepository = vendedorRepository;
    }

    @Override
    public List<Vendedor> findAll() {
        return vendedorRepository.findAll();
    }

    @Override
    public Optional<Vendedor> findById(Long id) {
        return vendedorRepository.findById(id);
    }

    @Override
    public boolean existsByDocumento(String documento) {
        return vendedorRepository.existsByDocumento(documento);
    }

    @Override
    public boolean existsByEmail(String email) {
        return vendedorRepository.existsByEmail(email);
    }

    @Override
    public Vendedor save(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    @Override
    public List<Vendedor> findByActivoTrue() {
        return vendedorRepository.findByActivoTrue();
    }

    @Override
    public Optional<Vendedor> findByDocumento(String documento) {
        return vendedorRepository.findByDocumento(documento);
    }

    @Override
    public List<Vendedor> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido) {
        return vendedorRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, apellido);
    }

    @Override
    public List<Vendedor> findByComisionPorcentajeBetween(Double minComision, Double maxComision) {
        return vendedorRepository.findByComisionPorcentajeBetween(minComision, maxComision);
    }

    @Override
    public List<Vendedor> findVendedoresConVentasEnPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return vendedorRepository.findVendedoresConVentasEnPeriodo(fechaInicio, fechaFin);
    }

    @Override
    public List<Object[]> findTopVendedoresPorCantidadVentas() {
        return vendedorRepository.findTopVendedoresPorCantidadVentas();
    }
}
