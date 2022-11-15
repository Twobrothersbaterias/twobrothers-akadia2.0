package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.models.entities.DespesaEntity;
import br.com.twobrothers.frontend.models.entities.OrdemEntity;
import br.com.twobrothers.frontend.repositories.OrdemRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.ClienteCrudService;
import br.com.twobrothers.frontend.repositories.services.DespesaCrudService;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.HashMap;
import java.util.List;

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

    @Autowired
    DespesaCrudService despesaCrudService;

    public ModelMap modelMapBuilder(ModelMap modelMap, String id) {

        log.info("[STARTING] Iniciando construção do modelMap...");
        HashMap<String, Object> atributos = new HashMap<>();

        OrdemEntity ordem = null;
        if (id != null) ordem = ordemRepository.findById(Long.parseLong(id)).get();
        if (ordem != null) atributos.put("ordemEdicao", ordem);

        log.info("[PROGRESS] Verificando se existem itens em atraso ou que vencem hoje...");
        List<DespesaEntity> despesasAtrasadas = despesaCrudService.buscaDespesasAtrasadas();
        List<DespesaEntity> despesasHoje = despesaCrudService.buscaDespesasComVencimentoParaHoje();

        log.info("[PROGRESS] Iniciando setagem da lista de objetos encontrados com o filtro atual...");
        atributos.put("despesasAtrasadas", despesasAtrasadas);
        atributos.put("despesasHoje", despesasHoje);
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
