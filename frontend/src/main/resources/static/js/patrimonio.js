/* =========================== RESPONSIVIDADE DA TELA =========================== */

window.onload = responsive();
window.onresize = doALoadOfStuff;
ajustaTabela();
buildUrlPages();

var privilegio = document.getElementById('body').getAttribute('data-privilegio');
var tipoFiltro = document.getElementById('tipo_filtro');

if (tipoFiltro != null) {
	tituloResponsivo(tipoFiltro.value);
}	
console.log('Tipo do filtro: ' + tipoFiltro);
console.log('Privilégio: ' + privilegio);

document.onkeydown=function(){

	var keyCode = window.event.keyCode;
	bind(keyCode);

}

function bind(keyCode) {

    if(keyCode == '27') {
        fechaNovoItem();
        fecharFiltro();
        fecharEditaItem();
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
	    	window.location.href="/patrimonios";
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
	var informativoRow = document.getElementById('informativo_row');
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
	var tdUsuario = document.getElementsByClassName('td_usuario');
	var thUsuario = document.getElementsByClassName('th_usuario');
	var tdData = document.getElementsByClassName('td_data');
	var thData = document.getElementsByClassName('th_data');
	var tdPagamento = document.getElementsByClassName('td_pagamento');
	var thPagamento = document.getElementsByClassName('th_pagamento');
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
	var listaVaziaTitulo = document.getElementById('lista_vazia_titulo');
	var listaVaziaTexto = document.getElementById('lista_vazia_texto');
	var listaVaziaBotao = document.getElementById('lista_vazia_botao');
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
		if (informativoRow != null) {
			informativoRow.style.justifyContent="center";
		}

		if(listaVaziaTitulo != null) {
			listaVaziaTitulo.style.fontSize="1.75rem";
			listaVaziaTexto.style.fontSize="1rem";
			listaVaziaBotao.style.fontSize="1rem";
		}

		containerNovo.style.fontSize="1rem";		
		novoTitulo.style.fontSize="1.15rem";	

		containerEdita.style.fontSize="1rem";		
		editaTitulo.style.fontSize="1.15rem";	
		editaItemSubmit.style.marginTop="0px";

		conteudoTituloText.style.fontSize="1.1rem";
		filtroTitulo.style.fontSize="1.1rem";

		conteudoContainer.style.marginTop="30px";
		hrTabela.style.marginBottom="20px";
		informativoRow.style.marginBottom="20px";	

		for(var i = 0; i < thUsuario.length; i++) {
			thUsuario[i].style.display="flex";
		}

		for(var i = 0; i < tdUsuario.length; i++) {
			tdUsuario[i].style.display="flex";
		}

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.70rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.70rem";
		}
		
		for(var i = 0; i < informativo.length; i++){
			informativo[i].style.fontSize="0.70rem";
			informativo[i].style.width="23.5%";
			informativo[i].style.margin="0 7px";
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

		for (var i = 0; i < pageClick.length; i++) {
			pageClick[i].style.fontSize="0.70rem";
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
		if (informativoRow != null) {
			informativoRow.style.justifyContent="center";
		}

		if(listaVaziaTitulo != null) {
			listaVaziaTitulo.style.fontSize="1.5rem";
			listaVaziaTexto.style.fontSize="1rem";
			listaVaziaBotao.style.fontSize="1rem";
		}

		containerNovo.style.fontSize="1rem";
		novoTitulo.style.fontSize="1.15rem";	

		containerEdita.style.fontSize="1rem";
		editaTitulo.style.fontSize="1.15rem";	
		editaItemSubmit.style.marginTop="0px";

		conteudoTituloText.style.fontSize="1.1rem";
		filtroTitulo.style.fontSize="1.1rem";

		conteudoContainer.style.marginTop="30px";
		hrTabela.style.marginBottom="20px";
		informativoRow.style.marginBottom="20px";		

		for(var i = 0; i < thUsuario.length; i++) {
			thUsuario[i].style.display="flex";
		}

		for(var i = 0; i < tdUsuario.length; i++) {
			tdUsuario[i].style.display="flex";
		}					

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.65rem";		
		}		
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.65rem";			
		}

		for(var i = 0; i < informativo.length; i++){
			informativo[i].style.fontSize="0.65rem";
			informativo[i].style.width="23.5%";
			informativo[i].style.margin="0 5px";
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

		conteudoContainer.style.marginTop="30px";
		hrTabela.style.marginBottom="15px";
		informativoRow.style.marginBottom="15px";		

		for(var i = 0; i < thUsuario.length; i++) {
			thUsuario[i].style.display="none";
		}

		for(var i = 0; i < tdUsuario.length; i++) {
			tdUsuario[i].style.display="none";
		}		

		menuMobile.style.display="none";

		if (informativoRow != null) {
			informativoRow.style.justifyContent="center";
		}

		if(listaVaziaTitulo != null) {
			listaVaziaTitulo.style.fontSize="1.5rem";
			listaVaziaTexto.style.fontSize="1rem";
			listaVaziaBotao.style.fontSize="1rem";
		}


		containerNovo.style.fontSize="1rem";		
		novoTitulo.style.fontSize="1.1rem";

		containerEdita.style.fontSize="1rem";		
		editaTitulo.style.fontSize="1.1rem";
		editaItemSubmit.style.marginTop="0px";

		conteudoTituloText.style.fontSize="1rem";
		filtroTitulo.style.fontSize="1.1rem";	

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.60rem";	
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.60rem";
		}	

		for(var i = 0; i < informativo.length; i++){
			informativo[i].style.fontSize="0.60rem";
			informativo[i].style.width="40%";	
			informativo[i].style.margin="7px";					
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

		if (informativoRow != null) {
			informativoRow.style.justifyContent="center";
		}

		if(listaVaziaTitulo != null) {
			listaVaziaTitulo.style.fontSize="1.2rem";
			listaVaziaTexto.style.fontSize="0.90rem";
			listaVaziaBotao.style.fontSize="0.90rem";
		}

		containerNovo.style.fontSize="0.90rem";	
		novoTitulo.style.fontSize="1.2rem";		

		containerEdita.style.fontSize="0.90rem";	
		editaTitulo.style.fontSize="1.2rem";		
		editaItemSubmit.style.marginTop="0px";

		conteudoTituloText.style.fontSize="0.90rem";
		filtroTitulo.style.fontSize="1.1rem";

		conteudoContainer.style.marginTop="10px";
		hrTabela.style.marginBottom="15px";
		informativoRow.style.marginBottom="15px";

		for(var i = 0; i < thUsuario.length; i++) {
			thUsuario[i].style.display="none";
		}

		for(var i = 0; i < tdUsuario.length; i++) {
			tdUsuario[i].style.display="none";
		}		


		for(var i = 0; i < th.length; i++) {
			th[i].style.fontSize="0.55rem";
		}
		for(var i = 0; i < td.length; i++) {
			td[i].style.fontSize="0.55rem";
		}
				
		for(var i = 0; i < informativo.length; i++) {
			informativo[i].style.fontSize="0.55rem";
			informativo[i].style.width="46%";
			informativo[i].style.margin="7px";			
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

		for(var i = 0; i < filtroBlock.length; i++) {
			filtroBlock[i].style.marginBottom="20px";
		}

		filtroBuscarBt.style.marginTop="20px";
		filtroBuscarBt.style.justifyContent="center";
	}
	else if(bodyWidth < 540){
		console.log('Tela: Muito pequena');

		sideMenu.style.display="none";
		main.style.width="100%";
		mainRow.style.width = "100%";
		menuMobile.style.display="flex";
		containerNovo.style.fontSize="0.90rem";
		novoTitulo.style.fontSize="1rem";

		containerEdita.style.fontSize="0.90rem";
		editaTitulo.style.fontSize="1rem";
		editaItemSubmit.style.marginTop="5px";

		conteudoTituloText.style.fontSize="0.80rem";
		filtroTitulo.style.fontSize="1.1rem";

		if(listaVaziaTitulo != null) {
			listaVaziaTitulo.style.fontSize="1.2rem";
			listaVaziaTexto.style.fontSize="0.90rem";
			listaVaziaBotao.style.fontSize="0.90rem";
		}

		conteudoContainer.style.marginTop="10px";
		hrTabela.style.marginBottom="15px";
		informativoRow.style.marginBottom="5px";

		for(var i = 0; i < thUsuario.length; i++) {
			thUsuario[i].style.display="none";
		}

		for(var i = 0; i < tdUsuario.length; i++) {
			tdUsuario[i].style.display="none";
		}		

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.50rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.50rem";
		}

		for(var i = 0; i < informativo.length; i++){
			informativo[i].style.fontSize="0.50rem";
			informativo[i].style.width="55%";
			informativo[i].style.margin="0 auto 10px auto";
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

		for(var i = 0; i < imgContainer.length; i++) {
			imgContainer[i].style.width="20px";
			imgContainer[i].style.marginLeft="15px";			
		}	

		for(var i = 0; i < btnExcluir.length; i++) {
			btnExcluir[i].style.display="none";
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

/* ================== TRATAMENTO DE INPUTS E VALIDAÇÕES ====================== */

/* RESETA AS CORES */
function resetCores(tipo) {

	if(tipo == "novo") {
		var descricao = document.getElementById('descricao_patrimonio_input');
		var valor = document.getElementById('valor_patrimonio_input');
	}
	else {
		var descricao = document.getElementById('descricao_patrimonio_input_edicao');
		var valor = document.getElementById('valor_patrimonio_input_edicao');
	}

	descricao.style.background="transparent";
	valor.style.background="transparent";
}

/* REALIZA VALIDAÇÃO DE ATRIBUTOS NULOS NO OBJETO */
function validacaoDoObjetoPatrimonio(submitar, tipo) {

	var erros = "Ocorreram alguns erros no cadastro do patrimônio:\n";

	if(tipo == "novo") {
		var descricaoPatrimonioInput = document.getElementById('descricao_patrimonio_input');
		var tipoPatrimonioInput = document.getElementById('tipo_patrimonio_input');
		var statusPatrimonioInput = document.getElementById('status_patrimonio');
		var valorPatrimonioInput = document.getElementById('valor_patrimonio_input');
		var dataPatrimonioInput = document.getElementById('data_pagamento_input');
		var botaoFinalizar = document.getElementById('novo_item_submit');
	}
	else {
		var descricaoPatrimonioInput = document.getElementById('descricao_patrimonio_input_edicao');
		var tipoPatrimonioInput = document.getElementById('tipo_patrimonio_input_edicao');
		var statusPatrimonioInput = document.getElementById('status_patrimonio_edicao');
		var valorPatrimonioInput = document.getElementById('valor_patrimonio_input_edicao');
		var dataPatrimonioInput = document.getElementById('data_pagamento_input_edicao');
		var botaoFinalizar = document.getElementById('edita_item_submit');
	}

	if (descricaoPatrimonioInput != null) {
		descricaoPatrimonioInput.value = descricaoPatrimonioInput.value.normalize("NFD").replace(/[\u0300-\u036f]/g, "")		
	}	

	// REALIZA VERIFICAÇÃO DOS 3 ATRIBUTOS OBRIGATÓRIOS NULOS
	if(descricaoPatrimonioInput.value != "" 
			|| tipoPatrimonioInput.value != "ATIVO" 
			|| statusPatrimonioInput.value != "PAGO" 
			|| valorPatrimonioInput.value != 0
			|| dataPatrimonioInput.value != "") {

		if(descricaoPatrimonioInput.value == "") {
			descricaoPatrimonioInput.style.background="#f5aea9";	
			erros += "- O preenchimento do campo descrição é obrigatório\n";	
		}
		else {
			descricaoPatrimonioInput.style.background="transparent";		
		}

		if(valorPatrimonioInput.value == 0) {
			valorPatrimonioInput.style.background="#f5aea9";
			erros += "- O preenchimento do campo valor é obrigatório\n";		
		}
		else {
			valorPatrimonioInput.style.background="transparent";		
		}		

	}

	else {
		descricaoPatrimonioInput.style.background="transparent";
		valorPatrimonioInput.style.background="transparent";
	}

	if(submitar == true) {
		submitPatrimonio(tipo, erros, botaoFinalizar);
	}
}

/* REALIZA SUBMIT DO OBJETO */
function submitPatrimonio(tipo, erros, botaoFinalizar) {

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
		botaoFinalizar.type="submit";
		return true;
	}	
}

/* ================== CONFIGURAÇÕES DA SUB-TELA NOVO ITEM ====================== */

function abrirNovoItem() {

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

	var containerPrincipal = document.getElementById('conteudo_container');
	var menuSuperior = document.getElementById('menu_superior');
	var menuSuperiorMobile = document.getElementById('menu_superior_mobile');
	var sideMenu = document.getElementById('side_menu');

	var containerNovo = document.getElementById('conteudo_container_novo');

	containerNovo.hidden=false;
	document.getElementById('data_pagamento_input').value=hoje;

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
}

function fechaNovoItem() {

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

	var containerPrincipal = document.getElementById('conteudo_container');
	var menuSuperior = document.getElementById('menu_superior');
	var menuSuperiorMobile = document.getElementById('menu_superior_mobile');
	var sideMenu = document.getElementById('side_menu');	

	var containerNovo = document.getElementById('conteudo_container_novo');	

	var descricaoPatrimonioInput = document.getElementById('descricao_patrimonio_input');
	var valorPatrimonioInput = document.getElementById('valor_patrimonio_input');
	var tipoPatrimonioInput = document.getElementById('tipo_patrimonio_input');
	var statusPatrimonioInput = document.getElementById('status_patrimonio');
	var dataEntradaInput = document.getElementById('data_pagamento_input');

	resetCores('novo');

	descricaoPatrimonioInput.value="";
	valorPatrimonioInput.value=0;
	statusPatrimonioInput.value="PAGO";
	tipoPatrimonioInput.value="ATIVO";

	dataEntradaInput.style.color="#121212";
	dataEntradaInput.disabled=false;
	dataEntradaInput.value=hoje;	
	
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

function changeTipo(tipo) {

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

	var statusPatrimonio = document.getElementById('status_patrimonio');
	var tipoPatrimonio = document.getElementById('tipo_patrimonio_input');
	var dataEntradaInput = document.getElementById('data_pagamento_input');	

	if (tipoPatrimonio.value == "ATIVO") {
		statusPatrimonio.value="PAGO";
		dataEntradaInput.style.color="#121212";
		dataEntradaInput.disabled=false;
		dataEntradaInput.value=hoje;
	}
	else {
		statusPatrimonio.value="PENDENTE";
		dataEntradaInput.style.color="#4444";
		dataEntradaInput.value="";
		dataEntradaInput.disabled=true;
	}

	validacaoDoObjetoPatrimonio(false, tipo);
}

function changeStatus(tipo) {

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

	var statusPatrimonio = document.getElementById('status_patrimonio');
	var tipoPatrimonio = document.getElementById('tipo_patrimonio_input');
	var dataEntradaInput = document.getElementById('data_pagamento_input');	

	if (statusPatrimonio.value == "PAGO") {
		dataEntradaInput.style.color="#121212";
		dataEntradaInput.disabled=false;
		dataEntradaInput.value=hoje;
	}
	else {
		dataEntradaInput.style.color="#4444";
		dataEntradaInput.value="";
		dataEntradaInput.disabled=true;
	}

	validacaoDoObjetoPatrimonio(false, tipo);	
}

function reloadNovoItem() {

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

	var descricaoPatrimonioInput = document.getElementById('descricao_patrimonio_input');
	var tipoPatrimonioInput = document.getElementById('tipo_patrimonio_input');
	var valorPatrimonioInput = document.getElementById('valor_patrimonio_input');
	var statusPatrimonioInput = document.getElementById('status_patrimonio');
	var dataEntradaInput = document.getElementById('data_pagamento_input');

	descricaoPatrimonioInput.value="";
	valorPatrimonioInput.value=0;
	tipoPatrimonioInput.value="ATIVO";
	statusPatrimonioInput.value="PAGO";
	dataEntradaInput.value=hoje;
	resetCores('novo');
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

	var descricaoBlock = document.getElementById('filtro_descricao_block');
	var descricaoInput = document.getElementById('descricao_filtro_input');

	var mesBlock = document.getElementById('filtro_mes_block');
	var mesInput = document.getElementById('mes_filtro_input');

	var anoBlock = document.getElementById('filtro_ano_block');
	var anoInput = document.getElementById('ano_filtro_input');

	var tipo = document.getElementById('filtro_tipo_block');
	var tipoInput = document.getElementById('tipo_filtro_input');	

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

	if (filtroTipo.value == 'DESCRICAO') {

		descricaoBlock.hidden=false;

		mesInput.value=mes;
		mesBlock.hidden=true;

		anoInput.value=ano;
		anoBlock.hidden=true;

		tipoInput.value="FIXO";
		tipo.hidden=true;

	}
	else if (filtroTipo.value == 'PERIODO') {

		descricaoInput.value="";
		descricaoBlock.hidden=true;

		mesInput.value=mes;
		mesBlock.hidden=false;

		anoInput.value=ano;
		anoBlock.hidden=false;

		tipoInput.value="FIXO";
		tipo.hidden=true;
		
	}
	else if (filtroTipo.value == 'TIPO') {

		descricaoInput.value="";
		descricaoBlock.hidden=true;

		mesInput.value=mes;
		mesBlock.hidden=true;

		anoInput.value=ano;
		anoBlock.hidden=true;

		tipoInput.value="FIXO";
		tipo.hidden=false;
		
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
	document.getElementById('filtro_input').value="DESCRICAO";
	document.getElementById('filtro_bt').hidden=false;
	document.getElementById('filtro_bt').disabled=false;
	document.getElementById('filtro_bt').style.pointerEvents="auto";
	document.getElementById('filtro_buscar_bt').hidden=true;

	document.getElementById('tipo_filtro_option_descricao').hidden=false;
	document.getElementById('filtro_descricao_block').hidden=false;	
	document.getElementById('filtro_descricao_tag').hidden=true;

	document.getElementById('tipo_filtro_option_periodo').hidden=false;
	document.getElementById('filtro_mes_block').hidden=true;	
	document.getElementById('filtro_ano_block').hidden=true;	
	document.getElementById('filtro_periodo_tag').hidden=true;		

	document.getElementById('tipo_filtro_option_tipo').hidden=false;
	document.getElementById('filtro_tipo_block').hidden=true;	
	document.getElementById('filtro_tipo_tag').hidden=true;	

	document.getElementById('descricao_filtro_input').value="";
	document.getElementById('mes_filtro_input').value=mes;
	document.getElementById('ano_filtro_input').value=ano;
	document.getElementById('tipo_filtro_input').value="FIXO";

	document.getElementById('input_descricao_backend').value="";
	document.getElementById('input_periodo_mes_backend').value="";
	document.getElementById('input_periodo_ano_backend').value="";
	document.getElementById('input_tipo_backend').value="";	
}

function addFiltro() {

	var filtroTipo = document.getElementById('filtro_input');
	var filtroBt = document.getElementById('filtro_bt');
	var buscarBt = document.getElementById('filtro_buscar_bt');

	var optionDescricao = document.getElementById('tipo_filtro_option_descricao');
	var descricaoBlock = document.getElementById('filtro_descricao_block');	
	var descricaoTag = document.getElementById('filtro_descricao_tag');

	var optionPeriodo = document.getElementById('tipo_filtro_option_periodo');
	var periodoMesBlock = document.getElementById('filtro_mes_block');	
	var periodoAnoBlock = document.getElementById('filtro_ano_block');	
	var periodoTag = document.getElementById('filtro_periodo_tag');		

	var optionTipo = document.getElementById('tipo_filtro_option_tipo');
	var tipoBlock = document.getElementById('filtro_tipo_block');	
	var tipoTag = document.getElementById('filtro_tipo_tag');	

	var inputDescricaoFiltro = document.getElementById('descricao_filtro_input');
	var inputMesFiltro = document.getElementById('mes_filtro_input');
	var inputAnoFiltro = document.getElementById('ano_filtro_input');
	var inputTipoFiltro = document.getElementById('tipo_filtro_input');

	var inputDescricaoBackend = document.getElementById('input_descricao_backend');
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputTipoBackend = document.getElementById('input_tipo_backend');

	if (filtroTipo.value == 'DESCRICAO') {
		if (inputDescricaoFiltro.value != "") {
			optionDescricao.hidden=true;
			descricaoBlock.hidden=true;
			descricaoTag.hidden=false;
			descricaoTag.innerText = "Descrição: " + inputDescricaoFiltro.value;
			filtroTipo.value="";
			inputDescricaoBackend.value=inputDescricaoFiltro.value;

			filtroBt.hidden=true;
			buscarBt.hidden=false;
			filtroTipo.style.border="1px solid grey";
			filtroTipo.disabled=true;
			filtroBt.disabled=true;
			filtroBt.style.pointerEvents="none";				
		}
	}
	else if (filtroTipo.value == 'PERIODO') {
		optionPeriodo.hidden=true;

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
	else if (filtroTipo.value == 'TIPO') {
		optionTipo.hidden=true;
		tipoBlock.hidden=true;

		tipoTag.hidden=false;
		tipoTag.innerText = 'Tipo: ' + inputTipoFiltro.value;

		filtroTipo.value="";
		inputTipoBackend.value=inputTipoFiltro.value;

		filtroBt.hidden=true;
		buscarBt.hidden=false;
		filtroTipo.style.border="1px solid grey";
		filtroTipo.disabled=true;
		filtroBt.disabled=true;
		filtroBt.style.pointerEvents="none";		
	}					
}

function removerFiltro(filtro) {

	var filtroTipo = document.getElementById('filtro_input');
	var filtroBt = document.getElementById('filtro_bt');
	var buscarBt = document.getElementById('filtro_buscar_bt');

	var optionDescricao = document.getElementById('tipo_filtro_option_descricao');
	var descricaoBlock = document.getElementById('filtro_descricao_block');	
	var descricaoTag = document.getElementById('filtro_descricao_tag');

	var optionPeriodo = document.getElementById('tipo_filtro_option_periodo');
	var periodoMesBlock = document.getElementById('filtro_mes_block');	
	var periodoAnoBlock = document.getElementById('filtro_ano_block');	
	var periodoTag = document.getElementById('filtro_periodo_tag');		

	var optionTipo = document.getElementById('tipo_filtro_option_tipo');
	var tipoBlock = document.getElementById('filtro_tipo_block');	
	var tipoTag = document.getElementById('filtro_tipo_tag');	

	var inputDescricaoBackend = document.getElementById('input_descricao_backend');
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputTipoBackend = document.getElementById('input_tipo_backend');	
	
	filtroTipo.style.border="1px solid #949393";
	filtroTipo.disabled=false;
	filtroBt.disabled=false;
	filtroBt.style.pointerEvents="auto";	

	if (filtro == 'descricao') {		

		optionDescricao.hidden=false;

		descricaoBlock.hidden=false;

		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;
		tipoBlock.hidden=true;

		descricaoTag.hidden=true;

		filtroTipo.value="DESCRICAO";

		inputDescricaoBackend.value="";
	}
	else if (filtro == 'periodo') {
		optionPeriodo.hidden=false;

		descricaoBlock.hidden=true;
		periodoMesBlock.hidden=false;
		periodoAnoBlock.hidden=false;
		tipoBlock.hidden=true;

		periodoTag.hidden=true;
		filtroTipo.value="PERIODO";

		inputMesBackend.value="";
		inputAnoBackend.value="";
	}
	else if (filtro == 'tipo') {
		optionTipo.hidden=false;

		descricaoBlock.hidden=true;

		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;
		tipoBlock.hidden=false;

		tipoTag.hidden=true;

		filtroTipo.value="TIPO";

		inputTipoBackend.value="";
	}	

	buscarBt.hidden=true;		
	filtroBt.hidden=false;
}

function efeitoRemoverFiltro(filtro) {

	var filtroDescricao = document.getElementById('filtro_descricao_tag');
	var filtroPeriodo = document.getElementById('filtro_periodo_tag');	
	var filtroTipo = document.getElementById('filtro_tipo_tag');	


	if (filtro == 'descricao') {
		filtroDescricao.style.transition="0.5s"
		filtroDescricao.style.background="#AA3C3C";
		filtroDescricao.style.border="1px solid #AA3C3C";
		filtroDescricao.style.color="#212121";
		filtroDescricao.innerText="Remover";
		filtroDescricao.style.cursor="pointer";

	}	
	else if (filtro == 'periodo') {
		filtroPeriodo.style.transition="0.5s"
		filtroPeriodo.style.background="#AA3C3C";
		filtroPeriodo.style.border="1px solid #AA3C3C";
		filtroPeriodo.style.color="#212121";
		filtroPeriodo.innerText="Remover";
		filtroPeriodo.style.cursor="pointer";
	}
	else if (filtro == 'tipo') {
		filtroTipo.style.transition="0.5s"
		filtroTipo.style.background="#AA3C3C";
		filtroTipo.style.border="1px solid #AA3C3C";
		filtroTipo.style.color="#212121";
		filtroTipo.innerText="Remover";
		filtroTipo.style.cursor="pointer";
	}			
}

function efeitoRemoverFiltroLeave(filtro) {

	var filtroDescricao = document.getElementById('filtro_descricao_tag');
	var filtroPeriodo = document.getElementById('filtro_periodo_tag');	
	var filtroTipo = document.getElementById('filtro_tipo_tag');		

	var inputDescricaoBackend = document.getElementById('input_descricao_backend');
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputTipoBackend = document.getElementById('input_tipo_backend');	

	if (filtro == 'descricao') {
		filtroDescricao.style.transition="1s"
		filtroDescricao.style.background="transparent";
		filtroDescricao.style.border="1px solid #212121"
		filtroDescricao.style.color="#212121";
		filtroDescricao.innerText="Descrição";
		filtroDescricao.innerText = 'Descrição: ' + inputDescricaoBackend.value;

	}
	else if (filtro == 'periodo') {
		filtroPeriodo.style.transition="1s"
		filtroPeriodo.style.background="transparent";
		filtroPeriodo.style.border="1px solid #212121"
		filtroPeriodo.style.color="#212121";
		filtroPeriodo.innerText = 'Mês ' + inputMesBackend.value + ' de ' + inputAnoBackend.value;
	}
	else if (filtro == 'tipo') {
		filtroTipo.style.transition="1s"
		filtroTipo.style.background="transparent";
		filtroTipo.style.border="1px solid #212121"
		filtroTipo.style.color="#212121";
		filtroTipo.innerText = 'Tipo: ' + inputTipoBackend.value;
	}		
}

/* ================== CONFIGURAÇÕES DA SUB-TELA EDITA ITEM ====================== */

function abrirEditaItem(
							id, 
							dataCadastro, 
							nome, 
							tipoPatrimonio, 
							statusPatrimonio, 
							dataEntrada, 
							valor, 
							usuarioResponsavel) {

	const d = new Date();
	var ano = d.getFullYear();
	var mes = d.getMonth()+1;
	var dia = d.getDate();

	if(mes < 10) {
		var mes = '0' + mes;
	}

	var hoje = (ano + '-' + mes + '-' + dia); 

	var containerPrincipal = document.getElementById('conteudo_container');
	var menuSuperior = document.getElementById('menu_superior');
	var menuSuperiorMobile = document.getElementById('menu_superior_mobile');
	var sideMenu = document.getElementById('side_menu');

	var containerEdita = document.getElementById('conteudo_container_edita');

	var subtitulo = document.getElementById('patrimonio_info');

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

	// CONVERTENDO DATA PARA PADRÃO BR
	if (dataCadastro != null) {
		var dataCadastroSplitada = dataCadastro.split("-");
		if (dataCadastroSplitada.length == 3) {
			var dataUsParaDataBr = dataCadastroSplitada[2] + "-" + dataCadastroSplitada[1] + "-" + dataCadastroSplitada[0];
			subtitulo.title="Patrimônio salvo dia " + dataUsParaDataBr + " por " + usuarioResponsavel;
		}	
	}

	// SETANDO VALORES NOS CAMPOS
	document.getElementById('id_input_edicao').value=id;
	document.getElementById('tipo_patrimonio_input_edicao').value=tipoPatrimonio;
	document.getElementById('descricao_patrimonio_input_edicao').value=nome;
	document.getElementById('valor_patrimonio_input_edicao').value=valor;
	document.getElementById('status_patrimonio_edicao').value=statusPatrimonio;
	document.getElementById('data_pagamento_input_edicao').value=dataEntrada;
}

function fecharEditaItem() {

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

	var containerPrincipal = document.getElementById('conteudo_container');
	var menuSuperior = document.getElementById('menu_superior');
	var menuSuperiorMobile = document.getElementById('menu_superior_mobile');
	var sideMenu = document.getElementById('side_menu');	

	var containerEdita = document.getElementById('conteudo_container_edita');	

	var descricaoPatrimonioInput = document.getElementById('descricao_patrimonio_input_edicao');
	var valorPatrimonioInput = document.getElementById('valor_patrimonio_input_edicao');
	var tipoPatrimonioInput = document.getElementById('tipo_patrimonio_input_edicao');
	var statusPatrimonioInput = document.getElementById('status_patrimonio_edicao');
	var dataEntradaInput = document.getElementById('data_pagamento_input_edicao');

	resetCores('edita');
	descricaoPatrimonioInput.value="";
	valorPatrimonioInput.value=0;
	statusPatrimonioInput.value="PAGO";
	tipoPatrimonioInput.value="ATIVO";

	dataEntradaInput.style.color="#121212";
	dataEntradaInput.disabled=false;
	dataEntradaInput.value=hoje;	
	
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

function editaItemChangeStatus() {

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

	var statusPatrimonio = document.getElementById('status_patrimonio_edicao');
	var dataEntradaInput = document.getElementById('data_pagamento_input_edicao');	

	if (statusPatrimonio.value == "PAGO") {
		dataEntradaInput.style.color="#121212";
		dataEntradaInput.disabled=false;
		dataEntradaInput.value=hoje;
	}
	else {
		dataEntradaInput.style.color="#4444";
		dataEntradaInput.value="";
		dataEntradaInput.disabled=true;
	}	

	validacaoDoObjetoPatrimonio(false, 'edita');	
}

function editaItemChangeTipo(tipo) {
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

	var statusPatrimonio = document.getElementById('status_patrimonio_edicao');
	var tipoPatrimonio = document.getElementById('tipo_patrimonio_input_edicao');
	var dataEntradaInput = document.getElementById('data_pagamento_input_edicao');	

	if (tipoPatrimonio.value == "ATIVO") {
		statusPatrimonio.value="PAGO";
		dataEntradaInput.style.color="#121212";
		dataEntradaInput.disabled=false;
		dataEntradaInput.value=hoje;
	}
	else {
		statusPatrimonio.value="PENDENTE";
		dataEntradaInput.style.color="#4444";
		dataEntradaInput.value="";
		dataEntradaInput.disabled=true;
	}

	validacaoDoObjetoPatrimonio(false, 'edita');
}

/* ================== MISC ====================== */

function buildUrlPages() {

	var paginaAtual = document.getElementById('pagina_atual');
	var tipoFiltro = document.getElementById('tipo_filtro');

	var descricao = document.getElementById('back_descricao');
	var periodoMes = document.getElementById('back_mes');
	var periodoAno = document.getElementById('back_ano');	
	var tipo = document.getElementById('back_tipo');		

	var pageNumber = document.getElementsByClassName('page_number');

	if(tipoFiltro != null) {
		if(tipoFiltro.value == 'descricao') {

			$('#anterior').attr("href", "/patrimonios?page=" + (parseInt(paginaAtual.value) - 1)  + "&descricao=" + descricao.value);
			$('#proxima').attr("href", "/patrimonios?page=" + (parseInt(paginaAtual.value) + 1)  + "&descricao=" + descricao.value);

			for (var i = 0; i < pageNumber.length; i ++) {
				pageNumber[i].id="numeroPagina_" + i;
				var idPagina = pageNumber[i].id;
				$('#' + idPagina).attr("href", "/patrimonios?page=" + 
					(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&descricao=" + descricao.value);
			}

		}

		else if(tipoFiltro.value == 'periodo') {

			$('#anterior').attr("href", "/patrimonios?page=" + (parseInt(paginaAtual.value) - 1)  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value);
			$('#proxima').attr("href", "/patrimonios?page=" + (parseInt(paginaAtual.value) + 1)  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value);

			
			for (var i = 0; i < pageNumber.length; i ++) {
				pageNumber[i].id="numeroPagina_" + i;
				var idPagina = pageNumber[i].id;
				$('#' + idPagina).attr("href", "/patrimonios?page=" + 
					(parseInt(pageNumber[i].getAttribute('data-numeroPagina')))  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value);
			}

		}

		else if(tipoFiltro.value == 'tipo') {

			$('#anterior').attr("href", "/patrimonios?page=" + (parseInt(paginaAtual.value) - 1)  + "&tipo=" + tipo.value);
			$('#proxima').attr("href", "/patrimonios?page=" + (parseInt(paginaAtual.value) + 1)  + "&tipo=" + tipo.value);

			for (var i = 0; i < pageNumber.length; i ++) {
				pageNumber[i].id="numeroPagina_" + i;
				var idPagina = pageNumber[i].id;
				$('#' + idPagina).attr("href", "/patrimonios?page=" + 
					(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&tipo=" + tipo.value);
			}

		}

		else {

			$('#anterior').attr("href", "/patrimonios?page=" + (parseInt(paginaAtual.value) - 1));
			$('#proxima').attr("href", "/patrimonios?page=" + (parseInt(paginaAtual.value) + 1));

			for (var i = 0; i < pageNumber.length; i ++) {
				pageNumber[i].id="numeroPagina_" + i;
				var idPagina = pageNumber[i].id;
				$('#' + idPagina).attr("href", "/patrimonios?page=" + 
					(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))));
			}		
		}
	}

}

function buildUrl(baseUrl, pagina, descricao, tipo, mes, ano) {

	var url = baseUrl + "?page=" + pagina;
	var paginaList = document.getElementById('pagina_list');

	if (descricao != null) {
		baseUrl + "&descricao=" + descricao;
	}		
	else if (tipo != null) {
		baseUrl + "&tipo=" + tipo;
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

function ajustaTabela(){

	var informativoValorCaixa = document.getElementById('informativo_valor_caixa');

	if (informativoValorCaixa != null) {
		if (informativoValorCaixa.innerText.replace("R$ ", "") < 0.00) {
			informativoValorCaixa.style.color="#D75353";
		}
		else {
			informativoValorCaixa.style.color="#C3C8C8";
		}
	}

	// Definindo propriedades
	var line = document.getElementsByClassName('tr');
	var columnStatus = document.getElementsByClassName('td_status');
	var columnValor = document.getElementsByClassName('td_valor');

	for(var i = 0; i < line.length; i++) {

		if (!columnValor[i].innerText.includes('R$') && !columnValor[i].innerText.includes('...')) {
			columnValor[i].innerText=
				parseFloat(columnValor[i].innerText).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
		}		

		if (columnStatus[i].innerText == "Pendente") {
			line[i].style.borderLeft="4px solid #f20a0a";
		}
		else if (columnStatus[i].innerText == "OK") {
			line[i].style.borderLeft="4px solid #5eff00";
		}		

	}
}

function pageResponsiva(){
	if (document.getElementById('pegando_page') != null) {
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
}

function doALoadOfStuff() {
	document.getElementById('conteudo_container').style.transition="2s";
	responsive();
}

function tituloResponsivo(filtro) {

	var titulo = document.getElementById('conteudo_titulo_text');
	var mes = document.getElementById('back_mes');
	var ano = document.getElementById('back_ano');	
	var descricao = document.getElementById('back_descricao');		
	var tipo = document.getElementById('back_tipo');			

	if(filtro == "mesAtual") {
		titulo.innerText="Patrimônios desse mês";
	}

	else if (filtro == "periodo") {
		titulo.innerText="Patrimônios: " + mes.value + "/" + ano.value;
	}
	else if (filtro == "descricao") {
		titulo.innerText="Patrimônios de nome " + descricao.value;
	}
	else if (filtro == "tipo") {
		titulo.innerText="Despesas: " + (tipo.value).toLowerCase() + "s";
	}

}