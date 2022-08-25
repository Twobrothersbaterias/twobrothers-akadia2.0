package br.com.twobrothers.msvendas.controllers;

import br.com.twobrothers.msvendas.models.dto.EnderecoDTO;
import br.com.twobrothers.msvendas.repositories.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;

@RestController
@RequestMapping("vendas/endereco")
public class EnderecoController {

    @Autowired
    EnderecoService service;

    @PostMapping
    public ResponseEntity<EnderecoDTO> criaNovo(@RequestBody EnderecoDTO endereco) {
        return ResponseEntity.ok().body(service.criaNovo(endereco));
    }

    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> buscaTodos() {
        return ResponseEntity.ok().body(service.buscaTodos());
    }

    @GetMapping("/id")
    public ResponseEntity<EnderecoDTO> buscaPorId(@PathParam("id") Long value) {
        return ResponseEntity.ok().body(service.buscaPorId(value));
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<EnderecoDTO>> buscaPorPaginacao(
            @PageableDefault(sort = "dataCadastro", direction = Sort.Direction.ASC, page = 0, size = 5) Pageable paginacao) {
        return ResponseEntity.ok().body(service.buscaPorPaginacao(paginacao));
    }

    @GetMapping("/range")
    public ResponseEntity<List<EnderecoDTO>> buscaPorRangeDeDataDeCadastro(@PathParam("inicio") String inicio, @PathParam("fim") String fim) {
        return ResponseEntity.ok().body(service.buscaPorRangeDeDataCadastro(inicio, fim));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTO> atualiza(@PathVariable("id") Long id, @RequestBody EnderecoDTO endereco) {
        return ResponseEntity.ok().body(service.atualizaPorId(id, endereco));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.deletaPorId(id));
    }

}
