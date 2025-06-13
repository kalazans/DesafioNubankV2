package com.desafio.nubank.desafio.controller;

import com.desafio.nubank.desafio.dto.CadastroClienteDTO;
import com.desafio.nubank.desafio.dto.ClienteDTO;
import com.desafio.nubank.desafio.dto.NovoContatoDTO;
import com.desafio.nubank.desafio.infra.advice.ApiError;
import com.desafio.nubank.desafio.service.ClienteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    public ClienteController(ClienteService clienteService){
        this.clienteService  = clienteService;
    }

    @PostMapping("/post")
    @Transactional
    public ResponseEntity<Object> inserirNoDB(@RequestBody @Valid CadastroClienteDTO cadastroClienteDTO){
        this.clienteService.inserirNoDB(cadastroClienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = {"/post/contato/{idCliente}","/post/contato"})
    @Transactional
    public ResponseEntity<Object> inserirContatoEmCliente(@RequestBody @Valid NovoContatoDTO novoContatoDTO,
                                                              @PathVariable(value = "idCliente",required = false) Optional<Long> idCliente){
        if(idCliente.isEmpty()){
            return ResponseEntity.badRequest().body("variavel de caminho vazia");
        }
        this.clienteService.inserirContatoEmCliente(novoContatoDTO,idCliente.get());
        return ResponseEntity.ok().build();


    }
    @GetMapping("/get")
    public ResponseEntity<List<ClienteDTO>> listarClientes(){
        return ResponseEntity.ok(this.clienteService.listarClientes());

    }
    @GetMapping(value = {"/get/contatos/{id}","/get/contatos"})
    public ResponseEntity<Object> listarContatosPorIdCliente(@PathVariable(required = false,name = "id") Optional<Long> id) {
        if(id.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiError(HttpStatus.BAD_REQUEST,"path variable missing","path variable missing", LocalDateTime.now()));
        }
     return ResponseEntity.ok(this.clienteService.listarContatoPorIdCliente(id.get()));
    }

}
