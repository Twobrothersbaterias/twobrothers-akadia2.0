package br.com.twobrothers.msvendas.services;

import br.com.twobrothers.msvendas.config.ModelMapperConfig;
import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msvendas.models.dto.FornecedorDTO;
import br.com.twobrothers.msvendas.models.entities.EnderecoEntity;
import br.com.twobrothers.msvendas.models.entities.FornecedorEntity;
import br.com.twobrothers.msvendas.models.enums.ValidationType;
import br.com.twobrothers.msvendas.repositories.FornecedorRepository;
import br.com.twobrothers.msvendas.validations.FornecedorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FornecedorService {

    @Autowired
    FornecedorRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    FornecedorValidation validation = new FornecedorValidation();

    //TODO Verificar como fica a parte da persistência de endereço
    public FornecedorDTO criaNovo(FornecedorDTO fornecedor) {
        if (validation.validaCorpoRequisicao(fornecedor, repository, ValidationType.CREATE)) {
            return modelMapper.mapper().map(repository
                    .save(modelMapper.mapper().map(fornecedor, FornecedorEntity.class)), FornecedorDTO.class);
        }
        throw new InvalidRequestException("A validação da requisição falhou. Verificar corpo da requisição.");
    }

    public List<FornecedorDTO> buscaTodos() {
        if (!repository.findAll().isEmpty()) return repository.findAll().stream()
                .map(x -> modelMapper.mapper().map(x, FornecedorDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum fornecedor salvo na base de dados.");
    }

    public List<FornecedorDTO> buscaPorPaginacao(Pageable paginacao) {
        if (!repository.findAll(paginacao).isEmpty()) return repository.findAll(paginacao)
                .getContent().stream().map(x -> modelMapper.mapper().map(x, FornecedorDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum fornecedor cadastrado na página indicada");
    }

    public List<FornecedorDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {

        try {
            List<FornecedorEntity> fornecedores = repository.buscaPorRangeDeDataCadastro(
                    (LocalDate.parse(dataInicio)).atTime(0, 0),
                    (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

            if (!fornecedores.isEmpty())
                return fornecedores.stream().map(x -> modelMapper.mapper().map(x, FornecedorDTO.class)).collect(Collectors.toList());
            throw new ObjectNotFoundException("Não existe nenhum fornecedor cadastrado no range de datas indicado");
        } catch (Exception e) {
            throw new InvalidRequestException("Falha na requisição. Motivo: Padrão de data recebido inválido");
        }

    }

    public FornecedorDTO buscaPorId(Long id) {
        if (repository.findById(id).isPresent()) {
            return modelMapper.mapper().map(repository.findById(id).get(), FornecedorDTO.class);
        }
        throw new ObjectNotFoundException("Não existe nenhum fornecedor cadastrado no banco de dados com o id " + id);
    }

    public FornecedorDTO buscaPorCpfCnpj(String cpfCnpj) {
        if (repository.buscaPorCpfCnpj(cpfCnpj).isPresent())
            return modelMapper.mapper().map(repository.buscaPorCpfCnpj(cpfCnpj).get(), FornecedorDTO.class);
        throw new ObjectNotFoundException("Nenhum fornecedor foi encontrado através do atributo cpfCnpj enviado.");
    }

    public FornecedorDTO buscaPorEmail(String email) {
        if (repository.buscaPorEmail(email).isPresent())
            return modelMapper.mapper().map(repository.buscaPorEmail(email).get(), FornecedorDTO.class);
        throw new ObjectNotFoundException("Nenhum fornecedor foi encontrado através do atributo email enviado.");
    }

    public FornecedorDTO buscaPorTelefone(String telefone) {
        if (!repository.buscaPorTelefone(telefone).isEmpty())
            return modelMapper.mapper().map(repository.buscaPorTelefone(telefone), FornecedorDTO.class);
        throw new ObjectNotFoundException("Nenhum fornecedor foi encontrado através do atributo telefone enviado.");
    }

    public FornecedorDTO buscaPorNome(String nome) {
        if (!repository.buscaPorNome(nome).isEmpty())
            return modelMapper.mapper().map(repository.buscaPorNome(nome), FornecedorDTO.class);
        throw new ObjectNotFoundException("Nenhum fornecedor foi encontrado através do atributo nomeCompleto enviado.");
    }

    //TODO Verificar como fica a parte da persistência de endereço
    public FornecedorDTO atualizaPorId(Long id, FornecedorDTO fornecedor) {

        Optional<FornecedorEntity> fornecedorOptional = repository.findById(id);

        if (fornecedorOptional.isPresent()) {

            FornecedorEntity fornecedorAtualizado = fornecedorOptional.get();

            if (validation.validaCorpoRequisicao(fornecedor, repository, ValidationType.UPDATE)) {

                fornecedorAtualizado.setCpfCnpj(fornecedor.getCpfCnpj());
                fornecedorAtualizado.setEmail(fornecedor.getEmail());
                fornecedorAtualizado.setEndereco(modelMapper.mapper().map(fornecedor.getEndereco(), EnderecoEntity.class));
                fornecedorAtualizado.setTelefone(fornecedor.getTelefone());
                fornecedorAtualizado.setNome(fornecedor.getNome());

                return modelMapper.mapper().map(repository.save(fornecedorAtualizado), FornecedorDTO.class);

            }

            throw new InvalidRequestException("Corpo da requisição inválido");

        }
        throw new ObjectNotFoundException("Não existe nenhum fornecedor cadastrado com o id " + id);

    }

    public Boolean deletaPorId(Long id) {
        Optional<FornecedorEntity> fornecedorOptional = repository.findById(id);
        if (fornecedorOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Não existe nenhum fornecedor cadastrado com o id " + id);
    }

}
