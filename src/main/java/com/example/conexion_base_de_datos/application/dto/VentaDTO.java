package com.example.conexion_base_de_datos.application.dto;

import com.example.conexion_base_de_datos.infrastucture.model.EstadoVenta;
import com.example.conexion_base_de_datos.infrastucture.model.MetodoPago;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VentaDTO {
    private Long id;
    private String numeroFactura;
    private LocalDateTime fechaVenta;
    private BigDecimal subtotal;
    private BigDecimal impuestos;
    private BigDecimal total;
    private String descripcion;
    private EstadoVenta estado;
    private MetodoPago metodoPago;
    private Long vendedorId;
    private String vendedorNombre;
    private Long clienteId;
    private String clienteNombre;

    public VentaDTO() {}

    public VentaDTO(BigDecimal subtotal, BigDecimal total, Long vendedorId, Long clienteId) {
        this.subtotal = subtotal;
        this.total = total;
        this.vendedorId = vendedorId;
        this.clienteId = clienteId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }

    public LocalDateTime getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getImpuestos() { return impuestos; }
    public void setImpuestos(BigDecimal impuestos) { this.impuestos = impuestos; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public EstadoVenta getEstado() { return estado; }
    public void setEstado(EstadoVenta estado) { this.estado = estado; }

    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }

    public Long getVendedorId() { return vendedorId; }
    public void setVendedorId(Long vendedorId) { this.vendedorId = vendedorId; }

    public String getVendedorNombre() { return vendedorNombre; }
    public void setVendedorNombre(String vendedorNombre) { this.vendedorNombre = vendedorNombre; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }
}