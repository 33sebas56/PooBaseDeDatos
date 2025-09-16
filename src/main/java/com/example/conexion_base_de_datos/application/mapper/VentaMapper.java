package com.example.conexion_base_de_datos.application.mapper;

import com.example.conexion_base_de_datos.application.dto.VentaDTO;
import com.example.conexion_base_de_datos.infrastucture.model.Venta;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VentaMapper {

    public VentaDTO toDTO(Venta venta) {
        if (venta == null) {
            return null;
        }

        VentaDTO dto = new VentaDTO();
        dto.setId(venta.getId());
        dto.setNumeroFactura(venta.getNumeroFactura());
        dto.setFechaVenta(venta.getFechaVenta());
        dto.setSubtotal(venta.getSubtotal());
        dto.setImpuestos(venta.getImpuestos());
        dto.setTotal(venta.getTotal());
        dto.setDescripcion(venta.getDescripcion());
        dto.setEstado(venta.getEstado());
        dto.setMetodoPago(venta.getMetodoPago());

        if (venta.getVendedor() != null) {
            dto.setVendedorId(venta.getVendedor().getId());
            dto.setVendedorNombre(venta.getVendedor().getNombre() + " " + venta.getVendedor().getApellido());
        }

        if (venta.getCliente() != null) {
            dto.setClienteId(venta.getCliente().getId());
            dto.setClienteNombre(venta.getCliente().getNombre() + " " + venta.getCliente().getApellido());
        }

        return dto;
    }

    public Venta toEntity(VentaDTO dto) {
        if (dto == null) {
            return null;
        }

        Venta venta = new Venta();
        venta.setId(dto.getId());
        venta.setNumeroFactura(dto.getNumeroFactura());
        venta.setSubtotal(dto.getSubtotal());
        venta.setImpuestos(dto.getImpuestos());
        venta.setTotal(dto.getTotal());
        venta.setDescripcion(dto.getDescripcion());
        venta.setEstado(dto.getEstado());
        venta.setMetodoPago(dto.getMetodoPago());


        return venta;
    }

    public void updateEntityFromDTO(VentaDTO dto, Venta venta) {
        if (dto == null || venta == null) {
            return;
        }

        venta.setSubtotal(dto.getSubtotal());
        venta.setImpuestos(dto.getImpuestos());
        venta.setTotal(dto.getTotal());
        venta.setDescripcion(dto.getDescripcion());
        if (dto.getEstado() != null) {
            venta.setEstado(dto.getEstado());
        }
        if (dto.getMetodoPago() != null) {
            venta.setMetodoPago(dto.getMetodoPago());
        }
    }

    public List<VentaDTO> toDTOList(List<Venta> ventas) {
        if (ventas == null) {
            return null;
        }
        return ventas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Venta> toEntityList(List<VentaDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public VentaDTO toDTOResumido(Venta venta) {
        if (venta == null) {
            return null;
        }

        VentaDTO dto = new VentaDTO();
        dto.setId(venta.getId());
        dto.setNumeroFactura(venta.getNumeroFactura());
        dto.setFechaVenta(venta.getFechaVenta());
        dto.setTotal(venta.getTotal());
        dto.setEstado(venta.getEstado());

        if (venta.getVendedor() != null) {
            dto.setVendedorNombre(venta.getVendedor().getNombre());
        }

        if (venta.getCliente() != null) {
            dto.setClienteNombre(venta.getCliente().getNombre());
        }

        return dto;
    }
}