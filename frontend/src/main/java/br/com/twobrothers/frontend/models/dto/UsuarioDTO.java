package br.com.twobrothers.frontend.models.dto;

import br.com.twobrothers.frontend.models.enums.PrivilegioEnum;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UsuarioDTO {

    private Long id;
    private String dataCadastro;
    private UsuarioDTO usuarioResponsavel;
    private String nome;
    private String dataNascimento;
    private String cpfCnpj;
    private String email;
    private String telefone;
    private String nomeUsuario;
    private String senha;
    private String senhaCriptografada;
    private PrivilegioEnum privilegio;
    private Boolean habilitado;
    private List<RetiradaDTO> retiradas = new ArrayList<>();

}
