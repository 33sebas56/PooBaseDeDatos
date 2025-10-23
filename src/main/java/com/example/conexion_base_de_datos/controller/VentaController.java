package com.example.conexion_base_de_datos.controller;

import com.example.conexion_base_de_datos.application.dto.VentaDTO;
import com.example.conexion_base_de_datos.application.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaDTO>> getAllVentas() {
        List<VentaDTO> ventas = ventaService.getAllVentas();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> getVentaById(@PathVariable Long id) {
        return ventaService.getVentaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VentaDTO> createVenta(@RequestBody VentaDTO ventaDTO) {
        try {
            VentaDTO nuevaVenta = ventaService.createVenta(ventaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> updateVenta(@PathVariable Long id, @RequestBody VentaDTO ventaDTO) {
        return ventaService.updateVenta(id, ventaDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        if (ventaService.deleteVenta(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<VentaDTO>> getVentasByVendedor(@PathVariable Long vendedorId) {
        List<VentaDTO> ventas = ventaService.getVentasByVendedor(vendedorId);
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VentaDTO>> getVentasByCliente(@PathVariable Long clienteId) {
        List<VentaDTO> ventas = ventaService.getVentasByCliente(clienteId);
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/hoy")
    public ResponseEntity<List<VentaDTO>> getVentasHoy() {
        List<VentaDTO> ventas = ventaService.getVentasHoy();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/factura/{numeroFactura}")
    public ResponseEntity<VentaDTO> getVentaByNumeroFactura(@PathVariable String numeroFactura) {
        return ventaService.getVentaByNumeroFactura(numeroFactura)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}