package br.com.twobrothers.msbalanco.services;

import br.com.twobrothers.msbalanco.config.ModelMapperConfig;
import br.com.twobrothers.msbalanco.models.dto.PatrimonioDTO;
import br.com.twobrothers.msbalanco.models.entities.PatrimonioEntity;
import br.com.twobrothers.msbalanco.repositories.PatrimonioRepository;
import br.com.twobrothers.msbalanco.services.exceptions.InvalidRequestException;
import br.com.twobrothers.msbalanco.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msbalanco.validations.PatrimonioValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatrimonioService {

    @Autowired
    PatrimonioRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    PatrimonioValidation validation = new PatrimonioValidation();

    public PatrimonioDTO criaNovo(PatrimonioDTO patrimonio) {
        patrimonio.setDataCadastro(LocalDateTime.now());
        if (validation.validaCorpoDaRequisicao(patrimonio))
            return modelMapper.mapper().map(repository.save(modelMapper.mapper().map(patrimonio, PatrimonioEntity.class)), PatrimonioDTO.class);
        throw new InvalidRequestException("Ocorreu uma falha na validação da requisição");
    }

    public List<PatrimonioDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {

        try {
            List<PatrimonioEntity> patrimonios = repository.buscaPorRangeDeDataCadastro(
                    (LocalDate.parse(dataInicio)).atTime(0, 0),
                    (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

            if (!patrimonios.isEmpty())
                return patrimonios.stream().map(x -> modelMapper.mapper().map(x, PatrimonioDTO.class)).collect(Collectors.toList());
            throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada no range de datas indicado");
        } catch (Exception e) {
            throw new InvalidRequestException("Falha na requisição. Motivo: Padrão de data recebido inválido");
        }

    }

    public List<PatrimonioDTO> buscaPorPaginacao(Pageable paginacao) {
        if (!repository.findAll(paginacao).isEmpty()) return repository.findAll(paginacao)
                .getContent().stream().map(x -> modelMapper.mapper().map(x, PatrimonioDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum patrimônio cadastrado na página indicada");
    }

    public List<PatrimonioDTO> buscaPorDescricao(String descricao) {
        List<PatrimonioEntity> patrimonios = repository.buscaPorDescricao(descricao);
        if (!patrimonios.isEmpty()) return patrimonios
                .stream().map(x -> modelMapper.mapper().map(x, PatrimonioDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum patrimônio cadastrado com a descrição passada");
    }

    public List<PatrimonioDTO> buscaTodos() {
        if (!repository.findAll().isEmpty()) return repository.findAll()
                .stream().map(x -> modelMapper.mapper().map(x, PatrimonioDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum patrimônio cadastrado");
    }

    public PatrimonioDTO atualizaPorId(Long id, PatrimonioDTO patrimonio) {

        Optional<PatrimonioEntity> patrimonioOptional = repository.findById(id);

        if (patrimonioOptional.isPresent()) {

            PatrimonioEntity patrimonioAtualizado = patrimonioOptional.get();

            if (validation.validaCorpoDaRequisicao(patrimonio)) {

                patrimonioAtualizado.setTipoPatrimonio(patrimonio.getTipoPatrimonio());
                patrimonioAtualizado.setStatusPatrimonio(patrimonio.getStatusPatrimonio());
                patrimonioAtualizado.setNome(patrimonio.getNome());
                patrimonioAtualizado.setDataAgendamento(patrimonio.getDataAgendamento());
                patrimonioAtualizado.setValor(patrimonio.getValor());

                return modelMapper.mapper().map(repository.save(patrimonioAtualizado), PatrimonioDTO.class);

            }

            throw new InvalidRequestException("Corpo da requisição inválido");

        }
        throw new ObjectNotFoundException("Não existe nenhum patrimônio cadastrado com o id " + id);

    }

    public Boolean deletaPorId(Long id) {
        Optional<PatrimonioEntity> patrimonioOptional = repository.findById(id);
        if (patrimonioOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Não existe nenhum patrimônio cadastrado com o id " + id);
    }

}
