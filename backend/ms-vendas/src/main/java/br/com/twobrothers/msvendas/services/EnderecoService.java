package br.com.twobrothers.msvendas.services;

import br.com.twobrothers.msvendas.config.ModelMapperConfig;
import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msvendas.models.dto.EnderecoDTO;
import br.com.twobrothers.msvendas.models.entities.EnderecoEntity;
import br.com.twobrothers.msvendas.models.enums.ValidationType;
import br.com.twobrothers.msvendas.repositories.EnderecoRepository;
import br.com.twobrothers.msvendas.validations.EnderecoValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    EnderecoValidation validation = new EnderecoValidation();

    public EnderecoDTO criaNovo(EnderecoDTO endereco) {
        if (validation.validaCorpoRequisicao(endereco, repository, ValidationType.CREATE)) {
            return modelMapper.mapper().map(repository
                    .save(modelMapper.mapper().map(endereco, EnderecoEntity.class)), EnderecoDTO.class);
        }
        throw new InvalidRequestException("A validação da requisição falhou. Verificar corpo da requisição.");
    }

    public List<EnderecoDTO> buscaTodos() {
        if (!repository.findAll().isEmpty()) return repository.findAll().stream()
                .map(x -> modelMapper.mapper().map(x, EnderecoDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum endereço salvo na base de dados.");
    }

    public List<EnderecoDTO> buscaPorPaginacao(Pageable paginacao) {
        if (!repository.findAll(paginacao).isEmpty()) return repository.findAll(paginacao)
                .getContent().stream().map(x -> modelMapper.mapper().map(x, EnderecoDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum endereço cadastrado na página indicada");
    }

    public List<EnderecoDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {

        List<EnderecoEntity> enderecos = repository.buscaPorRangeDeDataCadastro(
                (LocalDate.parse(dataInicio)).atTime(0, 0),
                (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

        if (!enderecos.isEmpty())
            return enderecos.stream().map(x -> modelMapper.mapper().map(x, EnderecoDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum endereço cadastrado no range de datas indicado");

    }

    public EnderecoDTO buscaPorId(Long id) {
        if (repository.findById(id).isPresent()) {
            return modelMapper.mapper().map(repository.findById(id).get(), EnderecoDTO.class);
        }
        throw new ObjectNotFoundException("Não existe nenhum endereço cadastrado no banco de dados com o id " + id);
    }

    public EnderecoDTO atualizaPorId(Long id, EnderecoDTO endereco) {

        Optional<EnderecoEntity> enderecoOptional = repository.findById(id);

        if (enderecoOptional.isPresent()) {

            EnderecoEntity enderecoAtualizado = enderecoOptional.get();

            if (validation.validaCorpoRequisicao(endereco, repository, ValidationType.UPDATE)) {

                Optional<EnderecoEntity> enderecoEncontrado =
                        repository.buscaPorAtributos(endereco.getLogradouro(), endereco.getBairro(), endereco.getNumero());

                if (enderecoEncontrado.isPresent() && !Objects.equals(enderecoEncontrado.get().getId(), id))
                    throw new InvalidRequestException("O endereço informado já existe");

                enderecoAtualizado.setBairro(endereco.getBairro());
                enderecoAtualizado.setCep(endereco.getCep());
                enderecoAtualizado.setComplemento(endereco.getComplemento());
                enderecoAtualizado.setEstado(endereco.getEstado());
                enderecoAtualizado.setLogradouro(endereco.getLogradouro());
                enderecoAtualizado.setNumero(endereco.getNumero());

                return modelMapper.mapper().map(repository.save(enderecoAtualizado), EnderecoDTO.class);

            }

            throw new InvalidRequestException("Corpo da requisição inválido");

        }
        throw new ObjectNotFoundException("Não existe nenhum endereço cadastrado com o id " + id);

    }

    public Boolean deletaPorId(Long id) {
        Optional<EnderecoEntity> enderecoOptional = repository.findById(id);
        if (enderecoOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Não existe nenhum endereço cadastrado com o id " + id);
    }

}
