package com.api.baozistore.system.controller.Dtos;

import java.time.LocalDate;
import java.util.UUID;

public record ClienteResponseDto(UUID idCliente, String nome, LocalDate clienteDesde) {
}
