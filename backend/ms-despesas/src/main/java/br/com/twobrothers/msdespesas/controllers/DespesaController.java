package br.com.twobrothers.msdespesas.controllers;

import br.com.twobrothers.msdespesas.config.ModelMapperConfig;
import br.com.twobrothers.msdespesas.models.dto.DespesaDTO;
import br.com.twobrothers.msdespesas.models.entities.DespesaEntity;
import br.com.twobrothers.msdespesas.repositories.DespesaRepository;
import br.com.twobrothers.msdespesas.services.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    DespesaService service;

    @Autowired
    DespesaRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    @PostMapping
    public ResponseEntity<DespesaDTO> novaDespesa(@RequestBody DespesaDTO despesa) {
        return ResponseEntity.ok().body(service.criaNovaDespesa(despesa));
    }

    @GetMapping
    public ResponseEntity<List<DespesaEntity>> todasDespesas() {
        return ResponseEntity.ok().body(repository.findAll());
    }

    @GetMapping("/range{dataInicio}{dataFim}")
    public ResponseEntity<List<DespesaDTO>> buscaPorRangeDeDataDeCadastro(@PathParam("dataInicio") LocalDate dataInicio, @PathParam("dataFim") LocalDate dataFim) {
        return ResponseEntity.ok().body(service.buscaPorRangeDeDataCadastro(dataInicio, dataFim));
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<DespesaDTO>> buscaDespesasPorPaginacao(@PageableDefault(sort = {"dataAgendamento", "dataCadastro"},
            direction = Sort.Direction.ASC, size = 10, page = 0) Pageable paginacao) {
        return ResponseEntity.ok().body(service.buscaPorPaginacao(paginacao));
    }
}
