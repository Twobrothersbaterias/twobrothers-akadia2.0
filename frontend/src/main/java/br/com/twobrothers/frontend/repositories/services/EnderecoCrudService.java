package br.com.twobrothers.frontend.repositories.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.EnderecoDTO;
import br.com.twobrothers.frontend.models.entities.EnderecoEntity;
import br.com.twobrothers.frontend.repositories.EnderecoRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.twobrothers.frontend.utils.StringConstants.BARRA_DE_LOG;
import static br.com.twobrothers.frontend.utils.StringConstants.REQUISICAO_FINALIZADA_COM_SUCESSO;

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
public class EnderecoCrudService {

    @Autowired
    EnderecoRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    public List<EnderecoDTO> buscaTodos() {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca por todos os endereços...");
        if (!repository.findAll().isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return repository.findAll().stream()
                    .map(x -> modelMapper.mapper().map(x, EnderecoDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Não existe nenhum endereço salvo na base de dados");
        throw new ObjectNotFoundException("Não existe nenhum endereço salvo na base de dados.");
    }

    public List<EnderecoDTO> buscaPorPaginacao(Pageable paginacao) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de endereços por paginação...");
        if (!repository.findAll(paginacao).isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return repository.findAll(paginacao)
                    .getContent().stream().map(x -> modelMapper.mapper().map(x, EnderecoDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Nenhum endereço foi encontrado com base nos parâmetros indicados");
        throw new ObjectNotFoundException("Não existe nenhum endereço cadastrado na página indicada");
    }

    public List<EnderecoDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de endereços por range de data de cadastro...");

        List<EnderecoEntity> enderecos = repository.buscaPorRangeDeDataCadastro(
                (LocalDate.parse(dataInicio)).atTime(0, 0),
                (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

        if (!enderecos.isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return enderecos.stream().map(x -> modelMapper.mapper().map(x, EnderecoDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Nenhum endereço foi encontrado com base nas datas indicadas");
        throw new ObjectNotFoundException("Não existe nenhum endereço cadastrado no range de datas indicado");

    }

    public EnderecoDTO buscaPorId(Long id) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de endereços por id...");
        if (repository.findById(id).isPresent()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return modelMapper.mapper().map(repository.findById(id).get(), EnderecoDTO.class);
        }
        log.error("[ERROR] Nenhum endereço foi encontrado através do id {}", id);
        throw new ObjectNotFoundException("Não existe nenhum endereço cadastrado no banco de dados com o id " + id);
    }

}
