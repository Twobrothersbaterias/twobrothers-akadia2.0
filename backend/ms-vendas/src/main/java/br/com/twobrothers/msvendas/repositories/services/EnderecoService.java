package br.com.twobrothers.msvendas.repositories.services;

import br.com.twobrothers.msvendas.config.ModelMapperConfig;
import br.com.twobrothers.msvendas.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msvendas.models.dto.EnderecoDTO;
import br.com.twobrothers.msvendas.models.entities.EnderecoEntity;
import br.com.twobrothers.msvendas.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

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

}
