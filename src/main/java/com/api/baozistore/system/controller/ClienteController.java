package com.api.baozistore.system.controller;

import com.api.baozistore.system.controller.Dtos.AdicionarClienteDto;
import com.api.baozistore.system.controller.Dtos.ClienteResponseDto;
import com.api.baozistore.system.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> adicionarCliente(@RequestBody AdicionarClienteDto cliente) {
        ClienteResponseDto clienteSalvo = clienteService.addClinte(cliente);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clienteSalvo.idCliente())
                .toUri();
        return ResponseEntity.created(location).body(clienteSalvo);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteResponseDto> buscarClientePorId(@PathVariable UUID idCliente) {
        ClienteResponseDto cliente = clienteService.getClientePorId(idCliente);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> listarClientes() {
        List<ClienteResponseDto> clientes = clienteService.getClientes();
        return ResponseEntity.ok(clientes);
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> deletarClientePorId(@PathVariable UUID idCliente) {
        clienteService.deleteClientePorId(idCliente);
        return ResponseEntity.noContent().build();
    }
}