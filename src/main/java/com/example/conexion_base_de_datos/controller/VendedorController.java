package com.example.conexion_base_de_datos.controller;

import com.example.conexion_base_de_datos.application.dto.VendedorDTO;
import com.example.conexion_base_de_datos.application.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendedores")
@CrossOrigin(origins = "*")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @GetMapping
    public ResponseEntity<List<VendedorDTO>> getAllVendedores() {
        List<VendedorDTO> vendedores = vendedorService.getAllVendedores();
        return ResponseEntity.ok(vendedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendedorDTO> getVendedorById(@PathVariable Long id) {
        return vendedorService.getVendedorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VendedorDTO> createVendedor(@RequestBody VendedorDTO vendedorDTO) {
        try {
            VendedorDTO nuevoVendedor = vendedorService.createVendedor(vendedorDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVendedor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendedorDTO> updateVendedor(@PathVariable Long id, @RequestBody VendedorDTO vendedorDTO) {
        return vendedorService.updateVendedor(id, vendedorDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendedor(@PathVariable Long id) {
        if (vendedorService.deleteVendedor(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/activos")
    public ResponseEntity<List<VendedorDTO>> getVendedoresActivos() {
        List<VendedorDTO> vendedores = vendedorService.getVendedoresActivos();
        return ResponseEntity.ok(vendedores);
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<VendedorDTO> getVendedorByDocumento(@PathVariable String documento) {
        return vendedorService.getVendedorByDocumento(documento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}