package br.com.twobrothers.frontend.models.dto;

import br.com.twobrothers.frontend.models.enums.TipoEntradaEnum;
import br.com.twobrothers.frontend.models.enums.TipoOrdemEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ProdutoEstoqueDTO produto;

    public void addOrdem(OrdemDTO ordem) {
        ordem.getEntradas().add(this);
        this.setOrdem(ordem);
    }

}
