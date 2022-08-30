package br.com.twobrothers.msbalanco.services;

import br.com.twobrothers.msbalanco.config.ModelMapperConfig;
import br.com.twobrothers.msbalanco.models.dto.PatrimonioDTO;
import br.com.twobrothers.msbalanco.models.entities.PatrimonioEntity;
import br.com.twobrothers.msbalanco.repositories.PatrimonioRepository;
import br.com.twobrothers.msbalanco.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msbalanco.validations.PatrimonioValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.twobrothers.msbalanco.utils.StringConstants.BARRA_DE_LOG;
import static br.com.twobrothers.msbalanco.utils.StringConstants.REQUISICAO_FINALIZADA_COM_SUCESSO;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Slf4j
@Service
public class PatrimonioService {

    @Autowired
    PatrimonioRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    PatrimonioValidation validation = new PatrimonioValidation();

    public PatrimonioDTO criaNovo(PatrimonioDTO patrimonio) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação de patrimonio...");

        log.info("[PROGRESS] Setando data de cadastro do patrimonio...");
        patrimonio.setDataCadastro(LocalDateTime.now());

        log.info("[PROGRESS] Inicializando método de validação do patrimônio...");
        validation.validaCorpoDaRequisicao(patrimonio);

        log.info("[PROGRESS] Persistindo patrimonio na base de dados...");
        PatrimonioEntity patrimonioEntity = repository.save(modelMapper.mapper().map(patrimonio, PatrimonioEntity.class));

        log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
        return modelMapper.mapper().map(patrimonioEntity, PatrimonioDTO.class);
    }

    public List<PatrimonioDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca por range de data de cadastro...");

        List<PatrimonioEntity> patrimonios = repository.buscaPorRangeDeDataCadastro(
                (LocalDate.parse(dataInicio)).atTime(0, 0),
                (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

        if (!patrimonios.isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return patrimonios.stream().map(x -> modelMapper.mapper().map(x, PatrimonioDTO.class)).collect(Collectors.toList());
        }

        log.error("[ERROR] Não existe nenhuma despesa cadastrada no range de datas indicado");
        throw new ObjectNotFoundException("Não existe nenhuma despesa cadastrada no range de datas indicado");
    }

    public List<PatrimonioDTO> buscaPorPaginacao(Pageable paginacao) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de patrimônio por paginação...");
        if (!repository.findAll(paginacao).isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return repository.findAll(paginacao)
                    .getContent().stream().map(x -> modelMapper.mapper().map(x, PatrimonioDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Não existe nenhum patrimônio cadastrado na página indicada");
        throw new ObjectNotFoundException("Não existe nenhum patrimônio cadastrado na página indicada");
    }

    public List<PatrimonioDTO> buscaPorDescricao(String descricao) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de patrimônio por descrição...");
        List<PatrimonioEntity> patrimonios = repository.buscaPorDescricao(descricao);
        if (!patrimonios.isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return patrimonios
                    .stream().map(x -> modelMapper.mapper().map(x, PatrimonioDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Não existe nenhum patrimônio cadastrado com a descrição recebida pela requisição");
        throw new ObjectNotFoundException("Não existe nenhum patrimônio cadastrado com a descrição passada");
    }

    public List<PatrimonioDTO> buscaTodos() {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de todos os patrimônios...");
        if (!repository.findAll().isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return repository.findAll()
                    .stream().map(x -> modelMapper.mapper().map(x, PatrimonioDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Não existe nenhum patrimônio cadastrado na base de dados");
        throw new ObjectNotFoundException("Não existe nenhum patrimônio cadastrado");
    }

    public PatrimonioDTO atualizaPorId(Long id, PatrimonioDTO patrimonio) {

        Optional<PatrimonioEntity> patrimonioOptional = repository.findById(id);

        if (patrimonioOptional.isEmpty()) {
            log.error("[ERROR] Não existe nenhum patrimônio cadastrado com o id {}", id);
            throw new ObjectNotFoundException("Não existe nenhum patrimônio cadastrado com o id " + id);
        }

        PatrimonioEntity patrimonioAtualizado = patrimonioOptional.get();

        log.info("[PROGRESS] Inicializando método de validação do objeto PatrimonioDTO...");
        validation.validaCorpoDaRequisicao(patrimonio);

        log.info("[PROGRESS] Setando atributos no objeto patrimonioAtualizado...");

        patrimonioAtualizado.setTipoPatrimonio(patrimonio.getTipoPatrimonio());
        patrimonioAtualizado.setStatusPatrimonio(patrimonio.getStatusPatrimonio());
        patrimonioAtualizado.setNome(patrimonio.getNome());
        patrimonioAtualizado.setDataAgendamento(patrimonio.getDataAgendamento());
        patrimonioAtualizado.setValor(patrimonio.getValor());

        log.info("[PROGRESS] Atualizando patrimonio no banco de dados...");
        PatrimonioEntity patrimonioEntity = repository.save(patrimonioAtualizado);

        log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
        return modelMapper.mapper().map(patrimonioEntity, PatrimonioDTO.class);
    }

    public Boolean deletaPorId(Long id) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de remoção de patrimônio por id...");

        Optional<PatrimonioEntity> patrimonioOptional = repository.findById(id);

        if (patrimonioOptional.isPresent()) {
            log.info("[PROGRESS] Removendo patrimônio da base de dados...");
            repository.deleteById(id);

            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return true;
        }
        log.error("[ERROR] Não existe nenhum patrimônio cadastrado com o id {}", id);
        throw new ObjectNotFoundException("Não existe nenhum patrimônio cadastrado com o id " + id);
    }

}
