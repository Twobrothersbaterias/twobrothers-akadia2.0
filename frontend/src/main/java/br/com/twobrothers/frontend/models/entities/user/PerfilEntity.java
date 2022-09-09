package br.com.twobrothers.frontend.models.entities.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "tb_perfil")
@SequenceGenerator(allocationSize = 1, sequenceName = "sq_perfil", name = "perfil")
public class PerfilEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sq_perfil")
    private Long id;

    private String nome;

    @Override
    public String getAuthority() {
        return this.nome;
    }
}
