package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.models.entities.OrdemEntity;
import br.com.twobrothers.frontend.models.entities.PatrimonioEntity;
import br.com.twobrothers.frontend.repositories.OrdemRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.ClienteCrudService;
import br.com.twobrothers.frontend.utils.ConversorDeDados;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static br.com.twobrothers.frontend.utils.StringConstants.TIPO_FILTRO;

@Slf4j
@Service
public class LancamentoService {

    @Autowired
    OrdemRepository ordemRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ProdutoEstoqueService produtoEstoqueService;

    @Autowired
    ClienteCrudService clienteCrudService;

    public ModelMap modelMapBuilder(ModelMap modelMap, String id) {

        log.info("[STARTING] Iniciando construção do modelMap...");
        HashMap<String, Object> atributos = new HashMap<>();

        OrdemEntity ordem = null;
        if (id != null) ordem = ordemRepository.findById(Long.parseLong(id)).get();
        if (ordem != null) atributos.put("ordemEdicao", ordem);

        log.info("[PROGRESS] Adicionando atributos ao modelMap...");
        atributos.put("ordem", null);
        atributos.put("entradas", null);
        atributos.put("pagamentos", null);
        atributos.put("colaboradores", usuarioRepository.buscaTodosSemPaginacao());
        atributos.put("privilegio", UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());
        atributos.put("produtos", produtoEstoqueService.buscaTodos());
        atributos.put("clientes", clienteCrudService.buscaTodos());

        modelMap.addAllAttributes(atributos);

        log.info("[SUCCESS] ModelMap construído com sucesso. Retornando para o controller...");
        return modelMap;
    }

}
