package br.com.twobrothers.msvendas.controllers;

import br.com.twobrothers.msvendas.models.dto.ProdutoEstoqueDTO;
import br.com.twobrothers.msvendas.repositories.services.ProdutoEstoqueService;
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
@RequestMapping("vendas/produto")
public class ProdutoEstoqueController {

    @Autowired
    ProdutoEstoqueService service;

    @PostMapping
    public ResponseEntity<ProdutoEstoqueDTO> criaNovo(@RequestBody ProdutoEstoqueDTO produto) {
        return ResponseEntity.ok().body(service.criaNovo(produto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoEstoqueDTO>> buscaTodos() {
        return ResponseEntity.ok().body(service.buscaTodos());
    }

    @GetMapping("/id")
    public ResponseEntity<ProdutoEstoqueDTO> buscaPorId(@PathParam("id") Long value) {
        return ResponseEntity.ok().body(service.buscaPorId(value));
    }

    @GetMapping("/code")
    public ResponseEntity<ProdutoEstoqueDTO> buscaPorSigla(@PathParam("sigla") String value) {
        return ResponseEntity.ok().body(service.buscaPorSigla(value));
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<ProdutoEstoqueDTO>> buscaPorPaginacao(
            @PageableDefault(sort = "dataCadastro", direction = Sort.Direction.ASC, size = 5) Pageable paginacao) {
        return ResponseEntity.ok().body(service.buscaPorPaginacao(paginacao));
    }

    @GetMapping("/range")
    public ResponseEntity<List<ProdutoEstoqueDTO>> buscaPorRangeDeDataDeCadastro(@PathParam("inicio") String inicio, @PathParam("fim") String fim) {
        return ResponseEntity.ok().body(service.buscaPorRangeDeDataCadastro(inicio, fim));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoEstoqueDTO> atualiza(@PathVariable("id") Long id, @RequestBody ProdutoEstoqueDTO produto) {
        return ResponseEntity.ok().body(service.atualizaPorId(id, produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.deletaPorId(id));
    }

}
