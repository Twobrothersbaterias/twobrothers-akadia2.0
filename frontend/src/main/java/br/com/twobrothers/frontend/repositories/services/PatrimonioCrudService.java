package br.com.twobrothers.frontend.repositories.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.PatrimonioDTO;
import br.com.twobrothers.frontend.models.entities.PatrimonioEntity;
import br.com.twobrothers.frontend.models.enums.TipoPatrimonioEnum;
import br.com.twobrothers.frontend.models.enums.ValidationType;
import br.com.twobrothers.frontend.repositories.PatrimonioRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import br.com.twobrothers.frontend.validations.PatrimonioValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
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
public class PatrimonioCrudService {

    @Autowired
    PatrimonioRepository repository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    PatrimonioValidation validation = new PatrimonioValidation();

    public void criaNovo(PatrimonioDTO patrimonio) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação de patrimonio...");

        log.info("[PROGRESS] Setando data de cadastro do patrimonio...");
        patrimonio.setDataCadastro(LocalDate.now().toString());

        log.info("[PROGRESS] Setando usuário responsável pelo patrimônio...");
        patrimonio.setUsuarioResponsavel(UsuarioUtils.loggedUser(usuarioRepository).getUsername());

        log.info("[PROGRESS] Inicializando método de validação do patrimônio...");
        validation.validaCorpoDaRequisicao(patrimonio, ValidationType.CREATE);

        log.info("[PROGRESS] Persistindo patrimonio na base de dados...");
        repository.save(modelMapper.mapper().map(patrimonio, PatrimonioEntity.class));

        if (patrimonio.getDataPagamento() == null) patrimonio.setDataPagamento("Em aberto");
        if (patrimonio.getDataAgendamento() == null) patrimonio.setDataAgendamento("Não possui");

        log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
    }

    public List<PatrimonioEntity> buscaPorRangeDeData(Pageable pageable, String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de patrimônio por range de data...");
        validation.validaRangeData(dataInicio, dataFim);
        return repository.buscaPorRangeDeData(pageable, dataInicio, dataFim);
    }

    public List<PatrimonioEntity> buscaPorPeriodo(Pageable pageable, Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de patrimônio por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return repository.buscaPorPeriodo(pageable, dataInicio.toString(), dataFim.toString());
    }

    public List<PatrimonioEntity> buscaHoje(Pageable pageable) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de todas as patrimônios pagos hoje ou agendados para hoje...");
        LocalDate hoje = LocalDate.now();
        return repository.buscaHoje(pageable, hoje.toString());
    }

    public List<PatrimonioEntity> buscaPorDescricao(Pageable pageable, String descricao) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de patrimônio por descrição...");
        return repository.buscaPorDescricao(pageable, descricao);
    }

    public List<PatrimonioEntity> buscaPorTipo(Pageable pageable, TipoPatrimonioEnum tipo) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de patrimônios por tipo...");
        return repository.buscaPorTipo(pageable, tipo);
    }

    public List<PatrimonioEntity> buscaPorRangeDeDataSemPaginacao(String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de patrimônio por range de data...");
        return repository.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
    }

    public List<PatrimonioEntity> buscaPorPeriodoSemPaginacao(Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de patrimônio por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return repository.buscaPorPeriodoSemPaginacao(dataInicio.toString(), dataFim.toString());
    }

    public List<PatrimonioEntity> buscaHojeSemPaginacao() {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de todos os patrimônios pagos hoje ou agendados para hoje...");
        LocalDate hoje = LocalDate.now();
        return repository.buscaHojeSemPaginacao(hoje.toString());
    }

    public List<PatrimonioEntity> buscaAgendadosHojeSemPaginacao() {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de todas as patrimônios agendadas para hoje...");
        LocalDate hoje = LocalDate.now();
        return repository.buscaAgendadosHojeSemPaginacao(hoje.toString());
    }

    public List<PatrimonioEntity> buscaPorDescricaoSemPaginacao(String descricao) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de patrimônio por descrição...");
        return repository.buscaPorDescricaoSemPaginacao(descricao);
    }

    public List<PatrimonioEntity> buscaPorTipoSemPaginacao(TipoPatrimonioEnum tipo) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de patrimônio por tipo...");
        return repository.buscaPorTipoSemPaginacao(tipo);
    }

    public void atualizaPorId(PatrimonioDTO patrimonio) {

        Optional<PatrimonioEntity> patrimonioOptional = repository.findById(patrimonio.getId());

        if (patrimonioOptional.isEmpty()) {
            log.error("[ERROR] Não existe nenhum patrimônio cadastrado com o id {}", patrimonio.getId());
            throw new ObjectNotFoundException("Não existe nenhum patrimônio cadastrado com o id " + patrimonio.getId());
        }

        PatrimonioEntity patrimonioAtualizado = patrimonioOptional.get();

        log.info("[PROGRESS] Inicializando método de validação do objeto PatrimonioDTO...");
        validation.validaCorpoDaRequisicao(patrimonio, ValidationType.UPDATE);

        log.info("[PROGRESS] Setando atributos no objeto patrimonioAtualizado...");

        patrimonioAtualizado.setTipoPatrimonio(patrimonio.getTipoPatrimonio());
        patrimonioAtualizado.setStatusPatrimonio(patrimonio.getStatusPatrimonio());
        patrimonioAtualizado.setNome(patrimonio.getNome());
        patrimonioAtualizado.setValor(patrimonio.getValor());

        if (patrimonio.getDataPagamento() == null) patrimonioAtualizado.setDataPagamento("Em aberto");
        else patrimonioAtualizado.setDataPagamento(patrimonio.getDataPagamento());

        if (patrimonio.getDataAgendamento() == null) patrimonioAtualizado.setDataAgendamento("Não possui");
        else patrimonioAtualizado.setDataAgendamento(patrimonio.getDataAgendamento());

        log.info("[PROGRESS] Atualizando patrimonio no banco de dados...");
        repository.save(patrimonioAtualizado);

        log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
    }

    public void deletaPorId(Long id) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de remoção de patrimônio por id...");

        Optional<PatrimonioEntity> patrimonioOptional = repository.findById(id);

        if (patrimonioOptional.isEmpty()) {
            log.error("[ERROR] Não existe nenhum patrimônio cadastrado com o id {}", id);
            throw new ObjectNotFoundException("Não existe nenhum patrimônio cadastrado com o id " + id);
        }

        log.info("[PROGRESS] Removendo patrimônio da base de dados...");
        repository.deleteById(id);

        log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);

    }

}
