package br.com.twobrothers.frontend.repositories.services;

import br.com.twobrothers.frontend.models.entities.postagem.CategoriaEntity;
import br.com.twobrothers.frontend.models.entities.postagem.SubCategoriaEntity;
import br.com.twobrothers.frontend.repositories.CategoriaRepository;
import br.com.twobrothers.frontend.repositories.SubCategoriaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubCategoriaCrudService {

    @Autowired
    SubCategoriaRepository subCategoriaRepository;

    public List<SubCategoriaEntity> buscaTodasCategorias() {
        log.info("[STARTING] Iniciando método de busca de todas as subcategorias");
        return subCategoriaRepository.findAll();
    }
}
