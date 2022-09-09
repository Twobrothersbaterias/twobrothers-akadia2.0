package br.com.twobrothers.frontend.controllers.api;

import br.com.twobrothers.frontend.models.dto.DespesaDTO;
import br.com.twobrothers.frontend.repositories.services.DespesaCrudService;
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
 * @version 1.0
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @since 30-08-22
 */
@RestController
@RequestMapping("api/despesas")
public class DespesaResource {

    @Autowired
    DespesaCrudService service;

    @PostMapping
    public ResponseEntity<DespesaDTO> novaDespesa(@RequestBody DespesaDTO despesa) {
        return ResponseEntity.ok().body(service.criaNovaDespesa(despesa));
    }

    @GetMapping
    public ResponseEntity<List<DespesaDTO>> todasDespesas() {
        return ResponseEntity.ok().body(service.buscaTodasAsDespesas());
    }

    @GetMapping("/range")
    public ResponseEntity<List<DespesaDTO>> buscaPorRangeDeDataDeCadastro(@PathParam("inicio") String inicio, @PathParam("fim") String fim) {
        return ResponseEntity.ok().body(service.buscaPorRangeDeDataCadastro(inicio, fim));
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<DespesaDTO>> buscaDespesasPorPaginacao(@PageableDefault(sort = {"dataAgendamento", "dataCadastro"},
            direction = Sort.Direction.ASC, size = 5) Pageable paginacao) {
        return ResponseEntity.ok().body(service.buscaPorPaginacao(paginacao));
    }

    @GetMapping("/description")
    public ResponseEntity<List<DespesaDTO>> buscaDespesaPorDescricao(@PathParam("descricao") String descricao) {
        return ResponseEntity.ok().body(service.buscaPorDescricao(descricao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaDTO> atualizaDespesa(@PathVariable("id") Long id, @RequestBody DespesaDTO despesa) {
        return ResponseEntity.ok().body(service.atualizaPorId(id, despesa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> removeDespesa(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.deletaDespesaPorId(id));
    }


}
