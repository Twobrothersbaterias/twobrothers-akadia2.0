package br.com.twobrothers.frontend.models.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
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

    private String dataCadastro;
    private String dataNascimento;
    private String nomeCompleto;

    @Column(unique = true)
    private String cpfCnpj;

    @Column(unique = true)
    private String email;

    private String telefone;
    private String usuarioResponsavel;

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


