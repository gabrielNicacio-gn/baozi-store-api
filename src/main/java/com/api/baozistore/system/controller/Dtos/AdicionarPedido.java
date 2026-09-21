package com.api.baozistore.system.controller.Dtos;

import java.util.List;
import java.util.UUID;

public record AdicionarPedido(UUID clienteId, List<AdicionarItemPedido> itens) {
}
