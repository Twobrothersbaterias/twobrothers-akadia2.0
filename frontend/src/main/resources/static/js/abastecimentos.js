/* =========================== RESPONSIVIDADE DA TELA =========================== */

window.onload = responsive();
window.onresize = doALoadOfStuff;
ajustaTabela();
buildUrlPagamentos();
buildUrlPages();
buildUrlRelatorio();
dataFiltroResponsiva();

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
        hideMessage();        
    }

    if(keyCode == '13') {
		if(document.getElementById('conteudo_container_edita').hidden==false) {
		    validacaoDoObjetoAbastecimento(true, 'edita');
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
			validacaoDoObjetoAbastecimento(true, 'novo');
		}    
	}    

    if (document.getElementById('conteudo_container_filtro').hidden==true
    	&& document.getElementById('conteudo_container_edita').hidden==true
    	&& document.getElementById('conteudo_container_novo').hidden==true) {

	    if (keyCode == '48') {
	    	window.location.href="/";
	    }

	    else if (keyCode == '49') {
	    	window.location.href="/clientes";
	    }

	    else if (keyCode == '50') {
	    	window.location.href="/vendas";
	    }	    

	    else if (keyCode == '51') {
	    	window.location.href="/estoque";
	    }

	    else if (keyCode == '52' && privilegio != "Vendedor") {
	    	window.location.href="/despesas";
	    }

	    else if (keyCode == '53' && privilegio != "Vendedor") {
	    	window.location.href="/relatorios";
	    }

	    else if (keyCode == '54' && privilegio != "Vendedor") {
	    	window.location.href="/patrimonios";
	    }

	    else if (keyCode == '55' && privilegio != "Vendedor") {
	    	window.location.href="/fornecedores";
	    }  

	    else if (keyCode == '56' && privilegio != "Vendedor") {
	    	window.location.href="/colaboradores";
	    } 
	    else if (keyCode == '57' && privilegio != "Vendedor") {
	    	window.location.href="/precos";
	    } 	        

	    else if(keyCode == '32') {
	    	window.location.href="/lancamento";
	    }

	    else if(keyCode == '107') {
	    	abrirNovoItem();
	    }

	    else if(keyCode == '82') {
	    	window.location.href="/compras";
	    }

	    else if(keyCode == '70') {
	    	abrirFiltro();
	    }	 	    

	    else if(keyCode == '192' && privilegio != "Vendedor") {
	    	document.getElementById('relatorio_button').click();
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

	var containerNovo = document.getElementById('conteudo_container_novo');	
	var novoTitulo	= document.getElementById('novo_titulo');
	var novoItemSubmit = document.getElementById('novo_item_submit');

	var conteudoContainer = document.getElementById('conteudo_container');
	var hrTabela = document.getElementById('hr_tabela');	

	var containerEdita = document.getElementById('conteudo_container_edita');	
	var editaTitulo	= document.getElementById('edita_titulo');
	var editaItemSubmit = document.getElementById('edita_item_submit');

	var img = document.getElementsByClassName('img');
	var aImg = document.getElementsByClassName('a_img');
	var imgContainer = document.getElementsByClassName('img_container');	

	var informativo = document.getElementsByClassName('informativo');
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

	var informativoChave = document.getElementsByClassName('key');
	var informativosRow = document.getElementsByClassName('informativos_row');
	var excluirImg = document.getElementsByClassName('excluir_img');

	mainRow.style.width = "100%";		

	if(bodyWidth > 1200){
		console.log("Tela: Muito grande");

		if (bodyHeight < 600) {
			sideMenu.style.display="none";		
			menuMobile.style.display="flex";	
			main.style.width="100%";
			sideMenu.style.width="0%";		
		}
		else {
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
		for(var i = 0; i < informativo.length; i++) {
			informativo[i].style.margin="7px";
			informativo[i].style.fontSize="0.70rem";
		}						

		for(var i = 0; i < informativoChave.length; i++) {
			informativoChave[i].style.padding="10px 0px";
			informativoChave[i].style.fontSize="0.70rem";
		}						

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.70rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.70rem";
		}

		for(var i = 0; i < thDataCadastro.length; i++){
			thDataCadastro[i].hidden=false;
		}
		for(var i = 0; i < tdDataCadastro.length; i++){
			tdDataCadastro[i].hidden=false;
		}				

		for(var i = 0; i < liA.length; i++){
			liA[i].style.fontSize="0.85rem";
			liA[i].style.padding="6px 10px";			
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
		for(var i = 0; i < menuSuperiorMobileItem.length; i++) {
			menuSuperiorMobileItem[i].style.width="3.5%";
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

		if (bodyHeight < 600) {
			sideMenu.style.display="none";		
			menuMobile.style.display="flex";	
			main.style.width="100%";
			sideMenu.style.width="0%";		
		}
		else {
			sideMenu.style.display="block";
			menuMobile.style.display="none";
			if (bodyWidth < 1090) {
				main.style.width="94.5%";
				sideMenu.style.width="5.5%";	
			}
			else {
				main.style.width="95%";
				sideMenu.style.width="5%";
			}			
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
		for(var i = 0; i < informativo.length; i++) {
			informativo[i].style.margin="7px";
			informativo[i].style.fontSize="0.65rem";
		}		

		for(var i = 0; i < informativoChave.length; i++) {
			informativoChave[i].style.padding="9px 0px";
			informativoChave[i].style.fontSize="0.65rem";			
		}														

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.65rem";
		}		
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.65rem";		
		}

		for(var i = 0; i < thDataCadastro.length; i++){
			thDataCadastro[i].hidden=false;
		}
		for(var i = 0; i < tdDataCadastro.length; i++){
			tdDataCadastro[i].hidden=false;
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
		for(var i = 0; i < menuSuperiorMobileItem.length; i++) {
			menuSuperiorMobileItem[i].style.width="4%";
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

		if (bodyHeight < 600) {
			sideMenu.style.display="none";		
			menuMobile.style.display="flex";	
			main.style.width="100%";
			sideMenu.style.width="0%";		
		}
		else {
			sideMenu.style.display="block";
			menuMobile.style.display="none";
			if (bodyWidth < 870) {
				main.style.width="94%";
				sideMenu.style.width="6%";	
			}
			else {
				main.style.width="92.5%";
				sideMenu.style.width="7.5%";
			}			
		}

		conteudoTituloText.style.fontSize="1.2rem";
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
		for(var i = 0; i < informativo.length; i++) {
			informativo[i].style.margin="7px";
			informativo[i].style.fontSize="0.60rem";			
		}		

		for(var i = 0; i < informativoChave.length; i++) {
			informativoChave[i].style.padding="9px 0px";
			informativoChave[i].style.fontSize="0.60rem";			
		}					

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.60rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.60rem";
		}

		for(var i = 0; i < thDataCadastro.length; i++){
			thDataCadastro[i].hidden=true;
		}
		for(var i = 0; i < tdDataCadastro.length; i++){
			tdDataCadastro[i].hidden=true;
		}					
				
		for(var i = 0; i < liA.length; i++){
			liA[i].style.fontSize="0.75rem";
			liA[i].style.padding="5px 10px";			
		}		
		for(var i = 0; i < formRemoveImg.length; i++) {
			formRemoveImg[i].style.display="block";
			formRemoveImg[i].style.maxWidth="42%";			
		}	

		for(var i = 0; i < excluirImg.length; i++) {
			excluirImg[i].style.width="20px";
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
		for(var i = 0; i < menuSuperiorMobileItem.length; i++) {
			menuSuperiorMobileItem[i].style.width="5%";
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
		hrTabela.style.marginBottom="20px";
		for(var i = 0; i < informativo.length; i++) {
			informativo[i].style.margin="7px";
			informativo[i].style.fontSize="0.55rem";			
		}		

		for(var i = 0; i < informativoChave.length; i++) {
			informativoChave[i].style.padding="9px 0px";
			informativoChave[i].style.fontSize="0.55rem";			
		}					

		for(var i = 0; i < th.length; i++) {
			th[i].style.fontSize="0.55rem";	
		}
		for(var i = 0; i < td.length; i++) {
			td[i].style.fontSize="0.55rem";	
		}

		for(var i = 0; i < thDataCadastro.length; i++){
			thDataCadastro[i].hidden=true;
		}
		for(var i = 0; i < tdDataCadastro.length; i++){
			tdDataCadastro[i].hidden=true;
		}		
		
		for(var i = 0; i < liA.length; i++) {
			liA[i].style.fontSize="0.70rem";
			liA[i].style.padding="5px 8px";
		}			
		for(var i = 0; i < formRemoveImg.length; i++) {
			formRemoveImg[i].style.display="block";
			formRemoveImg[i].style.maxWidth="47%";
		}	

		for(var i = 0; i < excluirImg.length; i++) {
			excluirImg[i].style.width="15px";
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

		for(var i = 0; i < informativo.length; i++) {
			informativo[i].style.margin="7px";
			informativo[i].style.fontSize="0.50rem";				
		}		

		for(var i = 0; i < informativosRow.length; i++) {		
			informativosRow[i].style.marginBottom="20px";
		}	

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
		hrTabela.style.marginBottom="20px";
		for(var i = 0; i < informativoChave.length; i++) {
			informativoChave[i].style.padding="9px 0px";
			informativoChave[i].style.fontSize="0.50rem";			
		}								

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.50rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.50rem";
		}

		for(var i = 0; i < thDataCadastro.length; i++){
			thDataCadastro[i].hidden=true;
		}
		for(var i = 0; i < tdDataCadastro.length; i++){
			tdDataCadastro[i].hidden=true;
		}		

		for(var i = 0; i < liA.length; i++){
			liA[i].style.fontSize="0.60rem";
			liA[i].style.padding="5px 10px";
		}
		for(var i = 0; i < formRemoveImg.length; i++) {
			formRemoveImg[i].style.display="block";
			formRemoveImg[i].style.maxWidth="55%";		
		}	

		for(var i = 0; i < excluirImg.length; i++) {
			excluirImg[i].style.width="15px";
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

		for(var i = 0; i < imgContainer.length; i++) {
			imgContainer[i].style.width="20px";
			imgContainer[i].style.marginLeft="15px";			
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

function paletasDeCores(paleta) {

	if(paleta == 1) {

	}
}

/* ================== TRATAMENTO DE INPUTS E VALIDAÇÕES ====================== */

/* RESETA AS CORES */
function resetCores(tipo) {

	if(tipo == "novo") {
		var produtoAbastecimentoInput = document.getElementById('produto_abastecimento_input');
		var fornecedorAbastecimentoInput = document.getElementById('fornecedor_abastecimento_input');
		var quantidadeAbastecimentoInput = document.getElementById('quantidade_input');
		var pagamentoAbastecimentoInput = document.getElementById('pagamento_input');
		var valorAbastecimentoInput = document.getElementById('valor_input');
		var observacaoAbastecimentoInput = document.getElementById('observacao_input');
		var botaoFinalizar = document.getElementById('novo_item_submit');
	}
	else {
		var produtoAbastecimentoInput = document.getElementById('edita_produto_abastecimento_input');
		var fornecedorAbastecimentoInput = document.getElementById('edita_fornecedor_abastecimento_input');
		var quantidadeAbastecimentoInput = document.getElementById('edita_quantidade_input');
		var pagamentoAbastecimentoInput = document.getElementById('edita_pagamento_input');
		var valorAbastecimentoInput = document.getElementById('edita_valor_input');
		var observacaoAbastecimentoInput = document.getElementById('edita_observacao_input');
		var botaoFinalizar = document.getElementById('edita_item_submit');
	}

	produtoAbastecimentoInput.style.background="transparent";
	quantidadeAbastecimentoInput.style.background="transparent";
	valorAbastecimentoInput.style.background="transparent";
}

/* REALIZA VALIDAÇÃO DE ATRIBUTOS NULOS NO OBJETO */
function validacaoDoObjetoAbastecimento(submitar, tipo) {

	var erros = "Ocorreram alguns erros no cadastro do patrimônio:\n";

	if(tipo == "novo") {
		var produtoAbastecimentoInput = document.getElementById('produto_abastecimento_input');
		var fornecedorAbastecimentoInput = document.getElementById('fornecedor_abastecimento_input');
		var quantidadeAbastecimentoInput = document.getElementById('quantidade_input');
		var pagamentoAbastecimentoInput = document.getElementById('pagamento_input');
		var valorAbastecimentoInput = document.getElementById('valor_input');
		var observacaoAbastecimentoInput = document.getElementById('observacao_input');
		var botaoFinalizar = document.getElementById('novo_item_submit');
	}
	else {
		var produtoAbastecimentoInput = document.getElementById('edita_produto_abastecimento_input');
		var fornecedorAbastecimentoInput = document.getElementById('edita_fornecedor_abastecimento_input');
		var quantidadeAbastecimentoInput = document.getElementById('edita_quantidade_input');
		var pagamentoAbastecimentoInput = document.getElementById('edita_pagamento_input');
		var valorAbastecimentoInput = document.getElementById('edita_valor_input');
		var observacaoAbastecimentoInput = document.getElementById('edita_observacao_input');
		var botaoFinalizar = document.getElementById('edita_item_submit');
	}

	if(submitar == true) {
		observacaoAbastecimentoInput.value = (observacaoAbastecimentoInput.value).trim();
	}

	// REALIZA VERIFICAÇÃO DOS 3 ATRIBUTOS OBRIGATÓRIOS NULOS
	if(produtoAbastecimentoInput.value != "" 
			|| fornecedorAbastecimentoInput.value != "null" 
			|| quantidadeAbastecimentoInput.value != "PAGO" 
			|| pagamentoAbastecimentoInput.value != "DINHEIRO"
			|| valorAbastecimentoInput.value != 0
			|| observacaoAbastecimentoInput.value != "") {

		if(produtoAbastecimentoInput.value == "" || produtoAbastecimentoInput.value == "...") {
			produtoAbastecimentoInput.style.background="#f5aea9";	
			erros += "- O preenchimento do campo produto é obrigatório\n";	
		}
		else {
			produtoAbastecimentoInput.style.background="transparent";		
		}

		if(valorAbastecimentoInput.value == 0) {
			valorAbastecimentoInput.style.background="#f5aea9";
			erros += "- O preenchimento do campo valor é obrigatório\n";		
		}
		else {
			valorAbastecimentoInput.style.background="transparent";		
		}		

		if(quantidadeAbastecimentoInput.value == 0) {
			quantidadeAbastecimentoInput.style.background="#f5aea9";
			erros += "- O preenchimento do campo quantidade é obrigatório\n";		
		}
		else {
			quantidadeAbastecimentoInput.style.background="transparent";		
		}					

	}

	else {
		produtoAbastecimentoInput.style.background="transparent";
		valorAbastecimentoInput.style.background="transparent";
	}

	if(submitar == true) {
		submitAbastecimento(tipo, erros, botaoFinalizar);
	}
}

/* REALIZA SUBMIT DO OBJETO */
function submitAbastecimento(tipo, erros, botaoFinalizar) {

	if (tipo == "novo") {
		form = document.getElementById('form_novo');
	}
	else{
		form = document.getElementById('form_edita');
	}

	// VALIDAÇÃO FINAL
	if (erros != "Ocorreram alguns erros no cadastro do patrimônio:\n") {
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
		form.submit();
		return true;
	}	
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

	setTimeout(() => {  $('#produto_abastecimento_input').focus(); }, 10);		
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
	resetCores('novo');
	document.getElementById('produto_abastecimento_input').value="";
	document.getElementById('fornecedor_abastecimento_input').value="";
	document.getElementById('quantidade_input').value=0;
	document.getElementById('pagamento_input').value="DINHEIRO";
	document.getElementById('valor_input').value=0;
	document.getElementById('observacao_input').value="";
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

	var dataInicio = document.getElementById('filtro_data_inicio_block');
	var dataInicioInput = document.getElementById('data_inicio_filtro_input');

	var dataFim = document.getElementById('filtro_data_fim_block');
	var dataFimInput = document.getElementById('data_fim_filtro_input');

	var mesBlock = document.getElementById('filtro_mes_block');
	var mesInput = document.getElementById('mes_filtro_input');

	var anoBlock = document.getElementById('filtro_ano_block');
	var anoInput = document.getElementById('ano_filtro_input');

	var fornecedorBlock = document.getElementById('filtro_fornecedor_block');
	var fornecedorInput = document.getElementById('fornecedor_filtro_input');	

	const d = new Date();
	var ano = d.getFullYear();
	var mes = d.getMonth()+1;
	var dia = d.getDate();

	if (dia < 10) {
		dia = '0'+dia;
	}

	if(mes < 10){
		mes = '0'+mes;
	}

	var data = (ano + '-' + mes + '-' + dia);

	if (filtroTipo.value == 'PRODUTO') {

		produtoBlock.hidden=false;

		dataInicioInput.value=data;
		dataInicio.hidden=true;

		dataFimInput.value=data;
		dataFim.hidden=true;

		mesInput.value=mes;
		mesBlock.hidden=true;

		anoInput.value=ano;
		anoBlock.hidden=true;

		fornecedorInput.value="";
		fornecedorBlock.hidden=true;

	}
	else if (filtroTipo.value == 'DATA') {

		produtoInput.value="";
		produtoBlock.hidden=true;

		dataInicioInput.value=data;
		dataInicio.hidden=false;

		dataFimInput.value=data;
		dataFim.hidden=false;

		mesInput.value=mes;
		mesBlock.hidden=true;

		anoInput.value=ano;
		anoBlock.hidden=true;

		fornecedorInput.value="";
		fornecedorBlock.hidden=true;
	}
	else if (filtroTipo.value == 'PERIODO') {

		produtoInput.value="";
		produtoBlock.hidden=true;

		dataInicioInput.value=data;
		dataInicio.hidden=true;

		dataFimInput.value=data;
		dataFim.hidden=true;

		mesInput.value=mes;
		mesBlock.hidden=false;

		anoInput.value=ano;
		anoBlock.hidden=false;

		fornecedorInput.value="";
		fornecedorBlock.hidden=true;
		
	}

	else if (filtroTipo.value == 'FORNECEDOR') {

		produtoInput.value="";
		produtoBlock.hidden=true;

		dataInicioInput.value=data;
		dataInicio.hidden=true;

		dataFimInput.value=data;
		dataFim.hidden=true;

		mesInput.value=mes;
		mesBlock.hidden=true;

		anoInput.value=ano;
		anoBlock.hidden=true;

		fornecedorBlock.hidden=false;
		
	}	
}

function reloadFiltro() {

	const d = new Date();
	var ano = d.getFullYear();
	var mes = d.getMonth()+1;
	var dia = d.getDate();

	if (dia < 10) {
		dia = '0'+dia;
	}

	if(mes < 10){
		mes = '0'+mes;
	}

	var data = (ano + '-' + mes + '-' + dia);

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

	document.getElementById('tipo_filtro_option_data').hidden=false;
	document.getElementById('filtro_data_inicio_block').hidden=true;
	document.getElementById('data_inicio_filtro_input').value=data;
	document.getElementById('input_data_inicio_backend').value="";
	document.getElementById('filtro_data_fim_block').hidden=true;	
	document.getElementById('data_fim_filtro_input').value=data;
	document.getElementById('input_data_fim_backend').value="";
	document.getElementById('filtro_data_tag').hidden=true;

	document.getElementById('tipo_filtro_option_periodo').hidden=false;
	document.getElementById('filtro_mes_block').hidden=true;
	document.getElementById('mes_filtro_input').value=mes;
	document.getElementById('input_periodo_mes_backend').value="";	
	document.getElementById('filtro_ano_block').hidden=true;
	document.getElementById('ano_filtro_input').value=ano;
	document.getElementById('input_periodo_ano_backend').value="";	
	document.getElementById('filtro_periodo_tag').hidden=true;

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

	var optionData = document.getElementById('tipo_filtro_option_data');
	var dataInicioBlock = document.getElementById('filtro_data_inicio_block');	
	var dataFimBlock = document.getElementById('filtro_data_fim_block');	
	var dataTag = document.getElementById('filtro_data_tag');	

	var optionPeriodo = document.getElementById('tipo_filtro_option_periodo');
	var periodoMesBlock = document.getElementById('filtro_mes_block');	
	var periodoAnoBlock = document.getElementById('filtro_ano_block');	
	var periodoTag = document.getElementById('filtro_periodo_tag');		

	var optionFornecedor = document.getElementById('tipo_filtro_option_fornecedor');
	var fornecedorBlock = document.getElementById('filtro_fornecedor_block');	
	var fornecedorTag = document.getElementById('filtro_fornecedor_tag');	

	var inputProdutoFiltro = document.getElementById('produto_filtro_input');
	var inputDataInicioFiltro = document.getElementById('data_inicio_filtro_input');
	var inputDataFimFiltro = document.getElementById('data_fim_filtro_input');
	var inputMesFiltro = document.getElementById('mes_filtro_input');
	var inputAnoFiltro = document.getElementById('ano_filtro_input');
	var inputFornecedorFiltro = document.getElementById('fornecedor_filtro_input');

	var inputProdutoBackend = document.getElementById('input_produto_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputFornecedorBackend = document.getElementById('input_fornecedor_backend');

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
	else if (filtroTipo.value == 'DATA') {
		optionData.hidden=true;
		optionPeriodo.hidden=true;

		dataInicioBlock.hidden=true;
		dataFimBlock.hidden=true;

		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;

		var columnDataSplitted = inputDataInicioFiltro.value.split("-");
		var inicio = columnDataSplitted[2] + "/" + columnDataSplitted[1] + "/" + columnDataSplitted[0];		
		var columnDataSplitted = inputDataFimFiltro.value.split("-");
		var fim = columnDataSplitted[2] + "/" + columnDataSplitted[1] + "/" + columnDataSplitted[0];	

		dataTag.hidden=false;
		dataTag.innerText = inicio + ' a ' + fim;

		filtroTipo.value="";
		inputDataInicioBackend.value=inputDataInicioFiltro.value;
		inputDataFimBackend.value=inputDataFimFiltro.value;

		filtroBt.hidden=true;
		buscarBt.hidden=false;
		filtroTipo.style.border="1px solid grey";
		filtroTipo.disabled=true;
		filtroBt.disabled=true;
		filtroBt.style.pointerEvents="none";			
	}
	else if (filtroTipo.value == 'PERIODO') {
		optionData.hidden=true;
		optionPeriodo.hidden=true;

		dataInicioBlock.hidden=true;
		dataFimBlock.hidden=true;

		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;

		periodoTag.hidden=false;
		periodoTag.innerText = 'Mês ' + inputMesFiltro.value + ' de ' + inputAnoFiltro.value;

		filtroTipo.value="";
		inputMesBackend.value=inputMesFiltro.value;
		inputAnoBackend.value=inputAnoFiltro.value;

		filtroBt.hidden=true;
		buscarBt.hidden=false;
		filtroTipo.style.border="1px solid grey";
		filtroTipo.disabled=true;
		filtroBt.disabled=true;
		filtroBt.style.pointerEvents="none";		
	}
	else if (filtroTipo.value == 'FORNECEDOR') {
		if (inputFornecedorFiltro.value != "") {
			optionFornecedor.hidden=true;
			fornecedorBlock.hidden=true;

			fornecedorTag.hidden=false;
			fornecedorTag.innerText = 'Fornecedor: ' + inputFornecedorFiltro.value;

			filtroTipo.value="";
			inputFornecedorBackend.value=inputFornecedorFiltro.value;

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

	var optionData = document.getElementById('tipo_filtro_option_data');
	var dataInicioBlock = document.getElementById('filtro_data_inicio_block');	
	var dataFimBlock = document.getElementById('filtro_data_fim_block');	
	var dataTag = document.getElementById('filtro_data_tag');	

	var optionPeriodo = document.getElementById('tipo_filtro_option_periodo');
	var periodoMesBlock = document.getElementById('filtro_mes_block');	
	var periodoAnoBlock = document.getElementById('filtro_ano_block');	
	var periodoTag = document.getElementById('filtro_periodo_tag');		

	var optionFornecedor = document.getElementById('tipo_filtro_option_fornecedor');
	var fornecedorBlock = document.getElementById('filtro_fornecedor_block');	
	var fornecedorTag = document.getElementById('filtro_fornecedor_tag');		

	var inputProdutoBackend = document.getElementById('input_produto_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputFornecedorBackend = document.getElementById('input_fornecedor_backend');	
	
	filtroTipo.style.border="1px solid #949393";
	filtroTipo.disabled=false;
	filtroBt.disabled=false;
	filtroBt.style.pointerEvents="auto";	

	if (filtro == 'produto') {		

		optionProduto.hidden=false;

		produtoBlock.hidden=false;

		dataInicioBlock.hidden=true;
		dataFimBlock.hidden=true;
		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;
		fornecedorBlock.hidden=true;

		produtoTag.hidden=true;

		filtroTipo.value="PRODUTO";

		inputProdutoBackend.value="";
	}
	else if (filtro == 'data') {
		optionData.hidden=false;
		optionPeriodo.hidden=false;

		produtoBlock.hidden=true;
		dataInicioBlock.hidden=false;
		dataFimBlock.hidden=false;
		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;
		fornecedorBlock.hidden=true;

		dataTag.hidden=true;
		filtroTipo.value="DATA";

		inputDataInicioBackend.value="";
		inputDataFimBackend.value="";
	}
	else if (filtro == 'periodo') {
		optionData.hidden=false;
		optionPeriodo.hidden=false;

		produtoBlock.hidden=true;
		dataInicioBlock.hidden=true;
		dataFimBlock.hidden=true;
		periodoMesBlock.hidden=false;
		periodoAnoBlock.hidden=false;
		fornecedorBlock.hidden=true;

		periodoTag.hidden=true;
		filtroTipo.value="PERIODO";

		inputMesBackend.value="";
		inputAnoBackend.value="";
	}
	else if (filtro == 'fornecedor') {

		produtoBlock.hidden=true;

		dataInicioBlock.hidden=true;
		dataFimBlock.hidden=true;
		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;

		fornecedorBlock.hidden=false;
		fornecedorTag.hidden=true;

		filtroTipo.value="FORNECEDOR";

		inputFornecedorBackend.value="";
	}	

	buscarBt.hidden=true;		
	filtroBt.hidden=false;
}

function efeitoRemoverFiltro(filtro) {

	var filtroProduto = document.getElementById('filtro_produto_tag');
	var filtroData = document.getElementById('filtro_data_tag');	
	var filtroPeriodo = document.getElementById('filtro_periodo_tag');	
	var filtroFornecedor = document.getElementById('filtro_fornecedor_tag');


	if (filtro == 'produto') {
		filtroProduto.style.transition="0.5s"
		filtroProduto.style.background="#AA3C3C";
		filtroProduto.style.border="1px solid #AA3C3C";
		filtroProduto.style.color="#212121";
		filtroProduto.innerText="Remover";
		filtroProduto.style.cursor="pointer";

	}
	else if (filtro == 'data') {
		filtroData.style.transition="0.5s"
		filtroData.style.background="#AA3C3C";
		filtroData.style.border="1px solid #AA3C3C";
		filtroData.style.color="#212121";
		filtroData.innerText="Remover";
		filtroData.style.cursor="pointer";
	}	
	else if (filtro == 'periodo') {
		filtroPeriodo.style.transition="0.5s"
		filtroPeriodo.style.background="#AA3C3C";
		filtroPeriodo.style.border="1px solid #AA3C3C";
		filtroPeriodo.style.color="#212121";
		filtroPeriodo.innerText="Remover";
		filtroPeriodo.style.cursor="pointer";
	}
	else if (filtro == 'fornecedor') {
		filtroFornecedor.style.transition="0.5s"
		filtroFornecedor.style.background="#AA3C3C";
		filtroFornecedor.style.border="1px solid #AA3C3C";
		filtroFornecedor.style.color="#212121";
		filtroFornecedor.innerText="Remover";
		filtroFornecedor.style.cursor="pointer";
	}			
}

function efeitoRemoverFiltroLeave(filtro) {

	var filtroProduto = document.getElementById('filtro_produto_tag');
	var filtroData = document.getElementById('filtro_data_tag');
	var filtroPeriodo = document.getElementById('filtro_periodo_tag');	
	var filtroFornecedor = document.getElementById('filtro_fornecedor_tag');	

	var inputProdutoBackend = document.getElementById('input_produto_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputFornecedorBackend = document.getElementById('input_fornecedor_backend');

	if (filtro == 'produto') {
		filtroProduto.style.transition="1s"
		filtroProduto.style.background="transparent";
		filtroProduto.style.border="1px solid #212121"
		filtroProduto.style.color="#212121";
		filtroProduto.innerText="Descrição";
		filtroProduto.innerText = 'Nome: ' + inputProdutoBackend.value;

	}
	else if (filtro == 'data') {
		filtroData.style.transition="1s"
		filtroData.style.background="transparent";
		filtroData.style.border="1px solid #212121"
		filtroData.style.color="#212121";
		var columnDataSplitted = inputDataInicioBackend.value.split("-");
		var inicio = columnDataSplitted[2] + "/" + columnDataSplitted[1] + "/" + columnDataSplitted[0];		
		var columnDataSplitted = inputDataFimBackend.value.split("-");
		var fim = columnDataSplitted[2] + "/" + columnDataSplitted[1] + "/" + columnDataSplitted[0];		
		filtroData.innerText = inicio + ' a ' + fim;
	}
	else if (filtro == 'periodo') {
		filtroPeriodo.style.transition="1s"
		filtroPeriodo.style.background="transparent";
		filtroPeriodo.style.border="1px solid #212121"
		filtroPeriodo.style.color="#212121";
		filtroPeriodo.innerText = 'Mês ' + inputMesBackend.value + ' de ' + inputAnoBackend.value;
	}
	else if (filtro == 'fornecedor') {
		filtroFornecedor.style.transition="1s"
		filtroFornecedor.style.background="transparent";
		filtroFornecedor.style.border="1px solid #212121"
		filtroFornecedor.style.color="#212121";
		filtroFornecedor.innerText = 'Fornecedor: ' + inputFornecedorBackend.value;
	}		
}

function dataFiltroResponsiva() {

	const d = new Date();
	var ano = d.getFullYear();
	var mes = d.getMonth()+1;
	var dia = d.getDate();

	if(mes < 10) {
		var mes = '0' + mes;
	}

	if(dia < 10) {
		var dia = '0' + dia;
	}	

	var hoje = (ano + '-' + mes + '-' + dia); 

	var dataInicio = document.getElementById('data_inicio_filtro_input');
	var dataFim = document.getElementById('data_fim_filtro_input');
	
	if (dataInicio.value == null || dataInicio.value == '') dataInicio.value = hoje;
	if (dataFim.value == null || dataFim.value == '') dataFim.value = hoje;

	$('#data_fim_filtro_input').attr("min", dataInicio.value);

	if (dataInicio.value > dataFim.value) dataFim.value = dataInicio.value;
}

/* ================== CONFIGURAÇÕES DA SUB-TELA EDITA ITEM ====================== */

function abrirEditaItem(
							id, 
							dataCadastro,
							quantidade,
							custoUnitario, 
							custoTotal,
							observacao, 
							usuarioResponsavel,
							formaPagamento,
							produtoId,
							produtoSigla,
							produtoEspecificacao,
							produtoQuantidade,
							fornecedorId,
							fornecedorNome) {


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

	if (dataCadastro == null) dataCadastro = "1998-07-21"
	var dataCadastroSplitada = dataCadastro.split("-");
	if (dataCadastroSplitada.length == 3) {
		var dataUsParaDataBr = dataCadastroSplitada[2] + "-" + dataCadastroSplitada[1] + "-" + dataCadastroSplitada[0];
		document.getElementById('edita_titulo').title="Compra cadastrada dia " + dataUsParaDataBr + " por " + usuarioResponsavel;
	}

	document.getElementById('id_input_edicao').value=id;
	document.getElementById('edita_produto_abastecimento_input').value=produtoId;

	if(fornecedorId == '' || fornecedorId == null) { 
		document.getElementById('edita_fornecedor_abastecimento_input').value = ''; 
	}
	else {
		for (var i = 0; document.getElementsByClassName('edita_option_fornecedor').length > i; i++) {
			if (document.getElementsByClassName('edita_option_fornecedor')[i].value == fornecedorId) {
				document.getElementById('edita_fornecedor_abastecimento_input').value=document.getElementsByClassName('option_fornecedor')[i].value; 
			}
			else {
				document.getElementById('edita_fornecedor_abastecimento_input').value='';
			}
		}
	}

	document.getElementById('edita_quantidade_input').value=quantidade;
	document.getElementById('edita_pagamento_input').value=formaPagamento;
	document.getElementById('edita_valor_input').value=custoTotal;
	document.getElementById('edita_observacao_input').value=observacao;
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
	resetCores('edita');
	document.getElementById('edita_quantidade_input').value=0;
	document.getElementById('edita_pagamento_input').value='';
	document.getElementById('edita_valor_input').value=0;
	document.getElementById('edita_observacao_input').value='';
	document.getElementById('edita_produto_abastecimento_input').value='';
	document.getElementById('edita_fornecedor_abastecimento_input').value = '';
}

/* ================== MISC ====================== */

function buildUrlPagamentos() {

	var tipoFiltro = document.getElementById('tipo_filtro');

	var dataInicio = document.getElementById('back_inicio');
	var dataFim = document.getElementById('back_fim');

	var periodoMes = document.getElementById('back_mes');
	var periodoAno = document.getElementById('back_ano');	

	var fornecedorId = document.getElementById('back_fornecedorId');		

	var fornecedor = document.getElementById('back_fornecedor');			

	var produto = document.getElementById('back_produto');	

	if(tipoFiltro.value == 'data') {
		$('#especie').attr("href", "/compras?inicio=" + dataInicio.value + "&fim=" + dataFim.value + "&meio=DINHEIRO");
		$('#credito').attr("href", "/compras?inicio=" + dataInicio.value + "&fim=" + dataFim.value + "&meio=CREDITO");
		$('#debito').attr("href", "/compras?inicio=" + dataInicio.value + "&fim=" + dataFim.value + "&meio=DEBITO");
		$('#cheque').attr("href", "/compras?inicio=" + dataInicio.value + "&fim=" + dataFim.value + "&meio=CHEQUE");
		$('#pix').attr("href", "/compras?inicio=" + dataInicio.value + "&fim=" + dataFim.value + "&meio=PIX");
		$('#boleto').attr("href", "/compras?inicio=" + dataInicio.value + "&fim=" + dataFim.value + "&meio=BOLETO");
	}
	else if(tipoFiltro.value == 'periodo') {
		$('#especie').attr("href", "/compras?mes=" + periodoMes.value + "&ano=" + periodoAno.value + "&meio=DINHEIRO");
		$('#credito').attr("href", "/compras?mes=" + periodoMes.value + "&ano=" + periodoAno.value + "&meio=CREDITO");
		$('#debito').attr("href", "/compras?mes=" + periodoMes.value + "&ano=" + periodoAno.value + "&meio=DEBITO");
		$('#cheque').attr("href", "/compras?mes=" + periodoMes.value + "&ano=" + periodoAno.value + "&meio=CHEQUE");
		$('#pix').attr("href", "/compras?mes=" + periodoMes.value + "&ano=" + periodoAno.value + "&meio=PIX");
		$('#boleto').attr("href", "/compras?mes=" + periodoMes.value + "&ano=" + periodoAno.value + "&meio=BOLETO");								
	}
	else if(tipoFiltro.value == 'fornecedorId') {
		$('#especie').attr("href", "/compras?fornecedorId=" + fornecedorId.value + "&meio=DINHEIRO");
		$('#credito').attr("href", "/compras?fornecedorId=" + fornecedorId.value + "&meio=CREDITO");
		$('#debito').attr("href", "/compras?fornecedorId=" + fornecedorId.value + "&meio=DEBITO");
		$('#cheque').attr("href", "/compras?fornecedorId=" + fornecedorId.value + "&meio=CHEQUE");
		$('#pix').attr("href", "/compras?fornecedorId=" + fornecedorId.value + "&meio=PIX");
		$('#boleto').attr("href", "/compras?fornecedorId=" + fornecedorId.value + "&meio=BOLETO");								
	}	
	else if(tipoFiltro.value == 'fornecedor') {
		$('#especie').attr("href", "/compras?fornecedor=" + fornecedor.value + "&meio=DINHEIRO");
		$('#credito').attr("href", "/compras?fornecedor=" + fornecedor.value + "&meio=CREDITO");
		$('#debito').attr("href", "/compras?fornecedor=" + fornecedor.value + "&meio=DEBITO");
		$('#cheque').attr("href", "/compras?fornecedor=" + fornecedor.value + "&meio=CHEQUE");
		$('#pix').attr("href", "/compras?fornecedor=" + fornecedor.value + "&meio=PIX");
		$('#boleto').attr("href", "/compras?fornecedor=" + fornecedor.value + "&meio=BOLETO");								
	}
	else if(tipoFiltro.value == 'produto') {
		$('#especie').attr("href", "/compras?produto=" + produto.value + "&meio=DINHEIRO");
		$('#credito').attr("href", "/compras?produto=" + produto.value + "&meio=CREDITO");
		$('#debito').attr("href", "/compras?produto=" + produto.value + "&meio=DEBITO");
		$('#cheque').attr("href", "/compras?produto=" + produto.value + "&meio=CHEQUE");
		$('#pix').attr("href", "/compras?produto=" + produto.value + "&meio=PIX");
		$('#boleto').attr("href", "/compras?produto=" + produto.value + "&meio=BOLETO");								
	}
	else {
		$('#especie').attr("href", "/compras?meio=DINHEIRO");
		$('#credito').attr("href", "/compras?meio=CREDITO");
		$('#debito').attr("href", "/compras?meio=DEBITO");
		$('#cheque').attr("href", "/compras?meio=CHEQUE");
		$('#pix').attr("href", "/compras?meio=PIX");
		$('#boleto').attr("href", "/compras?meio=BOLETO");		
	}
}

function buildUrlPages() {

	var paginaAtual = document.getElementById('pagina_atual');
	var tipoFiltro = document.getElementById('tipo_filtro');
	var meioAtivo = document.getElementById('meio_ativo');

	var dataInicio = document.getElementById('back_inicio');
	var dataFim = document.getElementById('back_fim');
	var periodoMes = document.getElementById('back_mes');
	var periodoAno = document.getElementById('back_ano');	
	var fornecedorId = document.getElementById('back_fornecedorId');		
	var fornecedor = document.getElementById('back_fornecedor');			
	var produto = document.getElementById('back_produto');	

	var pageNumber = document.getElementsByClassName('page_number');

	if(tipoFiltro.value == 'data') {
		
		if (meioAtivo == null || meioAtivo == "") {
			$('#anterior').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) - 1)  + "&inicio=" + dataInicio.value + "&fim=" + dataFim.value);
			$('#proxima').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) + 1)  + "&inicio=" + dataInicio.value + "&fim=" + dataFim.value);
		}
		else {
			$('#anterior').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) - 1)  + "&inicio=" + dataInicio.value + "&fim=" + dataFim.value + "&meio=" + meioAtivo.value);
			$('#proxima').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) + 1)  + "&inicio=" + dataInicio.value + "&fim=" + dataFim.value + "&meio=" + meioAtivo.value);
		}

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			if (meioAtivo.value == null || meioAtivo.value == "") {
				$('#' + idPagina).attr("href", "/compras?page=" + 
					(parseInt(pageNumber[i].getAttribute('data-numeroPagina')))  + "&inicio=" + dataInicio.value + "&fim=" + dataFim.value);
			}	
			else {
				$('#' + idPagina).attr("href", "/compras?page=" + 
					(parseInt(pageNumber[i].getAttribute('data-numeroPagina')))  + "&inicio=" + dataInicio.value + "&fim=" + dataFim.value + "&meio=" + meioAtivo.value);				
			}		
		}
	}

	else if(tipoFiltro.value == 'periodo') {
		
		if (meioAtivo.value == null || meioAtivo.value == "") {
			$('#anterior').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) - 1)  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value);
			$('#proxima').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) + 1)  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value);
		}
		else {
			$('#anterior').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) - 1)  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value + "&meio=" + meioAtivo.value);
			$('#proxima').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) + 1)  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value + "&meio=" + meioAtivo.value);
		}
		
		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			if (meioAtivo.value == null || meioAtivo.value == "") {
				$('#' + idPagina).attr("href", "/compras?page=" + 
					(parseInt(pageNumber[i].getAttribute('data-numeroPagina')))  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value);
			}	
			else {
				$('#' + idPagina).attr("href", "/compras?page=" + 
					(parseInt(pageNumber[i].getAttribute('data-numeroPagina')))  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value + "&meio=" + meioAtivo.value);				
			}		
		}
	}

	else if(tipoFiltro.value == 'fornecedorId') {
		if (meioAtivo.value == null || meioAtivo.value == "") {
			$('#anterior').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) - 1)  + "&fornecedorId=" + fornecedorId.value);
			$('#proxima').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) + 1)  + "&fornecedorId=" + fornecedorId.value);
		}
		else {
			$('#anterior').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) - 1)  + "&fornecedorId=" + fornecedorId.value + "&meio=" + meioAtivo.value);
			$('#proxima').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) + 1)  + "&fornecedorId=" + fornecedorId.value + "&meio=" + meioAtivo.value);	
		}

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			if (meioAtivo.value == null || meioAtivo.value == "") {
				$('#' + idPagina).attr("href", "/compras?page=" + 
					(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&fornecedorId=" + fornecedorId.value);
			}	
			else {
				$('#' + idPagina).attr("href", "/compras?page=" + 
					(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&fornecedorId=" + fornecedorId.value + "&meio=" + meioAtivo.value);				
			}		
		}
	}

	else if(tipoFiltro.value == 'fornecedor') {
		if (meioAtivo.value == null || meioAtivo.value == "") {
			$('#anterior').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) - 1)  + "&fornecedor=" + fornecedor.value);
			$('#proxima').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) + 1)  + "&fornecedor=" + fornecedor.value);
		}
		else {
			$('#anterior').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) - 1)  + "&fornecedor=" + fornecedor.value + "&meio=" + meioAtivo.value);
			$('#proxima').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) + 1)  + "&fornecedor=" + fornecedor.value + "&meio=" + meioAtivo.value);	
		}

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			if (meioAtivo.value == null || meioAtivo.value == "") {
				$('#' + idPagina).attr("href", "/compras?page=" + 
					(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&fornecedor=" + fornecedor.value);
			}	
			else {
				$('#' + idPagina).attr("href", "/compras?page=" + 
					(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&fornecedor=" + fornecedor.value + "&meio=" + meioAtivo.value);				
			}		
		}					
	}

	else if(tipoFiltro.value == 'produto') {
		if (meioAtivo.value == null || meioAtivo.value == "") {
			$('#anterior').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) - 1)  + "&produto=" + produto.value);
			$('#proxima').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) + 1)  + "&produto=" + produto.value);
		}
		else {
			$('#anterior').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) - 1)  + "&produto=" + produto.value + "&meio=" + meioAtivo.value);
			$('#proxima').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) + 1)  + "&produto=" + produto.value + "&meio=" + meioAtivo.value);	
		}

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			if (meioAtivo.value == null || meioAtivo.value == "") {
				$('#' + idPagina).attr("href", "/compras?page=" + 
					(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&produto=" + produto.value);
			}	
			else {
				$('#' + idPagina).attr("href", "/compras?page=" + 
					(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&produto=" + produto.value + "&meio=" + meioAtivo.value);				
			}		
		}								
	}
	else {
		if (meioAtivo.value == null || meioAtivo.value == "") {
			$('#anterior').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) - 1));
			$('#proxima').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) + 1));
		}
		else {
			$('#anterior').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) - 1) + "&meio=" + meioAtivo.value);
			$('#proxima').attr("href", "/compras?page=" + (parseInt(paginaAtual.value) + 1) + "&meio=" + meioAtivo.value);	
		}		

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			if (meioAtivo.value == null || meioAtivo.value == "") {
				$('#' + idPagina).attr("href", "/compras?page=" + 
					(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))));
			}	
			else {
				$('#' + idPagina).attr("href", "/compras?page=" + 
					(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&meio=" + meioAtivo.value);				
			}
		}		
	}	
}

function buildUrl(baseUrl, pagina, produto, dataInicio, dataFim, mes, ano, fornecedor) {

	var url = baseUrl + "?page=" + pagina;
	var paginaList = document.getElementById('pagina_list');

	if (produto != null) {
		baseUrl + "&produto=" + produto;
	}		
	else if (dataInicio != null && dataFim != null) {
		baseUrl + "&dataInicio=" + dataInicio + "&dataFim=" + dataFim;
	}
	else if (mes != null && ano != null) {
		baseUrl + "&mes=" + mes + "&ano=" + ano;	
	}
	else if(fornecedor != null) {
		baseUrl + "&fornecedor=" + fornecedor;
	}

	paginaList.href=baseUrl;
}

function hideMessage(){
	var alertas = document.getElementsByClassName('alert');
	for(var i = 0; i < alertas.length; i++){
		alertas[i].hidden=true;
	}
}

function ajustaTabela(){

	var optionFornecedor = document.getElementsByClassName('option_fornecedor');

	for (var i = 0; i < optionFornecedor.length; i++) {
		var fornecedorContent = optionFornecedor[i].innerText;
		if (fornecedorContent.includes('null')) {
			optionFornecedor[i].innerText=fornecedorContent.replace('null,', '').replace('null', 'Sem endereço cadastrado');
		}
	}

	if (document.getElementById('produto_abastecimento_input').length < 1) {
		document.getElementById('produto_abastecimento_input').disabled=true;
	}
	else {
		document.getElementById('produto_abastecimento_input').disabled=false;	
	}

	if (document.getElementById('fornecedor_abastecimento_input').length < 2) {
		document.getElementById('fornecedor_abastecimento_input').disabled=true;
	}
	else {
		document.getElementById('fornecedor_abastecimento_input').disabled=false;	
	}

	var line = document.getElementsByClassName('tr');	
	var columnData = document.getElementsByClassName('td_cadastro');
	var columnValor = document.getElementsByClassName('td_custo');	

	for(var i = 0; i < line.length; i++) {

		var columnDataSplitted = columnData[i].innerText.split("-");
		if (columnDataSplitted.length == 3) {
			var convertedDate = columnDataSplitted[2] + "/" + columnDataSplitted[1] + "/" + columnDataSplitted[0];
			columnData[i].innerText=convertedDate;
		}

		if (!columnValor[i].innerText.includes('R$') && !columnValor[i].innerText.includes('...')) {
			columnValor[i].innerText=
				parseFloat(columnValor[i].innerText).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
		}

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

	paginacaoComprimida();
}

function paginacaoComprimida() {

	var paginaAtual = document.getElementById('pegando_page');
	var paginas = document.getElementsByClassName('page_li');
	var paginasInside = document.getElementsByClassName('page_number');

	for (var i = 0; i < paginasInside.length; i++) {
		paginas[i].hidden=false;
		if (bodyWidth > 400) {
			if (paginasInside[i].text < (parseInt(paginaAtual.innerText) - 1)) {
				paginas[i].hidden=true;
			}
			else {
				if (paginasInside[i].text > (parseInt(paginaAtual.innerText) + 3)) {
					paginas[i].hidden=true;
				}
			}
		}
		else {
		if (paginasInside[i].text < (parseInt(paginaAtual.innerText))) {
				paginas[i].hidden=true;
			}
			else {
				if (paginasInside[i].text > (parseInt(paginaAtual.innerText) + 2)) {
					paginas[i].hidden=true;
				}
			}			
		}
	}

}

function doALoadOfStuff() {
	document.getElementById('conteudo_container').style.transition="2s";
	responsive();
}

function tituloResponsivo(filtro) {

	var titulo = document.getElementById('conteudo_titulo_text');
	var dataInicio = document.getElementById('back_inicio');
	var dataFim = document.getElementById('back_fim');	
	var mes = document.getElementById('back_mes');
	var ano = document.getElementById('back_ano');	
	var fornecedorId = document.getElementById('back_fornecedorId');	
	var fornecedor = document.getElementById('back_fornecedor');			
	var produto = document.getElementById('back_produto');		



	if(filtro == "hoje") {
		titulo.innerText="Compras de hoje";
	}

	else if (filtro == "data") {
		var columnDataSplitted = dataInicio.value.split("-");
		var inicio = columnDataSplitted[2] + "/" + columnDataSplitted[1] + "/" + columnDataSplitted[0];		
		var columnDataSplitted = dataFim.value.split("-");
		var fim = columnDataSplitted[2] + "/" + columnDataSplitted[1] + "/" + columnDataSplitted[0];
		titulo.innerText="Compras: " + inicio + " à " + fim;		
	}

	else if (filtro == "fornecedorId") {
		var nomeFornecedor = fornecedorId.getAttribute('data-nomeFornecedor');
		if (nomeFornecedor.length > 20) {
			nomeFornecedor = nomeFornecedor.slice(0,20) + "...";
		}
		titulo.innerText="Compras com " + nomeFornecedor;
	}	
	
	else if (filtro == "periodo") {
		titulo.innerText="Compras: " + mes.value + "/" + ano.value;
	}
	else if (filtro == "produto") {
		titulo.innerText="Compras de " + produto.value;
	}
	else if (filtro == "fornecedor") {
		titulo.innerText="Compras com  " + (fornecedor.value).toLowerCase();
	}

}

function buildUrlRelatorio() {

	if(document.getElementsByClassName('tr_spring').length > 0) {

		var tipoFiltro = document.getElementById('tipo_filtro');

		var dataInicio = document.getElementById('back_inicio');
		var dataFim = document.getElementById('back_fim');
		var periodoMes = document.getElementById('back_mes');
		var periodoAno = document.getElementById('back_ano');	
		var fornecedorId = document.getElementById('back_fornecedorId');		
		var fornecedor = document.getElementById('back_fornecedor');	
		var produto = document.getElementById('back_produto');	
		var meioAtivo = document.getElementById('meio_ativo');

		var url = "/compras/relatorio?"
		var acessado = false;

		if(tipoFiltro.value == 'data') {
			url += "inicio=" + dataInicio.value + "&fim=" + dataFim.value;
			acessado = true;
		}

		else if(tipoFiltro.value == 'periodo') {
			url += "mes=" + periodoMes.value + "&ano=" + periodoAno.value;
			acessado = true;
		}

		else if(tipoFiltro.value == 'fornecedorId') {
			url += "fornecedorId=" + fornecedorId.value;
			acessado = true;
		}

		else if(tipoFiltro.value == 'fornecedor') {
			url += "fornecedor=" + fornecedor.value;
			acessado = true;
		}	

		else if(tipoFiltro.value == 'produto') {
			url += "produto=" + produto.value;
			acessado = true;
		}

		if(meioAtivo.value != null && meioAtivo.value != "") {
			if (acessado == true) {
				url += "&";
			}
			url+= "meio=" + meioAtivo.value;
		}

		$('#relatorio_button').attr("href", url);	

	}

	else {
		$('#relatorio_button').disabled=true;
	}

}