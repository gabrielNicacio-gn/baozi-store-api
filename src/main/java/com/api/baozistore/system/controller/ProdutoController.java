package com.api.baozistore.system.controller;

import com.api.baozistore.system.controller.Dtos.AdicionarProdutoDto;
import com.api.baozistore.system.controller.Dtos.ProdutoResponseDto;
import com.api.baozistore.system.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDto> addProduto(@RequestBody AdicionarProdutoDto dto) {
        ProdutoResponseDto produtoSalvo = produtoService.addProduto(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produtoSalvo.idProduto())
                .toUri();
        return ResponseEntity.created(location).body(produtoSalvo);
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<ProdutoResponseDto> getProdutoPorId(@PathVariable UUID idProduto) {
        return ResponseEntity.ok(produtoService.getProdutoPorId(idProduto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDto>> getProdutos() {
        return ResponseEntity.ok(produtoService.getProdutos());
    }

    @DeleteMapping("/{idProduto}")
    public ResponseEntity<Void> deleteProdutoPorId(@PathVariable UUID idProduto) {
        produtoService.deleteClientePorId(idProduto);
        return ResponseEntity.noContent().build();
    }
}