package br.com.twobrothers.msdespesas.services;

import br.com.twobrothers.msdespesas.config.ModelMapperConfig;
import br.com.twobrothers.msdespesas.services.exceptions.InvalidRequestException;
import br.com.twobrothers.msdespesas.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msdespesas.models.dto.DespesaDTO;
import br.com.twobrothers.msdespesas.models.entities.DespesaEntity;
import br.com.twobrothers.msdespesas.repositories.DespesaRepository;
import br.com.twobrothers.msdespesas.validations.DespesaValidation;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<DespesaDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {

        try {
            List<DespesaEntity> despesas = repository.buscaPorRangeDeDataCadastro(
                    (LocalDate.parse(dataInicio)).atTime(0, 0),
                    (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

            if (!despesas.isEmpty())
                return despesas.stream().map(x -> modelMapper.mapper().map(x, DespesaDTO.class)).collect(Collectors.toList());
            throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada no range de datas indicado");
        } catch (Exception e) {
            throw new InvalidRequestException("Falha na requisição. Motivo: Padrão de data recebido inválido");
        }

    }

    public List<DespesaDTO> buscaPorPaginacao(Pageable paginacao) {
        if (!repository.findAll(paginacao).isEmpty()) return repository.findAll(paginacao)
                .getContent().stream().map(x -> modelMapper.mapper().map(x, DespesaDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada na página indicada");
    }

    public List<DespesaDTO> buscaPorDescricao(String descricao) {
        List<DespesaEntity> despesas = repository.buscaPorDescricao(descricao);
        if (!despesas.isEmpty()) return despesas
                .stream().map(x -> modelMapper.mapper().map(x, DespesaDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada com a descrição passada");
    }

    public List<DespesaDTO> buscaTodasAsDespesas() {
        if (!repository.findAll().isEmpty()) return repository.findAll()
                .stream().map(x -> modelMapper.mapper().map(x, DespesaDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada");
    }

    public DespesaDTO atualizaPorId(Long id, DespesaDTO despesa) {

        Optional<DespesaEntity> despesaOptional = repository.findById(id);

        if (despesaOptional.isPresent()) {

            DespesaEntity despesaAtualizada = despesaOptional.get();

            if (validation.validaCorpoDaRequisicao(despesa)) {

                despesaAtualizada.setDataPagamento(despesa.getDataPagamento());
                despesaAtualizada.setStatusDespesa(despesa.getStatusDespesa());
                despesaAtualizada.setTipoDespesa(despesa.getTipoDespesa());
                despesaAtualizada.setDescricao(despesa.getDescricao());
                despesaAtualizada.setDataAgendamento(despesa.getDataAgendamento());
                despesaAtualizada.setValor(despesa.getValor());

                return modelMapper.mapper().map(repository.save(despesaAtualizada), DespesaDTO.class);

            }

            throw new InvalidRequestException("Corpo da requisição inválido");

        }
        throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada com o id " + id);

    }

    public Boolean deletaDespesaPorId(Long id) {
        Optional<DespesaEntity> despesaOptional = repository.findById(id);
        if (despesaOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada com o id " + id);
    }

}
