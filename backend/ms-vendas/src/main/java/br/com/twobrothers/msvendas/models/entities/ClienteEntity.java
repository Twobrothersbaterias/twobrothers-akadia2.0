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

    private String email;

    private String telefone;
    private Long idUsuarioResponsavel;

    @OneToOne(targetEntity = EnderecoEntity.class, cascade = CascadeType.ALL)
    private EnderecoEntity endereco;

    @OneToMany(targetEntity = OrdemEntity.class, cascade = CascadeType.ALL)
    private List<OrdemEntity> ordens = new ArrayList<>();

    public void addOrdem(OrdemEntity ordem) {
        ordem.setCliente(this);
        this.ordens.add(ordem);
    }

    public void removeOrdem(OrdemEntity ordem) {
        ordem.setCliente(null);
        this.ordens.remove(ordem);
    }

}



