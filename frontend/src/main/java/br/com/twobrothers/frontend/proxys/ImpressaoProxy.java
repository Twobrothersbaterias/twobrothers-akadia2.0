package br.com.twobrothers.frontend.proxys;

import br.com.twobrothers.frontend.models.Impressao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "desktop", url = "localhost:8151")
public interface ImpressaoProxy {

    @PostMapping("imprime")
    ResponseEntity<Impressao> imprimeNaoFiscal(@RequestBody Impressao impressao);

}
