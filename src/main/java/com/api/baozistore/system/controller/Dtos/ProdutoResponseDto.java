package com.api.baozistore.system.controller.Dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoResponseDto(UUID idProduto, String nome, BigDecimal preco, boolean emEstoque) {

}
