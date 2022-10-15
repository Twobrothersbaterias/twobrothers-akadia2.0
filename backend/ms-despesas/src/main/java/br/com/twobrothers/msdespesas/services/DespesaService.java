package br.com.twobrothers.msdespesas.services;

import br.com.twobrothers.msdespesas.config.ModelMapperConfig;
import br.com.twobrothers.msdespesas.exceptions.InvalidRequestException;
import br.com.twobrothers.msdespesas.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msdespesas.models.dto.DespesaDTO;
import br.com.twobrothers.msdespesas.models.entities.DespesaEntity;
import br.com.twobrothers.msdespesas.repositories.DespesaRepository;
import br.com.twobrothers.msdespesas.validations.DespesaValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DespesaService {

    @Autowired
    DespesaRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    DespesaValidation validation = new DespesaValidation();

    public DespesaDTO criaNovaDespesa(DespesaDTO despesa) {
        despesa.setDataCadastro(LocalDateTime.now());
        if (validation.validaCorpoDaRequisicao(despesa))
            return modelMapper.mapper().map(repository.save(modelMapper.mapper().map(despesa, DespesaEntity.class)), DespesaDTO.class);
        throw new InvalidRequestException("Ocorreu uma falha na validação da requisição");
    }

    public DespesaDTO findById(Long id) {
        Optional<DespesaEntity> despesa = repository.findById(id);
        return modelMapper.mapper().map(
                despesa.orElseThrow(() -> new ObjectNotFoundException("Nenhuma despesa foi encontrada")), DespesaDTO.class);
    }

    public List<DespesaDTO> buscaPorRangeDeDataCadastro(LocalDate dataInicio, LocalDate dataFim) {
        //TODO PAREI AQUI, ESTAVA DANDO FALHA DE PARSE NA CONVERÃO DE LOCALDATE PARA LOCALDATETIME
        return repository.buscaPorRangeDeDataCadastro(dataInicio, dataFim)
                .stream().map(x -> modelMapper.mapper().map(x, DespesaDTO.class)).collect(Collectors.toList());
    }

    public List<DespesaDTO> buscaPorPaginacao(Pageable paginacao) {
        if (!repository.findAll(paginacao).isEmpty()) return repository.findAll(paginacao)
                .getContent().stream().map(x -> modelMapper.mapper().map(x, DespesaDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada na página indicada");
    }

    public List<DespesaDTO> buscaTodasAsDespesas() {
        if (!repository.findAll().isEmpty()) return repository.findAll()
                .stream().map(x -> modelMapper.mapper().map(x, DespesaDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada");
    }

}
