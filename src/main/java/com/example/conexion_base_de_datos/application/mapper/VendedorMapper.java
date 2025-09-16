package com.example.conexion_base_de_datos.application.mapper;
import com.example.conexion_base_de_datos.application.dto.VendedorDTO;
import com.example.conexion_base_de_datos.infrastucture.model.Vendedor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VendedorMapper {

    public VendedorDTO toDTO(Vendedor vendedor) {
        if (vendedor == null) {
            return null;
        }

        VendedorDTO dto = new VendedorDTO();
        dto.setId(vendedor.getId());
        dto.setNombre(vendedor.getNombre());
        dto.setApellido(vendedor.getApellido());
        dto.setDocumento(vendedor.getDocumento());
        dto.setEmail(vendedor.getEmail());
        dto.setTelefono(vendedor.getTelefono());
        dto.setFechaIngreso(vendedor.getFechaIngreso());
        dto.setComisionPorcentaje(vendedor.getComisionPorcentaje());
        dto.setActivo(vendedor.getActivo());

        return dto;
    }

    public Vendedor toEntity(VendedorDTO dto) {
        if (dto == null) {
            return null;
        }

        Vendedor vendedor = new Vendedor();
        vendedor.setId(dto.getId());
        vendedor.setNombre(dto.getNombre());
        vendedor.setApellido(dto.getApellido());
        vendedor.setDocumento(dto.getDocumento());
        vendedor.setEmail(dto.getEmail());
        vendedor.setTelefono(dto.getTelefono());
        vendedor.setComisionPorcentaje(dto.getComisionPorcentaje());
        vendedor.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        return vendedor;
    }

    public void updateEntityFromDTO(VendedorDTO dto, Vendedor vendedor) {
        if (dto == null || vendedor == null) {
            return;
        }

        vendedor.setNombre(dto.getNombre());
        vendedor.setApellido(dto.getApellido());
        vendedor.setEmail(dto.getEmail());
        vendedor.setTelefono(dto.getTelefono());
        vendedor.setComisionPorcentaje(dto.getComisionPorcentaje());
        if (dto.getActivo() != null) {
            vendedor.setActivo(dto.getActivo());
        }
    }

    public List<VendedorDTO> toDTOList(List<Vendedor> vendedores) {
        if (vendedores == null) {
            return null;
        }
        return vendedores.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Vendedor> toEntityList(List<VendedorDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}