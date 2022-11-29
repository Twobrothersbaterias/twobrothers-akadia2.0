package br.com.twobrothers.frontend.models;

import lombok.*;

import java.util.Map;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Impressao {

    private String data;
    private String nomeCliente;
    private String veiculo;
    private String tecnicoResponsavel;
    private Map<String, String> saidas;
    private String valorTotal;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private String numeroLogradouro;

}
