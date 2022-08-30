package br.com.twobrothers.msvendas.controllers;

import br.com.twobrothers.msvendas.models.dto.ClienteDTO;
import br.com.twobrothers.msvendas.repositories.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@RestController
@RequestMapping("vendas/cliente")
public class ClienteController {

    @Autowired
    ClienteService service;

    @PostMapping
    public ResponseEntity<ClienteDTO> criaNovo(@RequestBody ClienteDTO cliente) {
        return ResponseEntity.ok().body(service.criaNovo(cliente));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> buscaTodos() {
        return ResponseEntity.ok().body(service.buscaTodos());
    }

    @GetMapping("/id")
    public ResponseEntity<ClienteDTO> buscaPorId(@PathParam("id") Long value) {
        return ResponseEntity.ok().body(service.buscaPorId(value));
    }

    @GetMapping("/name")
    public ResponseEntity<List<ClienteDTO>> buscaPorNome(@PathParam("nomeCompleto") String value) {
        return ResponseEntity.ok().body(service.buscaPorNomeCompleto(value));
    }

    @GetMapping("/cpfcnpj")
    public ResponseEntity<ClienteDTO> buscaPorCpfCnpj(@PathParam("cpfCnpj") String value) {
        return ResponseEntity.ok().body(service.buscaPorCpfCnpj(value));
    }

    @GetMapping("/phone")
    public ResponseEntity<List<ClienteDTO>> buscaPorTelefone(@PathParam("telefone") String value) {
        return ResponseEntity.ok().body(service.buscaPorTelefone(value));
    }

    @GetMapping("/email")
    public ResponseEntity<ClienteDTO> buscaPorEmail(@PathParam("email") String value) {
        return ResponseEntity.ok().body(service.buscaPorEmail(value));
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<ClienteDTO>> buscaPorPaginacao(
            @PageableDefault(sort = "dataCadastro", direction = Sort.Direction.ASC, size = 5) Pageable paginacao) {
        return ResponseEntity.ok().body(service.buscaPorPaginacao(paginacao));
    }

    @GetMapping("/range")
    public ResponseEntity<List<ClienteDTO>> buscaPorRangeDeDataDeCadastro(@PathParam("inicio") String inicio, @PathParam("fim") String fim) {
        return ResponseEntity.ok().body(service.buscaPorRangeDeDataCadastro(inicio, fim));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualiza(@PathVariable("id") Long id, @RequestBody ClienteDTO cliente) {
        return ResponseEntity.ok().body(service.atualizaPorId(id, cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.deletaPorId(id));
    }

}
