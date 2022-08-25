package br.com.twobrothers.msvendas.repositories.services;

import br.com.twobrothers.msvendas.config.ModelMapperConfig;
import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msvendas.models.dto.AbastecimentoDTO;
import br.com.twobrothers.msvendas.models.entities.AbastecimentoEntity;
import br.com.twobrothers.msvendas.models.entities.FornecedorEntity;
import br.com.twobrothers.msvendas.repositories.AbastecimentoRepository;
import br.com.twobrothers.msvendas.validations.AbastecimentoValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AbastecimentoService {

    @Autowired
    AbastecimentoRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    AbastecimentoValidation validation = new AbastecimentoValidation();

    //TODO TESTAR PERSISTÊNCIA (SE PERSISTE NOS MANYTOONE)
    //TODO IMPLEMENTAR REGRA DE NEGÓCIO DE AUMENTO DE ESTOQUE NO PRODUTO A CADA ABSTECIMENTO REALIZADO
    public AbastecimentoDTO criaNovo(AbastecimentoDTO abastecimento) {
        if (validation.validaCorpoRequisicao(abastecimento)) {
            abastecimento.setCustoUnitario(abastecimento.getCustoTotal() / abastecimento.getQuantidade());
            return modelMapper.mapper().map(repository
                    .save(modelMapper.mapper().map(abastecimento, AbastecimentoEntity.class)), AbastecimentoDTO.class);
        }
        throw new InvalidRequestException("A validação da requisição falhou. Verificar corpo da requisição.");
    }

    public List<AbastecimentoDTO> buscaTodos() {
        if (!repository.findAll().isEmpty()) return repository.findAll().stream()
                .map(x -> modelMapper.mapper().map(x, AbastecimentoDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum abastecimento salvo na base de dados.");
    }

    public List<AbastecimentoDTO> buscaPorPaginacao(Pageable paginacao) {
        if (!repository.findAll(paginacao).isEmpty()) return repository.findAll(paginacao)
                .getContent().stream().map(x -> modelMapper.mapper().map(x, AbastecimentoDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum abastecimento cadastrado na página indicada");
    }

    public List<AbastecimentoDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {

        try {
            List<AbastecimentoEntity> abastecimentos = repository.buscaPorRangeDeDataCadastro(
                    (LocalDate.parse(dataInicio)).atTime(0, 0),
                    (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

            if (!abastecimentos.isEmpty())
                return abastecimentos.stream().map(x -> modelMapper.mapper().map(x, AbastecimentoDTO.class)).collect(Collectors.toList());
            throw new ObjectNotFoundException("Não existe nenhum abastecimento cadastrado no range de datas indicado");
        } catch (Exception e) {
            throw new InvalidRequestException("Falha na requisição. Motivo: Padrão de data recebido inválido");
        }

    }

    public AbastecimentoDTO buscaPorId(Long id) {
        if (repository.findById(id).isPresent()) {
            return modelMapper.mapper().map(repository.findById(id).get(), AbastecimentoDTO.class);
        }
        throw new ObjectNotFoundException("Não existe nenhum abastecimento cadastrado no banco de dados com o id " + id);
    }

    public AbastecimentoDTO atualizaPorId(Long id, AbastecimentoDTO abastecimento) {

        //TODO TESTAR PERSISTÊNCIA (SE PERSISTE NOS MANYTOONE)
        //TODO IMPLEMENTAR REGRA DE NEGÓCIO DE AUMENTO DE ESTOQUE NO PRODUTO A CADA ABSTECIMENTO REALIZADO

        Optional<AbastecimentoEntity> abastecimentoOptional = repository.findById(id);

        if (abastecimentoOptional.isPresent()) {

            AbastecimentoEntity abastecimentoAtualizado = abastecimentoOptional.get();

            if (validation.validaCorpoRequisicao(abastecimento)) {

                abastecimentoAtualizado.setCustoTotal(abastecimento.getCustoTotal());
                abastecimentoAtualizado.setObservacao(abastecimento.getObservacao());
                abastecimentoAtualizado.setFormaPagamento(abastecimento.getFormaPagamento());
                abastecimentoAtualizado.setQuantidade(abastecimento.getQuantidade());
                abastecimentoAtualizado.setCustoUnitario(abastecimento.getCustoTotal()/abastecimento.getQuantidade());
                abastecimentoAtualizado.setFornecedor(modelMapper.mapper().map(abastecimento.getFornecedor(), FornecedorEntity.class));

                return modelMapper.mapper().map(repository.save(abastecimentoAtualizado), AbastecimentoDTO.class);

            }

            throw new InvalidRequestException("Corpo da requisição inválido");

        }
        throw new ObjectNotFoundException("Não existe nenhum abastecimento cadastrado com o id " + id);

    }

    public Boolean deletaPorId(Long id) {
        Optional<AbastecimentoEntity> abastecimentoOptional = repository.findById(id);
        if (abastecimentoOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Não existe nenhum abastecimento cadastrado com o id " + id);
    }

}
