package br.com.twobrothers.msdespesas.services;

import br.com.twobrothers.msdespesas.config.ModelMapperConfig;
import br.com.twobrothers.msdespesas.models.dto.DespesaDTO;
import br.com.twobrothers.msdespesas.models.entities.DespesaEntity;
import br.com.twobrothers.msdespesas.repositories.DespesaRepository;
import br.com.twobrothers.msdespesas.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msdespesas.validations.DespesaValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.twobrothers.msdespesas.utils.StringConstants.BARRA_DE_LOG;
import static br.com.twobrothers.msdespesas.utils.StringConstants.REQUISICAO_FINALIZADA_COM_SUCESSO;

@Slf4j
@Service
public class DespesaService {

    @Autowired
    DespesaRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    DespesaValidation validation = new DespesaValidation();

    public DespesaDTO criaNovaDespesa(DespesaDTO despesa) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação de despesa...");

        log.info("[PROGRESS] Setando data de cadastro da despesa...");
        despesa.setDataCadastro(LocalDateTime.now());

        log.info("[PROGRESS] Inicializando método de validação da despesa...");
        validation.validaCorpoDaRequisicao(despesa);

        log.info("[PROGRESS] Persistindo despesa na base de dados...");
        DespesaEntity despesaEntity = repository.save(modelMapper.mapper().map(despesa, DespesaEntity.class));

        log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
        return modelMapper.mapper().map(despesaEntity, DespesaDTO.class);
    }

    public List<DespesaDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de despesa por range de data de cadastro...");

        List<DespesaEntity> despesas = repository.buscaPorRangeDeDataCadastro(
                (LocalDate.parse(dataInicio)).atTime(0, 0),
                (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

        if (!despesas.isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return despesas.stream().map(x -> modelMapper.mapper().map(x, DespesaDTO.class)).collect(Collectors.toList());
        }

        log.error("[ERROR] Não existe nenhuma despesa cadastrada no range de datas indicado");
        throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada no range de datas indicado");

    }

    public List<DespesaDTO> buscaPorPaginacao(Pageable paginacao) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de despesa por paginação...");
        if (!repository.findAll(paginacao).isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return repository.findAll(paginacao)
                    .getContent().stream().map(x -> modelMapper.mapper().map(x, DespesaDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Não existe nenhuma despesa cadastrada na página indicada");
        throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada na página indicada");
    }

    public List<DespesaDTO> buscaPorDescricao(String descricao) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de despesa por descrição...");
        List<DespesaEntity> despesas = repository.buscaPorDescricao(descricao);
        if (!despesas.isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return despesas
                    .stream().map(x -> modelMapper.mapper().map(x, DespesaDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Não existe nenhuma despesa cadastrada com a descrição passada");
        throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada com a descrição passada");
    }

    public List<DespesaDTO> buscaTodasAsDespesas() {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca por todas as despesas...");
        if (!repository.findAll().isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return repository.findAll()
                    .stream().map(x -> modelMapper.mapper().map(x, DespesaDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Não existe nenhuma despesa cadastrada na base de dados");
        throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada");
    }

    public DespesaDTO atualizaPorId(Long id, DespesaDTO despesa) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de atualização de despesa por id...");

        Optional<DespesaEntity> despesaOptional = repository.findById(id);

        if (despesaOptional.isEmpty()) {
            throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada com o id " + id);
        }

        DespesaEntity despesaAtualizada = despesaOptional.get();

        log.info("[PROGRESS] Inicializando validação do objeto despesa recebido na requisição...");
        validation.validaCorpoDaRequisicao(despesa);

        log.info("[PROGRESS] Setando atributos da variável despesaAtualizada com base nos valores recebidos na requisição...");
        despesaAtualizada.setDataPagamento(despesa.getDataPagamento());
        despesaAtualizada.setStatusDespesa(despesa.getStatusDespesa());
        despesaAtualizada.setTipoDespesa(despesa.getTipoDespesa());
        despesaAtualizada.setDescricao(despesa.getDescricao());
        despesaAtualizada.setDataAgendamento(despesa.getDataAgendamento());
        despesaAtualizada.setValor(despesa.getValor());

        log.info("[PROGRESS] Salvando despesa atualizada na base de dados...");
        DespesaEntity despesaEntity = repository.save(despesaAtualizada);

        log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
        return modelMapper.mapper().map(despesaEntity, DespesaDTO.class);
    }

    public Boolean deletaDespesaPorId(Long id) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de remoção de despesa por id...");
        Optional<DespesaEntity> despesaOptional = repository.findById(id);
        if (despesaOptional.isPresent()) {
            log.info("[PROGRESS] Removendo despesa da base de dados...");
            repository.deleteById(id);
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return true;
        }
        log.error("[ERROR] Não existe nenhuma despesa cadastrada com o id " + id);
        throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada com o id " + id);
    }

}
