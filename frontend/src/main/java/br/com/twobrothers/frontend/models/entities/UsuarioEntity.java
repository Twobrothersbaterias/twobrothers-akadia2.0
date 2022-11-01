package br.com.twobrothers.frontend.models.entities;

import br.com.twobrothers.frontend.models.enums.PrivilegioEnum;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Builder
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

    @ManyToOne(targetEntity = UsuarioEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuarioResponsavel;

    private String nome;

    private String dataNascimento;

    @Column(unique = true)
    private String cpfCnpj;

    @Column(unique = true)
    private String email;

    private String telefone;

    @Column(unique=true)
    private String nomeUsuario;

    private String senha;

    private String senhaCriptografada;

    @Enumerated(EnumType.STRING)
    private PrivilegioEnum privilegio;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<PerfilEntity> perfis = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfis;
    }

    @Override
    public String getPassword() {
        return this.senhaCriptografada;
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
