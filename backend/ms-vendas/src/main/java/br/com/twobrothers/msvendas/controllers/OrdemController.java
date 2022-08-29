package br.com.twobrothers.msvendas.controllers;

import br.com.twobrothers.msvendas.models.dto.OrdemDTO;
import br.com.twobrothers.msvendas.repositories.services.OrdemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;

@RestController
@RequestMapping("vendas/ordem")
public class OrdemController {

    @Autowired
    OrdemService service;

    @PostMapping
    public ResponseEntity<OrdemDTO> criaNovo(@RequestBody OrdemDTO ordem) {
        return ResponseEntity.ok().body(service.criaNovo(ordem, null));
    }

    @GetMapping
    public ResponseEntity<List<OrdemDTO>> buscaTodos() {
        return ResponseEntity.ok().body(service.buscaTodos());
    }

    @GetMapping("/id")
    public ResponseEntity<OrdemDTO> buscaPorId(@PathParam("id") Long value) {
        return ResponseEntity.ok().body(service.buscaPorId(value));
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<OrdemDTO>> buscaPorPaginacao(
            @PageableDefault(sort = "dataCadastro", direction = Sort.Direction.ASC, page = 0, size = 5) Pageable paginacao) {
        return ResponseEntity.ok().body(service.buscaPorPaginacao(paginacao));
    }

    @GetMapping("/range")
    public ResponseEntity<List<OrdemDTO>> buscaPorRangeDeDataDeCadastro(@PathParam("inicio") String inicio, @PathParam("fim") String fim) {
        return ResponseEntity.ok().body(service.buscaPorRangeDeDataCadastro(inicio, fim));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdemDTO> atualiza(@PathVariable("id") Long id, @RequestBody OrdemDTO ordem) {
        return ResponseEntity.ok().body(service.atualizaPorId(id, ordem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.deletaPorId(id));
    }

}
