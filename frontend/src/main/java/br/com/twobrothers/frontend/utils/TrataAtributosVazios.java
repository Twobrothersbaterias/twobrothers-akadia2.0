package br.com.twobrothers.frontend.utils;

import br.com.twobrothers.frontend.models.dto.ClienteDTO;
import br.com.twobrothers.frontend.models.dto.EnderecoDTO;
import br.com.twobrothers.frontend.models.dto.FornecedorDTO;
import br.com.twobrothers.frontend.models.dto.UsuarioDTO;

public class TrataAtributosVazios {

    TrataAtributosVazios(){}

    public static void trataAtributosVaziosDoObjetoCliente(ClienteDTO clienteDTO) {
        if (clienteDTO.getNomeCompleto().isEmpty()) clienteDTO.setNomeCompleto(null);
        if (clienteDTO.getEmail().isEmpty()) clienteDTO.setEmail(null);
        if (clienteDTO.getTelefone().isEmpty()) clienteDTO.setTelefone(null);
        if (clienteDTO.getDataNascimento().isEmpty()) clienteDTO.setDataNascimento(null);
        if (clienteDTO.getCpfCnpj().isEmpty()) clienteDTO.setCpfCnpj(null);
    }

    public static void trataAtributosVaziosDoObjetoColaborador(UsuarioDTO colaborador) {
        if (colaborador.getNome().isEmpty()) colaborador.setNome(null);
        if (colaborador.getEmail().isEmpty()) colaborador.setEmail(null);
        if (colaborador.getNomeUsuario().isEmpty()) colaborador.setNomeUsuario(null);
        if (colaborador.getTelefone().isEmpty()) colaborador.setTelefone(null);
        if (colaborador.getDataNascimento().isEmpty()) colaborador.setDataNascimento(null);
        if (colaborador.getCpfCnpj().isEmpty()) colaborador.setCpfCnpj(null);
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

    public static boolean verificaSeClienteNulo(ClienteDTO clienteDTO) {

        if (clienteDTO == null) return true;

        return clienteDTO.getNomeCompleto() == null
                && clienteDTO.getEmail() == null
                && clienteDTO.getTelefone() == null
                && clienteDTO.getDataNascimento() == null
                && clienteDTO.getCpfCnpj() == null;

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
