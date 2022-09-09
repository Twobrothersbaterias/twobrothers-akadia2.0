package br.com.twobrothers.frontend.controllers.api;

import br.com.twobrothers.frontend.models.dto.PrecoFornecedorDTO;
import br.com.twobrothers.frontend.repositories.services.PrecoFornecedorCrudService;
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
@RequestMapping("api/preco")
public class PrecoFornecedorResource {

    @Autowired
    PrecoFornecedorCrudService service;

    @PostMapping
    public ResponseEntity<PrecoFornecedorDTO> criaNovo(@RequestBody PrecoFornecedorDTO preco,
                                                       @PathParam("idFornecedor") Long idFornecedor,
                                                       @PathParam("idProduto") Long idProduto) {
        return ResponseEntity.ok().body(service.criaNovo(preco, idFornecedor, idProduto));
    }

    @GetMapping
    public ResponseEntity<List<PrecoFornecedorDTO>> buscaTodos() {
        return ResponseEntity.ok().body(service.buscaTodos());
    }

    @GetMapping("/id")
    public ResponseEntity<PrecoFornecedorDTO> buscaPorId(@PathParam("id") Long value) {
        return ResponseEntity.ok().body(service.buscaPorId(value));
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<PrecoFornecedorDTO>> buscaPorPaginacao(
            @PageableDefault(sort = "dataCadastro", direction = Sort.Direction.ASC, size = 5) Pageable paginacao) {
        return ResponseEntity.ok().body(service.buscaPorPaginacao(paginacao));
    }

    @GetMapping("/range")
    public ResponseEntity<List<PrecoFornecedorDTO>> buscaPorRangeDeDataDeCadastro(@PathParam("inicio") String inicio, @PathParam("fim") String fim) {
        return ResponseEntity.ok().body(service.buscaPorRangeDeDataCadastro(inicio, fim));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrecoFornecedorDTO> atualiza(@PathVariable("id") Long id, @RequestBody PrecoFornecedorDTO preco) {
        return ResponseEntity.ok().body(service.atualizaPorId(id, preco));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.deletaPorId(id));
    }

}
