package com.example.conexion_base_de_datos.controller;

import com.example.conexion_base_de_datos.application.dto.VentaDTO;
import com.example.conexion_base_de_datos.application.mapper.VentaMapper;
import com.example.conexion_base_de_datos.infrastucture.model.Venta;
import com.example.conexion_base_de_datos.infrastucture.model.Cliente;
import com.example.conexion_base_de_datos.infrastucture.model.Vendedor;
import com.example.conexion_base_de_datos.infrastucture.repository.VentaRepository;
import com.example.conexion_base_de_datos.infrastucture.repository.ClienteRepository;
import com.example.conexion_base_de_datos.infrastucture.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private VentaMapper ventaMapper;

    @GetMapping
    public ResponseEntity<List<VentaDTO>> getAllVentas() {
        List<Venta> ventas = ventaRepository.findAll();
        List<VentaDTO> ventasDTO = ventaMapper.toDTOList(ventas);
        return ResponseEntity.ok(ventasDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> getVentaById(@PathVariable Long id) {
        Optional<Venta> venta = ventaRepository.findById(id);
        if (venta.isPresent()) {
            VentaDTO ventaDTO = ventaMapper.toDTO(venta.get());
            return ResponseEntity.ok(ventaDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<VentaDTO> createVenta(@RequestBody VentaDTO ventaDTO) {
        try {
            Optional<Cliente> cliente = clienteRepository.findById(ventaDTO.getClienteId());
            Optional<Vendedor> vendedor = vendedorRepository.findById(ventaDTO.getVendedorId());

            if (!cliente.isPresent() || !vendedor.isPresent()) {
                return ResponseEntity.badRequest().build();
            }

            Venta venta = ventaMapper.toEntity(ventaDTO);
            venta.setCliente(cliente.get());
            venta.setVendedor(vendedor.get());

            Venta savedVenta = ventaRepository.save(venta);
            VentaDTO responseDTO = ventaMapper.toDTO(savedVenta);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> updateVenta(@PathVariable Long id, @RequestBody VentaDTO ventaDTO) {
        Optional<Venta> existingVenta = ventaRepository.findById(id);
        if (existingVenta.isPresent()) {
            Venta venta = existingVenta.get();
            ventaMapper.updateEntityFromDTO(ventaDTO, venta);

            Venta updatedVenta = ventaRepository.save(venta);
            VentaDTO responseDTO = ventaMapper.toDTO(updatedVenta);
            return ResponseEntity.ok(responseDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        if (ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<VentaDTO>> getVentasByVendedor(@PathVariable Long vendedorId) {
        List<Venta> ventas = ventaRepository.findByVendedorId(vendedorId);
        List<VentaDTO> ventasDTO = ventaMapper.toDTOList(ventas);
        return ResponseEntity.ok(ventasDTO);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VentaDTO>> getVentasByCliente(@PathVariable Long clienteId) {
        List<Venta> ventas = ventaRepository.findByClienteId(clienteId);
        List<VentaDTO> ventasDTO = ventaMapper.toDTOList(ventas);
        return ResponseEntity.ok(ventasDTO);
    }

    @GetMapping("/hoy")
    public ResponseEntity<List<VentaDTO>> getVentasHoy() {
        List<Venta> ventas = ventaRepository.findVentasDelDia(LocalDateTime.now());
        List<VentaDTO> ventasDTO = ventaMapper.toDTOList(ventas);
        return ResponseEntity.ok(ventasDTO);
    }

    @GetMapping("/factura/{numeroFactura}")
    public ResponseEntity<VentaDTO> getVentaByNumeroFactura(@PathVariable String numeroFactura) {
        Optional<Venta> venta = ventaRepository.findByNumeroFactura(numeroFactura);
        if (venta.isPresent()) {
            VentaDTO ventaDTO = ventaMapper.toDTO(venta.get());
            return ResponseEntity.ok(ventaDTO);
        }
        return ResponseEntity.notFound().build();
    }
}