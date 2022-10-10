package br.com.twobrothers.frontend.utils;

import br.com.twobrothers.frontend.models.dto.ClienteDTO;
import br.com.twobrothers.frontend.models.dto.EnderecoDTO;
import br.com.twobrothers.frontend.models.dto.FornecedorDTO;

public class TrataAtributosVazios {

    TrataAtributosVazios(){}

    public static void trataAtributosVaziosDoObjetoCliente(ClienteDTO clienteDTO) {
        if (clienteDTO.getNomeCompleto().isEmpty()) clienteDTO.setNomeCompleto(null);
        if (clienteDTO.getEmail().isEmpty()) clienteDTO.setEmail(null);
        if (clienteDTO.getTelefone().isEmpty()) clienteDTO.setTelefone(null);
        if (clienteDTO.getDataNascimento().isEmpty()) clienteDTO.setDataNascimento(null);
        if (clienteDTO.getCpfCnpj().isEmpty()) clienteDTO.setCpfCnpj(null);
    }

    public static void trataAtributosVaziosDoObjetoFornecedor(FornecedorDTO fornecedorDTO) {
        if (fornecedorDTO.getNome().isEmpty()) fornecedorDTO.setNome(null);
        if (fornecedorDTO.getEmail().isEmpty()) fornecedorDTO.setEmail(null);
        if (fornecedorDTO.getTelefone().isEmpty()) fornecedorDTO.setTelefone(null);
        if (fornecedorDTO.getDataNascimento().isEmpty()) fornecedorDTO.setDataNascimento(null);
        if (fornecedorDTO.getCpfCnpj().isEmpty()) fornecedorDTO.setCpfCnpj(null);
    }

    public static void trataAtributosVaziosDoObjetoEndereco(EnderecoDTO enderecoDTO) {
        if (enderecoDTO.getCep().isEmpty()) enderecoDTO.setCep(null);
        if (enderecoDTO.getLogradouro().isEmpty()) enderecoDTO.setLogradouro(null);
        if (enderecoDTO.getCidade().isEmpty()) enderecoDTO.setCidade(null);
        if (enderecoDTO.getComplemento().isEmpty()) enderecoDTO.setComplemento(null);
        if (enderecoDTO.getBairro().isEmpty()) enderecoDTO.setBairro(null);
    }

    public static boolean verificaSeEnderecoNulo(EnderecoDTO enderecoDTO) {

        if (enderecoDTO == null) return true;

        return enderecoDTO.getLogradouro() == null
                && enderecoDTO.getCidade() == null
                && enderecoDTO.getNumero() == null
                && enderecoDTO.getBairro() == null
                && enderecoDTO.getCep() == null;
    }

}
