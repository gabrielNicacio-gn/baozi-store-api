package com.api.baozistore.system.service;

import com.api.baozistore.system.controller.Dtos.AdicionarClienteDto;
import com.api.baozistore.system.controller.Dtos.ClienteResponseDto;
import com.api.baozistore.system.model.Cliente;
import com.api.baozistore.system.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteResponseDto addClinte(AdicionarClienteDto cliente){
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(cliente.nome());
        Cliente clienteSalvo = clienteRepository.save(novoCliente);
        return new ClienteResponseDto(clienteSalvo.getIdCliente(), clienteSalvo.getNome(), 
                clienteSalvo.getClienteDesde());
    }

    public ClienteResponseDto getClientePorId(UUID idCliente){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Id %s não corresponde a nenhum cliente existente", idCliente)));
        return new ClienteResponseDto(cliente.getIdCliente(), cliente.getNome(), cliente.getClienteDesde());
    }

    public List<ClienteResponseDto> getClientes(){
        return clienteRepository.findAllByOrderByClienteDesdeAsc()
                .stream().map(cliente -> new ClienteResponseDto(cliente.getIdCliente(), cliente.getNome(),
                        cliente.getClienteDesde())).toList();
    }

    public void deleteClientePorId(UUID idCliente){
        Cliente clienteDelete = clienteRepository.findById(idCliente)
                        .orElseThrow(()->
                                new EntityNotFoundException(String.format("Id %s não corresponde a nenhum cliente existente", idCliente)));
        clienteRepository.delete(clienteDelete);
    }

}




