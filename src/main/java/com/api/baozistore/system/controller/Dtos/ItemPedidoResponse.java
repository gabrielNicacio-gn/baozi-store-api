package com.api.baozistore.system.controller.Dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemPedidoResponse(UUID produtoId, int quantidade, BigDecimal precoUnitario, BigDecimal subtotal) {}
