package com.example.conexion_base_de_datos.application.dto;

import java.time.LocalDateTime;

public class VendedorDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String documento;
    private String email;
    private String telefono;
    private LocalDateTime fechaIngreso;
    private Double comisionPorcentaje;
    private Boolean activo;

    public VendedorDTO() {}

    public VendedorDTO(String nombre, String apellido, String documento, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDateTime getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDateTime fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public Double getComisionPorcentaje() { return comisionPorcentaje; }
    public void setComisionPorcentaje(Double comisionPorcentaje) { this.comisionPorcentaje = comisionPorcentaje; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}