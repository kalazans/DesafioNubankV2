package com.desafio.nubank.desafio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CadastroClienteDTO(@NotNull @NotBlank @Pattern(regexp = "([a-zA-z])+") String nome,
                                 @NotBlank @NotNull @Email String email,
                                 @NotNull @NotBlank @Pattern(regexp = "\\d{3}[.]?\\d{3}[.]?\\d{3}-?\\d{2}") String cpf) {
}
