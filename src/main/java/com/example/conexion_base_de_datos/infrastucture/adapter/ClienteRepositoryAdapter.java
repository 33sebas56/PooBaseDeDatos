package com.example.conexion_base_de_datos.infrastucture.adapter;

import com.example.conexion_base_de_datos.application.port.ClientePort;
import com.example.conexion_base_de_datos.infrastucture.model.Cliente;
import com.example.conexion_base_de_datos.infrastucture.repository.ClienteRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClienteRepositoryAdapter implements ClientePort {

    private final ClienteRepository clienteRepository;

    public ClienteRepositoryAdapter(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public boolean existsByDocumento(String documento) {
        return clienteRepository.existsByDocumento(documento);
    }

    @Override
    public boolean existsByEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> findByActivoTrue() {
        return clienteRepository.findByActivoTrue();
    }

    @Override
    public Optional<Cliente> findByDocumento(String documento) {
        return clienteRepository.findByDocumento(documento);
    }

    @Override
    public List<Cliente> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido) {
        return clienteRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, apellido);
    }

    @Override
    public List<Cliente> findByCiudadContainingIgnoreCase(String ciudad) {
        return clienteRepository.findByCiudadContainingIgnoreCase(ciudad);
    }

    @Override
    public List<Cliente> findClientesConVentas() {
        return clienteRepository.findClientesConVentas();
    }
}
