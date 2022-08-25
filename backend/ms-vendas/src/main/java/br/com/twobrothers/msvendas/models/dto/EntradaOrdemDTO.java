package br.com.twobrothers.msvendas.models.dto;

import br.com.twobrothers.msvendas.models.enums.TipoEntradaOrdemEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EntradaOrdemDTO {

    private Long id;

    private Double valor;
    private Integer quantidade;
    private String observação;
    private Long produtoEstoqueId;

    private TipoEntradaOrdemEnum tipoEntradaOrdem;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private OrdemDTO ordem;

    public void addOrdem(OrdemDTO ordem) {
        ordem.getEntradas().add(this);
        this.setOrdem(ordem);
    }

}
