package br.com.twobrothers.frontend.repositories.services;

import br.com.twobrothers.frontend.models.entities.postagem.CategoriaEntity;
import br.com.twobrothers.frontend.repositories.CategoriaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoriaCrudService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public List<CategoriaEntity> buscaTodasCategorias() {
        log.info("[STARTING] Iniciando método de busca de todas as categorias");
        return categoriaRepository.findAll();
    }
}
