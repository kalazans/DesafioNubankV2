package com.desafio.nubank.desafio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record NovoContatoDTO(@NotBlank @NotNull @Pattern(regexp = "([a-zA-z])+") String nome,
                             @NotNull @NotBlank @Email String emai  ,
                             @NotBlank @NotNull String celular) {
}
