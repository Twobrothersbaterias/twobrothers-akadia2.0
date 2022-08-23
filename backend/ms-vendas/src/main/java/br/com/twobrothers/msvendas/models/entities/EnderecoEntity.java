package br.com.twobrothers.msvendas.models.entities;

import br.com.twobrothers.msvendas.models.dto.ClienteDTO;
import br.com.twobrothers.msvendas.models.enums.EstadoEnum;
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
@Table(name = "tb_endereco")
@SequenceGenerator(allocationSize = 1, sequenceName = "sq_endereco", name = "endereco")
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sq_endereco")
    private Long id;

    private LocalDateTime dataCadastro;
    private String logradouro;
    private Integer numero;
    private String bairro;
    private String cep;
    private String complemento;

    @Enumerated(EnumType.STRING)
    private EstadoEnum estado;

    @OneToMany(targetEntity = ClienteEntity.class, cascade = CascadeType.ALL)
    private List<ClienteEntity> clientes = new ArrayList<>();

    @OneToMany(targetEntity = FornecedorEntity.class, cascade = CascadeType.ALL)
    private List<FornecedorEntity> fornecedores = new ArrayList<>();

    public void addCliente(ClienteEntity cliente) {
        cliente.setEndereco(this);
        this.clientes.add(cliente);
    }

    public void removeCliente(ClienteEntity cliente) {
        cliente.setEndereco(null);
        this.clientes.remove(cliente);
    }

    public void addFornecedor(FornecedorEntity fornecedor) {
        fornecedor.setEndereco(this);
        this.fornecedores.add(fornecedor);
    }

}
