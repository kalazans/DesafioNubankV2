package com.desafio.nubank.desafio.domain.contato;

import com.desafio.nubank.desafio.domain.cliente.Cliente;
import com.desafio.nubank.desafio.dto.NovoContatoDTO;
import jakarta.persistence.*;

import lombok.EqualsAndHashCode;


import java.util.Objects;

@Entity(name = "contato")
@Table(name = "contato")
@EqualsAndHashCode(of = "id")
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false,unique = true)
    private String celular;
    @ManyToOne
    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCelular() {
        return celular;
    }

    public Contato(Long id,String nome, String email, String celular,Cliente cliente) {
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        this.cliente = cliente;
        this.id = id;
    }

    public Contato(){}
    public Contato(NovoContatoDTO novoContatoDTO,Cliente cliente){
        this.nome = novoContatoDTO.nome();
        this.celular = novoContatoDTO.celular();
        this.email = novoContatoDTO.emai();
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contato contato = (Contato) o;
        return Objects.equals(id, contato.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Contato{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", celular='" + celular + '\'' +
                '}';
    }
}
