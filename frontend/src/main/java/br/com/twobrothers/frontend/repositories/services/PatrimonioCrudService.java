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

        if (patrimonio.getDataEntrada() == null) patrimonio.setDataEntrada("Em aberto");

        log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
    }

    public List<PatrimonioEntity> buscaPorPeriodo(Pageable pageable, Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de patrimônio por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return repository.buscaPorPeriodo(pageable, dataInicio.toString(), dataFim.toString());
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

    public List<PatrimonioEntity> buscaEsteMes(Pageable pageable) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de patrimônio por período...");
        LocalDate dataInicio = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate dataFim =
                LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),
                LocalDate.now()
                        .withMonth(LocalDate.now().getMonthValue())
                        .withYear(LocalDate.now().getYear())
                        .with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());

        return repository.buscaPorPeriodo(pageable, dataInicio.toString(), dataFim.toString());
    }

    public List<PatrimonioEntity> buscaPorPeriodoSemPaginacao(Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de patrimônio por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return repository.buscaPorPeriodoSemPaginacao(dataInicio.toString(), dataFim.toString());
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

    public List<PatrimonioEntity> buscaEsteMesSemPaginacao() {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de patrimônio por período...");
        LocalDate dataInicio = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate dataFim =
                LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),
                        LocalDate.now()
                                .withMonth(LocalDate.now().getMonthValue())
                                .withYear(LocalDate.now().getYear())
                                .with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());

        return repository.buscaPorPeriodoSemPaginacao(dataInicio.toString(), dataFim.toString());
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

        if (patrimonio.getDataEntrada() == null) patrimonioAtualizado.setDataEntrada("Em aberto");
        else patrimonioAtualizado.setDataEntrada(patrimonio.getDataEntrada());

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
