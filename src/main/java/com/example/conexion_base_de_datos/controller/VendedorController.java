package com.example.conexion_base_de_datos.controller;

import com.example.conexion_base_de_datos.application.dto.VendedorDTO;
import com.example.conexion_base_de_datos.application.mapper.VendedorMapper;
import com.example.conexion_base_de_datos.infrastucture.model.Vendedor;
import com.example.conexion_base_de_datos.infrastucture.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vendedores")
@CrossOrigin(origins = "*")
public class VendedorController {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private VendedorMapper vendedorMapper;

    @GetMapping
    public ResponseEntity<List<VendedorDTO>> getAllVendedores() {
        List<Vendedor> vendedores = vendedorRepository.findAll();
        List<VendedorDTO> vendedoresDTO = vendedorMapper.toDTOList(vendedores);
        return ResponseEntity.ok(vendedoresDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendedorDTO> getVendedorById(@PathVariable Long id) {
        Optional<Vendedor> vendedor = vendedorRepository.findById(id);
        if (vendedor.isPresent()) {
            VendedorDTO vendedorDTO = vendedorMapper.toDTO(vendedor.get());
            return ResponseEntity.ok(vendedorDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<VendedorDTO> createVendedor(@RequestBody VendedorDTO vendedorDTO) {
        try {
            if (vendedorRepository.existsByDocumento(vendedorDTO.getDocumento())) {
                return ResponseEntity.badRequest().build();
            }
            if (vendedorRepository.existsByEmail(vendedorDTO.getEmail())) {
                return ResponseEntity.badRequest().build();
            }

            Vendedor vendedor = vendedorMapper.toEntity(vendedorDTO);
            Vendedor savedVendedor = vendedorRepository.save(vendedor);
            VendedorDTO responseDTO = vendedorMapper.toDTO(savedVendedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendedorDTO> updateVendedor(@PathVariable Long id, @RequestBody VendedorDTO vendedorDTO) {
        Optional<Vendedor> existingVendedor = vendedorRepository.findById(id);
        if (existingVendedor.isPresent()) {
            Vendedor vendedor = existingVendedor.get();
            vendedorMapper.updateEntityFromDTO(vendedorDTO, vendedor);

            Vendedor updatedVendedor = vendedorRepository.save(vendedor);
            VendedorDTO responseDTO = vendedorMapper.toDTO(updatedVendedor);
            return ResponseEntity.ok(responseDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendedor(@PathVariable Long id) {
        Optional<Vendedor> vendedor = vendedorRepository.findById(id);
        if (vendedor.isPresent()) {
            Vendedor vendedorEntity = vendedor.get();
            vendedorEntity.setActivo(false);
            vendedorRepository.save(vendedorEntity);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/activos")
    public ResponseEntity<List<VendedorDTO>> getVendedoresActivos() {
        List<Vendedor> vendedores = vendedorRepository.findByActivoTrue();
        List<VendedorDTO> vendedoresDTO = vendedorMapper.toDTOList(vendedores);
        return ResponseEntity.ok(vendedoresDTO);
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<VendedorDTO> getVendedorByDocumento(@PathVariable String documento) {
        Optional<Vendedor> vendedor = vendedorRepository.findByDocumento(documento);
        if (vendedor.isPresent()) {
            VendedorDTO vendedorDTO = vendedorMapper.toDTO(vendedor.get());
            return ResponseEntity.ok(vendedorDTO);
        }
        return ResponseEntity.notFound().build();
    }
}