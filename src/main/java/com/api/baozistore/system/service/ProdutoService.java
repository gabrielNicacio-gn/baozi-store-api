package com.api.baozistore.system.service;

import com.api.baozistore.system.controller.Dtos.AdicionarClienteDto;
import com.api.baozistore.system.controller.Dtos.AdicionarProdutoDto;
import com.api.baozistore.system.controller.Dtos.ClienteResponseDto;
import com.api.baozistore.system.controller.Dtos.ProdutoResponseDto;
import com.api.baozistore.system.model.Cliente;
import com.api.baozistore.system.model.Produto;
import com.api.baozistore.system.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository ;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoResponseDto addProduto(AdicionarProdutoDto produto){
        Produto novoProduto = new Produto();
        novoProduto.setNome(produto.nome());
        novoProduto.setPreco(produto.preco());
        Produto produtoSalvo = produtoRepository.save(novoProduto);
        return new ProdutoResponseDto(produtoSalvo.getIdProduto(), produtoSalvo.getNome(),
                produtoSalvo.getPreco(),produtoSalvo.isEmEstoque());
    }

    public ProdutoResponseDto getProdutoPorId(UUID idProduto){
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Id %s não corresponde a nenhum produto existente",idProduto)));
        return new ProdutoResponseDto(produto.getIdProduto(), produto.getNome(), produto.getPreco(), produto.isEmEstoque());
    }

    public List<ProdutoResponseDto> getProdutos(){
        return produtoRepository.findAllByEmEstoqueTrue()
                .stream()
                .map(produto -> new ProdutoResponseDto(produto.getIdProduto(),produto.getNome(), produto.getPreco(),produto.isEmEstoque()))
                .toList();
    }

    public void deleteClientePorId(UUID idProduto){
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Id %s não corresponde a nenhum produto existente",idProduto)));
        produtoRepository.delete(produto);
    }
}
