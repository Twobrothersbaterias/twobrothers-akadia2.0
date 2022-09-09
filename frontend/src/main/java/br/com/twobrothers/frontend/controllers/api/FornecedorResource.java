package br.com.twobrothers.frontend.controllers.api;

import br.com.twobrothers.frontend.models.dto.FornecedorDTO;
import br.com.twobrothers.frontend.repositories.services.FornecedorCrudService;
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
@RequestMapping("api/fornecedor")
public class FornecedorResource {

    @Autowired
    FornecedorCrudService service;

    @PostMapping
    public ResponseEntity<FornecedorDTO> criaNovo(@RequestBody FornecedorDTO fornecedor) {
        return ResponseEntity.ok().body(service.criaNovo(fornecedor));
    }

    @GetMapping
    public ResponseEntity<List<FornecedorDTO>> buscaTodos() {
        return ResponseEntity.ok().body(service.buscaTodos());
    }

    @GetMapping("/id")
    public ResponseEntity<FornecedorDTO> buscaPorId(@PathParam("id") Long value) {
        return ResponseEntity.ok().body(service.buscaPorId(value));
    }

    @GetMapping("/name")
    public ResponseEntity<List<FornecedorDTO>> buscaPorNome(@PathParam("nome") String value) {
        return ResponseEntity.ok().body(service.buscaPorNome(value));
    }

    @GetMapping("/cpfcnpj")
    public ResponseEntity<FornecedorDTO> buscaPorCpfCnpj(@PathParam("cpfCnpj") String value) {
        return ResponseEntity.ok().body(service.buscaPorCpfCnpj(value));
    }

    @GetMapping("/phone")
    public ResponseEntity<List<FornecedorDTO>> buscaPorTelefone(@PathParam("telefone") String value) {
        return ResponseEntity.ok().body(service.buscaPorTelefone(value));
    }

    @GetMapping("/email")
    public ResponseEntity<FornecedorDTO> buscaPorEmail(@PathParam("email") String value) {
        return ResponseEntity.ok().body(service.buscaPorEmail(value));
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<FornecedorDTO>> buscaPorPaginacao(
            @PageableDefault(sort = "dataCadastro", direction = Sort.Direction.ASC, size = 5) Pageable paginacao) {
        return ResponseEntity.ok().body(service.buscaPorPaginacao(paginacao));
    }

    @GetMapping("/range")
    public ResponseEntity<List<FornecedorDTO>> buscaPorRangeDeDataDeCadastro(@PathParam("inicio") String inicio, @PathParam("fim") String fim) {
        return ResponseEntity.ok().body(service.buscaPorRangeDeDataCadastro(inicio, fim));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorDTO> atualiza(@PathVariable("id") Long id, @RequestBody FornecedorDTO fornecedor) {
        return ResponseEntity.ok().body(service.atualizaPorId(id, fornecedor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.deletaPorId(id));
    }

}
