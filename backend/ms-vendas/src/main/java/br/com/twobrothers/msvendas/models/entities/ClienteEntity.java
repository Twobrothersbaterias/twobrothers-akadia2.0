package br.com.twobrothers.msvendas.models.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "tb_cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataCadastro;
    private String dataNascimento;
    private String nomeCompleto;

    @Column(unique = true)
    private String cpfCnpj;

    @Column(unique = true)
    private String email;
    private String telefone;
    private Long idUsuarioResponsavel;

    @ManyToOne(targetEntity = EnderecoEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco")
    private EnderecoEntity endereco;

    @OneToMany(targetEntity = OrdemEntity.class, cascade = CascadeType.ALL)
    private List<OrdemEntity> ordens = new ArrayList<>();

}



