/* =========================== RESPONSIVIDADE DA TELA =========================== */

window.onload = responsive();
window.onresize = doALoadOfStuff;
ajustaTabela();
buildUrlPages();

var privilegio = document.getElementById('body').getAttribute('data-privilegio');
var tipoFiltro = document.getElementById('tipo_filtro');

console.log('Tipo do filtro: ' + tipoFiltro.value);
console.log('Privilégio: ' + privilegio);

if (tipoFiltro != null) {
	tituloResponsivo(tipoFiltro.value);
}	

document.onkeydown=function(){

	var keyCode = window.event.keyCode;
	bind(keyCode);

}

function bind(keyCode) {

    if(keyCode == '27') {
        fechaNovoItem();
        fecharFiltro();
        fechaEditaItem();
    }

    if(keyCode == '13') {
		if(document.getElementById('conteudo_container_edita').hidden==false) {
		    validacaoCampos('edita');
		}
		else if (document.getElementById('conteudo_container_filtro').hidden==false) {

		    var btAdd = document.getElementById('filtro_bt');
		    var btBuscar = document.getElementById('filtro_buscar_bt_inside');

		    if(btAdd.hidden == false) {
				btAdd.click();
			}
			else {
				btBuscar.click();
			}

		}
		else if (document.getElementById('conteudo_container_novo').hidden==false) {
			validacaoCampos('novo');
		}    
	}    

    if (document.getElementById('conteudo_container_filtro').hidden==true
    	&& document.getElementById('conteudo_container_edita').hidden==true
    	&& document.getElementById('conteudo_container_novo').hidden==true) {

	    if (keyCode == '49') {
	    	window.location.href="/";
	    }

	    else if (keyCode == '50') {
	    	window.location.href="/clientes";
	    }

	    else if (keyCode == '51') {
	    	window.location.href="/vendas";
	    }	    

	    else if (keyCode == '52') {
	    	window.location.href="/estoque";
	    }

	    else if (keyCode == '53' && privilegio != "Vendedor") {
	    	window.location.href="/despesas";
	    }

	    else if (keyCode == '54' && privilegio != "Vendedor") {
	    	window.location.href="/relatorios";
	    }

	    else if (keyCode == '55' && privilegio != "Vendedor") {
	    	window.location.href="/patrimonios";
	    }

	    else if (keyCode == '56' && privilegio != "Vendedor") {
	    	window.location.href="/compras";
	    }  

	    else if (keyCode == '56' && privilegio != "Vendedor") {
	    	window.location.href="/fornecedores";
	    }  

	    else if (keyCode == '57' && privilegio != "Vendedor") {
	    	window.location.href="/colaboradores";
	    } 
	    else if (keyCode == '48' && privilegio != "Vendedor") {
	    	window.location.href="/precos";
	    } 	    

	    else if(keyCode == '32') {
	    	window.location.href="/lancamento";
	    }

	    else if(keyCode == '107') {
	    	abrirNovoItem();
	    }

	    else if(keyCode == '82') {
	    	window.location.href="/precos";
	    }

	    else if(keyCode == '70') {
	    	abrirFiltro();
	    }	 	  
    }                   
}

function responsive(){

	document.getElementById('main_loader').style.display="none";
	document.getElementById('main_row').style.display="flex";

	bodyWidth = document.getElementById('body').clientWidth;
	bodyHeight = document.getElementById('body').clientHeight;

	/* Ajustando content */
	var main = document.getElementById('main');
	var mainRow = document.getElementById('main_row');
	var sideMenu = document.getElementById('side_menu');
	var conteudoTituloText = document.getElementById('conteudo_titulo_text');
	var nomePerfil = document.getElementById('nome_perfil');
	var menuMobile = document.getElementById('menu_superior_mobile');
	var excluirText = document.getElementById('excluir_text');

	var conteudoContainer = document.getElementById('conteudo_container');
	var hrTabela = document.getElementById('hr_tabela');

	var containerNovo = document.getElementById('conteudo_container_novo');	
	var novoTitulo	= document.getElementById('novo_titulo');
	var novoItemSubmit = document.getElementById('novo_item_submit');

	var containerEdita = document.getElementById('conteudo_container_edita');	
	var editaTitulo	= document.getElementById('edita_titulo');
	var editaItemSubmit = document.getElementById('edita_item_submit');

	var img = document.getElementsByClassName('img');
	var aImg = document.getElementsByClassName('a_img');
	var imgContainer = document.getElementsByClassName('img_container');		

	var th = document.getElementsByClassName('th');
	var td = document.getElementsByClassName('td');
	var thDataCadastro = document.getElementsByClassName('th_cadastro');
	var tdDataCadastro = document.getElementsByClassName('td_cadastro');
	var thUsuario = document.getElementsByClassName('th_usuario');
	var tdUsuario = document.getElementsByClassName('td_usuario');
	var tdRemove = document.getElementsByClassName('td_checkbox');
	var thRemove = document.getElementsByClassName('th_remove');	
	var btnExcluir = document.getElementsByClassName('btn_excluir');
	var formRemoveImg = document.getElementsByClassName('form_remove_img');
	var formRemoveText = document.getElementsByClassName('form_remove_text');
	var liA = document.getElementsByClassName('li_a');
	var menuSuperiorMobileItem = document.getElementsByClassName('menu_superior_mobile_item');
	var novoItemInput = document.getElementsByClassName('novo_item_input');
	var editaItemInput = document.getElementsByClassName('edita_item_input');
	var filtroBlock = document.getElementsByClassName('filtro_block');
	var filtroBuscarBt = document.getElementById('filtro_buscar_bt');
	var editaSubtitulo = document.getElementById('edita_item_subtitulo');
	var filtroTitulo = document.getElementById('filtro_titulo');
	var pageClick = document.getElementsByClassName('page_click');			

	if(bodyWidth > 1200){
		console.log("Tela: Muito grande");

		sideMenu.style.display="block";
		menuMobile.style.display="none";

		if (bodyWidth < 1400) {
			main.style.width="95.5%";
			sideMenu.style.width="4.5%";	
		}
		else {
			main.style.width="96%";
			sideMenu.style.width="4%";
		}

		containerNovo.style.fontSize="1rem";		
		novoTitulo.style.fontSize="1.15rem";	
		novoItemSubmit.style.marginTop="0px";

		containerEdita.style.fontSize="1rem";		
		editaTitulo.style.fontSize="1.15rem";	
		editaItemSubmit.style.marginTop="0px";

		conteudoTituloText.style.fontSize="1.1rem";
		filtroTitulo.style.fontSize="1.1rem";
		conteudoContainer.style.marginTop="30px";
		hrTabela.style.marginBottom="25px";
		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.70rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.70rem";
		}

		for(var i = 0; i < liA.length; i++){
			liA[i].style.fontSize="0.85rem";
			liA[i].style.padding="6px 10px";			
		}	

		for(var i = 0; i < thDataCadastro.length; i++) {
			thDataCadastro[i].hidden=false;
		}

		for(var i = 0; i < tdDataCadastro.length; i++) {
			tdDataCadastro[i].hidden=false;
		}	

		for(var i = 0; i < formRemoveImg.length; i++) {
			formRemoveImg[i].style.display="none";
		}	

		for(var i = 0; i < btnExcluir.length; i++) {
			btnExcluir[i].style.display="block";
			btnExcluir[i].style.fontSize="0.70rem";
			btnExcluir[i].style.padding="4px 6px";
			btnExcluir[i].innerText="Excluir";
		}

		for(var i = 0; i < img.length; i++) {
			img[i].style.width="22px";
		}

		for(var i = 0; i < aImg.length; i++) {
			aImg[i].style.marginLeft="12px";
		}	
		for(var i = 0; i < imgContainer.length; i++) {
			imgContainer[i].style.width="20px";
			imgContainer[i].style.marginLeft="10px";			
		}

		for (var i = 0; i < pageClick.length; i++) {
			pageClick[i].style.fontSize="0.70rem";
		}					

		for(var i = 0; i < novoItemInput.length; i++) {
			novoItemInput[i].style.marginBottom="0";
		}	

		for(var i = 0; i < editaItemInput.length; i++) {
			editaItemInput[i].style.marginBottom="0";
		}

		for(var i = 0; i < filtroBlock.length; i++) {
			filtroBlock[i].style.marginBottom="20px";
		}

		filtroBuscarBt.style.marginTop="20px";
		filtroBuscarBt.style.justifyContent="center";
	}
	else if(bodyWidth <= 1200 && bodyWidth > 992){
		console.log("Tela: Grande");

		menuMobile.style.display="none";		
		sideMenu.style.display="block";

		if (bodyWidth < 1090) {
			main.style.width="94.5%";
			sideMenu.style.width="5.5%";	
		}
		else {
			main.style.width="95%";
			sideMenu.style.width="5%";				
		}


		containerNovo.style.fontSize="1rem";
		novoTitulo.style.fontSize="1.15rem";	
		novoItemSubmit.style.marginTop="0px";

		containerEdita.style.fontSize="1rem";
		editaTitulo.style.fontSize="1.15rem";	
		editaItemSubmit.style.marginTop="0px";	

		conteudoTituloText.style.fontSize="1.1rem";
		filtroTitulo.style.fontSize="1.1rem";	
		conteudoContainer.style.marginTop="30px";
		hrTabela.style.marginBottom="25px";
		for(var i = 0; i < thDataCadastro.length; i++) {
			thDataCadastro[i].hidden=false;
		}

		for(var i = 0; i < tdDataCadastro.length; i++) {
			tdDataCadastro[i].hidden=false;
		}	

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.65rem";
		}		
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.65rem";		
		}
	
		for(var i = 0; i < liA.length; i++){
			liA[i].style.fontSize="0.80rem";
			liA[i].style.padding="5px 10px";
		}

		for(var i = 0; i < formRemoveImg.length; i++) {
			formRemoveImg[i].style.display="none";
		}	

		for(var i = 0; i < btnExcluir.length; i++) {
			btnExcluir[i].style.display="block";
			btnExcluir[i].style.fontSize="0.70rem";
			btnExcluir[i].style.padding="4px 6px";
			btnExcluir[i].innerText="Excluir";
		}	

		for(var i = 0; i < img.length; i++) {
			img[i].style.width="20px";
		}

		for(var i = 0; i < aImg.length; i++) {
			aImg[i].style.marginLeft="10px";
		}

		for(var i = 0; i < imgContainer.length; i++) {
			imgContainer[i].style.width="20px";
			imgContainer[i].style.marginLeft="15px";			
		}

		for (var i = 0; i < pageClick.length; i++) {
			pageClick[i].style.fontSize="0.65rem";
		}		

		for(var i = 0; i < novoItemInput.length; i++) {
			novoItemInput[i].style.marginBottom="0";
		}	

		for(var i = 0; i < editaItemInput.length; i++) {
			editaItemInput[i].style.marginBottom="0";
		}		

		for(var i = 0; i < filtroBlock.length; i++) {
			filtroBlock[i].style.marginBottom="20px";
		}

		filtroBuscarBt.style.marginTop="20px";
		filtroBuscarBt.style.justifyContent="center";			
	}
	else if(bodyWidth <= 992 && bodyWidth > 768){
		console.log('Tela: Média');	

		sideMenu.style.display="block";
		if (bodyWidth > 870) {
			main.style.width="94%";
			sideMenu.style.width="6%";
		}
		else {
			main.style.width="92.5%";
			sideMenu.style.width="7.5%";	
		}

		menuMobile.style.display="none";
		containerNovo.style.fontSize="1rem";		
		novoTitulo.style.fontSize="1.1rem";
		novoItemSubmit.style.marginTop="0px";

		containerEdita.style.fontSize="1rem";		
		editaTitulo.style.fontSize="1.1rem";
		editaItemSubmit.style.marginTop="0px";

		conteudoTituloText.style.fontSize="1.1rem";
		filtroTitulo.style.fontSize="1.1rem";		
		conteudoContainer.style.marginTop="30px";
		hrTabela.style.marginBottom="25px";
		for(var i = 0; i < thDataCadastro.length; i++) {
			thDataCadastro[i].hidden=false;
		}

		for(var i = 0; i < tdDataCadastro.length; i++) {
			tdDataCadastro[i].hidden=false;
		}

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.60rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.60rem";
		}			

		for(var i = 0; i < liA.length; i++){
			liA[i].style.fontSize="0.75rem";
			liA[i].style.padding="5px 10px";			
		}	

		for(var i = 0; i < formRemoveImg.length; i++) {
			formRemoveImg[i].style.display="block";
			formRemoveImg[i].style.maxWidth="42%";			
		}	

		for(var i = 0; i < btnExcluir.length; i++) {
			btnExcluir[i].style.display="none";
		}

		for(var i = 0; i < img.length; i++) {
			img[i].style.width="18px";
		}

		for(var i = 0; i < aImg.length; i++) {
			aImg[i].style.marginLeft="10px";
		}		
		for(var i = 0; i < imgContainer.length; i++) {
			imgContainer[i].style.width="20px";
			imgContainer[i].style.marginLeft="15px";			
		}

		for (var i = 0; i < pageClick.length; i++) {
			pageClick[i].style.fontSize="0.60rem";
		}	

		for(var i = 0; i < novoItemInput.length; i++) {
			novoItemInput[i].style.marginBottom="0";
		}	

		for(var i = 0; i < editaItemInput.length; i++) {
			editaItemInput[i].style.marginBottom="0";
		}		

		for(var i = 0; i < filtroBlock.length; i++) {
			filtroBlock[i].style.marginBottom="20px";
		}

		filtroBuscarBt.style.marginTop="20px";
		filtroBuscarBt.style.justifyContent="center";	
	}
	else if(bodyWidth <= 768 && bodyWidth > 540){
		console.log('Tela: Pequena');	

		conteudoTituloText.style.fontSize="1.1rem";
		sideMenu.style.display="none";
		main.style.width="100%";
		mainRow.style.width = "100%";
		menuMobile.style.display="flex";	
		containerNovo.style.fontSize="0.90rem";	
		novoTitulo.style.fontSize="1.2rem";		
		novoItemSubmit.style.marginTop="0px";

		containerEdita.style.fontSize="0.90rem";	
		editaTitulo.style.fontSize="1.2rem";		
		editaItemSubmit.style.marginTop="0px";

		conteudoTituloText.style.fontSize="0.90rem";
		filtroTitulo.style.fontSize="1.1rem";						
		conteudoContainer.style.marginTop="10px";
		hrTabela.style.marginBottom="15px";
		for(var i = 0; i < thDataCadastro.length; i++) {
			thDataCadastro[i].hidden=true;
		}

		for(var i = 0; i < tdDataCadastro.length; i++) {
			tdDataCadastro[i].hidden=true;
		}	

		for(var i = 0; i < th.length; i++) {
			th[i].style.fontSize="0.55rem";	
		}
		for(var i = 0; i < td.length; i++) {
			td[i].style.fontSize="0.55rem";	
		}
		
		for(var i = 0; i < liA.length; i++) {
			liA[i].style.fontSize="0.70rem";
			liA[i].style.padding="5px 8px";
		}

		for(var i = 0; i < formRemoveImg.length; i++) {
			formRemoveImg[i].style.display="block";
			formRemoveImg[i].style.maxWidth="47%";
		}	

		for(var i = 0; i < btnExcluir.length; i++) {
			btnExcluir[i].style.display="none";
		}	

		for(var i = 0; i < img.length; i++) {
			img[i].style.width="18px";
		}

		for(var i = 0; i < aImg.length; i++) {
			aImg[i].style.marginLeft="10px";
		}

		for(var i = 0; i < imgContainer.length; i++) {
			imgContainer[i].style.width="20px";
			imgContainer[i].style.marginLeft="15px";			
		}

		for (var i = 0; i < pageClick.length; i++) {
			pageClick[i].style.fontSize="0.55rem";
		}

		for(var i = 0; i < menuSuperiorMobileItem.length; i++) {
			if (bodyWidth > 670) {
				menuSuperiorMobileItem[i].style.width="7.05%";
			}
			else {
				menuSuperiorMobileItem[i].style.width="8%";
			}
		}

		for(var i = 0; i < novoItemInput.length; i++) {
			novoItemInput[i].style.marginBottom="0";
		}	

		for(var i = 0; i < editaItemInput.length; i++) {
			editaItemInput[i].style.marginBottom="0";
		}

		for(var i = 0; i < filtroBlock.length; i++) {
			filtroBlock[i].style.marginBottom="20px";
		}

		filtroBuscarBt.style.marginTop="20px";
		filtroBuscarBt.style.justifyContent="center";
	}
	else if(bodyWidth < 540){
		console.log('Tela: Muito pequena');

		conteudoTituloText.style.fontSize="1rem";
		sideMenu.style.display="none";
		main.style.width="100%";
		mainRow.style.width = "100%";
		menuMobile.style.display="flex";
		containerNovo.style.fontSize="0.90rem";
		novoTitulo.style.fontSize="1rem";
		novoItemSubmit.style.marginTop="5px";

		containerEdita.style.fontSize="0.90rem";
		editaTitulo.style.fontSize="1rem";
		editaItemSubmit.style.marginTop="5px";

		conteudoTituloText.style.fontSize="0.80rem";
		filtroTitulo.style.fontSize="1.1rem";		
		conteudoContainer.style.marginTop="10px";
		hrTabela.style.marginBottom="15px";
		for(var i = 0; i < thDataCadastro.length; i++) {
			thDataCadastro[i].hidden=true;
		}

		for(var i = 0; i < tdDataCadastro.length; i++) {
			tdDataCadastro[i].hidden=true;
		}	

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.50rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.50rem";
		}

		for(var i = 0; i < liA.length; i++){
			liA[i].style.fontSize="0.60rem";
			liA[i].style.padding="5px 10px";
		}
		for(var i = 0; i < formRemoveImg.length; i++) {
			formRemoveImg[i].style.display="block";
			formRemoveImg[i].style.maxWidth="55%";		
		}	

		for(var i = 0; i < btnExcluir.length; i++) {
			btnExcluir[i].style.display="none";
		}

		for(var i = 0; i < img.length; i++) {
			img[i].style.width="15px";
		}

		for(var i = 0; i < aImg.length; i++) {
			aImg[i].style.marginLeft="8px";
		}

		for (var i = 0; i < pageClick.length; i++) {
			pageClick[i].style.fontSize="0.50rem";
		}	

		for(var i = 0; i < menuSuperiorMobileItem.length; i++) {
			if (bodyWidth > 470) {
				menuSuperiorMobileItem[i].style.width="9%";
			}
			else {
				menuSuperiorMobileItem[i].style.width="10%";	
			}
		}

		for(var i = 0; i < filtroBlock.length; i++) {
			filtroBlock[i].style.marginBottom="20px";
		}

		filtroBuscarBt.style.marginTop="20px";
		filtroBuscarBt.style.justifyContent="center";
	}

	pageResponsiva();
}


/* ================== CONFIGURAÇÕES DA SUB-TELA NOVO ITEM ====================== */

function abrirNovoItem() {

	var containerPrincipal = document.getElementById('conteudo_container');
	var menuSuperior = document.getElementById('menu_superior');
	var menuSuperiorMobile = document.getElementById('menu_superior_mobile');
	var sideMenu = document.getElementById('side_menu');

	var containerNovo = document.getElementById('conteudo_container_novo');

	containerNovo.hidden=false;

	containerPrincipal.style.pointerEvents="none";
	containerPrincipal.style.opacity="0.075";
	containerPrincipal.style.transition="1s";

	menuSuperior.style.pointerEvents="none";
	menuSuperior.style.opacity="0.1";
	menuSuperior.style.transition="1s";

	menuSuperiorMobile.style.pointerEvents="none";
	menuSuperiorMobile.style.opacity="0.1";
	menuSuperiorMobile.style.transition="1s";	

	sideMenu.style.pointerEvents="none";
	sideMenu.style.opacity="0.1";
	sideMenu.style.transition="1s";		

	setTimeout(() => {  $('#valor_input').focus(); }, 10);		
}

function fechaNovoItem() {

	var containerPrincipal = document.getElementById('conteudo_container');
	var menuSuperior = document.getElementById('menu_superior');
	var menuSuperiorMobile = document.getElementById('menu_superior_mobile');
	var sideMenu = document.getElementById('side_menu');	

	var containerNovo = document.getElementById('conteudo_container_novo');	

	reloadNovoItem();
	
	containerNovo.hidden=true;

	containerPrincipal.style.opacity="1";
	containerPrincipal.style.transition="1s";
	containerPrincipal.style.pointerEvents="auto";

	menuSuperior.style.opacity="1";
	menuSuperior.style.transition="1s";
	menuSuperior.style.pointerEvents="auto";

	menuSuperiorMobile.style.opacity="1";
	menuSuperiorMobile.style.transition="1s";
	menuSuperiorMobile.style.pointerEvents="auto";	

	sideMenu.style.opacity="1";
	sideMenu.style.transition="1s";
	sideMenu.style.pointerEvents="auto";		
}

function reloadNovoItem() {

	var inputProduto = document.getElementById('produto_preco_input');
	var optionProdutos = document.getElementsByClassName('option_produtos');
	var inputFornecedor = document.getElementById('fornecedor_preco_input');
	var optionFornecedores = document.getElementsByClassName('option_fornecedores');		
	var inputValor = document.getElementById('valor_input');
	var inputObservacao = document.getElementById('observacao_input');
	var botaoFinalizar = document.getElementById('novo_item_submit');

	optionProdutos[0].selected=true;	
	optionFornecedores[0].selected=true;

	inputValor.value=0.0;
	inputValor.style.background="transparent";

	inputObservacao.value="";
	inputObservacao.style.background="transparent";

	botaoFinalizar.type="button";

}

/* ================== CONFIGURAÇÕES DA SUB-TELA FILTROS ====================== */

function abrirFiltro() {

	var containerPrincipal = document.getElementById('conteudo_container');
	var menuSuperior = document.getElementById('menu_superior');
	var menuSuperiorMobile = document.getElementById('menu_superior_mobile');
	var sideMenu = document.getElementById('side_menu');

	var containerFiltro = document.getElementById('conteudo_container_filtro');

	containerFiltro.hidden=false;

	containerPrincipal.style.pointerEvents="none";
	containerPrincipal.style.opacity="0.075";
	containerPrincipal.style.transition="1s";

	menuSuperior.style.pointerEvents="none";
	menuSuperior.style.opacity="0.1";
	menuSuperior.style.transition="1s";

	menuSuperiorMobile.style.pointerEvents="none";
	menuSuperiorMobile.style.opacity="0.1";
	menuSuperiorMobile.style.transition="1s";	

	sideMenu.style.pointerEvents="none";
	sideMenu.style.opacity="0.1";
	sideMenu.style.transition="1s";		

	setTimeout(() => {  $('#produto_filtro_input').focus(); }, 10);	
}

function fecharFiltro() {
	var containerPrincipal = document.getElementById('conteudo_container');
	var menuSuperior = document.getElementById('menu_superior');
	var menuSuperiorMobile = document.getElementById('menu_superior_mobile');
	var sideMenu = document.getElementById('side_menu');	

	var containerFiltro = document.getElementById('conteudo_container_filtro');	

	reloadFiltro();
	
	containerFiltro.hidden=true;

	containerPrincipal.style.opacity="1";
	containerPrincipal.style.transition="1s";
	containerPrincipal.style.pointerEvents="auto";

	menuSuperior.style.opacity="1";
	menuSuperior.style.transition="1s";
	menuSuperior.style.pointerEvents="auto";

	menuSuperiorMobile.style.opacity="1";
	menuSuperiorMobile.style.transition="1s";
	menuSuperiorMobile.style.pointerEvents="auto";	

	sideMenu.style.opacity="1";
	sideMenu.style.transition="1s";
	sideMenu.style.pointerEvents="auto";		
}

function filtroChange() {

	var filtroTipo = document.getElementById('filtro_input');

	var produtoBlock = document.getElementById('filtro_produto_block');
	var produtoInput = document.getElementById('produto_filtro_input');

	var fornecedorBlock = document.getElementById('filtro_fornecedor_block');
	var fornecedorInput = document.getElementById('fornecedor_filtro_input');

	if (filtroTipo.value == 'PRODUTO') {

		produtoBlock.hidden=false;

		fornecedorInput.value="";
		fornecedorBlock.hidden=true;

	}
	else if (filtroTipo.value == 'FORNECEDOR') {

		produtoInput.value="";
		produtoBlock.hidden=true;

		fornecedorBlock.hidden=false;
		
	}
}

function reloadFiltro() {

	document.getElementById('filtro_input').disabled=false;
	document.getElementById('filtro_input').style.border="1px solid #949393";
	document.getElementById('filtro_input').style.pointerEvents="auto";
	document.getElementById('filtro_input').value="PRODUTO";
	document.getElementById('filtro_bt').hidden=false;
	document.getElementById('filtro_bt').disabled=false;
	document.getElementById('filtro_bt').style.pointerEvents="auto";
	document.getElementById('filtro_buscar_bt').hidden=true;

	document.getElementById('tipo_filtro_option_produto').hidden=false;
	document.getElementById('filtro_produto_block').hidden=false;	
	document.getElementById('filtro_produto_tag').hidden=true;
	document.getElementById('produto_filtro_input').value="";
	document.getElementById('input_produto_backend').value="";

	document.getElementById('tipo_filtro_option_fornecedor').hidden=false;
	document.getElementById('filtro_fornecedor_block').hidden=true;
	document.getElementById('filtro_fornecedor_tag').hidden=true;
	document.getElementById('fornecedor_filtro_input').value="";
	document.getElementById('input_fornecedor_backend').value="";	
}

function addFiltro() {

	var filtroTipo = document.getElementById('filtro_input');
	var filtroBt = document.getElementById('filtro_bt');
	var buscarBt = document.getElementById('filtro_buscar_bt');

	var optionProduto = document.getElementById('tipo_filtro_option_produto');
	var produtoBlock = document.getElementById('filtro_produto_block');	
	var produtoTag = document.getElementById('filtro_produto_tag');

	var optionfornecedor = document.getElementById('tipo_filtro_option_fornecedor');
	var fornecedorBlock = document.getElementById('filtro_fornecedor_block');	
	var fornecedorTag = document.getElementById('filtro_fornecedor_tag');	

	var inputProdutoFiltro = document.getElementById('produto_filtro_input');
	var inputfornecedorFiltro = document.getElementById('fornecedor_filtro_input');

	var inputProdutoBackend = document.getElementById('input_produto_backend');
	var inputfornecedorBackend = document.getElementById('input_fornecedor_backend');

	if (filtroTipo.value == 'PRODUTO') {
		if (inputProdutoFiltro.value != "") {
			optionProduto.hidden=true;
			produtoBlock.hidden=true;
			produtoTag.hidden=false;
			produtoTag.innerText = "Produto: " + inputProdutoFiltro.value;
			filtroTipo.value="";
			inputProdutoBackend.value=inputProdutoFiltro.value;

			filtroBt.hidden=true;
			buscarBt.hidden=false;
			filtroTipo.style.border="1px solid grey";
			filtroTipo.disabled=true;
			filtroBt.disabled=true;
			filtroBt.style.pointerEvents="none";				
		}
	}
	else if (filtroTipo.value == 'FORNECEDOR') {
		if (inputfornecedorFiltro.value != "") {		
			optionfornecedor.hidden=true;
			fornecedorBlock.hidden=true;

			fornecedorTag.hidden=false;
			fornecedorTag.innerText = 'Fornecedor: ' + inputfornecedorFiltro.value;

			filtroTipo.value="";
			inputfornecedorBackend.value=inputfornecedorFiltro.value;

			filtroBt.hidden=true;
			buscarBt.hidden=false;
			filtroTipo.style.border="1px solid grey";
			filtroTipo.disabled=true;
			filtroBt.disabled=true;
			filtroBt.style.pointerEvents="none";		
		}
	}		
}

function removerFiltro(filtro) {

	var filtroTipo = document.getElementById('filtro_input');
	var filtroBt = document.getElementById('filtro_bt');
	var buscarBt = document.getElementById('filtro_buscar_bt');

	var optionProduto = document.getElementById('tipo_filtro_option_produto');
	var produtoBlock = document.getElementById('filtro_produto_block');	
	var produtoTag = document.getElementById('filtro_produto_tag');

	var optionfornecedor = document.getElementById('tipo_filtro_option_fornecedor');
	var fornecedorBlock = document.getElementById('filtro_fornecedor_block');	
	var fornecedorTag = document.getElementById('filtro_fornecedor_tag');

	var inputProdutoBackend = document.getElementById('input_produto_backend');
	var inputfornecedorBackend = document.getElementById('input_fornecedor_backend');	
	
	filtroTipo.style.border="1px solid #949393";
	filtroTipo.disabled=false;
	filtroBt.disabled=false;
	filtroBt.style.pointerEvents="auto";	

	if (filtro == 'produto') {		

		optionProduto.hidden=false;

		produtoBlock.hidden=false;

		fornecedorBlock.hidden=true;

		produtoTag.hidden=true;

		filtroTipo.value="PRODUTO";

		inputProdutoBackend.value="";
	}
	else if (filtro == 'fornecedor') {
		optionfornecedor.hidden=false;

		produtoBlock.hidden=true;

		fornecedorBlock.hidden=false;
		fornecedorTag.hidden=true;

		filtroTipo.value="FORNECEDOR";

		inputfornecedorBackend.value="";
	}	

	buscarBt.hidden=true;		
	filtroBt.hidden=false;
}

function efeitoRemoverFiltro(filtro) {

	var filtroProduto = document.getElementById('filtro_produto_tag');
	var filtrofornecedor = document.getElementById('filtro_fornecedor_tag');


	if (filtro == 'produto') {
		filtroProduto.style.transition="0.5s"
		filtroProduto.style.background="#AA3C3C";
		filtroProduto.style.border="1px solid #AA3C3C";
		filtroProduto.style.color="#212121";
		filtroProduto.innerText="Remover";
		filtroProduto.style.cursor="pointer";

	}
	else if (filtro == 'fornecedor') {
		filtrofornecedor.style.transition="0.5s"
		filtrofornecedor.style.background="#AA3C3C";
		filtrofornecedor.style.border="1px solid #AA3C3C";
		filtrofornecedor.style.color="#212121";
		filtrofornecedor.innerText="Remover";
		filtrofornecedor.style.cursor="pointer";
	}
		
}

function efeitoRemoverFiltroLeave(filtro) {

	var filtroProduto = document.getElementById('filtro_produto_tag');
	var filtrofornecedor = document.getElementById('filtro_fornecedor_tag');

	var inputProdutoBackend = document.getElementById('input_produto_backend');
	var inputfornecedorBackend = document.getElementById('input_fornecedor_backend');

	if (filtro == 'produto') {
		filtroProduto.style.transition="1s"
		filtroProduto.style.background="transparent";
		filtroProduto.style.border="1px solid #212121"
		filtroProduto.style.color="#212121";
		filtroProduto.innerText="Descrição";
		filtroProduto.innerText = 'Produto: ' + inputProdutoBackend.value;

	}
	else if (filtro == 'fornecedor') {
		filtrofornecedor.style.transition="1s"
		filtrofornecedor.style.background="transparent";
		filtrofornecedor.style.border="1px solid #212121"
		filtrofornecedor.style.color="#212121";
		filtrofornecedor.innerText = 'Fornecedor: ' + inputfornecedorBackend.value;
	}

}

/* ================== CONFIGURAÇÕES DA SUB-TELA EDITA ITEM ====================== */

function abrirEditaItem(
							id, 
							dataCadastro,
							valor,
							observacao,
							idFornecedor,
							idProduto,
							usuarioResponsavel) {

	var containerPrincipal = document.getElementById('conteudo_container');
	var menuSuperior = document.getElementById('menu_superior');
	var menuSuperiorMobile = document.getElementById('menu_superior_mobile');
	var sideMenu = document.getElementById('side_menu');
	var containerEdita = document.getElementById('conteudo_container_edita');
	containerEdita.hidden=false;

	containerPrincipal.style.pointerEvents="none";
	containerPrincipal.style.opacity="0.075";
	containerPrincipal.style.transition="1s";

	menuSuperior.style.pointerEvents="none";
	menuSuperior.style.opacity="0.1";
	menuSuperior.style.transition="1s";

	menuSuperiorMobile.style.pointerEvents="none";
	menuSuperiorMobile.style.opacity="0.1";
	menuSuperiorMobile.style.transition="1s";	

	sideMenu.style.pointerEvents="none";
	sideMenu.style.opacity="0.1";
	sideMenu.style.transition="1s";

	if(dataCadastro != null) {
		var dataCadastroSplitada = dataCadastro.split("-");
		if (dataCadastroSplitada.length == 3) {
			var dataUsParaDataBr = dataCadastroSplitada[2] + "-" + dataCadastroSplitada[1] + "-" + dataCadastroSplitada[0];
			document.getElementById('edita_info').title="Preço cadastrado dia " + dataUsParaDataBr + " por " + usuarioResponsavel;
		}
	}

	document.getElementById('id_input_edicao').value=id;
	document.getElementById('edita_produto_preco_input').value=idProduto;
	document.getElementById('edita_fornecedor_preco_input').value=idFornecedor;
	document.getElementById('edita_valor_input').value=valor;
	document.getElementById('edita_observacao_input').value=observacao;
	if (idFornecedor == null) {
		document.getElementById('edita_sem_fornecedor').selected=true;
	}	

}

function fechaEditaItem() {

	var containerPrincipal = document.getElementById('conteudo_container');
	var menuSuperior = document.getElementById('menu_superior');
	var menuSuperiorMobile = document.getElementById('menu_superior_mobile');
	var sideMenu = document.getElementById('side_menu');	
	var containerEdita = document.getElementById('conteudo_container_edita');	

	reloadEditaItem();

	containerEdita.hidden=true;
	containerPrincipal.style.opacity="1";
	containerPrincipal.style.transition="1s";
	containerPrincipal.style.pointerEvents="auto";
	menuSuperior.style.opacity="1";
	menuSuperior.style.transition="1s";
	menuSuperior.style.pointerEvents="auto";
	menuSuperiorMobile.style.opacity="1";
	menuSuperiorMobile.style.transition="1s";
	menuSuperiorMobile.style.pointerEvents="auto";	
	sideMenu.style.opacity="1";
	sideMenu.style.transition="1s";
	sideMenu.style.pointerEvents="auto";
}

function reloadEditaItem() {

	var inputProduto = document.getElementById('edita_produto_preco_input');
	var optionProdutos = document.getElementsByClassName('edita_option_produtos');
	var inputFornecedor = document.getElementById('edita_fornecedor_preco_input');
	var optionFornecedores = document.getElementsByClassName('edita_option_fornecedores');		
	var inputValor = document.getElementById('edita_valor_input');
	var inputObservacao = document.getElementById('edita_observacao_input');
	var botaoFinalizar = document.getElementById('edita_item_submit');

	optionProdutos[0].selected=true;	
	optionFornecedores[0].selected=true;

	inputValor.value=0.0;
	inputValor.style.background="transparent";

	inputObservacao.value="";
	inputObservacao.style.background="transparent";

	botaoFinalizar.type="button";
}

/* ================== TRATAMENTO DE INPUTS ====================== */

/* REALIZA VALIDAÇÃO DE ATRIBUTOS NULOS NO OBJETO COLABORADOR */
function validacaoDoObjetoPreco(tipo) {

	var inputProduto = null;
	var inputFornecedor = null;
	var inputValor = null;
	var inputObservacao = null;
	var botaoFinalizar = null;

	if(tipo == "novo") {
		inputProduto = document.getElementById('produto_preco_input');
		inputFornecedor = document.getElementById('fornecedor_preco_input');
		inputValor = document.getElementById('valor_input');
		inputObservacao = document.getElementById('observacao_input');
		botaoFinalizar = document.getElementById('novo_item_submit');

	}
	else {
		inputProduto = document.getElementById('edita_produto_preco_input');
		inputFornecedor = document.getElementById('edita_fornecedor_preco_input');
		inputValor = document.getElementById('edita_valor_input');
		inputObservacao = document.getElementById('edita_observacao_input');
		botaoFinalizar = document.getElementById('edita_item_submit');
	}


	if(
		inputProduto.value != "" 
		|| inputFornecedor.value != "" 
		|| inputValor.value != "" 
		|| inputObservacao.value != "") {

		if(inputProduto.value == "") {
			inputProduto.style.background="#f5aea9";	
		}
		else {
			inputProduto.style.background="transparent";			
		}
		if(inputValor.value == "" || inputValor.value == 0) {
			inputValor.style.background="#f5aea9";	
		}
		else {
			inputValor.style.background="transparent";			
		}
						
	}

	else {
		inputProduto.style.background="transparent";
		inputValor.style.background="transparent";
		return true;		
	}
}

/* VALIDAÇÃO DE TODOS OS CAMPOS */
function validacaoCampos(tipo) {

	var inputProduto = null;
	var inputFornecedor = null;
	var inputValor = null;
	var inputObservacao = null;
	var botaoFinalizar = null;

	if(tipo == "novo") {
		inputProduto = document.getElementById('produto_preco_input');
		inputFornecedor = document.getElementById('fornecedor_preco_input');
		inputValor = document.getElementById('valor_input');
		inputObservacao = document.getElementById('observacao_input');
		botaoFinalizar = document.getElementById('novo_item_submit');

	}
	else {
		inputProduto = document.getElementById('edita_produto_preco_input');
		inputFornecedor = document.getElementById('edita_fornecedor_preco_input');
		inputValor = document.getElementById('edita_valor_input');
		inputObservacao = document.getElementById('edita_observacao_input');
		botaoFinalizar = document.getElementById('edita_item_submit');
	}


	var erros = "Ocorreram alguns erros no lançamento do preço:\n";

	if(inputProduto.value == "") {
		erros += "- Um produto deve ser selecionado\n";
	}
	if(inputValor.value == 0) {
		erros += "- O campo valor deve ser preenchido\n";
	}		

	if (erros != "Ocorreram alguns erros no lançamento do preço:\n") {
		var quantidade = 0

		for (var i = 0; i < erros.length; i++) {
		  if (erros[i] == "-") {
		    quantidade++;
		  }
		}

		if (quantidade == 1) {
			erros = erros.replace("Ocorreram alguns erros", "Ocorreu um erro");
		}

		alert(erros);
		return false;
	}
	else {
		botaoFinalizar.type="submit";
		return true;
	}
}

/* ================== MISC ====================== */

function buildUrlPages() {

	var paginaAtual = document.getElementById('pagina_atual');
	var tipoFiltro = document.getElementById('tipo_filtro');

	var produto = document.getElementById('back_produto');
	var fornecedor = document.getElementById('back_fornecedor');

	var produtoId = document.getElementById('back_produtoId');
	var fornecedorId = document.getElementById('back_fornecedorId');			

	var pageNumber = document.getElementsByClassName('page_number');

	if(tipoFiltro.value == 'produto') {

		$('#anterior').attr("href", "/precos?page=" + (parseInt(paginaAtual.value) - 1)  + "&produto=" + produto.value);
		$('#proxima').attr("href", "/precos?page=" + (parseInt(paginaAtual.value) + 1)  + "&produto=" + produto.value);

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/precos?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&produto=" + produto.value);
		}

	}

	else if(tipoFiltro.value == 'fornecedor') {

		$('#anterior').attr("href", "/precos?page=" + (parseInt(paginaAtual.value) - 1)  + "&fornecedor=" + fornecedor.value);
		$('#proxima').attr("href", "/precos?page=" + (parseInt(paginaAtual.value) + 1)  + "&fornecedor=" + fornecedor.value);

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/precos?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&fornecedor=" + fornecedor.value);
		}					
	}

	else if(tipoFiltro.value == 'fornecedorId') {

		$('#anterior').attr("href", "/precos?page=" + (parseInt(paginaAtual.value) - 1)  + "&fornecedorId=" + fornecedorId.value);
		$('#proxima').attr("href", "/precos?page=" + (parseInt(paginaAtual.value) + 1)  + "&fornecedorId=" + fornecedorId.value);

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/precos?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&fornecedorId=" + fornecedorId.value);
		}					
	}

	else if(tipoFiltro.value == 'produtoId') {

		$('#anterior').attr("href", "/precos?page=" + (parseInt(paginaAtual.value) - 1)  + "&produtoId=" + produtoId.value);
		$('#proxima').attr("href", "/precos?page=" + (parseInt(paginaAtual.value) + 1)  + "&produtoId=" + produtoId.value);

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/precos?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&produtoId=" + produtoId.value);
		}					
	}		

	else {
		$('#anterior').attr("href", "/precos?page=" + (parseInt(paginaAtual.value) - 1));
		$('#proxima').attr("href", "/precos?page=" + (parseInt(paginaAtual.value) + 1));

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/precos?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))));
		}		
	}
}

function buildUrl(baseUrl, pagina, descricao, tipo, dataInicio, dataFim, mes, ano) {

	var url = baseUrl + "?page=" + pagina;
	var paginaList = document.getElementById('pagina_list');

	if (descricao != null) {
		baseUrl + "&descricao=" + descricao;
	}		
	else if (tipo != null) {
		baseUrl + "&tipo=" + tipo;
	}
	else if (dataInicio != null && dataFim != null) {
		baseUrl + "&dataInicio=" + dataInicio + "&dataFim=" + dataFim;
	}
	else if (mes != null && ano != null) {
		baseUrl + "&mes=" + mes + "&ano=" + ano;	
	}

	paginaList.href=baseUrl;
}

function hideMessage(){
	var alertas = document.getElementsByClassName('alert');
	for(var i = 0; i < alertas.length; i++){
		alertas[i].hidden=true;
	}
}

function consultaEndereco(tipo) {

	let cep = null;

	if(tipo == "novo") {
		cep = document.querySelector('#cep_input');
	}
	else if(tipo == "edita") {
		cep = document.querySelector('#edita_cep_input');
	}

	if (cep.value.length != 8) {
		return;
	}

	let url = 'https://viacep.com.br/ws/' + cep.value + '/json';

	fetch(url).then(function(response){
		response.json().then(function(data){
			if(data.erro == undefined) {
				mostrarEndereco(data, tipo);
			}
		})
	});
}

function mostrarEndereco(dados, tipo) {

	if(tipo == "novo") {
		var cepInput = document.getElementById('cep_input');
		var estadoInput = document.getElementById('estado_input');
		var cidadeInput = document.getElementById('cidade_input');
		var logradouroInput = document.getElementById('logradouro_input');
		var bairroInput = document.getElementById('bairro_input');

		estadoInput.value=dados.uf;
		cidadeInput.value = dados.localidade;
		logradouroInput.value=dados.logradouro;
		bairroInput.value=dados.bairro;

		cepInput.style.background="transparent";
		logradouroInput.style.background="transparent";		

		document.getElementById('novo_item_label_numero').focus();
	}
	else if(tipo == "edita") {
		var cepInput = document.getElementById('edita_cep_input');
		var estadoInput = document.getElementById('edita_estado_input');
		var cidadeInput = document.getElementById('edita_cidade_input');
		var logradouroInput = document.getElementById('edita_logradouro_input');
		var bairroInput = document.getElementById('edita_bairro_input');

		estadoInput.value=dados.uf;
		cidadeInput.value = dados.localidade;
		logradouroInput.value=dados.logradouro;
		bairroInput.value=dados.bairro;

		cepInput.style.background="transparent";
		logradouroInput.style.background="transparent";

		document.getElementById('edita_numero_input').focus();		
	}
}

function pageResponsiva(){
	var pages = document.getElementsByClassName('page_number');
	var PaginaSelecionada = (document.getElementById('pegando_page').innerText);
	var PaginaSelecionada = parseInt(PaginaSelecionada)+1;
	for(var i = 0; i < pages.length; i++){
		pages[i].style.border="1px solid #303030";
		pages[i].style.background="transparent";
		pages[i].style.color="#C3C8C8";
		if(pages[i].innerText == PaginaSelecionada){
			pages[i].style.background="#303030";
			pages[i].style.color="#C3C8C8";
		}
	}

	if((pages.length) == PaginaSelecionada){
		document.getElementById('proxima').style.pointerEvents="none";
		document.getElementById('proxima').style.borderColor="#303030";
		document.getElementById('proxima').style.color="#212121";
	}
	else{
		document.getElementById('proxima').style.pointerEvents="auto";
		document.getElementById('proxima').style.borderColor="#303030";
		document.getElementById('proxima').style.color="#C3C8C8";
	}

	if(PaginaSelecionada == 1){
		document.getElementById('anterior').style.pointerEvents="none";
		document.getElementById('anterior').style.borderColor="#303030";
		document.getElementById('anterior').style.color="#212121";
	}
	else{
		document.getElementById('anterior').style.pointerEvents="auto";
		document.getElementById('anterior').style.borderColor="#303030";
		document.getElementById('anterior').style.color="#C3C8C8";
	}
}

function ajustaTabela(){
	var line = document.getElementsByClassName('tr');	
	var columnData = document.getElementsByClassName('td_cadastro');
	var columnValor = document.getElementsByClassName('td_valor');

	for(var i = 0; i < line.length; i++) {

		var columnDataSplitted = columnData[i].innerText.split("-");
		if (columnDataSplitted.length == 3) {
			var convertedDate = columnDataSplitted[2] + "/" + columnDataSplitted[1] + "/" + columnDataSplitted[0];
			columnData[i].innerText=convertedDate;
		}
	}

	for(var i = 0; i < columnValor.length; i++) {

		columnValor[i].innerText = (parseFloat(columnValor[i].innerText)).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })

	}
}	

function doALoadOfStuff() {
	document.getElementById('conteudo_container').style.transition="2s";
	responsive();
}

function tituloResponsivo(filtro) {

	var titulo = document.getElementById('conteudo_titulo_text');	
	var produto = document.getElementById('back_produto');
	var fornecedor = document.getElementById('back_fornecedor');		
	var fornecedorId = document.getElementById('back_fornecedorId');
	var produtoId = document.getElementById('back_produtoId');	

	if(filtro == "todos") {
		titulo.innerText="Todos os preços";
	}
	else if (filtro == "produto") {
		titulo.innerText="Preços do produto " + produto.value;
	}

	else if (filtro == "produtoId") {
		var nomeProduto = produtoId.getAttribute('data-nomeProduto');
		if (produtoId.length > 20) {
			produtoId = nomeProduto.slice(0,20) + "...";
		}
		titulo.innerText="Preços do produto " + nomeProduto;
	}

	else if (filtro == "fornecedor") {
		titulo.innerText="Preços com " + fornecedor.value;
	}		


	else if (filtro == "fornecedorId") {
		var nomeFornecedor = fornecedorId.getAttribute('data-nomeFornecedor');
		if (nomeFornecedor.length > 20) {
			nomeFornecedor = nomeFornecedor.slice(0,20) + "...";
		}
		titulo.innerText="Preços com " + nomeFornecedor;
	}


	else if (filtro == "descricao") {
		titulo.innerText="Colaboradores de nome " + descricao.value;
	}
	else if (filtro == "usuario") {
		titulo.innerText="Colaboradores de usuário " + (usuario.value);
	}

}