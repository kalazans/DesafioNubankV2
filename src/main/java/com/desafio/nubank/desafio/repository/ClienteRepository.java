package com.desafio.nubank.desafio.repository;

import com.desafio.nubank.desafio.domain.cliente.Cliente;
import com.desafio.nubank.desafio.dto.ClienteDTO;
import com.desafio.nubank.desafio.dto.ContatoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    /*@Query("""
(select new com.desafio.nubank.desafio.dto.ClienteDTO(c.nome,c.email,
(select new com.desafio.nubank.desafio.dto.ContatoDTO(co.nome,co.email,co.celular) from contato co where c.id = co.cliente) from cliente c)""")
    List<ClienteDTO> listarTodosClientes();*/
    @Query("select new  com.desafio.nubank.desafio.dto.ContatoDTO(con.nome,con.email,con.celular) from contato con where con.cliente.id = :id")
    List<ContatoDTO> listarContatoPorIdCliente(Long id);
}
