package br.com.twobrothers.frontend.models.entities.user;

import br.com.twobrothers.frontend.models.entities.EnderecoEntity;
import br.com.twobrothers.frontend.models.entities.OrdemEntity;
import br.com.twobrothers.frontend.models.entities.RetiradaEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "tb_usuario")
@SequenceGenerator(allocationSize = 1, sequenceName = "sq_usuario", name = "usuario")
public class UsuarioEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sq_usuario")
    private Long id;

    private String dataCadastro;

    private String usuarioResponsavel;

    private String nome;

    private String dataNascimento;

    @Column(unique = true)
    private String cpfCnpj;

    @Column(unique = true)
    private String email;

    private String telefone;
    private String nomeUsuario;
    private String senha;

    @Enumerated(EnumType.STRING)
    private PrivilegioEnum privilegio;

    @OneToOne(targetEntity = EnderecoEntity.class, cascade = CascadeType.ALL)
    private EnderecoEntity endereco;

    @OneToMany(targetEntity = RetiradaEntity.class, cascade = CascadeType.ALL)
    private List<RetiradaEntity> retiradas = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<PerfilEntity> perfis = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfis;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.nomeUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
