package com.api.baozistore.system.controller.Dtos;

import com.api.baozistore.system.model.Pedido;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record PedidoResponse(
        UUID id, UUID clienteId, List<ItemPedidoResponse> itens, BigDecimal total
) {
    public static PedidoResponse from(Pedido pedido) {
        List<ItemPedidoResponse> itens = pedido.getItens().stream()
                .map(item -> new ItemPedidoResponse(
                        item.getProduto().getIdProduto(),
                        item.getQuantidade(),
                        item.getPrecoUnitario(),
                        item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()))))
                .toList();

        BigDecimal total = itens.stream()
                .map(ItemPedidoResponse::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new PedidoResponse(pedido.getIdPedido(), pedido.getCliente().getIdCliente(), itens, total);
    }
}