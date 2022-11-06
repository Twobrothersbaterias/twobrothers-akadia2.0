/* =========================== RESPONSIVIDADE DA TELA =========================== */

window.onload = responsive();
window.onresize = doALoadOfStuff;
ajustaMinMaxDosInputsData();
ajustaCaracteresBloco();
//buildUrlPages();

var privilegio = document.getElementById('body').getAttribute('data-privilegio');

console.log('Privilégio: ' + privilegio);

document.onkeydown=function(){

	var keyCode = window.event.keyCode;
	console.log(keyCode);
	bind(keyCode);

}

function bind(keyCode) {

    if(keyCode == '27') {
        fechaNovoItem();
        fecharFiltro();
        fecharEditaItem();
    }

    if (document.getElementById('conteudo_container_edita').hidden==true
    	&& document.getElementById('conteudo_container_filtro').hidden==true
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
	    	window.location.href="/";
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

	var postagemTitulo = document.getElementsByClassName('titulo_postagem');
	var postagemInfoResponsavel = document.getElementsByClassName('info_responsavel');
	var postagemInfoCategoria = document.getElementsByClassName('info_categoria');
	var postagemTexto = document.getElementsByClassName('texto_postagem');
	var botaoPostagem = document.getElementsByClassName('botao_postagem');

	if(bodyWidth > 1200){
		console.log("Muito grande");

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

		mainRow.style.height="100%";
		containerNovo.style.fontSize="1rem";		
		novoTitulo.style.fontSize="1.15rem";	
		novoItemSubmit.style.marginTop="0px";

		containerEdita.style.fontSize="1rem";		
		editaTitulo.style.fontSize="1.15rem";	
		editaItemSubmit.style.marginTop="0px";

		conteudoContainer.style.marginTop="30px";
		hrTabela.style.marginBottom="15px";			

		for(var i = 0; i < postagemTitulo.length; i++) {
			postagemTitulo[i].style.fontSize="0.80rem";
		}		
		for(var i = 0; i < postagemInfoResponsavel.length; i++) {
			postagemInfoResponsavel[i].style.fontSize="0.70rem";
		}
		for(var i = 0; i < postagemInfoCategoria.length; i++) {
			postagemInfoCategoria[i].style.fontSize="0.70rem";
		}	
		for(var i = 0; i < postagemTexto.length; i++) {
			postagemTexto[i].style.fontSize="0.70rem";
		}	
		for(var i = 0; i < botaoPostagem.length; i++) {
			botaoPostagem[i].style.fontSize="0.70rem";
		}

		for(var i = 0; i < thDataCadastro.length; i++) {
			thDataCadastro[i].hidden=false;
		}

		for(var i = 0; i < tdDataCadastro.length; i++) {
			tdDataCadastro[i].hidden=false;
		}	

		for(var i = 0; i < thUsuario.length; i++) {
			thUsuario[i].hidden=false;
		}

		for(var i = 0; i < tdUsuario.length; i++) {
			tdUsuario[i].hidden=false;
		}									

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.80rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.80rem";
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
			btnExcluir[i].style.fontSize="0.95rem";
			btnExcluir[i].style.padding="4px 6px";
			btnExcluir[i].innerText="Excluir";
		}		
		for(var i = 0; i < filtroBlock.length; i++) {
			filtroBlock[i].style.marginBottom="0";
		}

		for(var i = 0; i < novoItemInput.length; i++) {
			novoItemInput[i].style.marginBottom="0";
		}	

		for(var i = 0; i < editaItemInput.length; i++) {
			editaItemInput[i].style.marginBottom="0";
		}
	}
	else if(bodyWidth <= 1200 && bodyWidth > 992){
		console.log("Grande");

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

		mainRow.style.height="100%";
		containerNovo.style.fontSize="1rem";
		novoTitulo.style.fontSize="1.15rem";	
		novoItemSubmit.style.marginTop="0px";

		containerEdita.style.fontSize="1rem";
		editaTitulo.style.fontSize="1.15rem";	
		editaItemSubmit.style.marginTop="0px";	

		filtroTitulo.style.fontSize="1.4rem";		

		conteudoContainer.style.marginTop="30px";
		hrTabela.style.marginBottom="15px";			

		for(var i = 0; i < postagemTitulo.length; i++) {
			postagemTitulo[i].style.fontSize="0.80rem";
		}		
		for(var i = 0; i < postagemInfoResponsavel.length; i++) {
			postagemInfoResponsavel[i].style.fontSize="0.70rem";
		}
		for(var i = 0; i < postagemInfoCategoria.length; i++) {
			postagemInfoCategoria[i].style.fontSize="0.70rem";
		}	
		for(var i = 0; i < postagemTexto.length; i++) {
			postagemTexto[i].style.fontSize="0.70rem";
		}	
		for(var i = 0; i < botaoPostagem.length; i++) {
			botaoPostagem[i].style.fontSize="0.70rem";
		}

		for(var i = 0; i < thDataCadastro.length; i++) {
			thDataCadastro[i].hidden=false;
		}

		for(var i = 0; i < tdDataCadastro.length; i++) {
			tdDataCadastro[i].hidden=false;
		}	


		for(var i = 0; i < thUsuario.length; i++) {
			thUsuario[i].hidden=false;
		}

		for(var i = 0; i < tdUsuario.length; i++) {
			tdUsuario[i].hidden=false;
		}											

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.75rem";
		}		
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.75rem";		
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
			btnExcluir[i].style.fontSize="0.95rem";
			btnExcluir[i].style.padding="4px 6px";
			btnExcluir[i].innerText="Excluir";
		}		
		for(var i = 0; i < filtroBlock.length; i++) {
			filtroBlock[i].style.marginBottom="0";
		}	

		for(var i = 0; i < novoItemInput.length; i++) {
			novoItemInput[i].style.marginBottom="0";
		}	

		for(var i = 0; i < editaItemInput.length; i++) {
			editaItemInput[i].style.marginBottom="0";
		}		

		filtroBuscarBt.style.marginTop="0px";
		filtroBuscarBt.style.justifyContent="left";					
	}
	else if(bodyWidth <= 992 && bodyWidth > 768){
		console.log('Médio');	

		sideMenu.style.display="block";
		if (bodyWidth > 870) {
			main.style.width="94%";
			sideMenu.style.width="6%";
		}
		else {
			main.style.width="92.5%";
			sideMenu.style.width="7.5%";	
		}

		mainRow.style.height="100%";
		conteudoTituloText.style.fontSize="1.2rem";
		menuMobile.style.display="none";
		containerNovo.style.fontSize="1rem";		
		novoTitulo.style.fontSize="1.1rem";
		novoItemSubmit.style.marginTop="0px";

		containerEdita.style.fontSize="1rem";		
		editaTitulo.style.fontSize="1.1rem";
		editaItemSubmit.style.marginTop="0px";

		filtroTitulo.style.fontSize="1.3rem";	

		conteudoContainer.style.marginTop="30px";
		hrTabela.style.marginBottom="15px";				

		for(var i = 0; i < postagemTitulo.length; i++) {
			postagemTitulo[i].style.fontSize="0.75rem";
		}		
		for(var i = 0; i < postagemInfoResponsavel.length; i++) {
			postagemInfoResponsavel[i].style.fontSize="0.65rem";
		}
		for(var i = 0; i < postagemInfoCategoria.length; i++) {
			postagemInfoCategoria[i].style.fontSize="0.65rem";
		}	
		for(var i = 0; i < postagemTexto.length; i++) {
			postagemTexto[i].style.fontSize="0.65rem";
		}	
		for(var i = 0; i < botaoPostagem.length; i++) {
			botaoPostagem[i].style.fontSize="0.65rem";
		}	

		for(var i = 0; i < thDataCadastro.length; i++) {
			thDataCadastro[i].hidden=false;
		}

		for(var i = 0; i < tdDataCadastro.length; i++) {
			tdDataCadastro[i].hidden=false;
		}	

		for(var i = 0; i < thUsuario.length; i++) {
			thUsuario[i].hidden=true;
		}

		for(var i = 0; i < tdUsuario.length; i++) {
			tdUsuario[i].hidden=true;
		}				

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.70rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.70rem";
		}	
				
		for(var i = 0; i < liA.length; i++){
			liA[i].style.fontSize="0.75rem";
			liA[i].style.padding="5px 10px";			
		}		
		for(var i = 0; i < formRemoveImg.length; i++) {
			formRemoveImg[i].style.display="block";
			formRemoveImg[i].style.maxWidth="50%";			
		}	

		for(var i = 0; i < btnExcluir.length; i++) {
			btnExcluir[i].style.display="none";
		}	
		for(var i = 0; i < filtroBlock.length; i++) {
			filtroBlock[i].style.marginBottom="0";
		}

		for(var i = 0; i < novoItemInput.length; i++) {
			novoItemInput[i].style.marginBottom="0";
		}	

		for(var i = 0; i < editaItemInput.length; i++) {
			editaItemInput[i].style.marginBottom="0";
		}		

		filtroBuscarBt.style.marginTop="0px";
		filtroBuscarBt.style.justifyContent="left";		
	}
	else if(bodyWidth <= 768 && bodyWidth > 540){
		console.log('Pequeno');	

		mainRow.style.height="100%";
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

		filtroTitulo.style.fontSize="1.2rem";	

		conteudoContainer.style.marginTop="10px";
		hrTabela.style.marginBottom="15px";			

		for(var i = 0; i < postagemTitulo.length; i++) {
			postagemTitulo[i].style.fontSize="0.75rem";
		}		
		for(var i = 0; i < postagemInfoResponsavel.length; i++) {
			postagemInfoResponsavel[i].style.fontSize="0.65rem";
		}
		for(var i = 0; i < postagemInfoCategoria.length; i++) {
			postagemInfoCategoria[i].style.fontSize="0.65rem";
		}	
		for(var i = 0; i < postagemTexto.length; i++) {
			postagemTexto[i].style.fontSize="0.65rem";
		}	
		for(var i = 0; i < botaoPostagem.length; i++) {
			botaoPostagem[i].style.fontSize="0.65rem";
		}	

		for(var i = 0; i < thDataCadastro.length; i++) {
			thDataCadastro[i].hidden=false;
		}

		for(var i = 0; i < tdDataCadastro.length; i++) {
			tdDataCadastro[i].hidden=false;
		}

		for(var i = 0; i < thUsuario.length; i++) {
			thUsuario[i].hidden=true;
		}

		for(var i = 0; i < tdUsuario.length; i++) {
			tdUsuario[i].hidden=true;
		}				

		for(var i = 0; i < th.length; i++) {
			th[i].style.fontSize="0.65rem";	
		}
		for(var i = 0; i < td.length; i++) {
			td[i].style.fontSize="0.65rem";	
		}
		
		for(var i = 0; i < liA.length; i++) {
			liA[i].style.fontSize="0.70rem";
			liA[i].style.padding="5px 8px";
		}			
		for(var i = 0; i < formRemoveImg.length; i++) {
			formRemoveImg[i].style.display="block";
			formRemoveImg[i].style.maxWidth="55%";
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
		console.log('Muito pequeno');

		mainRow.style.height="100%";
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

		filtroTitulo.style.fontSize="1.1rem";		

		conteudoContainer.style.marginTop="10px";
		hrTabela.style.marginBottom="15px";

		for(var i = 0; i < postagemTitulo.length; i++) {
			postagemTitulo[i].style.fontSize="0.70rem";
		}		
		for(var i = 0; i < postagemInfoResponsavel.length; i++) {
			postagemInfoResponsavel[i].style.fontSize="0.60rem";
		}
		for(var i = 0; i < postagemInfoCategoria.length; i++) {
			postagemInfoCategoria[i].style.fontSize="0.60rem";
		}	
		for(var i = 0; i < postagemTexto.length; i++) {
			postagemTexto[i].style.fontSize="0.60rem";
		}	
		for(var i = 0; i < botaoPostagem.length; i++) {
			botaoPostagem[i].style.fontSize="0.60rem";
		}					

		for(var i = 0; i < thDataCadastro.length; i++) {
			thDataCadastro[i].hidden=true;
		}

		for(var i = 0; i < tdDataCadastro.length; i++) {
			tdDataCadastro[i].hidden=true;
		}	

		for(var i = 0; i < thUsuario.length; i++) {
			thUsuario[i].hidden=true;
		}

		for(var i = 0; i < tdUsuario.length; i++) {
			tdUsuario[i].hidden=true;
		}						

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.60rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.60rem";
		}

		for(var i = 0; i < liA.length; i++){
			liA[i].style.fontSize="0.60rem";
			liA[i].style.padding="5px 10px";
		}
		for(var i = 0; i < formRemoveImg.length; i++) {
			formRemoveImg[i].style.display="block";
			formRemoveImg[i].style.maxWidth="60%";			
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

	//pageResponsiva();
}


/* ================== CONFIGURAÇÕES DA SUB-TELA NOVO ITEM ====================== */

function abrirNovoItem() {

	if (document.getElementsByClassName('option_fonte_titulo').length >= 6) {
		document.getElementsByClassName('option_fonte_titulo')[6].selected=true;
		document.getElementsByClassName('option_fonte_conteudo')[6].selected=true;
	}
	document.getElementById('input_conteudo').value="";

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
	document.getElementById('input_fonte_titulo').value=document.getElementById('input_fonte_titulo').options[0];
	if(document.getElementsByClassName('option_fonte_titulo').length >= 6) {	
		document.getElementsByClassName('option_fonte_titulo')[6].selected=true;
	}

	document.getElementById('input_cor_titulo').value="#303030";

	document.getElementById('input_fonte_conteudo').value=document.getElementById('input_fonte_conteudo').options[0];
	if(document.getElementsByClassName('option_fonte_conteudo').length >= 6) {	
		document.getElementsByClassName('option_fonte_conteudo')[6].selected=true;
	}

	document.getElementById('input_cor_conteudo').value="#303030";

	document.getElementById('input_categoria').value="";
	document.getElementById('input_categoria').style.background="transparent";

	document.getElementById('input_subCategoria').value="";
	document.getElementById('input_subCategoria').style.background="transparent";

	document.getElementById('input_titulo').value="";
	document.getElementById('input_titulo').style.color="#303030";
	document.getElementById('input_titulo').style.background="transparent";

	document.getElementById('input_conteudo').value="";
	document.getElementById('input_conteudo').style.color="#303030";	
	document.getElementById('input_conteudo').style.background="transparent";	
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

	var tituloBlock = document.getElementById('filtro_titulo_block');
	var tituloInput = document.getElementById('titulo_filtro_input');

	var dataInicio = document.getElementById('filtro_data_inicio_block');
	var dataInicioInput = document.getElementById('data_inicio_filtro_input');

	var dataFim = document.getElementById('filtro_data_fim_block');
	var dataFimInput = document.getElementById('data_fim_filtro_input');

	var mesBlock = document.getElementById('filtro_mes_block');
	var mesInput = document.getElementById('mes_filtro_input');

	var anoBlock = document.getElementById('filtro_ano_block');
	var anoInput = document.getElementById('ano_filtro_input');

	var categoriaBlock = document.getElementById('filtro_categoria_block');
	var categoriaInput = document.getElementById('categoria_filtro_input');

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

	if (filtroTipo.value == 'TITULO') {

		tituloBlock.hidden=false;

		dataInicioInput.value=data;
		dataInicio.hidden=true;

		dataFimInput.value=data;
		dataFim.hidden=true;

		mesInput.value=mes;
		mesBlock.hidden=true;

		anoInput.value=ano;
		anoBlock.hidden=true;

		categoriaInput.value="";
		categoriaBlock.hidden=true;

	}
	else if (filtroTipo.value == 'DATA') {

		tituloInput.value="";
		tituloBlock.hidden=true;

		dataInicioInput.value=data;
		dataInicio.hidden=false;

		dataFimInput.value=data;
		dataFim.hidden=false;

		mesInput.value=mes;
		mesBlock.hidden=true;

		anoInput.value=ano;
		anoBlock.hidden=true;

		categoriaInput.value="";
		categoriaBlock.hidden=true;

	}
	else if (filtroTipo.value == 'PERIODO') {

		tituloInput.value="";
		tituloBlock.hidden=true;

		dataInicioInput.value=data;
		dataInicio.hidden=true;

		dataFimInput.value=data;
		dataFim.hidden=true;

		mesInput.value=mes;
		mesBlock.hidden=false;

		anoInput.value=ano;
		anoBlock.hidden=false;

		categoriaInput.value="";
		categoriaBlock.hidden=true;
		
	}
	else if (filtroTipo.value == 'CATEGORIA') {

		tituloInput.value="";
		tituloBlock.hidden=true;

		dataInicioInput.value=data;
		dataInicio.hidden=true;

		dataFimInput.value=data;
		dataFim.hidden=true;

		mesInput.value=mes;
		mesBlock.hidden=true;

		anoInput.value=ano;
		anoBlock.hidden=true;

		categoriaBlock.hidden=false;
		
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
	document.getElementById('filtro_input').value="TITULO";
	document.getElementById('filtro_bt').hidden=false;
	document.getElementById('filtro_bt').disabled=false;
	document.getElementById('filtro_bt').style.pointerEvents="auto";
	document.getElementById('filtro_buscar_bt').hidden=true;

	document.getElementById('tipo_filtro_option_titulo').hidden=false;
	document.getElementById('filtro_titulo_block').hidden=false;	
	document.getElementById('filtro_titulo_tag').hidden=true;
	document.getElementById('titulo_filtro_input').value="";
	document.getElementById('input_titulo_backend').value="";

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

	document.getElementById('tipo_filtro_option_categoria').hidden=false;
	document.getElementById('filtro_categoria_block').hidden=true;
	document.getElementById('filtro_categoria_tag').hidden=true;
	document.getElementById('categoria_filtro_input').value="";
	document.getElementById('input_categoria_backend').value="";	

}

function addFiltro() {

	var filtroTipo = document.getElementById('filtro_input');
	var filtroBt = document.getElementById('filtro_bt');
	var buscarBt = document.getElementById('filtro_buscar_bt');

	var optiontitulo = document.getElementById('tipo_filtro_option_titulo');
	var tituloBlock = document.getElementById('filtro_titulo_block');	
	var tituloTag = document.getElementById('filtro_titulo_tag');

	var optionData = document.getElementById('tipo_filtro_option_data');
	var dataInicioBlock = document.getElementById('filtro_data_inicio_block');	
	var dataFimBlock = document.getElementById('filtro_data_fim_block');	
	var dataTag = document.getElementById('filtro_data_tag');	

	var optionPeriodo = document.getElementById('tipo_filtro_option_periodo');
	var periodoMesBlock = document.getElementById('filtro_mes_block');	
	var periodoAnoBlock = document.getElementById('filtro_ano_block');	
	var periodoTag = document.getElementById('filtro_periodo_tag');		

	var optioncategoria = document.getElementById('tipo_filtro_option_categoria');
	var categoriaBlock = document.getElementById('filtro_categoria_block');	
	var categoriaTag = document.getElementById('filtro_categoria_tag');	

	var inputtituloFiltro = document.getElementById('titulo_filtro_input');
	var inputDataInicioFiltro = document.getElementById('data_inicio_filtro_input');
	var inputDataFimFiltro = document.getElementById('data_fim_filtro_input');
	var inputMesFiltro = document.getElementById('mes_filtro_input');
	var inputAnoFiltro = document.getElementById('ano_filtro_input');
	var inputcategoriaFiltro = document.getElementById('categoria_filtro_input');

	var inputtituloBackend = document.getElementById('input_titulo_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputcategoriaBackend = document.getElementById('input_categoria_backend');

	if (filtroTipo.value == 'TITULO') {
		if (inputtituloFiltro.value != "") {
			optiontitulo.hidden=true;
			tituloBlock.hidden=true;
			tituloTag.hidden=false;
			tituloTag.innerText = "Título: " + inputtituloFiltro.value;
			filtroTipo.value="";
			inputtituloBackend.value=inputtituloFiltro.value;

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

		dataTag.hidden=false;
		dataTag.innerText = inputDataInicioFiltro.value + ' a ' + inputDataFimFiltro.value;

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
	else if (filtroTipo.value == 'CATEGORIA') {
		if (inputcategoriaFiltro.value != "") {		
			optioncategoria.hidden=true;
			categoriaBlock.hidden=true;

			categoriaTag.hidden=false;
			categoriaTag.innerText = 'Categoria: ' + inputcategoriaFiltro.value;

			filtroTipo.value="";
			inputcategoriaBackend.value=inputcategoriaFiltro.value;

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

	var optiontitulo = document.getElementById('tipo_filtro_option_titulo');
	var tituloBlock = document.getElementById('filtro_titulo_block');	
	var tituloTag = document.getElementById('filtro_titulo_tag');

	var optionData = document.getElementById('tipo_filtro_option_data');
	var dataInicioBlock = document.getElementById('filtro_data_inicio_block');	
	var dataFimBlock = document.getElementById('filtro_data_fim_block');	
	var dataTag = document.getElementById('filtro_data_tag');	

	var optionPeriodo = document.getElementById('tipo_filtro_option_periodo');
	var periodoMesBlock = document.getElementById('filtro_mes_block');	
	var periodoAnoBlock = document.getElementById('filtro_ano_block');	
	var periodoTag = document.getElementById('filtro_periodo_tag');		

	var optioncategoria = document.getElementById('tipo_filtro_option_categoria');
	var categoriaBlock = document.getElementById('filtro_categoria_block');	
	var categoriaTag = document.getElementById('filtro_categoria_tag');

	var inputtituloBackend = document.getElementById('input_titulo_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputcategoriaBackend = document.getElementById('input_categoria_backend');	
	
	filtroTipo.style.border="1px solid #949393";
	filtroTipo.disabled=false;
	filtroBt.disabled=false;
	filtroBt.style.pointerEvents="auto";	

	if (filtro == 'titulo') {		

		optiontitulo.hidden=false;

		tituloBlock.hidden=false;

		dataInicioBlock.hidden=true;
		dataFimBlock.hidden=true;
		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;
		categoriaBlock.hidden=true;

		tituloTag.hidden=true;

		filtroTipo.value="TITULO";

		inputtituloBackend.value="";
	}
	else if (filtro == 'data') {
		optionData.hidden=false;
		optionPeriodo.hidden=false;

		tituloBlock.hidden=true;
		dataInicioBlock.hidden=false;
		dataFimBlock.hidden=false;
		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;
		categoriaBlock.hidden=true;

		dataTag.hidden=true;
		filtroTipo.value="DATA";

		inputDataInicioBackend.value="";
		inputDataFimBackend.value="";
	}
	else if (filtro == 'periodo') {
		optionData.hidden=false;
		optionPeriodo.hidden=false;

		tituloBlock.hidden=true;
		dataInicioBlock.hidden=true;
		dataFimBlock.hidden=true;
		periodoMesBlock.hidden=false;
		periodoAnoBlock.hidden=false;
		categoriaBlock.hidden=true;

		periodoTag.hidden=true;
		filtroTipo.value="PERIODO";

		inputMesBackend.value="";
		inputAnoBackend.value="";
	}
	else if (filtro == 'categoria') {
		optioncategoria.hidden=false;

		tituloBlock.hidden=true;

		dataInicioBlock.hidden=true;
		dataFimBlock.hidden=true;
		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;

		categoriaBlock.hidden=false;
		categoriaTag.hidden=true;

		filtroTipo.value="CATEGORIA";

		inputcategoriaBackend.value="";
	}	

	buscarBt.hidden=true;		
	filtroBt.hidden=false;
}

function efeitoRemoverFiltro(filtro) {

	var filtrotitulo = document.getElementById('filtro_titulo_tag');
	var filtroData = document.getElementById('filtro_data_tag');	
	var filtroPeriodo = document.getElementById('filtro_periodo_tag');	
	var filtrocategoria = document.getElementById('filtro_categoria_tag');

	if (filtro == 'titulo') {
		filtrotitulo.style.transition="0.5s"
		filtrotitulo.style.background="#AA3C3C";
		filtrotitulo.style.border="1px solid #AA3C3C";
		filtrotitulo.style.color="#212121";
		filtrotitulo.innerText="Remover";
		filtrotitulo.style.cursor="pointer";

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
	else if (filtro == 'categoria') {
		filtrocategoria.style.transition="0.5s"
		filtrocategoria.style.background="#AA3C3C";
		filtrocategoria.style.border="1px solid #AA3C3C";
		filtrocategoria.style.color="#212121";
		filtrocategoria.innerText="Remover";
		filtrocategoria.style.cursor="pointer";
	}

}

function efeitoRemoverFiltroLeave(filtro) {

	var filtrotitulo = document.getElementById('filtro_titulo_tag');
	var filtroData = document.getElementById('filtro_data_tag');
	var filtroPeriodo = document.getElementById('filtro_periodo_tag');	
	var filtrocategoria = document.getElementById('filtro_categoria_tag');

	var inputtituloBackend = document.getElementById('input_titulo_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputcategoriaBackend = document.getElementById('input_categoria_backend');

	if (filtro == 'titulo') {
		filtrotitulo.style.transition="1s"
		filtrotitulo.style.background="transparent";
		filtrotitulo.style.border="1px solid #212121"
		filtrotitulo.style.color="#212121";
		filtrotitulo.innerText="Título";
		filtrotitulo.innerText = 'Título: ' + inputtituloBackend.value;

	}
	else if (filtro == 'data') {
		filtroData.style.transition="1s"
		filtroData.style.background="transparent";
		filtroData.style.border="1px solid #212121"
		filtroData.style.color="#212121";
		filtroData.innerText = inputDataInicioBackend.value + ' a ' + inputDataFimBackend.value;
	}
	else if (filtro == 'periodo') {
		filtroPeriodo.style.transition="1s"
		filtroPeriodo.style.background="transparent";
		filtroPeriodo.style.border="1px solid #212121"
		filtroPeriodo.style.color="#212121";
		filtroPeriodo.innerText = 'Mês ' + inputMesBackend.value + ' de ' + inputAnoBackend.value;
	}
	else if (filtro == 'categoria') {
		filtrocategoria.style.transition="1s"
		filtrocategoria.style.background="transparent";
		filtrocategoria.style.border="1px solid #212121"
		filtrocategoria.style.color="#212121";
		filtrocategoria.innerText = 'Categoria: ' + inputcategoriaBackend.value;
	}
	
}

/* ================== CONFIGURAÇÕES DA SUB-TELA EDITA ITEM ====================== */

function abrirEditaItem(
							id, 
							dataCadastro,
							dataNascimento,
							nomeCompleto, 
							cpfCnpj,
							email, 
							telefone, 
							usuarioResponsavel,
							logradouro,
							numero,
							bairro,
							cidade,
							cep,
							complemento,
							estado) {

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
			document.getElementById('edita_info').title="Cliente cadastrado dia " + dataUsParaDataBr + " por " + usuarioResponsavel;
		}
	}

	$('#giro').attr("href", "/vendas?cliente=" + id);
	document.getElementById('id_input_edicao').value=id;
	document.getElementById('edita_descricao_cliente_input').value=nomeCompleto;
	document.getElementById('edita_email_cliente_input').value=email;
	document.getElementById('edita_telefone_cliente_input').value=telefone;
	document.getElementById('edita_data_nascimento_input').value=dataNascimento;
	document.getElementById('edita_cpfCnpj_input').value=cpfCnpj;
	document.getElementById('edita_cep_input').value=cep;
	document.getElementById('edita_estado_input').value=estado;
	document.getElementById('edita_cidade_input').value=cidade;
	document.getElementById('edita_logradouro_input').value=logradouro;
	document.getElementById('edita_numero_input').value=numero;
	document.getElementById('edita_bairro_input').value=bairro;
	document.getElementById('edita_complemento_input').value=complemento;
}

function fecharEditaItem() {

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
	document.getElementById('edita_descricao_cliente_input').value="";
	document.getElementById('edita_descricao_cliente_input').style.background="transparent";

	document.getElementById('edita_email_cliente_input').value="";
	document.getElementById('edita_email_cliente_input').style.background="transparent";

	document.getElementById('edita_telefone_cliente_input').value="";
	document.getElementById('edita_telefone_cliente_input').style.background="transparent";

	document.getElementById('edita_data_nascimento_input').value="";

	document.getElementById('edita_cpfCnpj_input').value="";
	document.getElementById('edita_cpfCnpj_input').style.background="transparent";

	document.getElementById('edita_cep_input').value="";
	document.getElementById('edita_cep_input').style.background="transparent";

	document.getElementById('edita_estado_input').value="SP";

	document.getElementById('edita_cidade_input').value="";
	document.getElementById('edita_cidade_input').style.background="transparent";

	document.getElementById('edita_logradouro_input').value="";
	document.getElementById('edita_logradouro_input').style.background="transparent";	

	document.getElementById('edita_numero_input').value="";
	document.getElementById('edita_numero_input').style.background="transparent";	
	
	document.getElementById('edita_bairro_input').value="";
	document.getElementById('edita_complemento_input').value="";
}

/* ================== TRATAMENTO DE INPUTS ====================== */

function categoriaMudada(tipo) {

	if(tipo == 'novo') {
		var inputFonteTitulo = document.getElementById('input_fonte_titulo');
		var inputCorTitulo = document.getElementById('input_cor_titulo');
		var inputFonteConteudo = document.getElementById('input_fonte_conteudo');
		var inputCorConteudo = document.getElementById('input_cor_conteudo');
		var inputCategoria = document.getElementById('input_categoria');
		var inputSubCategoria = document.getElementById('input_subCategoria');
		var inputTitulo = document.getElementById('input_titulo');
		var inputConteudo = document.getElementById('input_conteudo');	
		var optionsSubcategoria = document.getElementsByClassName('option_subcategoria');		
	}
	else {
		var inputFonteTitulo = document.getElementById('edita_input_fonte_titulo');
		var inputCorTitulo = document.getElementById('edita_input_cor_titulo');
		var inputFonteConteudo = document.getElementById('edita_input_fonte_conteudo');
		var inputCorConteudo = document.getElementById('edita_input_cor_conteudo');
		var inputCategoria = document.getElementById('edita_input_categoria');
		var inputSubCategoria = document.getElementById('edita_input_subCategoria');
		var inputTitulo = document.getElementById('edita_input_titulo');
		var inputConteudo = document.getElementById('edita_input_conteudo');
		var optionsSubcategoria = document.getElementsByClassName('edita_option_subcategoria');					
	}

	if (inputCategoria.value != '') {
		inputSubCategoria.disabled=false;
		document.getElementById('label_subCategoria').style.color="#303030";
	} 
	else {
		inputSubCategoria.disabled=true;
		document.getElementById('label_subCategoria').style.color="#4444";
	} 	

	for(var i = 0; i < optionsSubcategoria.length; i++) {
		if(optionsSubcategoria[i].getAttribute('data-nomeCategoria') != inputCategoria.value) {
			optionsSubcategoria[i].disabled=true;
		}
		else{
			optionsSubcategoria[i].disabled=false;
		}
	}

	validacaoDoObjetoPostagem(tipo);
}

function mudaFonteTitulo(tipo) {

	if(tipo == 'novo') {
		var inputFonteTitulo = document.getElementById('input_fonte_titulo');
		var inputTitulo = document.getElementById('input_titulo');
		var optionFonteTitulo = document.getElementsByClassName('option_fonte_titulo');
	}
	else {
		var inputFonteTitulo = document.getElementById('edita_input_fonte_titulo');
		var inputTitulo = document.getElementById('edita_input_titulo');
		var optionFonteTitulo = document.getElementsByClassName('edita_option_fonte_titulo');
	}	

	var fonte = null;

	for (var i = 0; i < optionFonteTitulo.length; i++) {
		if (inputFonteTitulo.value == optionFonteTitulo[i].value) {
			fonte = optionFonteTitulo[i].getAttribute('data-fonte');
		}
	}

	$(inputTitulo).css("font-family", fonte);

	validacaoDoObjetoPostagem(tipo);
}

function mudaCorTitulo(tipo) {

	if(tipo == 'novo') {
		var inputCorTitulo = document.getElementById('input_cor_titulo');
		var inputTitulo = document.getElementById('input_titulo');
	}
	else {
		var inputCorTitulo = document.getElementById('edita_input_fonte_titulo');
		var inputTitulo = document.getElementById('edita_input_titulo');
	}	

	$(inputTitulo).css("color", inputCorTitulo.value);

	validacaoDoObjetoPostagem(tipo);

}

function mudaFonteConteudo(tipo) {

	if(tipo == 'novo') {
		var inputFonteConteudo = document.getElementById('input_fonte_conteudo');
		var inputConteudo = document.getElementById('input_conteudo');
		var optionFonteConteudo = document.getElementsByClassName('option_fonte_conteudo');
	}
	else {
		var inputFonteConteudo = document.getElementById('edita_input_fonte_conteudo');
		var inputConteudo = document.getElementById('edita_input_conteudo');
		var optionFonteConteudo = document.getElementsByClassName('edita_option_fonte_conteudo');
	}	

	var fonte = null;

	for (var i = 0; i < optionFonteConteudo.length; i++) {
		if (inputFonteConteudo.value == optionFonteConteudo[i].value) {
			fonte = optionFonteConteudo[i].getAttribute('data-fonte');
		}
	}

	$(inputConteudo).css("font-family", fonte);

	validacaoDoObjetoPostagem(tipo);
}

function mudaCorConteudo(tipo) {

	if(tipo == 'novo') {
		var inputCorConteudo = document.getElementById('input_cor_conteudo');
		var inputConteudo = document.getElementById('input_conteudo');
	}
	else {
		var inputCorConteudo = document.getElementById('edita_input_fonte_conteudo');
		var inputConteudo = document.getElementById('edita_input_conteudo');
	}	

	$(inputConteudo).css("color", inputCorConteudo.value);

	validacaoDoObjetoPostagem(tipo);
}

/* REALIZA VALIDAÇÃO DE ATRIBUTOS NULOS NO OBJETO POSTAGEM */
function validacaoDoObjetoPostagem(tipo) {

	if(tipo == 'novo') {
		var inputFonteTitulo = document.getElementById('input_fonte_titulo');
		var inputCorTitulo = document.getElementById('input_cor_titulo');
		var inputFonteConteudo = document.getElementById('input_fonte_conteudo');
		var inputCorConteudo = document.getElementById('input_cor_conteudo');
		var inputCategoria = document.getElementById('input_categoria');
		var inputSubCategoria = document.getElementById('input_subCategoria');
		var inputTitulo = document.getElementById('input_titulo');
		var inputConteudo = document.getElementById('input_conteudo');	
		var optionsSubcategoria = document.getElementsByClassName('option_subcategoria');		
	}
	else {
		var inputFonteTitulo = document.getElementById('edita_input_fonte_titulo');
		var inputCorTitulo = document.getElementById('edita_input_cor_titulo');
		var inputFonteConteudo = document.getElementById('edita_input_fonte_conteudo');
		var inputCorConteudo = document.getElementById('edita_input_cor_conteudo');
		var inputCategoria = document.getElementById('edita_input_categoria');
		var inputSubCategoria = document.getElementById('edita_input_subCategoria');
		var inputTitulo = document.getElementById('edita_input_titulo');
		var inputConteudo = document.getElementById('edita_input_conteudo');
		var optionsSubcategoria = document.getElementsByClassName('edita_option_subcategoria');					
	}

	inputCategoria.value = inputCategoria.value.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
	inputSubCategoria.value = inputSubCategoria.value.normalize("NFD").replace(/[\u0300-\u036f]/g, "");

	if(inputCategoria.value != ""
		|| inputSubCategoria.value != "" 
		|| inputTitulo.value != "" 
		|| inputConteudo.value != "") {

		if(inputTitulo.value == "") {
			inputTitulo.style.background="#f5aea9";		
		}
		else {
			inputTitulo.style.background="transparent";	
		}

		if(inputConteudo.value == "") {
			inputConteudo.style.background="#f5aea9";	
		}
		else {
			inputConteudo.style.background="transparent";
		}		

		if(inputCategoria.value == "") {
			inputCategoria.style.background="#f5aea9";
		}
		else {
			inputCategoria.style.background="transparent";
		}

		if(inputSubCategoria.value == "") {
			inputSubCategoria.style.background="#f5aea9";
		}
		else {
			inputSubCategoria.style.background="transparent";		
		}	

		if (inputCategoria.value != '') {
			inputSubCategoria.disabled=false;
			document.getElementById('label_subCategoria').style.color="#303030";
		} 
		else {
			inputSubCategoria.disabled=true;
			document.getElementById('label_subCategoria').style.color="#4444";
		} 						

	}

	else {
		inputTitulo.style.background="transparent";		
		inputConteudo.style.background="transparent";
		inputCategoria.style.background="transparent";		
		inputSubCategoria.style.background="transparent";		
		return true;		
	}
}

/* VALIDAÇÃO DE TODOS OS CAMPOS */
function validacaoCampos(tipo) {

	if(tipo == 'novo') {
		var inputFonteTitulo = document.getElementById('input_fonte_titulo');
		var inputCorTitulo = document.getElementById('input_cor_titulo');
		var inputFonteConteudo = document.getElementById('input_fonte_conteudo');
		var inputCorConteudo = document.getElementById('input_cor_conteudo');
		var inputCategoria = document.getElementById('input_categoria');
		var inputSubCategoria = document.getElementById('input_subCategoria');
		var inputTitulo = document.getElementById('input_titulo');
		var inputConteudo = document.getElementById('input_conteudo');	
		var optionsSubcategoria = document.getElementsByClassName('option_subcategoria');
		var botaoFinalizar = document.getElementById('novo_item_submit');		
	}
	else {
		var inputFonteTitulo = document.getElementById('edita_input_fonte_titulo');
		var inputCorTitulo = document.getElementById('edita_input_cor_titulo');
		var inputFonteConteudo = document.getElementById('edita_input_fonte_conteudo');
		var inputCorConteudo = document.getElementById('edita_input_cor_conteudo');
		var inputCategoria = document.getElementById('edita_input_categoria');
		var inputSubCategoria = document.getElementById('edita_input_subCategoria');
		var inputTitulo = document.getElementById('edita_input_titulo');
		var inputConteudo = document.getElementById('edita_input_conteudo');
		var optionsSubcategoria = document.getElementsByClassName('edita_option_subcategoria');					
		var botaoFinalizar = document.getElementById('edita_item_submit');
	}

	var erros = "Ocorreram alguns erros no lançamento do post:\n";

	if(inputTitulo.value == "") {
		erros += "- O campo título deve ser preenchido\n";
	}
	if(inputConteudo.value == "") {
		erros += "- O campo conteúdo deve ser preenchido\n";
	}
	if(inputCategoria.value == "") {
		erros += "- O campo categoria deve ser preenchido\n";
	}
	if(inputSubCategoria.value == "") {
		erros += "- O campo subcategoria deve ser preenchido\n";
	}	

	if (erros != "Ocorreram alguns erros no lançamento do post:\n") {
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

function escreveConteudo(tipo) {

	validacaoDoObjetoPostagem(tipo);

	var total = 1500;
	var novoTotal = 1500;
	
	var inputConteudo = document.getElementById('input_conteudo');
	var caracteres = document.getElementById('caracteres');

	novoTotal = total - inputConteudo.value.length;

	caracteres.innerText = novoTotal + '/' + total;
}

/* ================== MISC ====================== */

function ajustaMinMaxDosInputsData() {

	var inputDataNascimento = document.getElementById('data_nascimento_input');
	var editainputDataNascimento = document.getElementById('edita_data_nascimento_input');
}

function buildUrlPages() {

	var paginaAtual = document.getElementById('pagina_atual');
	var tipoFiltro = document.getElementById('tipo_filtro');

	var dataInicio = document.getElementById('back_inicio');
	var dataFim = document.getElementById('back_fim');
	var periodoMes = document.getElementById('back_mes');
	var periodoAno = document.getElementById('back_ano');	
	var descricao = document.getElementById('back_descricao');		
	var cpfCnpj = document.getElementById('back_cpfCnpj');			
	var telefone = document.getElementById('back_telefone');	

	var pageNumber = document.getElementsByClassName('page_number');

	if(tipoFiltro.value == 'data') {

		$('#anterior').attr("href", "/clientes?page=" + (parseInt(paginaAtual.value) - 1)  + "&inicio=" + dataInicio.value + "&fim=" + dataFim.value);
		$('#proxima').attr("href", "/clientes?page=" + (parseInt(paginaAtual.value) + 1)  + "&inicio=" + dataInicio.value + "&fim=" + dataFim.value);


		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/clientes?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina')))  + "&inicio=" + dataInicio.value + "&fim=" + dataFim.value);
		}

	}

	else if(tipoFiltro.value == 'periodo') {

		$('#anterior').attr("href", "/clientes?page=" + (parseInt(paginaAtual.value) - 1)  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value);
		$('#proxima').attr("href", "/clientes?page=" + (parseInt(paginaAtual.value) + 1)  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value);

		
		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/clientes?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina')))  + "&mes=" + periodoMes.value + "&ano=" + periodoAno.value);
		}

	}

	else if(tipoFiltro.value == 'descricao') {

		$('#anterior').attr("href", "/clientes?page=" + (parseInt(paginaAtual.value) - 1)  + "&descricao=" + descricao.value);
		$('#proxima').attr("href", "/clientes?page=" + (parseInt(paginaAtual.value) + 1)  + "&descricao=" + descricao.value);

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/clientes?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&descricao=" + descricao.value);
		}

	}

	else if(tipoFiltro.value == 'cpfCnpj') {

		$('#anterior').attr("href", "/clientes?page=" + (parseInt(paginaAtual.value) - 1)  + "&cpfCnpj=" + cpfCnpj.value);
		$('#proxima').attr("href", "/clientes?page=" + (parseInt(paginaAtual.value) + 1)  + "&cpfCnpj=" + cpfCnpj.value);

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/clientes?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&cpfCnpj=" + cpfCnpj.value);
		}					
	}

	else if(tipoFiltro.value == 'telefone') {

		$('#anterior').attr("href", "/clientes?page=" + (parseInt(paginaAtual.value) - 1)  + "&telefone=" + telefone.value);
		$('#proxima').attr("href", "/clientes?page=" + (parseInt(paginaAtual.value) + 1)  + "&telefone=" + telefone.value);

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/clientes?page=" + 
				(parseInt(pageNumber[i].getAttribute('data-numeroPagina'))) + "&telefone=" + telefone.value);
		}	

	}
	else {
		$('#anterior').attr("href", "/clientes?page=" + (parseInt(paginaAtual.value) - 1));
		$('#proxima').attr("href", "/clientes?page=" + (parseInt(paginaAtual.value) + 1));

		for (var i = 0; i < pageNumber.length; i ++) {
			pageNumber[i].id="numeroPagina_" + i;
			var idPagina = pageNumber[i].id;
			$('#' + idPagina).attr("href", "/clientes?page=" + 
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
	var columnValor = document.getElementsByClassName('td_gerado');

	for(var i = 0; i < line.length; i++) {

		if (!columnValor[i].innerText.includes('R$') && !columnValor[i].innerText.includes('...')) {
			columnValor[i].innerText=
				parseFloat(columnValor[i].innerText).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
		}

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

/* ================== BLOCOS ====================== */

function ajustaCaracteresBloco() {

	var blocoPostagem = document.getElementsByClassName('bloco_pai_postagem');
	var tituloPostagem = document.getElementsByClassName('titulo_postagem');
	var textoPostagem = document.getElementsByClassName('texto_postagem');	
	var informativoUser= document.getElementsByClassName('info_responsavel');
	var informativoCategoria = document.getElementsByClassName('info_categoria');

	for (var i = 0; i < blocoPostagem.length; i++) {
		
		ajustaEstiloBloco(
			tituloPostagem[i], 
			textoPostagem[i], 
			blocoPostagem[i],
			informativoUser[i],
			informativoCategoria[i]);

		if (tituloPostagem[i].innerText.length > 25) {
			tituloPostagem[i].innerText = tituloPostagem[i].innerText.slice(0, 25) + "...";
		}

		if (textoPostagem[i].innerText.length > 100) {
			textoPostagem[i].innerText = textoPostagem[i].innerText.slice(0, 100) + "...";
		}

	}
}

function ajustaEstiloBloco(titulo, texto, bloco, informativoUser, informativoCategoria) {

	titulo.style.color=bloco.getAttribute('data-corTitulo');
	texto.style.color=bloco.getAttribute('data-corConteudo');
	informativoCategoria.style.color=bloco.getAttribute('data-corTitulo');

	//$(titulo).css("font-family", bloco.getAttribute('data-fonteTitulo'));
	//$(texto).css("font-family", bloco.getAttribute('data-fonteConteudo'));

	// CONVERTENDO DATA PARA PADRÃO BR
	var dataUsParaDataBr = 	null
	if(bloco.getAttribute('data-dataCadastro') != null) {
		var dataCadastroSplitada = bloco.getAttribute('data-dataCadastro').split("-");
		if (dataCadastroSplitada.length == 3) {
			dataUsParaDataBr = dataCadastroSplitada[2] + "/" + dataCadastroSplitada[1] + "/" + dataCadastroSplitada[0];
		}	
	}

	informativoUser.innerText = bloco.getAttribute('data-usuarioResponsavel') 
		+ " - " + dataUsParaDataBr;
}

function removePostagem(id) {

	if (privilegio != "Vendedor") {
	window.location.href="/deleta/" + id;
	}
	else {
		alert("Você não possui permissão para remover uma postagem da tela inicial!\n" +
			"Entre em contato com um gestor para solicitar permissão");
	}
}


/*function categoriaMouseOver(id) {

	var categoria = document.getElementById('categoria_filho_' + id);
	var categoriaTitulo = document.getElementById('categoria_titulo_' + id);

	if(categoria.disabled != true) {
		categoria.style.background="#2f3d61";
		categoria.style.cursor="pointer";
		categoriaTitulo.style.color="#C3C8C8";
	}

}

function categoriaMouseLeave(id) {

	var categoria = document.getElementById('categoria_filho_' + id);
	var categoriaTitulo = document.getElementById('categoria_titulo_' + id);

	if(categoria.disabled != true) {
		categoria.style.background="#C3C8C8";
		categoria.style.cursor="none";
		categoriaTitulo.style.color="#2f3d61";
	}

}

function categoriaClick(id) {

	var subcategorias = document.getElementsByClassName('bloco_filho_subcategoria');
	var subcategoriasTitulo = document.getElementsByClassName('titulo_subcategoria');

	for(var i = 0; i < subcategorias.length; i++) {
		subcategorias[i].disabled=false;
		subcategorias[i].style.background="#C3C8C8";
		subcategorias[i].style.cursor="none";	
	}

	for(var i = 0; i < subcategoriasTitulo.length; i++) {
		subcategoriasTitulo[i].style.color="#2f3d61"		
	}	

	var categoria = document.getElementById('categoria_filho_' + id);
	var categoriaNome = categoria.getAttribute('data-nome');
	var categoriaTitulo = document.getElementById('categoria_titulo_' + id);	
	var categorias = document.getElementsByClassName('bloco_filho_categoria');
	var categoriasTitulo = document.getElementsByClassName('titulo');

	for(var i = 0; i < categorias.length; i++) {
		categorias[i].disabled=false;
		categorias[i].style.background="#C3C8C8";
		categorias[i].style.cursor="none";	
	}

	for(var i = 0; i < categoriasTitulo.length; i++) {
		categoriasTitulo[i].style.color="#2f3d61"		
	}
	categoria.disabled=true;
	categoria.style.background="#2f3d61";
	categoria.style.cursor="pointer";
	categoriaTitulo.style.color="#C3C8C8";

	document.getElementById('container_subcategorias').hidden=false;
	var subCategorias = document.getElementsByClassName('bloco_pai_subcategoria');

	for(var i = 0; i < subCategorias.length; i++) {
		subCategorias[i].hidden=false;
	}

	for(var i = 0; i < subCategorias.length; i++) {
		if (subCategorias[i].getAttribute('data-nomeCategoria') != categoriaNome) {
			subCategorias[i].hidden=true;
		}
	}

}

function subCategoriaMouseOver(id) {

	var subcategoria = document.getElementById('subcategoria_filho_' + id);
	var subcategoriaTitulo = document.getElementById('subcategoria_titulo_' + id);

	if(subcategoria.disabled != true) {
		subcategoria.style.background="#2f3d61";
		subcategoria.style.cursor="pointer";
		subcategoriaTitulo.style.color="#C3C8C8";
	}

}

function subCategoriaMouseLeave(id) {

	var subcategoria = document.getElementById('subcategoria_filho_' + id);
	var subcategoriaTitulo = document.getElementById('subcategoria_titulo_' + id);

	if(subcategoria.disabled != true) {
		subcategoria.style.background="#C3C8C8";
		subcategoria.style.cursor="none";
		subcategoriaTitulo.style.color="#2f3d61";
	}

}

function subCategoriaClick(id) {

	var subcategoria = document.getElementById('subcategoria_filho_' + id);
	var subcategoriaNome = subcategoria.getAttribute('data-nome');
	var subcategoriaTitulo = document.getElementById('subcategoria_titulo_' + id);	
	var subcategorias = document.getElementsByClassName('bloco_filho_subcategoria');
	var subcategoriasTitulo = document.getElementsByClassName('titulo_subcategoria');

	for(var i = 0; i < subcategorias.length; i++) {
		subcategorias[i].disabled=false;
		subcategorias[i].style.background="#C3C8C8";
		subcategorias[i].style.cursor="none";	
	}

	for(var i = 0; i < subcategoriasTitulo.length; i++) {
		subcategoriasTitulo[i].style.color="#2f3d61"		
	}
	subcategoria.disabled=true;
	subcategoria.style.background="#2f3d61";
	subcategoria.style.cursor="pointer";
	subcategoriaTitulo.style.color="#C3C8C8";

	document.getElementById('container_postagens').hidden=false;
	var postagens = document.getElementsByClassName('bloco_pai_postagem');

	for(var i = 0; i < postagens.length; i++) {
		postagens[i].hidden=false;
	}

	for(var i = 0; i < postagens.length; i++) {
		console.log(postagens[i].getAttribute('data-subcategoria'));
		console.log(subcategoria.getAttribute('data-nome'));
		if (postagens[i].getAttribute('data-subcategoria') != subcategoriaNome) {
			postagens[i].hidden=true;
		}
	}

}*/