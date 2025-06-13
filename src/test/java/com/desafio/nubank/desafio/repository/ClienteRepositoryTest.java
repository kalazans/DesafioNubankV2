package com.desafio.nubank.desafio.repository;

import com.desafio.nubank.desafio.domain.cliente.Cliente;
import com.desafio.nubank.desafio.domain.contato.Contato;
import com.desafio.nubank.desafio.dto.CadastroClienteDTO;
import com.desafio.nubank.desafio.dto.ContatoDTO;
import com.desafio.nubank.desafio.dto.NovoContatoDTO;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ClienteRepositoryTest {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("deve retorna uma lista de projection ContatoDTO em vez da entitdade Contato")
    void listar_Contato_Por_IdCliente() {
        Cliente cliente =  cliente(cadastroClienteDTO("wendell","emtail@gmail.com","155.000.000-20"));
        Contato contato = contato(novoContatoDTO("jao","jao@gmail.com","55555"),cliente);
        List<ContatoDTO> contatoDTOList = this.clienteRepository.listarContatoPorIdCliente(1L);
        assertThat(contatoDTOList.get(0)).isInstanceOf(ContatoDTO.class);
    }

    private Cliente cliente(CadastroClienteDTO cadastroClienteDTO){
        Cliente cliente = new Cliente(cadastroClienteDTO);
        testEntityManager.persist(cliente);
        return cliente;
    }
    private Contato contato(NovoContatoDTO novoContatoDTO,Cliente cliente){
        Contato contato = new Contato(novoContatoDTO,cliente);
        testEntityManager.persist(contato);
        return contato;
    }
    private CadastroClienteDTO cadastroClienteDTO(String nome,String email,String cpf){
        return new CadastroClienteDTO(nome,email,cpf);
    }

    private NovoContatoDTO novoContatoDTO(String nome,String email,String celular){
        return new NovoContatoDTO(nome,email,celular);
    }


    }
