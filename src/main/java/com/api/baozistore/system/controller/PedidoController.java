package com.api.baozistore.system.controller;

import com.api.baozistore.system.controller.Dtos.AdicionarPedido;
import com.api.baozistore.system.controller.Dtos.PedidoResponse;
import com.api.baozistore.system.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> addPedido(@RequestBody AdicionarPedido pedido) {
        PedidoResponse response = pedidoService.addPedido(pedido);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> getPedidos() {
        return ResponseEntity.ok(pedidoService.getPedidos());
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoResponse> getPedidoPorId(@PathVariable UUID idPedido) {
        return ResponseEntity.ok(pedidoService.getPedidoPorId(idPedido));
    }

    @DeleteMapping("/{idPedido}")
    public ResponseEntity<Void> deletePedidoPorId(@PathVariable UUID idPedido) {
        pedidoService.deletePedidoPorId(idPedido);
        return ResponseEntity.noContent().build();
    }
}
