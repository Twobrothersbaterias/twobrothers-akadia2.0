/* =========================== RESPONSIVIDADE DA TELA =========================== */

window.onload = responsive();
window.onresize = doALoadOfStuff;
buildUrlPages();
buildUrlRelatorio();

var privilegio = document.getElementById('body').getAttribute('data-privilegio');
var tipoFiltro = document.getElementById('tipo_filtro').value;

tituloResponsivo(tipoFiltro);

console.log('Privilégio: ' + privilegio);
console.log('Tipo do filtro: ' + tipoFiltro);

document.onkeydown=function(){

	var keyCode = window.event.keyCode;
	bind(keyCode);

}

function bind(keyCode) {

    if(keyCode == '27') {
        fecharFiltro();
    }

    if(keyCode == '13') {
		 if (document.getElementById('conteudo_container_filtro').hidden==false) {

		    var btAdd = document.getElementById('filtro_bt');
		    var btBuscar = document.getElementById('filtro_buscar_bt_inside');

		    if(btAdd.hidden == false) {
				btAdd.click();
			}
			else {
				btBuscar.click();
			}

		}
  
	}    

    if (document.getElementById('conteudo_container_filtro').hidden==true) {

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
	    	window.location.href="/lancamento";
	    }

	    else if(keyCode == '82') {
	    	window.location.href="/vendas";
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
	var novoTh = document.getElementsByClassName('novo_th');
	var novoTd = document.getElementsByClassName('novo_td');	
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

		conteudoTituloText.style.fontSize="1.1rem";

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

		filtroTitulo.style.fontSize="1.1rem";

		conteudoContainer.style.marginTop="30px";
		hrTabela.style.marginBottom="25px";			

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

		for(var i = 0; i < novoTh.length; i++){
			novoTh[i].style.fontSize="1rem";
		}
		for(var i = 0; i < novoTd.length; i++){
			novoTd[i].style.fontSize="1rem";
		}		

		informativoRow.style.marginBottom="20px";		
		
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

		conteudoTituloText.style.fontSize="1.1rem";
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

		filtroTitulo.style.fontSize="1.1rem";

		conteudoContainer.style.marginTop="30px";
		hrTabela.style.marginBottom="25px";			

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

		informativoRow.style.marginBottom="20px";		

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
			btnExcluir[i].style.fontSize="0.65rem";
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
		hrTabela.style.marginBottom="25px";			

		for(var i = 0; i < thUsuario.length; i++) {
			thUsuario[i].style.display="none";
		}

		for(var i = 0; i < tdUsuario.length; i++) {
			tdUsuario[i].style.display="none";
		}		

		conteudoTituloText.style.fontSize="1rem";
		menuMobile.style.display="none";

		if (informativoRow != null) {
			informativoRow.style.justifyContent="center";
		}

		if(listaVaziaTitulo != null) {
			listaVaziaTitulo.style.fontSize="1.5rem";
			listaVaziaTexto.style.fontSize="1rem";
			listaVaziaBotao.style.fontSize="1rem";
		}

		filtroTitulo.style.fontSize="1.1rem";	

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.60rem";	
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.60rem";
		}	

		informativoRow.style.marginBottom="20px";		

		for(var i = 0; i < informativo.length; i++){
			informativo[i].style.fontSize="0.70rem";
			informativo[i].style.width="40%";	
			informativo[i].style.margin="7px";					
		}			
		for(var i = 0; i < liA.length; i++){
			liA[i].style.fontSize="0.60rem";
			liA[i].style.padding="5px 10px";			
		}		
		for(var i = 0; i < formRemoveImg.length; i++) {
			formRemoveImg[i].style.display="block";
			formRemoveImg[i].style.maxWidth="42%";			
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

		for(var i = 0; i < btnExcluir.length; i++) {
			btnExcluir[i].style.display="none";
		}	
		for(var i = 0; i < filtroBlock.length; i++) {
			filtroBlock[i].style.marginBottom="20px";
		}

		filtroBuscarBt.style.marginTop="20px";
		filtroBuscarBt.style.justifyContent="center";	
	}
	else if(bodyWidth <= 768 && bodyWidth > 540){
		console.log('Tela: Pequeno');	

		conteudoTituloText.style.fontSize="0.90rem";
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

		filtroTitulo.style.fontSize="1.1rem";

		conteudoContainer.style.marginTop="5px";
		hrTabela.style.marginBottom="15px";	
		hrTabela.style.marginTop="10px";	

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

		informativoRow.style.marginBottom="15px";
				
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

		for(var i = 0; i < btnExcluir.length; i++) {
			btnExcluir[i].style.display="none";
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
		console.log('Tela: Muito pequeno');

		conteudoTituloText.style.fontSize="0.80rem";
		sideMenu.style.display="none";
		main.style.width="100%";
		mainRow.style.width = "100%";		
		menuMobile.style.display="flex";

		filtroTitulo.style.fontSize="1.1rem";

		if(listaVaziaTitulo != null) {
			listaVaziaTitulo.style.fontSize="1.2rem";
			listaVaziaTexto.style.fontSize="0.90rem";
			listaVaziaBotao.style.fontSize="0.90rem";
		}

		conteudoContainer.style.marginTop="5px";
		hrTabela.style.marginTop="10px";	
		hrTabela.style.marginBottom="15px";			

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

		informativoRow.style.marginBottom="8px";

		for(var i = 0; i < informativo.length; i++){
			informativo[i].style.fontSize="0.55rem";
			informativo[i].style.width="46%";
			informativo[i].style.margin="5px auto";	
		}	
		for(var i = 0; i < liA.length; i++){
			liA[i].style.fontSize="0.60rem";
			liA[i].style.padding="5px 10px";
		}
		for(var i = 0; i < formRemoveImg.length; i++) {
			formRemoveImg[i].style.display="block";
			formRemoveImg[i].style.maxWidth="55%";				
		}

		for(var i = 0; i < img.length; i++) {
			img[i].style.width="15px";
		}

		for(var i = 0; i < aImg.length; i++) {
			aImg[i].style.marginLeft="8px";
		}

		for(var i = 0; i < imgContainer.length; i++) {
			imgContainer[i].style.width="20px";
			imgContainer[i].style.marginLeft="15px";			
		}

		for (var i = 0; i < pageClick.length; i++) {
			pageClick[i].style.fontSize="0.50rem";
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
	ajustaTabela();
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

	var bairro = document.getElementById('filtro_bairro_block');
	var bairroInput = document.getElementById('bairro_filtro_input');	

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

		bairroInput.value="";
		bairro.hidden=true;

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

		bairroInput.value="";
		bairro.hidden=true;
		
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

		bairroInput.value="";
		bairro.hidden=true;
		
	}
	else if (filtroTipo.value == 'BAIRRO') {

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

		bairro.hidden=false;
		
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

	document.getElementById('tipo_filtro_option_periodo').hidden=false;
	document.getElementById('filtro_mes_block').hidden=true;	
	document.getElementById('filtro_ano_block').hidden=true;	
	document.getElementById('filtro_periodo_tag').hidden=true;		

	document.getElementById('tipo_filtro_option_bairro').hidden=false;
	document.getElementById('filtro_bairro_block').hidden=true;	
	document.getElementById('filtro_bairro_tag').hidden=true;	

	document.getElementById('produto_filtro_input').value="";
	document.getElementById('mes_filtro_input').value=mes;
	document.getElementById('ano_filtro_input').value=ano;
	document.getElementById('bairro_filtro_input').value="";

	document.getElementById('input_produto_backend').value="";
	document.getElementById('input_periodo_mes_backend').value="";
	document.getElementById('input_periodo_ano_backend').value="";
	document.getElementById('input_bairro_backend').value="";	

	document.getElementById('tipo_filtro_option_data').hidden=false;
	document.getElementById('filtro_data_inicio_block').hidden=true;
	document.getElementById('data_inicio_filtro_input').value=data;
	document.getElementById('input_data_inicio_backend').value="";
	document.getElementById('filtro_data_fim_block').hidden=true;	
	document.getElementById('data_fim_filtro_input').value=data;
	document.getElementById('input_data_fim_backend').value="";
	document.getElementById('filtro_data_tag').hidden=true;	
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

	var optionBairro = document.getElementById('tipo_filtro_option_bairro');
	var bairroBlock = document.getElementById('filtro_bairro_block');	
	var bairroTag = document.getElementById('filtro_bairro_tag');	

	var inputProdutoFiltro = document.getElementById('produto_filtro_input');
	var inputDataInicioFiltro = document.getElementById('data_inicio_filtro_input');
	var inputDataFimFiltro = document.getElementById('data_fim_filtro_input');		
	var inputMesFiltro = document.getElementById('mes_filtro_input');
	var inputAnoFiltro = document.getElementById('ano_filtro_input');
	var inputBairroFiltro = document.getElementById('bairro_filtro_input');

	var inputProdutoBackend = document.getElementById('input_produto_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');	
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputBairroBackend = document.getElementById('input_bairro_backend');

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
	else if (filtroTipo.value == 'BAIRRO') {
		optionBairro.hidden=true;
		bairroBlock.hidden=true;

		bairroTag.hidden=false;
		bairroTag.innerText = 'Bairro: ' + inputBairroFiltro.value;

		filtroTipo.value="";
		inputBairroBackend.value=inputBairroFiltro.value;

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

	var optionBairro = document.getElementById('tipo_filtro_option_bairro');
	var bairroBlock = document.getElementById('filtro_bairro_block');	
	var bairroTag = document.getElementById('filtro_bairro_tag');	

	var inputProdutoBackend = document.getElementById('input_produto_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');	
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputBairroBackend = document.getElementById('input_bairro_backend');	
	
	filtroTipo.style.border="1px solid #949393";
	filtroTipo.disabled=false;
	filtroBt.disabled=false;
	filtroBt.style.pointerEvents="auto";	

	if (filtro == 'produto') {		

		optionProduto.hidden=false;

		produtoBlock.hidden=false;

		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;
		bairroBlock.hidden=true;
		dataInicioBlock.hidden=true;
		dataFimBlock.hidden=true;		

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
		bairroBlock.hidden=true;

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
		bairroBlock.hidden=true;

		periodoTag.hidden=true;
		filtroTipo.value="PERIODO";

		inputMesBackend.value="";
		inputAnoBackend.value="";
	}
	else if (filtro == 'bairro') {
		optionBairro.hidden=false;

		produtoBlock.hidden=true;
		dataInicioBlock.hidden=true;
		dataFimBlock.hidden=true;
		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;
		bairroBlock.hidden=false;

		bairroTag.hidden=true;

		filtroTipo.value="BAIRRO";

		inputBairroBackend.value="";
	}	

	buscarBt.hidden=true;		
	filtroBt.hidden=false;
}

function efeitoRemoverFiltro(filtro) {

	var filtroProduto = document.getElementById('filtro_produto_tag');
	var filtroPeriodo = document.getElementById('filtro_periodo_tag');	
	var filtroBairro = document.getElementById('filtro_bairro_tag');
	var filtroData = document.getElementById('filtro_data_tag');			


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
	else if (filtro == 'bairro') {
		filtroBairro.style.transition="0.5s"
		filtroBairro.style.background="#AA3C3C";
		filtroBairro.style.border="1px solid #AA3C3C";
		filtroBairro.style.color="#212121";
		filtroBairro.innerText="Remover";
		filtroBairro.style.cursor="pointer";
	}			
}

function efeitoRemoverFiltroLeave(filtro) {

	var filtroProduto = document.getElementById('filtro_produto_tag');
	var filtroData = document.getElementById('filtro_data_tag');	
	var filtroPeriodo = document.getElementById('filtro_periodo_tag');	
	var filtroBairro = document.getElementById('filtro_bairro_tag');		

	var inputProdutoBackend = document.getElementById('input_produto_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');	
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputBairroBackend = document.getElementById('input_bairro_backend');	

	if (filtro == 'produto') {
		filtroProduto.style.transition="1s"
		filtroProduto.style.background="transparent";
		filtroProduto.style.border="1px solid #212121"
		filtroProduto.style.color="#212121";
		filtroProduto.innerText="Descrição";
		filtroProduto.innerText = 'Produto: ' + inputProdutoBackend.value;
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
	else if (filtro == 'bairro') {
		filtroBairro.style.transition="1s"
		filtroBairro.style.background="transparent";
		filtroBairro.style.border="1px solid #212121"
		filtroBairro.style.color="#212121";
		filtroBairro.innerText = 'Bairro: ' + inputBairroBackend.value;
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
	var dataCadastroSplitada = dataCadastro.split("-");
	if (dataCadastroSplitada.length == 3) {
		var dataUsParaDataBr = dataCadastroSplitada[2] + "-" + dataCadastroSplitada[1] + "-" + dataCadastroSplitada[0];
		subtitulo.title="Patrimônio salvo dia " + dataUsParaDataBr + " por " + usuarioResponsavel;
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

	descricaoPatrimonioInput.value="";
	valorPatrimonioInput.value="";
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
}

function editaItemChangeTipo() {
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
}

/* ============================ AJUSTES DA TABELA ================================ */

function ajustaTabela(){

	ajustaCampos();
	ajustaDatas();
	ajustaUrlEdicao();

}

function ajustaCampos() {

	var trContent = document.getElementsByClassName('tr_content');
	var ordemId = null;

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
	var hojeBarreado = (dia + '/' + mes + '/' + ano); 

	for(var i = 0; i < trContent.length; i++) {

		var totalEntradas = 0.0;
		var nomeAtualizado = "";

		ordemId = trContent[i].getAttribute('data-ordemId');	

		var entradaValorCampo = document.getElementsByClassName('entradas_valor_' + ordemId);
		var entradaQuantidadeCampo = document.getElementsByClassName('entradas_quantidade_' + ordemId);
		var entradaNome = document.getElementsByClassName('entradas_nome_' + ordemId);
		var entradaTipo = document.getElementsByClassName('entradas_tipo_' + ordemId);

		// Formatação do campo valor
		for(var j = 0; j < entradaValorCampo.length; j++) {
			totalEntradas += parseFloat(entradaValorCampo[j].innerText);
		}		

		// Formatação do campo entradas
		for(var k = 0; k < entradaNome.length; k++) {

			if (entradaNome[k].innerText == "..." && entradaTipo[k].innerText == "PADRAO_SERVICO") {
				if (nomeAtualizado == "") {
					nomeAtualizado = nomeAtualizado + "Serviço";				
				}
				else {
					nomeAtualizado = nomeAtualizado + ", Serviço";
				}
			}
			else if (entradaNome[k].innerText == "..." && entradaTipo[k].innerText != "PADRAO_SERVICO") {
				if (nomeAtualizado == "") {
					nomeAtualizado = nomeAtualizado + "Vazio";
				}
				else {
					nomeAtualizado = nomeAtualizado 
					+ ", Vazio";
				}				
			}
			else {
				if(entradaTipo[k].innerText == "PADRAO_PRODUTO") {
					if (nomeAtualizado == "") {
						nomeAtualizado = nomeAtualizado 
						+ entradaNome[k].innerText ;
					}
					else {
						nomeAtualizado = nomeAtualizado 
						+ ", " 
						+ entradaNome[k].innerText;
					}
				}	
				else if(entradaTipo[k].innerText == "GARANTIA"){
					if (nomeAtualizado == "") {
						nomeAtualizado = nomeAtualizado 
						+ entradaNome[k].innerText + ' (Garantia)' ;
					}
					else {
						nomeAtualizado = nomeAtualizado 
						+ ", " 
						+ entradaNome[k].innerText + ' (Garantia)';
					}					
				}
			}
		}

		if (document.getElementsByClassName('td_status')[i].innerText == 'Loja física') {
			trContent[i].style.borderLeft="4px solid #5eff00";
			document.getElementsByClassName('td_status')[i].style.color="#5eff00";
			document.getElementsByClassName('td_status')[i].innerText = "Loja física";
		}
		else if (document.getElementsByClassName('td_status')[i].innerText == 'Entrega - em trânsito' 
			|| document.getElementsByClassName('td_status')[i].innerText == 'Em trânsito') {

			var agendamentoRetirada = document.getElementById('agendamentoRetirada_' + ordemId);
			var agendamentoRetiradaBarreado = 
				agendamentoRetirada.innerText.split("-")[2] + "/"
				+ agendamentoRetirada.innerText.split("-")[1] + "/"
				+ agendamentoRetirada.innerText.split("-")[0];

			if(agendamentoRetirada.innerText != "Não possui" 
				&& agendamentoRetirada.innerText != "Sem agendamento"
				&& agendamentoRetirada.innerText != "Em aberto") {
				// ENTREGAR HOJE
				if(compareDates(hojeBarreado, agendamentoRetiradaBarreado) == "hoje" || agendamentoRetirada.innerText == "Entregar hoje") {
					document.getElementsByClassName('td_status')[i].innerText = "Entregar hoje";
					document.getElementsByClassName('td_status')[i].style.color="#ff5900";
					trContent[i].style.borderLeft="4px solid #ff5900";			
				}
				// ATRASADO
				else if(compareDates(hojeBarreado, agendamentoRetiradaBarreado) == "atrasado" || agendamentoRetirada.innerText == "Atrasado") {
					document.getElementsByClassName('td_status')[i].innerText = "Entrega atrasada";
					document.getElementsByClassName('td_status')[i].style.color="#f20a0a";
					trContent[i].style.borderLeft="4px solid #f20a0a";			
				}	
				// AGENDADO
				else if(compareDates(hojeBarreado, agendamentoRetiradaBarreado) == "agendado" || agendamentoRetirada.innerText == "agendado") {
					document.getElementsByClassName('td_status')[i].innerText = "Agendado";
					trContent[i].style.borderLeft="4px solid #ffdd00";
					document.getElementsByClassName('td_status')[i].style.color="#ffdd00";
				}					
			}
			else {
				document.getElementsByClassName('td_status')[i].innerText = "Em trânsito";
				trContent[i].style.borderLeft="4px solid #d645d9";		
				document.getElementsByClassName('td_status')[i].style.color="#d645d9";		
			}

		}
		else if (document.getElementsByClassName('td_status')[i].innerText == 'Entrega - entregue' 
			|| document.getElementsByClassName('td_status')[i].innerText == 'Entregue') {
			document.getElementsByClassName('td_status')[i].innerText = "Entregue";
			trContent[i].style.borderLeft="4px solid #5eff00";
			document.getElementsByClassName('td_status')[i].style.color="#5eff00";
		}		



		if(ordemId != null) {
			document.getElementById('td_total_' + ordemId).innerText=totalEntradas.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
			document.getElementById('td_entradas_' + ordemId).innerText=nomeAtualizado;
		}

	}	
}

function compareDates (d1, d2) {

	if(d1 != null && d2 != null && d2.split("/").length == 3) {
		var hojeSplittado = d1.split("/");
		var agendamentosplittado = d2.split("/");
		// SE O ANO DO AGENDAMENTO FOR MAIOR OU IGUAL DO QUE O DE HOJE
		if(agendamentosplittado[2] >= hojeSplittado[2]) {
			// SE O MÊS DO AGENDAMENTO FOR MAIOR OU IGUAL DO QUE O DE HOJE
			if(agendamentosplittado[1] >= hojeSplittado[1]) {
				// SE O DIA DO AGENDAMENTO FOR MAIOR DO QUE O DIA DE HOJE
				if (agendamentosplittado[0] > hojeSplittado[0]) {
					return "agendado";
				}
				//SE O DIA DO AGENDAMENTO FOR IGUAL AO DIA DE HOJE
				else if(agendamentosplittado[0] == hojeSplittado[0]) {
					return "hoje";
				}
				// SE O DIA DO AGENDAMENTO FOR MENOR DO QUE O DIA DE HOJE
				else {
					return "atrasado";
				}
			}
			// SE O MÊS DO AGENDAMENTO FOR MENOR DO QUE O MÊS ATUAL
			else {
				return "atrasado";
			}
		}
		// SE O ANO DO AGENDAMENTO FOR MENOR DO QUE O ANO ATUAL
		else {
			return "atrasado";
		}
	}

}

function ajustaDatas() {

	var dataCadastro = document.getElementsByClassName('td_data');

	for(var i = 0; i < dataCadastro.length; i++) {
	
		var dataCadastroSplitada = dataCadastro[i].innerText.split("-");
		if (dataCadastroSplitada.length == 3) {
			var dataUsParaDataBr = dataCadastroSplitada[2] + "/" + dataCadastroSplitada[1] + "/" + dataCadastroSplitada[0];
			dataCadastro[i].innerText=dataUsParaDataBr;
		}	

	}
}

function ajustaUrlEdicao() {

	var linhasTabela = document.getElementsByClassName('tr_content');	

	for (var i = 0; i < linhasTabela.length; i++) {

		var ordemId = linhasTabela[i].getAttribute('data-ordemId');

		var itensLinha = document.getElementsByClassName('td_' + ordemId);

		for (var j = 0; j < itensLinha.length; j++) {
			$(itensLinha[j]).attr("onClick", "editaVenda('/lancamento?id=" + ordemId + "')");
		}

	}

}

function editaVenda(url) {
	window.location.href=url;
}

/* ================== MISC ====================== */

function buildUrlPages() {

	var paginaAtual = document.getElementById('pagina_atual');
	var tipoFiltro = document.getElementById('tipo_filtro');

	var dataInicio = document.getElementById('back_inicio');
	var dataFim = document.getElementById('back_fim');
	var periodoMes = document.getElementById('back_mes');
	var periodoAno = document.getElementById('back_ano');	
	var produto = document.getElementById('back_produto');		
	var bairro = document.getElementById('back_bairro');			

	var pageNumber = document.getElementsByClassName('page_number');

	if(tipoFiltro.value == 'data') {

		$('#anterior').attr("href", "/vendas?page=" + (parseInt(paginaAtual.value) - 1)  + "&inicio=" + dataInicio.value + "&fim=" + dataFim.value);
		$('#proxima').attr("href", "/vendas?page=" + (parseInt(paginaAtual.value) + 1)  + "&inicio=" + dataInicio.value + "&fim=" + dataFim.value);


		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/vendas?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina')))  + "&inicio=" + dataInicio.value + "&fim=" + dataFim.value);
		}
	}

	else if(tipoFiltro.value == 'periodo') {

		$('#anterior').attr("href", "/vendas?page=" + (parseInt(paginaAtual.value) - 1)  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value);
		$('#proxima').attr("href", "/vendas?page=" + (parseInt(paginaAtual.value) + 1)  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value);

		
		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/vendas?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina')))  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value);
		}
	}

	else if(tipoFiltro.value == 'produto') {

		$('#anterior').attr("href", "/vendas?page=" + (parseInt(paginaAtual.value) - 1)  + "&produto=" + produto.value);
		$('#proxima').attr("href", "/vendas?page=" + (parseInt(paginaAtual.value) + 1)  + "&produto=" + produto.value);

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/vendas?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&produto=" + produto.value);
		}
	}

	else if(tipoFiltro.value == 'bairro') {

		$('#anterior').attr("href", "/vendas?page=" + (parseInt(paginaAtual.value) - 1)  + "&bairro=" + bairro.value);
		$('#proxima').attr("href", "/vendas?page=" + (parseInt(paginaAtual.value) + 1)  + "&bairro=" + bairro.value);

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/vendas?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&bairro=" + bairro.value);
		}					
	}

	else {
		$('#anterior').attr("href", "/vendas?page=" + (parseInt(paginaAtual.value) - 1));
		$('#proxima').attr("href", "/vendas?page=" + (parseInt(paginaAtual.value) + 1));

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/vendas?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))));
		}		
	}	
}

function buildUrl(baseUrl, pagina, produto, bairro, mes, ano) {

	var url = baseUrl + "?page=" + pagina;
	var paginaList = document.getElementById('pagina_list');

	if (produto != null) {
		baseUrl + "&produto=" + produto;
	}		
	else if (bairro != null) {
		baseUrl + "&bairro=" + bairro;
	}
	else if (mes != null && ano != null) {
		baseUrl + "&mes=" + mes + "&ano=" + ano;	
	}

	paginaList.href=baseUrl;
}

function hideMessage() {
	var alertas = document.getElementsByClassName('alert');
	for(var i = 0; i < alertas.length; i++){
		alertas[i].hidden=true;
	}
}

function pageResponsiva() {
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
	var dataInicio = document.getElementById('back_inicio');
	var dataFim = document.getElementById('back_fim');
	var mes = document.getElementById('back_mes');
	var ano = document.getElementById('back_ano');	
	var produto = document.getElementById('back_produto');		
	var bairro = document.getElementById('back_bairro');			

	if(filtro == "hoje") {
		titulo.innerText="Vendas hoje";
	}
	else if (filtro == "data") {
		var columnDataSplitted = dataInicio.value.split("-");
		var inicio = columnDataSplitted[2] + "/" + columnDataSplitted[1] + "/" + columnDataSplitted[0];		
		var columnDataSplitted = dataFim.value.split("-");
		var fim = columnDataSplitted[2] + "/" + columnDataSplitted[1] + "/" + columnDataSplitted[0];
		titulo.innerText="Vendas: " + inicio + " à " + fim;		
	}
	else if (filtro == "periodo") {
		titulo.innerText="Vendas: " + mes.value + "/" + ano.value;
	}
	else if (filtro == "produto") {
		titulo.innerText="Vendas de " + produto.value;
	}
	else if (filtro == "bairro") {
		titulo.innerText="Vendas em " + bairro.value;
	}

}

function buildUrlRelatorio() {

	if(document.getElementsByClassName('tr_spring').length > 0) {

		var tipoFiltro = document.getElementById('tipo_filtro');

		var dataInicio = document.getElementById('back_inicio');
		var dataFim = document.getElementById('back_fim');
		var periodoMes = document.getElementById('back_mes');
		var periodoAno = document.getElementById('back_ano');	
		var produto = document.getElementById('back_produto');		
		var bairro = document.getElementById('back_bairro');	

		var url = "/vendas/relatorio?"

		if(tipoFiltro.value == 'data') {
			url += "inicio=" + dataInicio.value + "&fim=" + dataFim.value;
		}

		else if(tipoFiltro.value == 'periodo') {
			url += "mes=" + periodoMes.value + "&ano=" + periodoAno.value;
		}

		else if(tipoFiltro.value == 'produto') {
			url += "produto=" + produto.value;
		}

		else if(tipoFiltro.value == 'bairro') {
			url += "bairro=" + bairro.value;
		}	

		$('#relatorio_button').attr("href", url);	

	}

	else {
		$('#relatorio_button').disabled=true;
	}

}