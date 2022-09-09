package br.com.twobrothers.frontend.controllers.api;

import br.com.twobrothers.frontend.models.dto.AbastecimentoDTO;
import br.com.twobrothers.frontend.repositories.services.AbastecimentoCrudService;
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
@RequestMapping("api/abastecimento")
public class AbastecimentoResource {

    @Autowired
    AbastecimentoCrudService service;

    @PostMapping
    public ResponseEntity<AbastecimentoDTO> criaNovo(@RequestBody AbastecimentoDTO abastecimento,
                                                     @PathParam("idFornecedor") Long idFornecedor,
                                                     @PathParam("idProduto") Long idProduto) {
        return ResponseEntity.ok().body(service.criaNovo(abastecimento, idProduto, idFornecedor));
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
            @PageableDefault(sort = "dataCadastro", direction = Sort.Direction.ASC, size = 5) Pageable paginacao) {
        return ResponseEntity.ok().body(service.buscaPorPaginacao(paginacao));
    }

    @GetMapping("/range")
    public ResponseEntity<List<AbastecimentoDTO>> buscaPorRangeDeDataDeCadastro(@PathParam("inicio") String inicio, @PathParam("fim") String fim) {
        return ResponseEntity.ok().body(service.buscaPorRangeDeDataCadastro(inicio, fim));
    }

}
