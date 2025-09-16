package com.example.conexion_base_de_datos.application.mapper;

import com.example.conexion_base_de_datos.application.dto.ClienteDTO;
import com.example.conexion_base_de_datos.infrastucture.model.Cliente;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setDocumento(cliente.getDocumento());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setDireccion(cliente.getDireccion());
        dto.setCiudad(cliente.getCiudad());
        dto.setFechaRegistro(cliente.getFechaRegistro());
        dto.setActivo(cliente.getActivo());

        return dto;
    }

    public Cliente toEntity(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setDocumento(dto.getDocumento());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
        cliente.setCiudad(dto.getCiudad());
        cliente.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        return cliente;
    }

    public void updateEntityFromDTO(ClienteDTO dto, Cliente cliente) {
        if (dto == null || cliente == null) {
            return;
        }

        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
        cliente.setCiudad(dto.getCiudad());
        if (dto.getActivo() != null) {
            cliente.setActivo(dto.getActivo());
        }
    }

    public List<ClienteDTO> toDTOList(List<Cliente> clientes) {
        if (clientes == null) {
            return null;
        }
        return clientes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Cliente> toEntityList(List<ClienteDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
