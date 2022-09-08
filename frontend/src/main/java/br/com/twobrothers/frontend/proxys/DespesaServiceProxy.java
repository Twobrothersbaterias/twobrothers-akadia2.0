package br.com.twobrothers.frontend.proxys;

import br.com.twobrothers.frontend.models.dto.DespesaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="ms-despesas", url = "localhost:8125")
public interface DespesaServiceProxy {

    @PostMapping(value="/api/despesas")
    ResponseEntity<DespesaDTO> novaDespesa(@RequestBody DespesaDTO despesa);

    @GetMapping(value="api/despesas/pagination")
    ResponseEntity<List<DespesaDTO>> buscaDespesasPorPaginacao(@PageableDefault(sort = {"dataAgendamento", "dataCadastro"},
            direction = Sort.Direction.ASC, size = 5) Pageable paginacao);

}
