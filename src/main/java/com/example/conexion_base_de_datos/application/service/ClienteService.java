package com.example.conexion_base_de_datos.application.service;

import com.example.conexion_base_de_datos.application.dto.ClienteDTO;
import com.example.conexion_base_de_datos.application.mapper.ClienteMapper;
import com.example.conexion_base_de_datos.application.port.ClientePort;
import com.example.conexion_base_de_datos.infrastucture.model.Cliente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    private final ClientePort clientePort;
    private final ClienteMapper clienteMapper;

    public ClienteService(ClientePort clientePort, ClienteMapper clienteMapper) {
        this.clientePort = clientePort;
        this.clienteMapper = clienteMapper;
    }

    public List<ClienteDTO> getAllClientes() {
        List<Cliente> clientes = clientePort.findAll();
        return clienteMapper.toDTOList(clientes);
    }

    public Optional<ClienteDTO> getClienteById(Long id) {
        return clientePort.findById(id)
                .map(clienteMapper::toDTO);
    }

    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        if (clientePort.existsByDocumento(clienteDTO.getDocumento())) {
            throw new IllegalArgumentException("Ya existe un cliente con el documento: " + clienteDTO.getDocumento());
        }
        if (clientePort.existsByEmail(clienteDTO.getEmail())) {
            throw new IllegalArgumentException("Ya existe un cliente con el email: " + clienteDTO.getEmail());
        }

        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        Cliente savedCliente = clientePort.save(cliente);
        return clienteMapper.toDTO(savedCliente);
    }

    public Optional<ClienteDTO> updateCliente(Long id, ClienteDTO clienteDTO) {
        return clientePort.findById(id)
                .map(cliente -> {
                    clienteMapper.updateEntityFromDTO(clienteDTO, cliente);
                    Cliente updatedCliente = clientePort.save(cliente);
                    return clienteMapper.toDTO(updatedCliente);
                });
    }

    public boolean deleteCliente(Long id) {
        return clientePort.findById(id)
                .map(cliente -> {
                    cliente.setActivo(false);
                    clientePort.save(cliente);
                    return true;
                })
                .orElse(false);
    }

    public List<ClienteDTO> getClientesActivos() {
        List<Cliente> clientes = clientePort.findByActivoTrue();
        return clienteMapper.toDTOList(clientes);
    }

    public Optional<ClienteDTO> getClienteByDocumento(String documento) {
        return clientePort.findByDocumento(documento)
                .map(clienteMapper::toDTO);
    }

    public List<ClienteDTO> buscarClientes(String nombre) {
        List<Cliente> clientes = clientePort
                .findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, nombre);
        return clienteMapper.toDTOList(clientes);
    }

    public List<ClienteDTO> getClientesPorCiudad(String ciudad) {
        List<Cliente> clientes = clientePort.findByCiudadContainingIgnoreCase(ciudad);
        return clienteMapper.toDTOList(clientes);
    }

    public List<ClienteDTO> getClientesConVentas() {
        List<Cliente> clientes = clientePort.findClientesConVentas();
        return clienteMapper.toDTOList(clientes);
    }
}