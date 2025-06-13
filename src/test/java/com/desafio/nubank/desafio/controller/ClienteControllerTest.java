package com.desafio.nubank.desafio.controller;

import com.desafio.nubank.desafio.dto.CadastroClienteDTO;
import com.desafio.nubank.desafio.dto.NovoContatoDTO;
import com.desafio.nubank.desafio.infra.advice.ApiError;
import com.desafio.nubank.desafio.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ClienteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<CadastroClienteDTO> cadastroClienteDTOJacksonTester;
    @Autowired
    private JacksonTester<NovoContatoDTO> novoContatoDTOJacksonTester;
    @MockitoBean
    private ClienteService clienteService;


    @Test
    @DisplayName("deve retornar 400 se a requisição vier sem json")
    void inserir_no_db_sem_json() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(post("/api/v1/clientes/post")).andReturn().getResponse();
        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("deve retornar 400 se o json estiver não formarto certo")
    void inserir_no_db_json_invalido() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc
                .perform(
                        post("/api/v1/clientes/post")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(cadastroClienteDTOJacksonTester.write(new CadastroClienteDTO("teste1","testegmail.com","15a5")).getJson()))
                .andReturn()
                .getResponse();
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        ApiError apiError = mapper.readValue(mockHttpServletResponse.getContentAsString(),ApiError.class);
        assertThat(apiError.getMessage()).isEqualTo("campos invalidos no json");
        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("deve retornar 201 se o json estiver certo")
    void inserir_no_db() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc
                .perform(
                        post("/api/v1/clientes/post")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(cadastroClienteDTOJacksonTester.write(new CadastroClienteDTO("teste", "testeas@gmail.com", "155.322.111-00")).getJson()))
                .andReturn()
                .getResponse();
        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }
    @Test
    @DisplayName("deve retornar 400 se o json for invalido")
    void inserir_Contato_Em_Cliente_json_invalido() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc
                .perform(
                        post("/api/v1/clientes/post/contato/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(novoContatoDTOJacksonTester.write(new NovoContatoDTO("teste1", "testeasgmail.com", "15a.322.111-00")).getJson()))
                .andReturn()
                .getResponse();
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        ApiError apiError = mapper.readValue(mockHttpServletResponse.getContentAsString(),ApiError.class);
        assertThat(apiError.getMessage()).isEqualTo("campos invalidos no json");
        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("deve retornar 400 se a variavel de caminho estiver vazia")
    void inserir_Contato_Em_Cliente_erro_sem_path_variable() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc
                .perform(
                        post("/api/v1/clientes/post/contato")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(novoContatoDTOJacksonTester.write(new NovoContatoDTO("teste", "testea@gmail.com", "151.322.111-00")).getJson()))
                .andReturn()
                .getResponse();
        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("deve retornar 400 se a variavel de caminho nao for um numero")
    void inserir_Contato_Em_Cliente_erro_path_variable_nao_e_numero() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc
                .perform(
                        post("/api/v1/clientes/post/contato/a")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(novoContatoDTOJacksonTester.write(new NovoContatoDTO("teste", "testea@gmail.com", "151.322.111-00")).getJson()))
                .andReturn()
                .getResponse();
        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }
    @Test
    @DisplayName("deve retornar 400 se a variavel de caminho estiver vazia")
    void listar_Contatos_Por_IdCliente_erro_sem_pathVariavel() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v1/clientes/get/contatos")).andReturn().getResponse();
        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }
    @Test
    @DisplayName("deve retornar 400 se a variavel de caminho nao for um numero")
    void listar_Contatos_Por_IdCliente_erro_pathVariavel_nao_e_numero() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v1/clientes/get/contatos/a")).andReturn().getResponse();
        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }




}