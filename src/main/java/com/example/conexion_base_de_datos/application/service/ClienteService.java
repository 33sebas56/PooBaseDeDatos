package com.example.conexion_base_de_datos.application.service;

import com.example.conexion_base_de_datos.application.dto.ClienteDTO;
import com.example.conexion_base_de_datos.application.mapper.ClienteMapper;
import com.example.conexion_base_de_datos.infrastucture.model.Cliente;
import com.example.conexion_base_de_datos.infrastucture.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    public List<ClienteDTO> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clienteMapper.toDTOList(clientes);
    }

    public Optional<ClienteDTO> getClienteById(Long id) {
        return clienteRepository.findById(id)
                .map(clienteMapper::toDTO);
    }

    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        if (clienteRepository.existsByDocumento(clienteDTO.getDocumento())) {
            throw new IllegalArgumentException("Ya existe un cliente con el documento: " + clienteDTO.getDocumento());
        }
        if (clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new IllegalArgumentException("Ya existe un cliente con el email: " + clienteDTO.getEmail());
        }

        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        Cliente savedCliente = clienteRepository.save(cliente);
        return clienteMapper.toDTO(savedCliente);
    }

    public Optional<ClienteDTO> updateCliente(Long id, ClienteDTO clienteDTO) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    clienteMapper.updateEntityFromDTO(clienteDTO, cliente);
                    Cliente updatedCliente = clienteRepository.save(cliente);
                    return clienteMapper.toDTO(updatedCliente);
                });
    }

    public boolean deleteCliente(Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setActivo(false);
                    clienteRepository.save(cliente);
                    return true;
                })
                .orElse(false);
    }

    public List<ClienteDTO> getClientesActivos() {
        List<Cliente> clientes = clienteRepository.findByActivoTrue();
        return clienteMapper.toDTOList(clientes);
    }

    public Optional<ClienteDTO> getClienteByDocumento(String documento) {
        return clienteRepository.findByDocumento(documento)
                .map(clienteMapper::toDTO);
    }

    public List<ClienteDTO> buscarClientes(String nombre) {
        List<Cliente> clientes = clienteRepository
                .findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, nombre);
        return clienteMapper.toDTOList(clientes);
    }

    public List<ClienteDTO> getClientesPorCiudad(String ciudad) {
        List<Cliente> clientes = clienteRepository.findByCiudadContainingIgnoreCase(ciudad);
        return clienteMapper.toDTOList(clientes);
    }

    public List<ClienteDTO> getClientesConVentas() {
        List<Cliente> clientes = clienteRepository.findClientesConVentas();
        return clienteMapper.toDTOList(clientes);
    }
}