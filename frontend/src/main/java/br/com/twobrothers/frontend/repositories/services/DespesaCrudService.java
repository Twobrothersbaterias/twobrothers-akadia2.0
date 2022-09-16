package br.com.twobrothers.frontend.repositories.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.DespesaDTO;
import br.com.twobrothers.frontend.models.entities.DespesaEntity;
import br.com.twobrothers.frontend.models.enums.PersistenciaEnum;
import br.com.twobrothers.frontend.models.enums.StatusDespesaEnum;
import br.com.twobrothers.frontend.models.enums.TipoDespesaEnum;
import br.com.twobrothers.frontend.repositories.DespesaRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import br.com.twobrothers.frontend.validations.DespesaValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.twobrothers.frontend.utils.StringConstants.BARRA_DE_LOG;
import static br.com.twobrothers.frontend.utils.StringConstants.REQUISICAO_FINALIZADA_COM_SUCESSO;

/**
 * @author Gabriel Lagrota
 * @version 1.0
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @since 30-08-22
 */
@Slf4j
@Service
public class DespesaCrudService {

    @Autowired
    DespesaRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    @Autowired
    UsuarioRepository usuario;

    DespesaValidation validation = new DespesaValidation();

    public DespesaDTO criaNovaDespesa(DespesaDTO despesa) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação de despesa...");

        log.info("[PROGRESS] Setando data de cadastro da despesa...");
        despesa.setDataCadastro(LocalDate.now().toString());

        log.info("[PROGRESS] Setando usuário responsável pela despesa...");
        despesa.setUsuarioResponsavel(UsuarioUtils.loggedUser(usuario).getUsername());

        log.info("[PROGRESS] Inicializando método de validação da despesa...");
        validation.validaCorpoDaRequisicao(despesa);

        if (!despesa.getPersistencia().equals(PersistenciaEnum.NAO)) {
            log.info("[PROGRESS] Propagando persistência: {}", despesa.getPersistencia().getDesc());
            repository.saveAll(criaDespesasDePersistencia(despesa));
        }

        if (despesa.getDataPagamento() == null) despesa.setDataPagamento("Em aberto");
        if (despesa.getDataAgendamento() == null) despesa.setDataAgendamento("Não possui");

        log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
        log.info("[PROGRESS] Persistindo despesa na base de dados...");
        DespesaEntity despesaEntity = repository.save(modelMapper.mapper().map(despesa, DespesaEntity.class));
        return modelMapper.mapper().map(despesaEntity, DespesaDTO.class);
    }

    public List<DespesaEntity> criaDespesasDePersistencia(DespesaDTO despesa) {
        log.warn("[INFO] Método de criação de despesas de persistência acessado...");

        List<DespesaEntity> despesas = new ArrayList<>();
        for (int i = 1; i < despesa.getPersistencia().getMeses() + 1; i++) {
            log.info("[PROGRESS] Acessando persistência da {}° despesa...", i);

            log.info("[PROGRESS] Realizando setagem de atributos do objeto despesa...");
            DespesaEntity despesaEntity = modelMapper.mapper().map(despesa, DespesaEntity.class);
            despesaEntity.setDataCadastro(LocalDate.now().toString());
            despesaEntity.setStatusDespesa(StatusDespesaEnum.PENDENTE);
            despesaEntity.setPersistencia(PersistenciaEnum.PERSISTIDO);
            if (despesa.getDataAgendamento() != null) {
                despesaEntity.setDataAgendamento(
                        LocalDate.parse(despesaEntity.getDataAgendamento()).plusMonths(Long.parseLong(String.valueOf(i))).toString());
                despesaEntity.setDataPagamento("Em aberto");
            }
            else {
                despesaEntity.setDataAgendamento(
                        LocalDate.parse(despesaEntity.getDataPagamento()).plusMonths(Long.parseLong(String.valueOf(i))).toString());
                despesaEntity.setDataPagamento("Em aberto");
            }

            despesas.add(despesaEntity);
        }

        return despesas;
    }

    public List<DespesaEntity> buscaPorRangeDeData(Pageable pageable, String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de despesa por range de data...");
        validation.validaRangeData(dataInicio, dataFim);
        return repository.buscaPorRangeDeData(pageable, dataInicio, dataFim);
    }

    public List<DespesaEntity> buscaPorPeriodo(Pageable pageable, Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de despesa por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return repository.buscaPorPeriodo(pageable, dataInicio.toString(), dataFim.toString());
    }

    public List<DespesaEntity> buscaHoje(Pageable pageable) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de todas as despesas cadastradas hoje, pagas hoje ou agendadas para hoje...");
        LocalDate hoje = LocalDate.now();
        return repository.buscaHoje(pageable, hoje.toString());
    }

    public List<DespesaEntity> buscaPorDescricao(Pageable pageable, String descricao) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de despesa por descrição...");
        return repository.buscaPorDescricao(pageable, descricao);
    }

    public List<DespesaEntity> buscaPorTipo(Pageable pageable, TipoDespesaEnum tipo) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de despesa por tipo...");
        return repository.buscaPorTipo(pageable, tipo);
    }

    public List<DespesaEntity> buscaPorRangeDeDataSemPaginacao(String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de despesa por range de data...");
        return repository.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
    }

    public List<DespesaEntity> buscaPorPeriodoSemPaginacao(Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de despesa por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return repository.buscaPorPeriodoSemPaginacao(dataInicio.toString(), dataFim.toString());
    }

    public List<DespesaEntity> buscaHojeSemPaginacao() {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de todas as despesas cadastradas hoje, pagas hoje ou agendadas para hoje...");
        LocalDate hoje = LocalDate.now();
        return repository.buscaHojeSemPaginacao(hoje.toString());
    }

    public List<DespesaEntity> buscaAgendadosHojeSemPaginacao() {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de todas as despesas agendadas para hoje...");
        LocalDate hoje = LocalDate.now();
        return repository.buscaAgendadosHojeSemPaginacao(hoje.toString());
    }

    public List<DespesaEntity> buscaPorDescricaoSemPaginacao(String descricao) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de despesa por descrição...");
        return repository.buscaPorDescricaoSemPaginacao(descricao);
    }

    public List<DespesaEntity> buscaPorTipoSemPaginacao(TipoDespesaEnum tipo) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de despesa por tipo...");
        return repository.buscaPorTipoSemPaginacao(tipo);
    }

    public DespesaDTO atualizaPorId(DespesaDTO despesa) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de atualização de despesa por id...");

        Optional<DespesaEntity> despesaOptional = repository.findById(despesa.getId());

        if (despesaOptional.isEmpty()) {
            throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada com o id " + despesa.getId());
        }

        DespesaEntity despesaAtualizada = despesaOptional.get();

        log.info("[PROGRESS] Inicializando validação do objeto despesa recebido na requisição...");
        validation.validaCorpoDaRequisicao(despesa);

        log.info("[PROGRESS] Setando atributos da variável despesaAtualizada com base nos valores recebidos na requisição...");
        despesaAtualizada.setStatusDespesa(despesa.getStatusDespesa());
        despesaAtualizada.setTipoDespesa(despesa.getTipoDespesa());
        despesaAtualizada.setDescricao(despesa.getDescricao());

        if (despesa.getDataPagamento() == null) despesaAtualizada.setDataPagamento("Em aberto");
        else despesaAtualizada.setDataPagamento(despesa.getDataPagamento());

        if (despesa.getDataAgendamento() == null) despesaAtualizada.setDataAgendamento("Não possui");
        else despesaAtualizada.setDataAgendamento(despesa.getDataAgendamento());

        despesaAtualizada.setValor(despesa.getValor());

        log.info("[PROGRESS] Salvando despesa atualizada na base de dados...");
        DespesaEntity despesaEntity = repository.save(despesaAtualizada);

        log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
        return modelMapper.mapper().map(despesaEntity, DespesaDTO.class);
    }

    public void deletaDespesaPorId(Long id) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de remoção de despesa por id...");
        Optional<DespesaEntity> despesaOptional = repository.findById(id);
        if (despesaOptional.isPresent()) {
            log.info("[PROGRESS] Removendo despesa da base de dados...");
            repository.deleteById(id);
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
        }
        else {
            log.error("[ERROR] Não existe nenhuma despesa cadastrada com o id " + id);
            throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada com o id " + id);
        }
    }

}
