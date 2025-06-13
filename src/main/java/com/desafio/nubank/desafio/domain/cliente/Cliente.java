package com.desafio.nubank.desafio.domain.cliente;

import com.desafio.nubank.desafio.domain.contato.Contato;
import com.desafio.nubank.desafio.dto.CadastroClienteDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "cliente")
@Table(name = "cliente")

@EqualsAndHashCode(of = "id")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false,unique = true)
    private String cpf;
    @OneToMany(mappedBy = "cliente",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Contato> contatos;
    public Cliente(){}

    public Cliente(String nome, String email, String cpf,Long id) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.id = id;
    }

    public Cliente(CadastroClienteDTO cadastroClienteDTO){
        this.nome = cadastroClienteDTO.nome();
        this.email = cadastroClienteDTO.email();
        this.cpf = cadastroClienteDTO.cpf();
        this.contatos = new ArrayList<>();

    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", contatos=" + contatos +
                '}';
    }
}
