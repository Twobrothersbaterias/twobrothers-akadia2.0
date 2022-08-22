package br.com.twobrothers.msvendas.services;

import br.com.twobrothers.msvendas.config.ModelMapperConfig;
import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msvendas.models.dto.ClienteDTO;
import br.com.twobrothers.msvendas.models.entities.ClienteEntity;
import br.com.twobrothers.msvendas.models.entities.EnderecoEntity;
import br.com.twobrothers.msvendas.models.entities.OrdemEntity;
import br.com.twobrothers.msvendas.models.enums.ValidationType;
import br.com.twobrothers.msvendas.repositories.ClienteRepository;
import br.com.twobrothers.msvendas.validations.ClienteValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    ClienteValidation validation = new ClienteValidation();

    //TODO Verificar como fica a parte da persistência de endereço
    public ClienteDTO criaNovo(ClienteDTO cliente) {
        if (validation.validaCorpoRequisicao(cliente, repository, ValidationType.CREATE)) {
            return modelMapper.mapper().map(repository
                    .save(modelMapper.mapper().map(cliente, ClienteEntity.class)), ClienteDTO.class);
        }
        throw new InvalidRequestException("A validação da requisição falhou. Verificar corpo da requisição.");
    }

    public List<ClienteDTO> buscaTodos() {
        if (!repository.findAll().isEmpty()) return repository.findAll().stream()
                .map(x -> modelMapper.mapper().map(x, ClienteDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum cliente salvo na base de dados.");
    }

    public List<ClienteDTO> buscaPorPaginacao(Pageable paginacao) {
        if (!repository.findAll(paginacao).isEmpty()) return repository.findAll(paginacao)
                .getContent().stream().map(x -> modelMapper.mapper().map(x, ClienteDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum cliente cadastrado na página indicada");
    }

    public List<ClienteDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {

        try {
            List<ClienteEntity> clientes = repository.buscaPorRangeDeDataCadastro(
                    (LocalDate.parse(dataInicio)).atTime(0, 0),
                    (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

            if (!clientes.isEmpty())
                return clientes.stream().map(x -> modelMapper.mapper().map(x, ClienteDTO.class)).collect(Collectors.toList());
            throw new ObjectNotFoundException("Não existe nenhum cliente cadastrado no range de datas indicado");
        } catch (Exception e) {
            throw new InvalidRequestException("Falha na requisição. Motivo: Padrão de data recebido inválido");
        }

    }

    public ClienteDTO buscaPorId(Long id) {
        if (repository.findById(id).isPresent()) {
            return modelMapper.mapper().map(repository.findById(id).get(), ClienteDTO.class);
        }
        throw new ObjectNotFoundException("Não existe nenhum cliente cadastrado no banco de dados com o id " + id);
    }

    public ClienteDTO buscaPorCpfCnpj(String cpfCnpj) {
        if (repository.buscaPorCpfCnpj(cpfCnpj).isPresent())
            return modelMapper.mapper().map(repository.buscaPorCpfCnpj(cpfCnpj).get(), ClienteDTO.class);
        throw new ObjectNotFoundException("Nenhum cliente foi encontrado através do atributo cpfCnpj enviado.");
    }

    public ClienteDTO buscaPorEmail(String email) {
        if (repository.buscaPorEmail(email).isPresent())
            return modelMapper.mapper().map(repository.buscaPorEmail(email).get(), ClienteDTO.class);
        throw new ObjectNotFoundException("Nenhum cliente foi encontrado através do atributo email enviado.");
    }

    public ClienteDTO buscaPorTelefone(String telefone) {
        if (!repository.buscaPorTelefone(telefone).isEmpty())
            return modelMapper.mapper().map(repository.buscaPorTelefone(telefone), ClienteDTO.class);
        throw new ObjectNotFoundException("Nenhum cliente foi encontrado através do atributo telefone enviado.");
    }

    public ClienteDTO buscaPorNomeCompleto(String nomeCompleto) {
        if (!repository.buscaPorNomeCompleto(nomeCompleto).isEmpty())
            return modelMapper.mapper().map(repository.buscaPorNomeCompleto(nomeCompleto), ClienteDTO.class);
        throw new ObjectNotFoundException("Nenhum cliente foi encontrado através do atributo nomeCompleto enviado.");
    }

    //TODO Verificar como fica a parte da persistência de endereço
    public ClienteDTO atualizaPorId(Long id, ClienteDTO cliente) {

        Optional<ClienteEntity> clienteOptional = repository.findById(id);

        if (clienteOptional.isPresent()) {

            ClienteEntity clienteAtualizado = clienteOptional.get();

            if (validation.validaCorpoRequisicao(cliente, repository, ValidationType.UPDATE)) {

                clienteAtualizado.setCpfCnpj(cliente.getCpfCnpj());
                clienteAtualizado.setEmail(cliente.getEmail());
                clienteAtualizado.setEndereco(modelMapper.mapper().map(cliente.getEndereco(), EnderecoEntity.class));
                clienteAtualizado.setDataNascimento(cliente.getDataNascimento());
                clienteAtualizado.setNomeCompleto(cliente.getNomeCompleto());
                clienteAtualizado.setTelefone(cliente.getTelefone());
                clienteAtualizado.setOrdens(cliente.getOrdens().stream()
                        .map(x -> modelMapper.mapper().map(x, OrdemEntity.class)).collect(Collectors.toList()));

                return modelMapper.mapper().map(repository.save(clienteAtualizado), ClienteDTO.class);

            }

            throw new InvalidRequestException("Corpo da requisição inválido");

        }
        throw new ObjectNotFoundException("Não existe nenhum cliente cadastrado com o id " + id);

    }

    public Boolean deletaPorId(Long id) {
        Optional<ClienteEntity> clienteOptional = repository.findById(id);
        if (clienteOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Não existe nenhum cliente cadastrado com o id " + id);
    }

}
