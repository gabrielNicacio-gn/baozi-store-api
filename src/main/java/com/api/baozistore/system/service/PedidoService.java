package com.api.baozistore.system.service;

import com.api.baozistore.system.controller.Dtos.*;
import com.api.baozistore.system.model.Cliente;
import com.api.baozistore.system.model.ItemPedido;
import com.api.baozistore.system.model.Pedido;
import com.api.baozistore.system.model.Produto;
import com.api.baozistore.system.repository.ClienteRepository;
import com.api.baozistore.system.repository.PedidoRepository;
import com.api.baozistore.system.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         ClienteRepository clienteRepository,
                         ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public PedidoResponse addPedido(AdicionarPedido pedido){
        Cliente cliente = clienteRepository.findById(pedido.clienteId())
                .orElseThrow(()-> new EntityNotFoundException
                        (String.format("Id %s não corresponde a nenhum Cliente",pedido.clienteId())));

        List<UUID> ids = pedido.itens().stream()
                .map(AdicionarItemPedido::produtoId)
                .toList();

        Map<UUID, Produto> produtos = produtoRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(Produto::getIdProduto, Function.identity()));

        Pedido novoPedido = new Pedido();
        novoPedido.setCliente(cliente);

        for(AdicionarItemPedido itemPedido : pedido.itens()){
            Produto produto = produtos.get(itemPedido.produtoId());

            if (produto == null){
                throw new IllegalStateException(String.format("Id %s não encontrado",itemPedido.produtoId()));
            }
            if (!produto.isEmEstoque()){
                throw new IllegalStateException(String.format("Produto fora do estoque: Id %s",produto.getIdProduto()));
            }
            ItemPedido novoItemPedido = new ItemPedido();
            novoItemPedido.setProduto(produto);
            novoItemPedido.setPrecoUnitario(produto.getPreco());
            novoItemPedido.setQuantidade(itemPedido.quantidade());

            novoPedido.adicionarItem(novoItemPedido);
        }
        return PedidoResponse.from(pedidoRepository.save(novoPedido));
    }

    @Transactional()
    public List<PedidoResponse> getPedidos() {
        return pedidoRepository.findAll().stream()
                .map(PedidoResponse::from)
                .toList();
    }

    @Transactional()
    public PedidoResponse getPedidoPorId(UUID idPedido) {
        return pedidoRepository.findById(idPedido)
                .map(PedidoResponse::from)
                .orElseThrow(() -> new EntityNotFoundException(String.format("%s Pedido não encontrado: ", idPedido)));
    }

    @Transactional
    public void deletePedidoPorId(UUID idPedido) {
        if (!pedidoRepository.existsById(idPedido)) {
            throw new EntityNotFoundException(String.format("%s Pedido não encontrado: ", idPedido));
        }
        pedidoRepository.deleteById(idPedido);
    }
}
