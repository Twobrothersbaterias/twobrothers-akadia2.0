package br.com.twobrothers.msvendas.models.dto;

import br.com.twobrothers.msvendas.models.enums.TipoEntradaEnum;
import br.com.twobrothers.msvendas.models.enums.TipoOrdemEnum;
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
    private String observacao;
    private TipoOrdemEnum tipoOrdem;
    private TipoEntradaEnum tipoEntrada;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private OrdemDTO ordem;

    //TODO Remover atributo
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ProdutoEstoqueDTO produto;

    public void addOrdem(OrdemDTO ordem) {
        ordem.getEntradas().add(this);
        this.setOrdem(ordem);
    }

}
