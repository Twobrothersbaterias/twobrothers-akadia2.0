package br.com.twobrothers.msvendas.controllers;

import br.com.twobrothers.msvendas.models.dto.AbastecimentoDTO;
import br.com.twobrothers.msvendas.repositories.services.AbastecimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;

@RestController
@RequestMapping("vendas/abastecimento")
public class AbastecimentoController {

    @Autowired
    AbastecimentoService service;

    @PostMapping
    public ResponseEntity<AbastecimentoDTO> criaNovo(@RequestBody AbastecimentoDTO abastecimento) {
        return ResponseEntity.ok().body(service.criaNovo(abastecimento));
    }

    @GetMapping
    public ResponseEntity<List<AbastecimentoDTO>> buscaTodos() {
        return ResponseEntity.ok().body(service.buscaTodos());
    }

    @GetMapping("/id")
    public ResponseEntity<AbastecimentoDTO> buscaPorId(@PathParam("id") Long value) {
        return ResponseEntity.ok().body(service.buscaPorId(value));
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<AbastecimentoDTO>> buscaPorPaginacao(
            @PageableDefault(sort = "dataCadastro", direction = Sort.Direction.ASC, page = 0, size = 5) Pageable paginacao) {
        return ResponseEntity.ok().body(service.buscaPorPaginacao(paginacao));
    }

    @GetMapping("/range")
    public ResponseEntity<List<AbastecimentoDTO>> buscaPorRangeDeDataDeCadastro(@PathParam("inicio") String inicio, @PathParam("fim") String fim) {
        return ResponseEntity.ok().body(service.buscaPorRangeDeDataCadastro(inicio, fim));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbastecimentoDTO> atualiza(@PathVariable("id") Long id, @RequestBody AbastecimentoDTO abastecimento) {
        return ResponseEntity.ok().body(service.atualizaPorId(id, abastecimento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.deletaPorId(id));
    }

}
