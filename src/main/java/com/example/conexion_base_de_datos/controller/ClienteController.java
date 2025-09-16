package com.example.conexion_base_de_datos.controller;
import com.example.conexion_base_de_datos.application.dto.ClienteDTO;
import com.example.conexion_base_de_datos.application.mapper.ClienteMapper;
import com.example.conexion_base_de_datos.infrastucture.model.Cliente;
import com.example.conexion_base_de_datos.infrastucture.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> clientesDTO = clienteMapper.toDTOList(clientes);
        return ResponseEntity.ok(clientesDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            ClienteDTO clienteDTO = clienteMapper.toDTO(cliente.get());
            return ResponseEntity.ok(clienteDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            // Validaciones
            if (clienteRepository.existsByDocumento(clienteDTO.getDocumento())) {
                return ResponseEntity.badRequest().build();
            }
            if (clienteRepository.existsByEmail(clienteDTO.getEmail())) {
                return ResponseEntity.badRequest().build();
            }

            Cliente cliente = clienteMapper.toEntity(clienteDTO);
            Cliente savedCliente = clienteRepository.save(cliente);
            ClienteDTO responseDTO = clienteMapper.toDTO(savedCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        Optional<Cliente> existingCliente = clienteRepository.findById(id);
        if (existingCliente.isPresent()) {
            Cliente cliente = existingCliente.get();
            clienteMapper.updateEntityFromDTO(clienteDTO, cliente);

            Cliente updatedCliente = clienteRepository.save(cliente);
            ClienteDTO responseDTO = clienteMapper.toDTO(updatedCliente);
            return ResponseEntity.ok(responseDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            Cliente clienteEntity = cliente.get();
            clienteEntity.setActivo(false);
            clienteRepository.save(clienteEntity);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/activos")
    public ResponseEntity<List<ClienteDTO>> getClientesActivos() {
        List<Cliente> clientes = clienteRepository.findByActivoTrue();
        List<ClienteDTO> clientesDTO = clienteMapper.toDTOList(clientes);
        return ResponseEntity.ok(clientesDTO);
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<ClienteDTO> getClienteByDocumento(@PathVariable String documento) {
        Optional<Cliente> cliente = clienteRepository.findByDocumento(documento);
        if (cliente.isPresent()) {
            ClienteDTO clienteDTO = clienteMapper.toDTO(cliente.get());
            return ResponseEntity.ok(clienteDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteDTO>> buscarClientes(@RequestParam String nombre) {
        List<Cliente> clientes = clienteRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, nombre);
        List<ClienteDTO> clientesDTO = clienteMapper.toDTOList(clientes);
        return ResponseEntity.ok(clientesDTO);
    }
}