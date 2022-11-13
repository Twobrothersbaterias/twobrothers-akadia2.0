/* =========================== RESPONSIVIDADE DA TELA =========================== */

window.onload = responsive();
window.onresize = doALoadOfStuff;
ajustaMinMaxDosInputsData();
ajustaTabela();
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
	    	window.location.href="/colaboradores";
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

		conteudoTituloText.style.fontSize="1.2rem";
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

		document.getElementById('email_colaborador_input').style.marginBottom="10px";	
		document.getElementById('data_nascimento_input').style.marginBottom="10px";
	
		document.getElementById('edita_email_colaborador_input').style.marginBottom="10px";	
		document.getElementById('edita_data_nascimento_input').style.marginBottom="10px";

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

	setTimeout(() => {  $('#descricao_colaborador_input').focus(); }, 10);			
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
	document.getElementById('descricao_colaborador_input').value="";
	document.getElementById('descricao_colaborador_input').style.background="transparent";

	document.getElementById('email_colaborador_input').value="";
	document.getElementById('email_colaborador_input').style.background="transparent";

	document.getElementById('telefone_colaborador_input').value="";
	document.getElementById('telefone_colaborador_input').style.background="transparent";

	document.getElementById('data_nascimento_input').value="";

	document.getElementById('cpfCnpj_input').value="";
	document.getElementById('cpfCnpj_input').style.background="transparent";

	document.getElementById('privilegio_colaborador_input').value="VENDEDOR";
	document.getElementById('privilegio_colaborador_input').style.background="transparent";

	document.getElementById('username_colaborador_input').value="";
	document.getElementById('username_colaborador_input').style.background="transparent";

	document.getElementById('senha_colaborador_input').value="";
	document.getElementById('senha_colaborador_input').style.background="transparent";			

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

	setTimeout(() => {  $('#descricao_filtro_input').focus(); }, 10);		
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

	var dataInicio = document.getElementById('filtro_data_inicio_block');
	var dataInicioInput = document.getElementById('data_inicio_filtro_input');

	var dataFim = document.getElementById('filtro_data_fim_block');
	var dataFimInput = document.getElementById('data_fim_filtro_input');

	var mesBlock = document.getElementById('filtro_mes_block');
	var mesInput = document.getElementById('mes_filtro_input');

	var anoBlock = document.getElementById('filtro_ano_block');
	var anoInput = document.getElementById('ano_filtro_input');

	var usuarioBlock = document.getElementById('filtro_usuario_block');
	var usuarioInput = document.getElementById('usuario_filtro_input');

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

		dataInicioInput.value=data;
		dataInicio.hidden=true;

		dataFimInput.value=data;
		dataFim.hidden=true;

		mesInput.value=mes;
		mesBlock.hidden=true;

		anoInput.value=ano;
		anoBlock.hidden=true;

		usuarioInput.value="";
		usuarioBlock.hidden=true;

	}
	else if (filtroTipo.value == 'DATA') {

		descricaoInput.value="";
		descricaoBlock.hidden=true;

		dataInicioInput.value=data;
		dataInicio.hidden=false;

		dataFimInput.value=data;
		dataFim.hidden=false;

		mesInput.value=mes;
		mesBlock.hidden=true;

		anoInput.value=ano;
		anoBlock.hidden=true;

		usuarioInput.value="";
		usuarioBlock.hidden=true;

	}
	else if (filtroTipo.value == 'PERIODO') {

		descricaoInput.value="";
		descricaoBlock.hidden=true;

		dataInicioInput.value=data;
		dataInicio.hidden=true;

		dataFimInput.value=data;
		dataFim.hidden=true;

		mesInput.value=mes;
		mesBlock.hidden=false;

		anoInput.value=ano;
		anoBlock.hidden=false;

		usuarioInput.value="";
		usuarioBlock.hidden=true;
		
	}
	else if (filtroTipo.value == 'USUARIO') {

		descricaoInput.value="";
		descricaoBlock.hidden=true;

		dataInicioInput.value=data;
		dataInicio.hidden=true;

		dataFimInput.value=data;
		dataFim.hidden=true;

		mesInput.value=mes;
		mesBlock.hidden=true;

		anoInput.value=ano;
		anoBlock.hidden=true;

		usuarioBlock.hidden=false;
		
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
	document.getElementById('descricao_filtro_input').value="";
	document.getElementById('input_descricao_backend').value="";

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

	document.getElementById('tipo_filtro_option_usuario').hidden=false;
	document.getElementById('filtro_usuario_block').hidden=true;
	document.getElementById('filtro_usuario_tag').hidden=true;
	document.getElementById('usuario_filtro_input').value="";
	document.getElementById('input_usuario_backend').value="";	
}

function addFiltro() {

	var filtroTipo = document.getElementById('filtro_input');
	var filtroBt = document.getElementById('filtro_bt');
	var buscarBt = document.getElementById('filtro_buscar_bt');

	var optionDescricao = document.getElementById('tipo_filtro_option_descricao');
	var descricaoBlock = document.getElementById('filtro_descricao_block');	
	var descricaoTag = document.getElementById('filtro_descricao_tag');

	var optionData = document.getElementById('tipo_filtro_option_data');
	var dataInicioBlock = document.getElementById('filtro_data_inicio_block');	
	var dataFimBlock = document.getElementById('filtro_data_fim_block');	
	var dataTag = document.getElementById('filtro_data_tag');	

	var optionPeriodo = document.getElementById('tipo_filtro_option_periodo');
	var periodoMesBlock = document.getElementById('filtro_mes_block');	
	var periodoAnoBlock = document.getElementById('filtro_ano_block');	
	var periodoTag = document.getElementById('filtro_periodo_tag');		

	var optionusuario = document.getElementById('tipo_filtro_option_usuario');
	var usuarioBlock = document.getElementById('filtro_usuario_block');	
	var usuarioTag = document.getElementById('filtro_usuario_tag');	

	var inputDescricaoFiltro = document.getElementById('descricao_filtro_input');
	var inputDataInicioFiltro = document.getElementById('data_inicio_filtro_input');
	var inputDataFimFiltro = document.getElementById('data_fim_filtro_input');
	var inputMesFiltro = document.getElementById('mes_filtro_input');
	var inputAnoFiltro = document.getElementById('ano_filtro_input');
	var inputusuarioFiltro = document.getElementById('usuario_filtro_input');

	var inputDescricaoBackend = document.getElementById('input_descricao_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputusuarioBackend = document.getElementById('input_usuario_backend');

	if (filtroTipo.value == 'DESCRICAO') {
		if (inputDescricaoFiltro.value != "") {
			optionDescricao.hidden=true;
			descricaoBlock.hidden=true;
			descricaoTag.hidden=false;
			descricaoTag.innerText = "Nome: " + inputDescricaoFiltro.value;
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
	else if (filtroTipo.value == 'USUARIO') {
		if (inputusuarioFiltro.value != "") {		
			optionusuario.hidden=true;
			usuarioBlock.hidden=true;

			usuarioTag.hidden=false;
			usuarioTag.innerText = 'Usuario: ' + inputusuarioFiltro.value;

			filtroTipo.value="";
			inputusuarioBackend.value=inputusuarioFiltro.value;

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

	var optionDescricao = document.getElementById('tipo_filtro_option_descricao');
	var descricaoBlock = document.getElementById('filtro_descricao_block');	
	var descricaoTag = document.getElementById('filtro_descricao_tag');

	var optionData = document.getElementById('tipo_filtro_option_data');
	var dataInicioBlock = document.getElementById('filtro_data_inicio_block');	
	var dataFimBlock = document.getElementById('filtro_data_fim_block');	
	var dataTag = document.getElementById('filtro_data_tag');	

	var optionPeriodo = document.getElementById('tipo_filtro_option_periodo');
	var periodoMesBlock = document.getElementById('filtro_mes_block');	
	var periodoAnoBlock = document.getElementById('filtro_ano_block');	
	var periodoTag = document.getElementById('filtro_periodo_tag');		

	var optionusuario = document.getElementById('tipo_filtro_option_usuario');
	var usuarioBlock = document.getElementById('filtro_usuario_block');	
	var usuarioTag = document.getElementById('filtro_usuario_tag');

	var inputDescricaoBackend = document.getElementById('input_descricao_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputusuarioBackend = document.getElementById('input_usuario_backend');	
	
	filtroTipo.style.border="1px solid #949393";
	filtroTipo.disabled=false;
	filtroBt.disabled=false;
	filtroBt.style.pointerEvents="auto";	

	if (filtro == 'descricao') {		

		optionDescricao.hidden=false;

		descricaoBlock.hidden=false;

		dataInicioBlock.hidden=true;
		dataFimBlock.hidden=true;
		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;
		usuarioBlock.hidden=true;

		descricaoTag.hidden=true;

		filtroTipo.value="DESCRICAO";

		inputDescricaoBackend.value="";
	}
	else if (filtro == 'data') {
		optionData.hidden=false;
		optionPeriodo.hidden=false;

		descricaoBlock.hidden=true;
		dataInicioBlock.hidden=false;
		dataFimBlock.hidden=false;
		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;
		usuarioBlock.hidden=true;

		dataTag.hidden=true;
		filtroTipo.value="DATA";

		inputDataInicioBackend.value="";
		inputDataFimBackend.value="";
	}
	else if (filtro == 'periodo') {
		optionData.hidden=false;
		optionPeriodo.hidden=false;

		descricaoBlock.hidden=true;
		dataInicioBlock.hidden=true;
		dataFimBlock.hidden=true;
		periodoMesBlock.hidden=false;
		periodoAnoBlock.hidden=false;
		usuarioBlock.hidden=true;

		periodoTag.hidden=true;
		filtroTipo.value="PERIODO";

		inputMesBackend.value="";
		inputAnoBackend.value="";
	}
	else if (filtro == 'usuario') {
		optionusuario.hidden=false;

		descricaoBlock.hidden=true;

		dataInicioBlock.hidden=true;
		dataFimBlock.hidden=true;
		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;

		usuarioBlock.hidden=false;
		usuarioTag.hidden=true;

		filtroTipo.value="USUARIO";

		inputusuarioBackend.value="";
	}	

	buscarBt.hidden=true;		
	filtroBt.hidden=false;
}

function efeitoRemoverFiltro(filtro) {

	var filtroDescricao = document.getElementById('filtro_descricao_tag');
	var filtroData = document.getElementById('filtro_data_tag');	
	var filtroPeriodo = document.getElementById('filtro_periodo_tag');	
	var filtrousuario = document.getElementById('filtro_usuario_tag');


	if (filtro == 'descricao') {
		filtroDescricao.style.transition="0.5s"
		filtroDescricao.style.background="#AA3C3C";
		filtroDescricao.style.border="1px solid #AA3C3C";
		filtroDescricao.style.color="#212121";
		filtroDescricao.innerText="Remover";
		filtroDescricao.style.cursor="pointer";

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
	else if (filtro == 'usuario') {
		filtrousuario.style.transition="0.5s"
		filtrousuario.style.background="#AA3C3C";
		filtrousuario.style.border="1px solid #AA3C3C";
		filtrousuario.style.color="#212121";
		filtrousuario.innerText="Remover";
		filtrousuario.style.cursor="pointer";
	}
		
}

function efeitoRemoverFiltroLeave(filtro) {

	var filtroDescricao = document.getElementById('filtro_descricao_tag');
	var filtroData = document.getElementById('filtro_data_tag');
	var filtroPeriodo = document.getElementById('filtro_periodo_tag');	
	var filtrousuario = document.getElementById('filtro_usuario_tag');

	var inputDescricaoBackend = document.getElementById('input_descricao_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputusuarioBackend = document.getElementById('input_usuario_backend');

	if (filtro == 'descricao') {
		filtroDescricao.style.transition="1s"
		filtroDescricao.style.background="transparent";
		filtroDescricao.style.border="1px solid #212121"
		filtroDescricao.style.color="#212121";
		filtroDescricao.innerText="Descrição";
		filtroDescricao.innerText = 'Nome: ' + inputDescricaoBackend.value;

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
	else if (filtro == 'usuario') {
		filtrousuario.style.transition="1s"
		filtrousuario.style.background="transparent";
		filtrousuario.style.border="1px solid #212121"
		filtrousuario.style.color="#212121";
		filtrousuario.innerText = 'Usuario: ' + inputusuarioBackend.value;
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
							dataNascimento,
							nomeCompleto,
							nomeUsuario,
							privilegio,
							senha,
							senhaCriptografada,
							cpfCnpj,
							email, 
							telefone, 
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
			document.getElementById('edita_titulo').title="Colaborador cadastrado dia " + dataUsParaDataBr + " por " + usuarioResponsavel;
		}
	}

	document.getElementById('id_input_edicao').value=id;
	document.getElementById('edita_descricao_colaborador_input').value=nomeCompleto;
	document.getElementById('edita_email_colaborador_input').value=email;
	document.getElementById('edita_telefone_colaborador_input').value=telefone;
	document.getElementById('edita_data_nascimento_input').value=dataNascimento;
	document.getElementById('edita_privilegio_colaborador_input').value=privilegio;
	document.getElementById('edita_username_colaborador_input').value=nomeUsuario;
	document.getElementById('edita_senha_colaborador_input').value=senha;
	document.getElementById('edita_cpfCnpj_input').value=cpfCnpj;
	document.getElementById('edita_descricao_colaborador_input').value=nomeCompleto;
	document.getElementById('edita_email_colaborador_input').value=email;
	document.getElementById('edita_telefone_colaborador_input').value=telefone;
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
	document.getElementById('edita_descricao_colaborador_input').value="";
	document.getElementById('edita_descricao_colaborador_input').style.background="transparent";

	document.getElementById('edita_email_colaborador_input').value="";
	document.getElementById('edita_email_colaborador_input').style.background="transparent";

	document.getElementById('edita_telefone_colaborador_input').value="";
	document.getElementById('edita_telefone_colaborador_input').style.background="transparent";

	document.getElementById('edita_data_nascimento_input').value="";

	document.getElementById('edita_cpfCnpj_input').value="";
	document.getElementById('edita_cpfCnpj_input').style.background="transparent";

	document.getElementById('edita_privilegio_colaborador_input').value="VENDEDOR";
	document.getElementById('edita_privilegio_colaborador_input').style.background="transparent";

	document.getElementById('edita_username_colaborador_input').value="";
	document.getElementById('edita_username_colaborador_input').style.background="transparent";

	document.getElementById('edita_senha_colaborador_input').value="";
	document.getElementById('edita_senha_colaborador_input').style.background="transparent";	

}

/* ================== TRATAMENTO DE INPUTS ====================== */

/* TRATAMENTO DO CAMPO EMAIL */
function tratamentoCampoEmail(tipo) {

	var emailRegex =  new RegExp("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)" +
                    "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");

	var inputEmail = document.getElementById('input_email');

	if(tipo == "novo") {
		var inputEmail = document.getElementById('email_colaborador_input');
		var inputTelefone = document.getElementById('telefone_colaborador_input');		
	}
	else {
		var inputEmail = document.getElementById('edita_email_colaborador_input');
		var inputTelefone = document.getElementById('edita_telefone_colaborador_input');		
	}

	if (inputEmail.value != "") {

		if (emailRegex.test(inputEmail.value)) {			
			inputEmail.style.background="transparent";
			if(inputEmail.value.includes(".com")) {			
				inputTelefone.focus();
			}
			return true;
		}
		else {
			inputEmail.style.background="#f5aea9";			
			return false;
		}

	}

	else {
		inputEmail.style.background="transparent";
		return true;
	}
}

/* TRATAMENTO DO CAMPO TELEFONE */
function tratamentoCampoTelefone(tipo) {

	var telefoneRegex =  new RegExp("^\\([1-9]{2}\\)[9]{0,1}[1-9]{1}[0-9]{3}\\-[0-9]{4}$");

	if(tipo == "novo") {
		var inputTelefone = document.getElementById('telefone_colaborador_input');
		var inputDataNascimento = document.getElementById('data_nascimento_input');				
	}
	else {
		var inputTelefone = document.getElementById('edita_telefone_colaborador_input');
		var inputDataNascimento = document.getElementById('edita_data_nascimento_input');				
	}

	inputTelefone.value = inputTelefone.value.replace(/([a-zA-Z ])/g, "");

	if (inputTelefone.value != "") {

		if (telefoneRegex.test(inputTelefone.value)) {
			inputTelefone.style.background="transparent";
			inputDataNascimento.focus();
			return true;
		}
		else {
			inputTelefone.style.background="#f5aea9";
			return false;
		}

	}
	else {
		inputTelefone.style.background="transparent";
		return true;
	}
}

/* TRATAMENTO DO CAMPO CPF CNPJ */
function tratamentoCampoCpfCnpj(tipo) {

	var cnpjRegex =  new RegExp("[0-9]{2}.[0-9]{3}.[0-9]{3}/000[1-9]-[0-9]{2}");
	var cpfRegex =  new RegExp("[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}");

	if(tipo == "novo") {
		var inputCpfCnpj = document.getElementById('cpfCnpj_input');
		var botaoFinalizar = document.getElementById('novo_item_submit');		
	}
	else {
		var inputCpfCnpj = document.getElementById('edita_cpfCnpj_input');
		var botaoFinalizar = document.getElementById('edita_item_submit');				
	}

	inputCpfCnpj.value = inputCpfCnpj.value.replace(/([a-zA-Z ])/g, "");

	if (inputCpfCnpj.value != "") {

		if (cpfRegex.test(inputCpfCnpj.value) || cnpjRegex.test(inputCpfCnpj.value)) {
			inputCpfCnpj.style.background="transparent";
			botaoFinalizar.focus();
			return true;
		}
		else {
			inputCpfCnpj.style.background="#f5aea9";
			return false;
		}

	}
	else {
		inputCpfCnpj.style.background="transparent";
		return true;
	}
}

/* REALIZA VALIDAÇÃO DE ATRIBUTOS NULOS NO OBJETO COLABORADOR */
function validacaoDoObjetoColaborador(tipo) {

	if(tipo == "novo") {
		var inputNome = document.getElementById('descricao_colaborador_input');
		var inputTelefone = document.getElementById('telefone_colaborador_input');
		var inputEmail = document.getElementById('email_colaborador_input');
		var inputCpfCnpj = document.getElementById('cpfCnpj_input');
		var inputDataNascimento = document.getElementById('data_nascimento_input');
		var inputPrivilegio = document.getElementById('privilegio_colaborador_input');
		var inputUsername = document.getElementById('username_colaborador_input');
		var inputSenha = document.getElementById('senha_colaborador_input');
	}
	else {
		var inputNome = document.getElementById('edita_descricao_colaborador_input');
		var inputTelefone = document.getElementById('edita_telefone_colaborador_input');
		var inputEmail = document.getElementById('edita_email_colaborador_input');
		var inputCpfCnpj = document.getElementById('edita_cpfCnpj_input');
		var inputDataNascimento = document.getElementById('edita_data_nascimento_input');		
		var inputPrivilegio = document.getElementById('edita_privilegio_colaborador_input');
		var inputUsername = document.getElementById('edita_username_colaborador_input');
		var inputSenha = document.getElementById('edita_senha_colaborador_input');		
	}

	inputUsername.value = inputUsername.value.toLowerCase();

	if (inputNome != null) {
		inputNome.value = inputNome.value.normalize("NFD").replace(/[\u0300-\u036f]/g, "")		
	}	

	if (inputUsername != null) {
		inputUsername.value = inputUsername.value.normalize("NFD").replace(/[\u0300-\u036f]/g, "")		
	}

	if (inputSenha != null) {
		inputSenha.value = inputSenha.value.normalize("NFD").replace(/[\u0300-\u036f]/g, "")		
	}		

	if (inputEmail != null) {
		inputEmail.value = inputEmail.value.normalize("NFD").replace(/[\u0300-\u036f]/g, "")		
	}		

	if (inputDataNascimento != null) {
		if (inputDataNascimento.value.split("-").length == 3) {
			if (inputDataNascimento.value.split("-")[0] > 1900) {
				inputCpfCnpj.focus();
			}
		}
	}		

	if(
		inputNome.value != "" 
		|| inputUsername.value != "" 
		|| inputSenha.value != "" 
		|| inputTelefone.value != "" 
		|| inputEmail.value != "" 
		|| inputCpfCnpj.value != "" 
		|| inputDataNascimento.value != "") {

		if(inputNome.value == "") {
			inputNome.style.background="#f5aea9";	
		}
		else {
			inputNome.style.background="transparent";			
		}

		if(inputUsername.value == "") {
			inputUsername.style.background="#f5aea9";	
		}
		else {
			inputUsername.style.background="transparent";			
		}

		if(inputSenha.value == "") {
			inputSenha.style.background="#f5aea9";	
		}
		else {
			inputSenha.style.background="transparent";			
		}
						
	}

	else {
		inputNome.style.background="transparent";
		return true;		
	}
}

/* VALIDAÇÃO DE TODOS OS CAMPOS */
function validacaoCampos(tipo) {

	var inputNome = null;
	var inputTelefone = null;
	var inputEmail = null;
	var inputCep = null;
	var inputCpfCnpj = null;
	var botaoFinalizar = null;

	if(tipo == "novo") {
		var inputNome = document.getElementById('descricao_colaborador_input');
		var inputTelefone = document.getElementById('telefone_colaborador_input');
		var inputEmail = document.getElementById('email_colaborador_input');
		var inputCpfCnpj = document.getElementById('cpfCnpj_input');
		var inputDataNascimento = document.getElementById('data_nascimento_input');
		var inputPrivilegio = document.getElementById('privilegio_colaborador_input');
		var inputUsername = document.getElementById('username_colaborador_input');
		var inputSenha = document.getElementById('senha_colaborador_input');
		var botaoFinalizar = document.getElementById('novo_item_submit');
		var form = document.getElementById('form_novo');
	}
	else {
		var inputNome = document.getElementById('edita_descricao_colaborador_input');
		var inputTelefone = document.getElementById('edita_telefone_colaborador_input');
		var inputEmail = document.getElementById('edita_email_colaborador_input');
		var inputCpfCnpj = document.getElementById('edita_cpfCnpj_input');
		var inputDataNascimento = document.getElementById('edita_data_nascimento_input');		
		var inputPrivilegio = document.getElementById('edita_privilegio_colaborador_input');
		var inputUsername = document.getElementById('edita_username_colaborador_input');
		var inputSenha = document.getElementById('edita_senha_colaborador_input');		
		var botaoFinalizar = document.getElementById('edita_item_submit');		
		var form = document.getElementById('form_edita');
	}

	var erros = "Ocorreram alguns erros no lançamento da ordem:\n";

	if(inputTelefone.value != "" && !tratamentoCampoTelefone(tipo)) {
		erros += "- Telefone com padrão incorreto\n";
	}
	if(inputEmail.value != "" && !tratamentoCampoEmail(tipo)) {
		erros += "- Email com padrão incorreto\n";
	}
	if(inputCpfCnpj.value != "" && !tratamentoCampoCpfCnpj(tipo)) {
		erros += "- CPF/CNPJ com padrão incorreto\n";
	}	
	if(inputNome.value == "" || inputNome == null) {
		erros += "- O campo nome não pode ser vazio\n";
	}
	if(inputUsername.value == "" || inputUsername == null) {
		erros += "- O campo usuario não pode ser vazio\n";
	}
	if(inputSenha.value == "" || inputSenha == null) {
		erros += "- O campo senha não pode ser vazio\n";
	}		

	if (erros != "Ocorreram alguns erros no lançamento da ordem:\n") {
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

function ajustaMinMaxDosInputsData() {

	var inputDataNascimento = document.getElementById('data_nascimento_input');
	var editainputDataNascimento = document.getElementById('edita_data_nascimento_input');

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

	inputDataNascimento.max=hoje;	
	inputDataNascimento.min='1900-01-01';

	editainputDataNascimento.max=hoje;	
	editainputDataNascimento.min='1900-01-01';
}

function buildUrlPages() {

	var paginaAtual = document.getElementById('pagina_atual');
	var tipoFiltro = document.getElementById('tipo_filtro');

	var dataInicio = document.getElementById('back_inicio');
	var dataFim = document.getElementById('back_fim');
	var periodoMes = document.getElementById('back_mes');
	var periodoAno = document.getElementById('back_ano');	
	var descricao = document.getElementById('back_descricao');		
	var usuario = document.getElementById('back_usuario');			
	var telefone = document.getElementById('back_telefone');	

	var pageNumber = document.getElementsByClassName('page_number');

	if(tipoFiltro.value == 'data') {

		$('#anterior').attr("href", "/colaboradores?page=" + (parseInt(paginaAtual.value) - 1)  + "&inicio=" + dataInicio.value + "&fim=" + dataFim.value);
		$('#proxima').attr("href", "/colaboradores?page=" + (parseInt(paginaAtual.value) + 1)  + "&inicio=" + dataInicio.value + "&fim=" + dataFim.value);


		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/colaboradores?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina')))  + "&inicio=" + dataInicio.value + "&fim=" + dataFim.value);
		}

	}

	else if(tipoFiltro.value == 'periodo') {

		$('#anterior').attr("href", "/colaboradores?page=" + (parseInt(paginaAtual.value) - 1)  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value);
		$('#proxima').attr("href", "/colaboradores?page=" + (parseInt(paginaAtual.value) + 1)  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value);

		
		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/colaboradores?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina')))  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value);
		}

	}

	else if(tipoFiltro.value == 'descricao') {

		$('#anterior').attr("href", "/colaboradores?page=" + (parseInt(paginaAtual.value) - 1)  + "&descricao=" + descricao.value);
		$('#proxima').attr("href", "/colaboradores?page=" + (parseInt(paginaAtual.value) + 1)  + "&descricao=" + descricao.value);

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/colaboradores?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&descricao=" + descricao.value);
		}

	}

	else if(tipoFiltro.value == 'usuario') {

		$('#anterior').attr("href", "/colaboradores?page=" + (parseInt(paginaAtual.value) - 1)  + "&usuario=" + usuario.value);
		$('#proxima').attr("href", "/colaboradores?page=" + (parseInt(paginaAtual.value) + 1)  + "&usuario=" + usuario.value);

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/colaboradores?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&usuario=" + usuario.value);
		}					
	}

	else {
		$('#anterior').attr("href", "/colaboradores?page=" + (parseInt(paginaAtual.value) - 1));
		$('#proxima').attr("href", "/colaboradores?page=" + (parseInt(paginaAtual.value) + 1));

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/colaboradores?page=" + 
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

	for(var i = 0; i < line.length; i++) {

		var columnDataSplitted = columnData[i].innerText.split("-");
		if (columnDataSplitted.length == 3) {
			var convertedDate = columnDataSplitted[2] + "/" + columnDataSplitted[1] + "/" + columnDataSplitted[0];
			columnData[i].innerText=convertedDate;
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
	var descricao = document.getElementById('back_descricao');	
	var fornecedor = document.getElementById('back_fornecedor');			
	var usuario = document.getElementById('back_usuario');		

	if(filtro == "hoje") {
		titulo.innerText="Todos os colaboradores";
	}
	else if (filtro == "data") {
		var columnDataSplitted = dataInicio.value.split("-");
		var inicio = columnDataSplitted[2] + "/" + columnDataSplitted[1] + "/" + columnDataSplitted[0];		
		var columnDataSplitted = dataFim.value.split("-");
		var fim = columnDataSplitted[2] + "/" + columnDataSplitted[1] + "/" + columnDataSplitted[0];
		titulo.innerText="Colaboradores: " + inicio + " à " + fim;		
	}		
	else if (filtro == "periodo") {
		titulo.innerText="Colaboradores: " + mes.value + "/" + ano.value;
	}
	else if (filtro == "descricao") {
		titulo.innerText="Colaboradores de nome " + descricao.value;
	}
	else if (filtro == "usuario") {
		titulo.innerText="Colaboradores de usuário " + (usuario.value);
	}
}

function buildUrlRelatorio() {

	if(document.getElementsByClassName('tr_spring').length > 0) {

		var tipoFiltro = document.getElementById('tipo_filtro');

		var dataInicio = document.getElementById('back_inicio');
		var dataFim = document.getElementById('back_fim');
		var periodoMes = document.getElementById('back_mes');
		var periodoAno = document.getElementById('back_ano');	
		var descricao = document.getElementById('back_descricao');		
		var usuario = document.getElementById('back_usuario');	

		var url = "/colaboradores/relatorio?"

		if(tipoFiltro.value == 'data') {
			url += "inicio=" + dataInicio.value + "&fim=" + dataFim.value;
		}

		else if(tipoFiltro.value == 'periodo') {
			url += "mes=" + periodoMes.value + "&ano=" + periodoAno.value;
		}

		else if(tipoFiltro.value == 'descricao') {
			url += "descricao=" + descricao.value;
		}

		else if(tipoFiltro.value == 'usuario') {
			url += "usuario=" + usuario.value;
		}

		$('#relatorio_button').attr("href", url);	

	}

	else {
		$('#relatorio_button').disabled=true;
	}

}