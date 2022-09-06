package br.com.twobrothers.frontend.proxys;

import br.com.twobrothers.frontend.models.dto.DespesaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="ms-despesas", url = "localhost:8125")
public interface DespesaServiceProxy {

    @PostMapping(value="/api/despesas")
    ResponseEntity<DespesaDTO> novaDespesa(@RequestBody DespesaDTO despesa);

}
