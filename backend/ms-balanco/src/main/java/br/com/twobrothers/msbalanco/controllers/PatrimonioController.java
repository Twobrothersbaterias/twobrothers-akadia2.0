package br.com.twobrothers.msbalanco.controllers;

import br.com.twobrothers.msbalanco.models.dto.PatrimonioDTO;
import br.com.twobrothers.msbalanco.services.PatrimonioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;

@RestController
@RequestMapping("/patrimonio")
public class PatrimonioController {

    @Autowired
    PatrimonioService service;

    @PostMapping
    public ResponseEntity<PatrimonioDTO> novoPatrimonio(@RequestBody PatrimonioDTO patrimonio) {
        return ResponseEntity.ok().body(service.criaNovo(patrimonio));
    }

    @GetMapping
    public ResponseEntity<List<PatrimonioDTO>> todosPatrimonios() {
        return ResponseEntity.ok().body(service.buscaTodos());
    }

    @GetMapping("/range")
    public ResponseEntity<List<PatrimonioDTO>> buscaPorRangeDeDataDeCadastro(@PathParam("inicio") String inicio, @PathParam("fim") String fim) {
        return ResponseEntity.ok().body(service.buscaPorRangeDeDataCadastro(inicio, fim));
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<PatrimonioDTO>> buscaPatrimoniosPorPaginacao(@PageableDefault(sort = {"dataAgendamento", "dataCadastro"},
            direction = Sort.Direction.ASC, size = 5) Pageable paginacao) {
        return ResponseEntity.ok().body(service.buscaPorPaginacao(paginacao));
    }

    @GetMapping("/description")
    public ResponseEntity<List<PatrimonioDTO>> buscaPorDescricao(@PathParam("descricao") String descricao) {
        return ResponseEntity.ok().body(service.buscaPorDescricao(descricao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatrimonioDTO> atualizaPatrimonio(@PathVariable("id") Long id, @RequestBody PatrimonioDTO patrimonio) {
        return ResponseEntity.ok().body(service.atualizaPorId(id, patrimonio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> removePatrimonio(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.deletaPorId(id));
    }

}
