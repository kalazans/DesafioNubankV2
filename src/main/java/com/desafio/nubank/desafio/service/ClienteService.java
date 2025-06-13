package com.desafio.nubank.desafio.service;

import com.desafio.nubank.desafio.domain.cliente.Cliente;
import com.desafio.nubank.desafio.domain.contato.Contato;
import com.desafio.nubank.desafio.dto.CadastroClienteDTO;
import com.desafio.nubank.desafio.dto.ClienteDTO;
import com.desafio.nubank.desafio.dto.ContatoDTO;
import com.desafio.nubank.desafio.dto.NovoContatoDTO;
import com.desafio.nubank.desafio.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public void inserirNoDB(CadastroClienteDTO cadastroClienteDTO){
        this.clienteRepository.save(new Cliente(cadastroClienteDTO));
    }

    public void inserirContatoEmCliente(NovoContatoDTO novoContatoDTO,Long idCliente){
        Optional<Cliente> cliente = this.clienteRepository.findById(idCliente);
        if(cliente.isPresent()){
            cliente.get().getContatos().add(new Contato(novoContatoDTO,cliente.get()));

        } else{
            throw new EntityNotFoundException("id do cliente nao existe no banco de dados");
        }
    }

    public List<ClienteDTO> listarClientes(){
        return this.clienteRepository
                .findAll()
                .stream()
                .map(cli->new ClienteDTO(cli.getNome(),cli.getEmail(),cli.getContatos())).toList();

    }
    public List<ContatoDTO> listarContatoPorIdCliente(Long id){
        if(this.clienteRepository.existsById(id)) {
            return this.clienteRepository.listarContatoPorIdCliente(id);
        }
        return new ArrayList<>();
    }

}
