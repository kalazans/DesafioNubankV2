package com.desafio.nubank.desafio.dto;

import com.desafio.nubank.desafio.domain.contato.Contato;

import java.util.List;

public record ClienteDTO(String nome, String email, List<Contato> contatos) {
}
