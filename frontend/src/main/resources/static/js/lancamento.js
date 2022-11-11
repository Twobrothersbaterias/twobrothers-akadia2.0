/* =========================== RESPONSIVIDADE DA TELA =========================== */

window.onload = responsive();
window.onresize = doALoadOfStuff;

var privilegio = document.getElementById('body').getAttribute('data-privilegio');

console.log('Privilégio: ' + privilegio);

document.getElementById('input_nome').focus();

/* ================== TECLAS DE ATALHO ====================== */

document.getElementById('botao_add_pagamento').onkeypress=function() {
	var keyCode = window.event.keyCode;
	if (keyCode == '13') {
		return false;
	}
}

document.getElementById('botao_add_produto').onkeypress=function() {
	var keyCode = window.event.keyCode;
	if (keyCode == '13') {
		return false;
	}
}

document.getElementById('input_tipo_produto').onkeypress=function() {
	var keyCode = window.event.keyCode;
	if (keyCode == '13') {
		return false;
	}
}

document.getElementById('input_tipo_entrada').onkeypress=function() {
	var keyCode = window.event.keyCode;
	if (keyCode == '13') {
		return false;
	}
}

document.getElementById('select_produto').onkeypress=function() {
	var keyCode = window.event.keyCode;
	if (keyCode == '13') {
		return false;
	}
}

document.getElementById('select_forma_pagamento').onkeypress=function() {
	var keyCode = window.event.keyCode;
	if (keyCode == '13') {
		return false;
	}
}

document.getElementById('select_tecnico').onkeypress=function() {
	var keyCode = window.event.keyCode;
	if (keyCode == '13') {
		return false;
	}
}

document.getElementById('estado_input').onkeypress=function() {
	var keyCode = window.event.keyCode;
	if (keyCode == '13') {
		return false;
	}
}

document.getElementById('input_tipoNfe').onkeypress=function() {
	var keyCode = window.event.keyCode;
	if (keyCode == '13') {
		return false;
	}
}

document.getElementById('input_ponto').onkeypress=function() {
	var keyCode = window.event.keyCode;
	if (keyCode == '13') {
		return false;
	}
}

document.getElementById('input_tipo_retirada').onkeypress=function() {
	var keyCode = window.event.keyCode;
	if (keyCode == '13') {
		return false;
	}
}

document.onkeydown=function(){
	var keyCode = window.event.keyCode;
	bind(keyCode);
}


function bind(keyCode) {

    if(keyCode == '27') {
    	if (document.getElementById('container_produtos').hidden == true) {
        	window.location.replace("/vendas");
    	}
    	else {
    		fecharTabelaProdutos();
    	}
    }

    if(keyCode == '13') {

    	console.log("ACESSADO");
		if(document.getElementById('container_produtos').hidden==false) {
		    fecharTabelaProdutos();
		    setTimeout(() => {validacaoCampos()},10);	
		}

		else {

		    if (document.activeElement.id.includes('botao_add_produto')) {
		    	addNovoProduto();
		    }
		    else if(document.activeElement.id.includes('botao_add_pagamento')) {
		    	addNovoPagamento();
		    }
		    else {
		    	validacaoCampos();
		    }

		}
	}   

    if (
    	!document.activeElement.className.includes('novo_item_input')
    	&& !document.activeElement.className.includes('botao_adicionar')) {

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

	    else if(keyCode == '82') {
			if (confirm("Ao reiniciar a tela atual todos os dados da ordem serão perdidos\n" + 
				"Deseja prosseguir mesmo assim?") == true) {
				window.location.href="/lancamento";
			}
	    }
 
	    else if(keyCode == '192') {
	    	document.getElementById('eye').click();
	    }	    	    

    }  
    else {
    	return;
    } 
}

function responsive(){

	//document.documentElement.style.overflowX = 'hidden';

	document.getElementById('main_loader').style.display="none";
	document.getElementById('main_row').style.display="flex";

	bodyWidth = document.getElementById('body').clientWidth;
	bodyHeight = document.getElementById('body').clientHeight;

	var indicadorEdicao = document.getElementById('indicador_edicao');

	setupProdutosVazios();
	setupTecnicosVazios();

	if(indicadorEdicao != null) {
		setupEdicao();
	}

	/* Ajustando content */
	var main = document.getElementById('main');
	var mainRow = document.getElementById('main_row');
	var sideMenu = document.getElementById('side_menu');
	var conteudoTituloText = document.getElementById('conteudo_titulo_text');
	var menuSuperior = document.getElementById('menu_superior');
	var nomePerfil = document.getElementById('nome_perfil');
	var menuMobile = document.getElementById('menu_superior_mobile');

	var divIcones = document.getElementsByClassName('img_div');
	var selectSuperiorContainer = document.getElementsByClassName('select_superior_container');

	var conteudoTituloItem = document.getElementsByClassName('conteudo_titulo_item');	
	var formTitulo = document.getElementsByClassName('form_titulo');
	var containerInformativo = document.getElementsByClassName('container_informativo');
	var selectSuperiorContainer = document.getElementsByClassName('select_superior_container');

	var img = document.getElementsByClassName('img');
	var aImg = document.getElementsByClassName('a_img');
	var imgContainer = document.getElementsByClassName('img_container');

	var btnExcluir = document.getElementsByClassName('btn_excluir');
	var formRemoveImg = document.getElementsByClassName('form_remove_img');
	var formRemoveText = document.getElementsByClassName('form_remove_text');	

	var th = document.getElementsByClassName('th_novo');
	var td = document.getElementsByClassName('td_novo');
	var thTipo = document.getElementsByClassName('th_novo_tipo');
	var tdTipo = document.getElementsByClassName('td_tipo');

	var containerProdutos = document.getElementById('container_produtos');
	var containerTabela = document.getElementById('container_tabela');

	var novoItemLabel = document.getElementsByClassName('novo_item_label');
	var novoItemInput = document.getElementsByClassName('novo_item_input');
	var botaoAdicionar = document.getElementsByClassName('botao_adicionar');
	var informativoCampo = document.getElementsByClassName('informativo_campo');

	var tituloVenda = document.getElementById('edita_titulo');

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

		mainRow.style.width = "100%";

		tituloVenda.style.fontSize="1.125rem";

		for(var i = 0; i < selectSuperiorContainer.length; i++) {
			selectSuperiorContainer[i].style.justifyContent="left";
		}		

		document.getElementById('row_produtos').style.marginBottom="9.19px";
		document.getElementById('container_all').style.marginBottom="0";
		containerTabela.style.marginTop="10px";
		containerProdutos.style.padding="20px 30px 25px 30px";

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.70rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.70rem";
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

		for(var i = 0; i < novoItemLabel.length; i++) {
			novoItemLabel[i].style.fontSize="0.80rem";
		}

		for(var i = 0; i < novoItemInput.length; i++) {
			novoItemInput[i].style.fontSize="0.70rem";
		}

		for(var i = 0; i < botaoAdicionar.length; i++) {
			botaoAdicionar[i].style.fontSize="0.70rem";
			botaoAdicionar[i].style.marginTop="18px";
		}

		for(var i = 0; i < informativoCampo.length; i++) {
			informativoCampo[i].style.fontSize="0.70rem";
		}						

		for (var i = 0; i < selectSuperiorContainer.length; i++) {
			selectSuperiorContainer[i].style.margin="0";
			containerInformativo[i].style.fontSize="0.90rem";
		}

		for (var i = 0 ; i < containerInformativo.length; i++) {
			containerInformativo[i].padding="0";
		}

		for (var i = 0 ; i < conteudoTituloItem.length; i++) {
			conteudoTituloItem[i].style.fontSize="0.90rem";
		}	

		for(var i = 0; i < img.length; i++) {
			img[i].style.width="22px";
			img[i].style.marginTop="0px";
		}

		for(var i = 0; i < aImg.length; i++) {
			aImg[i].style.marginLeft="12px";
		}	

		for(var i = 0; i < thTipo.length; i++){
			thTipo[i].hidden=false;
		}
		for(var i = 0; i < tdTipo.length; i++){
			tdTipo[i].hidden=false;
		}

  		const parent = document.getElementById('container_all');
  		if(parent.children[2].id == "container_endereco") {
  			parent.insertBefore(
  				document.getElementById("container_endereco"), parent.children[2]);			
  		}
  		else {
  			parent.insertBefore(
  				document.getElementById("container_produto"), parent.children[1]);	  			
  		}		
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


		for(var i = 0; i < selectSuperiorContainer.length; i++) {
			selectSuperiorContainer[i].style.justifyContent="left";
		}

		document.getElementById('row_produtos').style.marginBottom="9.40px";			
		document.getElementById('container_all').style.marginBottom="0";
		containerTabela.style.marginTop="10px";
		containerProdutos.style.padding="15px 20px 20px 20px";

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.65rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.65rem";
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

		for(var i = 0; i < novoItemLabel.length; i++) {
			novoItemLabel[i].style.fontSize="0.70rem";
		}

		for(var i = 0; i < novoItemInput.length; i++) {
			novoItemInput[i].style.fontSize="0.60rem";
		}

		for(var i = 0; i < botaoAdicionar.length; i++) {
			botaoAdicionar[i].style.fontSize="0.60rem";
			botaoAdicionar[i].style.marginTop="18px";
		}

		for(var i = 0; i < informativoCampo.length; i++) {
			informativoCampo[i].style.fontSize="0.60rem";
		}			

		for (var i = 0 ; i < containerInformativo.length; i++) {
			containerInformativo[i].style.padding="0";
			containerInformativo[i].style.fontSize="0.85rem";
		}	

		for (var i = 0 ; i < conteudoTituloItem.length; i++) {
			conteudoTituloItem[i].style.fontSize="0.85rem";
		}

		for(var i = 0; i < img.length; i++) {
			img[i].style.width="20px";
			img[i].style.marginTop="0px";
		}

		for(var i = 0; i < aImg.length; i++) {
			aImg[i].style.marginLeft="12px";
		}

		for(var i = 0; i < thTipo.length; i++){
			thTipo[i].hidden=false;
		}
		for(var i = 0; i < tdTipo.length; i++){
			tdTipo[i].hidden=false;
		}									

  		const parent = document.getElementById('container_all');
  		if(parent.children[2].id == "container_endereco") {
  			parent.insertBefore(
  				document.getElementById("container_endereco"), parent.children[2]);			
  		}
  		else {
  			parent.insertBefore(
  				document.getElementById("container_produto"), parent.children[1]);	  			
  		}		
	}

	else if(bodyWidth <= 992 && bodyWidth > 768){
		console.log('Tela: Média');

		sideMenu.style.display="block";
		if (bodyWidth > 860) {
			main.style.width="94%";
			sideMenu.style.width="6%";
		}
		else {
			main.style.width="93.5%";
			sideMenu.style.width="6.5%";	
		}

		mainRow.style.width = "100%";

		document.getElementById('row_produtos').style.marginBottom="9.19px";
		document.getElementById('container_all').style.marginBottom="30px";		

		containerTabela.style.marginTop="0px";

		containerProdutos.style.paddingTop="10px";
		containerProdutos.style.paddingRight="15px";
		containerProdutos.style.paddingBottom="15px";
		containerProdutos.style.paddingLeft="15px";

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.60rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.60rem";
		}

		for(var i = 0; i < formRemoveImg.length; i++) {
			formRemoveImg[i].style.display="block";
			formRemoveImg[i].style.maxWidth="42%";			
		}	

		for(var i = 0; i < btnExcluir.length; i++) {
			btnExcluir[i].style.display="none";
		}		

		for(var i = 0; i < novoItemLabel.length; i++) {
			novoItemLabel[i].style.fontSize="0.70rem";
		}

		for(var i = 0; i < novoItemInput.length; i++) {
			novoItemInput[i].style.fontSize="0.60rem";
		}

		for(var i = 0; i < botaoAdicionar.length; i++) {
			botaoAdicionar[i].style.fontSize="0.60rem";
			botaoAdicionar[i].style.marginTop="10px";
		}

		for(var i = 0; i < informativoCampo.length; i++) {
			informativoCampo[i].style.fontSize="0.60rem";
		}				

		for(var i = 0; i < selectSuperiorContainer.length; i++) {
			selectSuperiorContainer[i].style.justifyContent="left";
		}				

		for (var i = 0 ; i < containerInformativo.length; i++) {
			containerInformativo[i].style.padding="0";
			containerInformativo[i].style.fontSize="0.80rem";
		}	

		for (var i = 0 ; i < conteudoTituloItem.length; i++) {
			conteudoTituloItem[i].style.fontSize="0.80rem";
		}	

		for(var i = 0; i < img.length; i++) {
			img[i].style.width="18px";
			img[i].style.marginTop="0px";
		}

		for(var i = 0; i < aImg.length; i++) {
			aImg[i].style.marginLeft="12px";
		}

		for(var i = 0; i < thTipo.length; i++){
			thTipo[i].hidden=false;
		}
		for(var i = 0; i < tdTipo.length; i++){
			tdTipo[i].hidden=false;
		}											
	
		menuMobile.style.display="none";	

		for(var i = 0; i < divIcones.length; i++) {
			divIcones[i].style.padding="6px 0";
		}

  		const parent = document.getElementById('container_all');
  		parent.insertBefore(
  			document.getElementById("container_endereco"), parent.children[1]);		
	}

	else if(bodyWidth <= 768 && bodyWidth > 540){
		console.log('Tela: Pequena');	

		sideMenu.style.display="none";
		main.style.width="100%";	
		menuMobile.style.display="flex";

		mainRow.style.width = "100%";

		document.getElementById('row_produtos').style.marginBottom="9.19px";
		document.getElementById('container_all').style.marginBottom="30px";

		containerTabela.style.marginTop="5px";

		containerProdutos.style.paddingTop="0";
		containerProdutos.style.paddingRight="15px";
		containerProdutos.style.paddingBottom="15px";
		containerProdutos.style.paddingLeft="15px";

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.55rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.55rem";
		}

		for(var i = 0; i < formRemoveImg.length; i++) {
			formRemoveImg[i].style.display="block";
			formRemoveImg[i].style.maxWidth="52%";
		}	

		for(var i = 0; i < btnExcluir.length; i++) {
			btnExcluir[i].style.display="none";
		}			

		for(var i = 0; i < selectSuperiorContainer.length; i++) {
			selectSuperiorContainer[i].style.justifyContent="center";
		}		

		for(var i = 0; i < divIcones.length; i++) {
			divIcones[i].style.paddingTop="15px";
			divIcones[i].style.paddingRight="0px";
		}

		for (var i = 0 ; i < containerInformativo.length; i++) {
			containerInformativo[i].style.padding="0 14px";
			containerInformativo[i].style.fontSize="0.75rem";
		}

		for (var i = 0 ; i < conteudoTituloItem.length; i++) {
			conteudoTituloItem[i].style.fontSize="0.70rem";
		}			

		for(var i = 0; i < novoItemLabel.length; i++) {
			novoItemLabel[i].style.fontSize="0.65rem";
		}

		for(var i = 0; i < novoItemInput.length; i++) {
			novoItemInput[i].style.fontSize="0.55rem";
		}

		for(var i = 0; i < botaoAdicionar.length; i++) {
			botaoAdicionar[i].style.fontSize="0.55rem";
			botaoAdicionar[i].style.marginTop="10px";
		}

		for(var i = 0; i < informativoCampo.length; i++) {
			informativoCampo[i].style.fontSize="0.55rem";
		}	

		for(var i = 0; i < img.length; i++) {
			img[i].style.width="16px";
			img[i].style.marginTop="0px";
		}

		for(var i = 0; i < aImg.length; i++) {
			aImg[i].style.marginLeft="0px";
		}		

		for(var i = 0; i < thTipo.length; i++){
			thTipo[i].hidden=true;
		}
		for(var i = 0; i < tdTipo.length; i++){
			tdTipo[i].hidden=true;
		}										

  		const parent = document.getElementById('container_all');
  		parent.insertBefore(
  			document.getElementById("container_endereco"), parent.children[1]);			
	}

	else if(bodyWidth < 540){
		console.log('Tela: Muito pequena');

		sideMenu.style.display="none";
		main.style.width="100%";
		menuMobile.style.display="flex";

		mainRow.style.width = "100%";

		containerTabela.style.marginTop="0px";
		containerProdutos.style.padding="5px 10px 10px 10px";

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.50rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.50rem";
		}

		for(var i = 0; i < formRemoveImg.length; i++) {
			formRemoveImg[i].style.display="block";
			formRemoveImg[i].style.maxWidth="80%";		
		}	

		for(var i = 0; i < btnExcluir.length; i++) {
			btnExcluir[i].style.display="none";
		}		

		for(var i = 0; i < selectSuperiorContainer.length; i++) {
			selectSuperiorContainer[i].style.justifyContent="center";
		}		

		for(var i = 0; i < divIcones.length; i++) {
			divIcones[i].style.paddingTop="5px";
			divIcones[i].style.paddingRight="0px";
		}

		for (var i = 0; i < selectSuperiorContainer.length; i++) {
			selectSuperiorContainer[i].style.marginBottom="10px";
		}	

		for (var i = 0 ; i < containerInformativo.length; i++) {
			containerInformativo[i].style.padding="0 14px";
			containerInformativo[i].style.fontSize="0.70rem";
		}

		for (var i = 0 ; i < conteudoTituloItem.length; i++) {
			conteudoTituloItem[i].style.fontSize="0.70rem";
		}			

		for(var i = 0; i < novoItemLabel.length; i++) {
			novoItemLabel[i].style.fontSize="0.65rem";
		}

		for(var i = 0; i < novoItemInput.length; i++) {
			novoItemInput[i].style.fontSize="0.55rem";
		}

		for(var i = 0; i < botaoAdicionar.length; i++) {
			botaoAdicionar[i].style.fontSize="0.55rem";
			botaoAdicionar[i].style.marginTop="10px";
		}

		for(var i = 0; i < informativoCampo.length; i++) {
			informativoCampo[i].style.fontSize="0.55rem";
		}	

		for(var i = 0; i < img.length; i++) {
			img[i].style.width="14px";
			img[i].style.marginTop="0px";
		}

		for(var i = 0; i < aImg.length; i++) {
			aImg[i].style.marginLeft="0px";
		}				

		for(var i = 0; i < thTipo.length; i++){
			thTipo[i].hidden=true;
		}
		for(var i = 0; i < tdTipo.length; i++){
			tdTipo[i].hidden=true;
		}								

  		const parent = document.getElementById('container_all');
  		parent.insertBefore(
  			document.getElementById("container_endereco"), parent.children[1]);			
	}

	pageResponsiva();
	calculaInformativos();
	ajustaMinMaxDosInputsData();
}

function getScrollbarWidth() {

  // Creating invisible container
  const outer = document.createElement('div');
  outer.style.visibility = 'hidden';
  outer.style.overflow = 'scroll'; // forcing scrollbar to appear
  outer.style.msOverflowStyle = 'scrollbar'; // needed for WinJS apps
  document.body.appendChild(outer);

  // Creating inner element and placing it in the container
  const inner = document.createElement('div');
  outer.appendChild(inner);

  // Calculating difference between container's full width and the child width
  const scrollbarWidth = (outer.offsetWidth - inner.offsetWidth);

  // Removing temporary elements from the DOM
  outer.parentNode.removeChild(outer);

  return scrollbarWidth;
}

/* ================== TRATAMENTO DE ADIÇÃO DE PRODUTOS ====================== */

function resetaBotao() {
	
	var inputTipoEntrada = document.getElementById('input_tipo_entrada');	
	var inputValor = document.getElementById('input_valor');	
	var inputQuantidade = document.getElementById('input_quantidade');

	var botaoAddProduto = document.getElementById('botao_add_produto');
	botaoAddProduto.disabled=false;
	botaoAddProduto.style.background="#2f3d61";		

	inputTipoEntrada.value="TROCA";
	inputValor.value=0.00;
	inputQuantidade.value=0;	
	inputQuantidade.style.background="transparent";
}

function trocaTipoDaEntrada() {

	var tipoDaEntrada = document.getElementById('input_tipo_produto');

	var selectProduto = document.getElementById('select_produto');

	var optionServico = document.getElementById('option_servico');
	var optionProdutos = document.getElementsByClassName('option_produto');

	var labelTipoEntrada = document.getElementById('label_tipo_entrada');
	var inputTipoEntrada = document.getElementById('input_tipo_entrada');

	var labelQuantidade = document.getElementById('label_quantidade')
	var inputQuantidade = document.getElementById('input_quantidade');

	var labelValor = document.getElementById('label_valor')
	var inputValor = document.getElementById('input_valor');	

	var botaoAddProduto = document.getElementById('botao_add_produto');
	botaoAddProduto.disabled=false;
	botaoAddProduto.style.background="#2f3d61";			

	if(tipoDaEntrada.value == "PADRAO_SERVICO") {

		optionServico.hidden=false;
		for(var i = 0; i < optionProdutos.length; i++) {
			optionProdutos[i].hidden=true;
		}

		optionServico.selected=true;

		labelTipoEntrada.style.color="#4444";
		inputTipoEntrada.style.color="#4444";
		inputTipoEntrada.style.borderColor="#4444";
		inputTipoEntrada.disabled=true;

		document.getElementById('option_comum').selected=true;

		labelQuantidade.style.color="#4444";
		inputQuantidade.style.color="#4444";
		inputQuantidade.style.borderColor="#4444";
		inputQuantidade.disabled=true;

		inputQuantidade.style.background="transparent";
		inputQuantidade.value=0;
		botaoAddProduto.disabled=false;
		botaoAddProduto.style.background="#2f3d61";

		labelValor.style.color="#303030";
		inputValor.style.color="#303030";
		inputValor.style.borderColor="grey";
		inputValor.disabled=false;			

	}
	else if (tipoDaEntrada.value == "PADRAO_PRODUTO") {

		optionServico.hidden=true;
		for(var i = 0; i < optionProdutos.length; i++) {
			optionProdutos[i].hidden=false;
		}

		optionProdutos[0].selected=true;

		labelTipoEntrada.style.color="#303030";
		inputTipoEntrada.style.color="#303030";
		inputTipoEntrada.style.borderColor="grey";
		inputTipoEntrada.disabled=false;

		document.getElementById('option_troca').selected=true;

		labelQuantidade.style.color="#303030";
		inputQuantidade.style.color="#303030";
		inputQuantidade.style.borderColor="grey";
		inputQuantidade.disabled=false;

		labelValor.style.color="#303030";
		inputValor.style.color="#303030";
		inputValor.style.borderColor="grey";
		inputValor.disabled=false;		

	}
	else if (tipoDaEntrada.value == "GARANTIA") {

		optionServico.hidden=true;
		for(var i = 0; i < optionProdutos.length; i++) {
			optionProdutos[i].hidden=false;
		}

		optionProdutos[0].selected=true;

		labelTipoEntrada.style.color="#4444";
		inputTipoEntrada.style.color="#4444";
		inputTipoEntrada.style.borderColor="#4444";
		inputTipoEntrada.disabled=true;

		document.getElementById('option_comum').selected=true;

		labelQuantidade.style.color="#303030";
		inputQuantidade.style.color="#303030";
		inputQuantidade.style.borderColor="grey";
		inputQuantidade.disabled=false;

		labelValor.style.color="#4444";
		inputValor.style.color="#4444";
		inputValor.value=0;
		inputValor.style.borderColor="#4444";
		inputValor.disabled=true;		

	}	
}

function validaNovoProduto() {

	var erros = "Ocorreu o seguinte erro na adição do produto ou serviço:\n";

	var inputProduto = document.getElementById('select_produto').value;
	var inputValor = document.getElementById('input_valor').value;
	var inputQuantidade = document.getElementById('input_quantidade').value;

	if (inputProduto == "..." || inputProduto == null || inputProduto == "") {
		erros += "- O campo produto não pode ser vazio\n";
	}

	if(inputValor.replace(" ", "") == "" || inputValor == null) {
		erros += "- O campo valor não pode ser vazio\n";
	}
	if(parseFloat(inputValor) < 0) {
		erros += "- O campo valor não pode ser menor do que zero\n";
	}
	if(inputQuantidade.replace(" ", "") == "" || inputQuantidade == null) {
		erros += "- O campo quantidade não pode ser vazio\n";
	}
	if(parseInt(inputQuantidade) < 1  && inputProduto != "Serviço") {
		erros += "- O campo quantidade não pode ser menor do que 1";
	}

	if (erros != "Ocorreu o seguinte erro na adição do produto ou serviço:\n") {
		var quantidade = 0

		for (var i = 0; i < erros.length; i++) {
		  if (erros[i] == "-") {
		    quantidade++;
		  }
		}

		if (quantidade > 1) {
			erros = erros.replace("Ocorreu o seguinte erro", "Ocorreram os seguintes erros");
		}

		alert(erros);
		return false;

	}
	else {
		return true;
	}
}

function resetNovoProduto() {

	var indicadorEdicao = document.getElementById('indicador_edicao');

	var inputTipoProduto = document.getElementById('input_tipo_produto');
	var inputTipoEntrada = document.getElementById('input_tipo_entrada');
	var inputProduto = document.getElementById('select_produto');
	var inputValor = document.getElementById('input_valor');
	var inputQuantidade = document.getElementById('input_quantidade');
	var optionProdutos = document.getElementsByClassName('option_produto');
	var optionServico = document.getElementById('option_servico');
	var labelTipoEntrada = document.getElementById('label_tipo_entrada');
	var labelQuantidade = document.getElementById('label_quantidade');	
	var botaoAddProduto = document.getElementById('botao_add_produto');

	if(optionProdutos.length > 0) {

		inputTipoProduto.value="PADRAO_PRODUTO";
		inputTipoEntrada.value="TROCA";
		inputValor.value=0.00;
		inputQuantidade.value=0;

		optionServico.hidden=true;
		for(var i = 0; i < optionProdutos.length; i++) {
			optionProdutos[i].hidden=false;
		}

		optionProdutos[0].selected=true;

		labelTipoEntrada.style.color="#303030";
		inputTipoEntrada.style.color="#303030";
		inputTipoEntrada.style.borderColor="grey";
		inputTipoEntrada.disabled=false;

		labelQuantidade.style.color="#303030";
		inputQuantidade.style.color="#303030";
		inputQuantidade.style.borderColor="grey";
		inputQuantidade.disabled=false;	

	}

	else {

		inputTipoProduto.value="PADRAO_SERVICO";
		inputTipoEntrada.value="TROCA";
		inputValor.value=0.00;
		inputQuantidade.value=0;

		optionServico.hidden=false;
		optionServico.selected=true;

		labelTipoEntrada.style.color="#4444";
		inputTipoEntrada.style.color="#4444";
		inputTipoEntrada.style.borderColor="#4444";
		inputTipoEntrada.disabled=true;

		labelQuantidade.style.color="#4444";
		inputQuantidade.style.color="#4444";
		inputQuantidade.style.borderColor="#4444";
		inputQuantidade.disabled=true;

		inputQuantidade.style.background="transparent";
		inputQuantidade.value=0;
		botaoAddProduto.disabled=false;
		botaoAddProduto.style.background="#2f3d61";		

	}
}

function addNovoProduto() {

		var inputTipoProduto = document.getElementById('input_tipo_produto');
		var inputTipoEntrada = document.getElementById('input_tipo_entrada');
		var inputProduto = document.getElementById('select_produto');
		var inputValor = document.getElementById('input_valor');
		var inputQuantidade = document.getElementById('input_quantidade');

		var inputEntradas = document.getElementById('input_entradas');	

	if (validaNovoProduto()) {

		var string = "ENTRADA=";

		string += inputTipoProduto.value
		+ ";" + inputTipoEntrada.value
		+ ";" + inputProduto.value 
		+ ";" + (parseFloat(inputValor.value)).toFixed(2)
		+ ";" + inputQuantidade.value + ";";

		var inputEntradasSplitPai = inputEntradas.value.split("ENTRADA=");
		for(var i = 1; i < inputEntradasSplitPai.length; i++) {
			if (inputEntradasSplitPai[i].split(";")[2] == inputProduto.value) {
				alert("O produto " + inputProduto.value + " já está adicionado nessa ordem");
				return;				
			}
		}		


		if (((inputEntradas.value).split("ENTRADA=")).length > 7) {
			alert("Número máximo de entradas adicionado à ordem.\n");
			return;	
		}


		inputEntradas.value = inputEntradas.value + string;

		resetNovoProduto();
		calculaInformativos();

		inputValor.style.background="transparent";

		document.getElementById('sucessoCadastro').hidden=false;
		document.getElementById('sucessoCadastro').innerText="Nova entrada inserida à ordem com sucesso";		

		inputTipoProduto.focus();
		AjustaTabelaDeProdutos(string);
		responsive();

	}
	else {
		inputTipoProduto.focus();
	}
}

/* ================== TRATAMENTO DE ADIÇÃO DE PAGAMENTOS ====================== */

function trocaFormaDePagamento() {

	var selectFormaPagamento = document.getElementById('select_forma_pagamento');
	var inputDataAgendamento = document.getElementById('input_dt_agendamento');
	var labelDataAgendamento = document.getElementById('label_dt_agendamento');

	if (selectFormaPagamento.value == "FATURADO") {

		inputDataAgendamento.disabled=false;

		inputDataAgendamento.style.color="#303030";
		inputDataAgendamento.style.border="1px solid grey";

		labelDataAgendamento.style.color="#303030";

	}
	else {

		inputDataAgendamento.disabled=true;

		inputDataAgendamento.style.color="#4444";
		inputDataAgendamento.style.border="1px solid #4444";

		labelDataAgendamento.style.color="#4444";		

	}
}

function validaNovoPagamento() {

	var erros = "Ocorreu o seguinte erro na adição do pagamento:\n";

	var inputValor = document.getElementById('input_valor_pagamento').value;
	var inputDataAgendamento = document.getElementById('input_dt_agendamento').value;

	if(inputValor.replace(" ", "") == "" || inputValor == null) {
		erros += "- O campo valor não pode ser vazio\n";
	}
	if(parseFloat(inputValor) < 0) {
		erros += "- O campo valor não pode ser menor do que zero\n";
	}
	if(parseFloat(inputValor) == 0) {
		erros += "- O campo valor não pode ser igual a zero\n";
	}
	if(inputDataAgendamento != null && inputDataAgendamento != "") {
		if(inputDataAgendamento.split("-").length < 3) {
			erros += "- O campo data de agendamento deve possuir dia mês e ano";		
		}
	}

	if (erros != "Ocorreu o seguinte erro na adição do pagamento:\n") {
		var quantidade = 0

		for (var i = 0; i < erros.length; i++) {
		  if (erros[i] == "-") {
		    quantidade++;
		  }
		}

		if (quantidade > 1) {
			erros = erros.replace("Ocorreu o seguinte erro", "Ocorreram os seguintes erros");
		}

		alert(erros);
		return false;

	}
	else {
		return true;
	}	
}

function resetNovoPagamento() {

	var inputFormaPagamento = document.getElementById('select_forma_pagamento');
	var inputValorPagamento = document.getElementById('input_valor_pagamento');
	var labelDataAgendamento = document.getElementById('label_dt_agendamento');
	var inputDataAgendamento = document.getElementById('input_dt_agendamento');
	var inputObservacao = document.getElementById('input_observacao');

	document.getElementById('option_dinheiro').selected=true;

	inputValorPagamento.value=0.0;
	inputObservacao.value="";

	inputDataAgendamento.disabled=true;
	inputDataAgendamento.style.color="#4444";
	inputDataAgendamento.style.border="1px solid #4444";

	labelDataAgendamento.style.color="#4444";

	inputDataAgendamento.value="";	
}

function addNovoPagamento() {

	if (validaNovoPagamento()) {

		var string = "PAGAMENTO=";

		var inputFormaPagamento = document.getElementById('select_forma_pagamento');
		var inputValorPagamento = document.getElementById('input_valor_pagamento');
		var inputDataAgendamento = document.getElementById('input_dt_agendamento');
		var inputObservacao = document.getElementById('input_observacao');

		var inputPagamentos = document.getElementById('input_pagamentos');

		var obsValue = inputObservacao.value;
		var dataAgendamentoValue = inputDataAgendamento.value;

		if (obsValue == "") {
			obsValue = "Sem observação";
		}

		if (dataAgendamentoValue == "") {
			dataAgendamentoValue = "Sem agendamento"
		}

		string += inputFormaPagamento.value
		+ ";" + inputValorPagamento.value
		+ ";" + dataAgendamentoValue 
		+ ";" + obsValue + ";";

		inputPagamentos.value = inputPagamentos.value + string;

		resetNovoPagamento();
		calculaInformativos();

		document.getElementById('sucessoCadastro').hidden=false;
		document.getElementById('sucessoCadastro').innerText="Novo pagamento inserido à ordem com sucesso";

		inputFormaPagamento.focus();
	}
	else {
		if (inputFormaPagamento != null) {
			setTimeout(() => {inputFormaPagamento.focus()},10);	
			
		}
	}
}

/* ================== TRATAMENTO DE INFORMATIVOS ====================== */

function calculaInformativos() {

	var totalEntradas = 0;
	var totalPagamentos = 0;

	var inputPagamentos = document.getElementById('input_pagamentos');
	var inputEntradas = document.getElementById('input_entradas');

	var inputPagamentosSplitPai = inputPagamentos.value.split("PAGAMENTO=");
	var inputEntradasSplitPai = inputEntradas.value.split("ENTRADA=");

	for(var i = 1; i < inputEntradasSplitPai.length; i++) {
		totalEntradas += parseFloat(inputEntradasSplitPai[i].split(";")[3]);
	}

	for(var i = 1; i < inputPagamentosSplitPai.length; i++) {
		totalPagamentos += parseFloat(inputPagamentosSplitPai[i].split(";")[1]);
	}

	document.getElementById('informativo_total').innerText=totalEntradas.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
	document.getElementById('informativo_pago').innerText=totalPagamentos.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });

	var troco = 0;
	if (totalPagamentos - totalEntradas > 0) {
		troco = totalPagamentos - totalEntradas;
		informativo_troco.style.color="#ba4c49";
	}
	else {
		informativo_troco.style.color="#2e8c4e";
	}
	
	var restaPagar = 0;
	if (totalEntradas - totalPagamentos > 0) {
		restaPagar = totalEntradas - totalPagamentos;
		informativo_resta.style.color="#ba4c49"
	}
	else {
		informativo_resta.style.color="#2e8c4e";
	}

	document.getElementById('informativo_troco').innerText=troco.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
	document.getElementById('informativo_resta').innerText=restaPagar.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
}

/* ================== TRATAMENTO DE INPUTS ====================== */

/* ESCOLHENDO CLIENTE DA LISTA */
function escolhaCliente() {
	if(document.getElementById('input_nome').value.includes(" | ")) {

		var optionCliente = document.getElementsByClassName('option_cliente');
		var inputCliente = document.getElementById('input_nome');
		var inputClienteSplitado = inputCliente.value.split("|");
		var id = inputClienteSplitado[0].replace(/\s/g, '').replace("Id", "");

		var idSelecionado = null

		for(var i = 0; i < optionCliente.length; i++) {
			if (optionCliente[i].id == id) {
				idSelecionado = id;
			}
		}

		var optionSelecionada = document.getElementById(id);

		resetaCliente();

		document.getElementById('input_nome').value=optionSelecionada.getAttribute('data-nome');
		document.getElementById('input_telefone').value=optionSelecionada.getAttribute('data-telefone');
		document.getElementById('input_email').value=optionSelecionada.getAttribute('data-email');
		document.getElementById('input_cpfCnpj').value=optionSelecionada.getAttribute('data-cpfCnpj');
		document.getElementById('input_dataNascimento').value=optionSelecionada.getAttribute('data-nascimento');

		document.getElementById('cep_input').value=optionSelecionada.getAttribute('data-endereco-cep');
		document.getElementById('estado_input').value=optionSelecionada.getAttribute('data-endereco-estado');
		document.getElementById('cidade_input').value=optionSelecionada.getAttribute('data-endereco-cidade');
		document.getElementById('bairro_input').value=optionSelecionada.getAttribute('data-endereco-bairro');
		document.getElementById('logradouro_input').value=optionSelecionada.getAttribute('data-endereco-logradouro');
		document.getElementById('input_numero').value=optionSelecionada.getAttribute('data-endereco-numero');
		document.getElementById('input_complemento').value=optionSelecionada.getAttribute('data-endereco-complemento');

		$('#select_tecnico').focus();
	}
}

function resetaCliente() {
	document.getElementById('input_telefone').value="";
	document.getElementById('input_email').value="";
	document.getElementById('input_cpfCnpj').value="";
	document.getElementById('input_dataNascimento').value="";

	document.getElementById('cep_input').value="";
	document.getElementById('estado_input').value="SP";
	document.getElementById('cidade_input').value="";
	document.getElementById('bairro_input').value="";
	document.getElementById('logradouro_input').value="";
	document.getElementById('input_complemento').value="";	
	document.getElementById('input_numero').value="";
}

/* TRATAMENTO DO CAMPO QUANTIDADE */

function tratamentoCampoQuantidade() {

	var inputQuantidade = document.getElementById('input_quantidade');
	var selectProduto = document.getElementById('select_produto');
	var campoProduto = 0.0;
	var botaoAddProduto = document.getElementById('botao_add_produto');

	if(selectProduto.value != 'Serviço') {

		if (selectProduto.value == "..." || selectProduto == "") {
			campoProduto = 0.0;
		}
		else {
			var campoProduto = 
			parseInt(((selectProduto.options[selectProduto.selectedIndex].text)
				.split("|")[0]).replace(/\s/g, '').replace("Qtd:", ""));
		}

		if (parseInt(inputQuantidade.value) > campoProduto) {
			inputQuantidade.style.background="#f5aea9";
			botaoAddProduto.disabled=true;
			botaoAddProduto.style.background="grey";
			return false;
		}
		else {
			inputQuantidade.style.background="transparent";
			botaoAddProduto.disabled=false;
			botaoAddProduto.style.background="#2f3d61";
			return true;
		}

	}
}

/* TRATAMENTO DO CAMPO EMAIL */
function tratamentoCampoEmail() {

	var emailRegex =  new RegExp("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)" +
                    "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");

	var inputEmail = document.getElementById('input_email');

	if (inputEmail.value != "") {

		if (emailRegex.test(inputEmail.value)) {
			inputEmail.style.background="transparent";
			if(inputEmail.value.includes(".com") 
				&& document.activeElement.id == inputEmail.id) {
				$('#input_complemento').focus();
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
function tratamentoCampoTelefone() {

	var telefoneRegex =  new RegExp("^\\([1-9]{2}\\)[9]{0,1}[1-9]{1}[0-9]{3}\\-[0-9]{4}$");

	var inputTelefone = document.getElementById('input_telefone');

	inputTelefone.value = inputTelefone.value.replace(/([a-zA-Z ])/g, "");

	if (inputTelefone.value != "") {

		if (telefoneRegex.test(inputTelefone.value)) {
			inputTelefone.style.background="transparent";
			if(document.activeElement.id == inputTelefone.id) {
				$('#input_dataNascimento').focus();
			}
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
function tratamentoCampoCpfCnpj() {

	var cnpjRegex =  new RegExp("[0-9]{2}.[0-9]{3}.[0-9]{3}/000[1-9]-[0-9]{2}");
	var cpfRegex =  new RegExp("[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}");

	var inputCpfCnpj = document.getElementById('input_cpfCnpj');

	inputCpfCnpj.value = inputCpfCnpj.value.replace(/([a-zA-Z ])/g, "");

	if (inputCpfCnpj.value != "") {

		if (cpfRegex.test(inputCpfCnpj.value) || cnpjRegex.test(inputCpfCnpj.value)) {
			inputCpfCnpj.style.background="transparent";
			if (document.activeElement.id == inputCpfCnpj.id) {
				$('#cep_input').focus();
			}
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

/* TRATAMENTO DO CAMPO CEP */
function tratamentoCampoCep() {

	var cepRegex = new RegExp("[0-9]{8}");

	var inputCep = document.getElementById('cep_input');

	var fraseSemCaracteresEspeciais = inputCep.value.normalize("NFD").replace(/[^a-zA-Z0-9s]/g, "")
	inputCep.value = fraseSemCaracteresEspeciais.replace(/([a-zA-Z ])/g, "");		

	if (inputCep.value != "") {

		if (cepRegex.test(inputCep.value)) {
			inputCep.style.background="transparent";
			return true;
		}
		else {
			inputCep.style.background="#f5aea9";
			return false;
		}
	}
	else {
		inputCep.style.background="transparent";
		return true;
	}
}

/* REALIZA VALIDAÇÃO DE ATRIBUTOS NULOS NO OBJETO CLIENTE */
function validacaoDoObjetoCliente() {

	var inputNome = document.getElementById('input_nome');
	var inputTelefone = document.getElementById('input_telefone');
	var inputEmail = document.getElementById('input_email');
	var inputCpfCnpj = document.getElementById('input_cpfCnpj');
	var inputDataNascimento = document.getElementById('input_dataNascimento');

	if (inputNome != null 
				&& document.activeElement.id == inputNome.id) {
		inputNome.value = inputNome.value.normalize("NFD").replace(/[\u0300-\u036f]/g, "")		
	}	

	if (inputEmail != null 
				&& document.activeElement.id == inputEmail.id) {
		inputEmail.value = inputEmail.value.normalize("NFD").replace(/[\u0300-\u036f]/g, "")		
	}		

	if (inputDataNascimento != null) {
		if (inputDataNascimento.value.split("-").length == 3) {
			if (inputDataNascimento.value.split("-")[0] > 1900 
				&& document.activeElement.id == inputDataNascimento.id) {
				inputCpfCnpj.focus();
			}
		}
	}	

	if(inputTelefone.value != "" 
		|| inputEmail.value != "" 
		|| inputCpfCnpj.value != "" 
		|| inputDataNascimento.value != "") {

		if(inputNome.value == "") {
			inputNome.style.background="#f5aea9";
			return false;			
		}
		else {
			inputNome.style.background="transparent";
			return true;			
		}

	}

	else {
		inputNome.style.background="transparent";
		return true;		
	}
}

/* REALIZA VALIDAÇÃO DE ATRIBUTOS NULOS NO OBJETO ENDEREÇO */
function validacaoDoObjetoEndereco() {

	var inputCep = document.getElementById('cep_input');
	var inputCidade = document.getElementById('cidade_input');
	var inputBairro = document.getElementById('bairro_input');
	var inputLogradouro = document.getElementById('logradouro_input');
	var inputNumero = document.getElementById('input_numero');
	var inputComplemento = document.getElementById('input_complemento');

	if (inputCidade != null) {
		inputCidade.value = inputCidade.value.normalize("NFD").replace(/[\u0300-\u036f]/g, "")		
	}	

	if (inputBairro != null) {
		inputBairro.value = inputBairro.value.normalize("NFD").replace(/[\u0300-\u036f]/g, "")		
	}	

	if (inputLogradouro != null) {
		inputLogradouro.value = inputLogradouro.value.normalize("NFD").replace(/[\u0300-\u036f]/g, "")		
	}	

	if (inputComplemento != null) {
		inputComplemento.value = inputComplemento.value.normalize("NFD").replace(/[\u0300-\u036f]/g, "")		
	}

	if(inputCep.value != "" 
		|| inputCidade.value != "" 
		|| inputBairro.value != "" 
		|| inputLogradouro.value != ""
		|| inputNumero.value != ""
		|| inputComplemento.value != "") {

		if(inputLogradouro.value == "") {
			inputLogradouro.style.background="#f5aea9";	
		}
		else {
			inputLogradouro.style.background="transparent";		
		}

		if(inputNumero.value == "") {
			inputNumero.style.background="#f5aea9";		
		}
		else {
			inputNumero.style.background="transparent";		
		}		

	}

	else {
		inputLogradouro.style.background="transparent";
		inputNumero.style.background="transparent";		
		return true;		
	}
}

/* VALIDAÇÃO DE TODOS OS CAMPOS */
function validacaoCampos() {

	var botaoFinalizar = document.getElementById('botao_finalizar');

	var inputPagamentos = document.getElementById('input_pagamentos');
	var inputEntradas = document.getElementById('input_entradas');

	var inputPagamentosSplitted = inputPagamentos.value.split("PAGAMENTO=");
	var inputEntradasSplitted = inputEntradas.value.split("ENTRADA=");

	var totEntradas = 0.0;
	var totPagamentos = 0.0;

	for(var i = 1; i < inputEntradasSplitted.length; i++) {
		totEntradas += parseFloat(inputEntradasSplitted[i].split(";")[3]);
	}

	for(var i = 1; i < inputPagamentosSplitted.length; i++) {
		totPagamentos += parseFloat(inputPagamentosSplitted[i].split(";")[1]);
	}

	/* ======================================================================= */

	var inputTelefone = document.getElementById('input_telefone');
	var inputEmail = document.getElementById('input_email');
	var inputCep = document.getElementById('cep_input');
	var inputCidade = document.getElementById('cidade_input');
	var inputBairro = document.getElementById('bairro_input');
	var inputLogradouro = document.getElementById('logradouro_input');
	var inputNumero = document.getElementById('input_numero');
	var inputComplemento = document.getElementById('input_complemento');	
	var inputCpfCnpj = document.getElementById('input_cpfCnpj');
	var selectTecnico = document.getElementById('select_tecnico');

	var erros = "Ocorreram alguns erros no lançamento da ordem:\n";

	if(inputTelefone.value != "" && !tratamentoCampoTelefone()) {
		erros += "- Telefone com padrão incorreto\n";
	}
	if(inputEmail.value != "" && !tratamentoCampoEmail()) {
		erros += "- Email com padrão incorreto\n";
	}
	if(inputCep.value != "" && !tratamentoCampoCep()) {
		erros += "- Cep com padrão incorreto\n";
	}
	if(inputCpfCnpj.value != "" && !tratamentoCampoCpfCnpj()) {
		erros += "- CPF/CNPJ com padrão incorreto\n";
	}	
	if(inputEntradas.value == "") {
		erros += "- Nenhuma entrada adicionada à ordem\n";
	}
	if(selectTecnico.value == "" || selectTecnico == null) {
		erros += "- É necessário adicionar um técnico à ordem\n";
	}

	if(inputCep.value != "" 
		|| inputCidade.value != "" 
		|| inputBairro.value != "" 
		|| inputLogradouro.value != ""
		|| inputNumero.value != ""
		|| inputComplemento.value != "") {

		if(inputLogradouro.value == "" || inputLogradouro.value == null) {
			erros += "- Ao preencher o endereço, o campo logradouro é obrigatório\n"
		}
		if(inputNumero.value == "" || inputNumero.value == null) {
			erros += "- Ao preencher o endereço, o campo número é obrigatório\n"
		}		

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
		if (totEntradas > totPagamentos) {
			if (confirm("O valor pago é menor do que o valor dos lançamentos." + 
				"\n====================================\nTotal da ordem:                 " 
				+ totEntradas.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) 
				+ "\nValor pago:                        " + totPagamentos.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
				+ "\nValor em aberto:                " + (parseFloat(totEntradas) - parseFloat(totPagamentos)).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
				+ "\n====================================\nDeseja prosseguir mesmo assim?") == true) {
				botaoFinalizar.type="submit";
			}
		}
		else if (totEntradas < totPagamentos) {
			if (confirm("O valor pago é maior do que o valor dos lançamentos." + 
				"\n====================================\nTotal da ordem:                 " 
				+ totEntradas.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) 
				+ "\nValor pago:                        " + totPagamentos.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
				+ "\nTroco pendente:                " + (parseFloat(totPagamentos) - parseFloat(totEntradas)).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
				+ "\n====================================\nDeseja prosseguir mesmo assim?") == true) {
				botaoFinalizar.type="submit";
			}
		}
		else {
			botaoFinalizar.type="submit";
		}

	}
}

/* ================== RESETS ====================== */

/* RESETA QUANTIDADE SE VAZIA */
function resetQuantidadeSeVazia() {
	if (document.getElementById('input_quantidade').value == "") {
		document.getElementById('input_quantidade').value = 0;
	}	
}

/* RESETA VALOR SE VAZIO */
function resetValorSeVazio() {
	if (document.getElementById('input_valor').value == "") {
		document.getElementById('input_valor').value = 0.0;
	}
}

function resetValorPagamentoSeVazio() {
	if (document.getElementById('input_valor_pagamento').value == "") {
		document.getElementById('input_valor_pagamento').value = 0.0;
	}	
}

/* ====================== Ajuste MIN e MAX das datas ========================= */

function ajustaMinMaxDosInputsData() {

	var inputDataNascimento = document.getElementById('input_dataNascimento');
	var inputAgendamentoPagamento = document.getElementById('input_dt_agendamento');
	var inputAgendamentoRetirada = document.getElementById('input_agendamento_retirada');

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

	inputAgendamentoPagamento.min=hoje;
	inputAgendamentoRetirada.min=hoje;	
}

/* ====================== Ajuste na tabela ========================= */

function exibeTabela() {

	var containerProdutos = document.getElementById('container_produtos');
	var containerPrincipal = document.getElementById('conteudo_container');
	var menuSuperior = document.getElementById('menu_superior');
	var menuSuperiorMobile = document.getElementById('menu_superior_mobile');
	var sideMenu = document.getElementById('side_menu');
	containerProdutos.hidden=false;

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

	/*var containerInformativoAll = document.getElementById('container_informativo_all');
	
	if(containerInformativoAll.hidden==true) {
		containerInformativoAll.hidden=false;
		containerInformativoAll.style.transition="2s";
		$("#eye").attr("src","img/eye-green.png");
		$("#eye").attr("th:src", "@{img/eye-green.png}");
	}	
	else if(containerInformativoAll.hidden==false) {
		containerInformativoAll.hidden=true;
		containerInformativoAll.style.transition="2s";
		$("#eye").attr("src","img/eye-grey.png");
		$("#eye").attr("th:src", "@{img/eye-grey.png}");
	}*/
}

function fecharTabelaProdutos() {

	var containerProdutos = document.getElementById('container_produtos');
	var containerPrincipal = document.getElementById('conteudo_container');
	var menuSuperior = document.getElementById('menu_superior');
	var menuSuperiorMobile = document.getElementById('menu_superior_mobile');
	var sideMenu = document.getElementById('side_menu');

	containerProdutos.hidden=true;

	containerPrincipal.style.pointerEvents="auto";
	containerPrincipal.style.opacity="1";
	containerPrincipal.style.transition="1s";

	menuSuperior.style.pointerEvents="auto";
	menuSuperior.style.opacity="1";
	menuSuperior.style.transition="1s";

	menuSuperiorMobile.style.pointerEvents="auto";
	menuSuperiorMobile.style.opacity="1";
	menuSuperiorMobile.style.transition="1s";	

	sideMenu.style.pointerEvents="auto";
	sideMenu.style.opacity="1";
	sideMenu.style.transition="1s";	
}

function AjustaTabelaDeProdutos(inputEntradas) {

	var totalEntradas = 0;

	var inputEntradasSplitPai = inputEntradas.split("ENTRADA=");

	document.getElementById('tr_base').hidden=true;

	for(var i = 1; i < inputEntradasSplitPai.length; i++) {

		var tipo = null;

		if ((inputEntradasSplitPai[i].split(";")[0]) == "PADRAO_SERVICO") {
			tipo = "Serviço"
		}
		else if ((inputEntradasSplitPai[i].split(";")[0]) == "PADRAO_PRODUTO") {
			tipo = "Produto";
		}
		else {
			tipo = "Garantia"
		}

		$(table).find('tbody').append(
			"<tr class='tr_novo_body col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12'>" +
				"<td class='td_novo td_tipo col-xl-2 col-lg-2 col-md-2 col-sm-2 col-2'>" 
					+ tipo +
				"</td>" +
				"<td class='td_novo td_produto col-xl-4 col-lg-4 col-md-4 col-sm-4 col-4'>"
					+ (inputEntradasSplitPai[i].split(";")[2]) + 
				"</td>" +
				"<td class='td_novo td_quantidade col-xl-2 col-lg-2 col-md-2 col-sm-3 col-3'>" 
					+ (inputEntradasSplitPai[i].split(";")[4]) + "</td>" +
				"<td class='td_novo td_valor col-xl-3 col-lg-3 col-md-3 col-sm-4 col-4'>" 
					+ parseFloat((inputEntradasSplitPai[i].split(";")[3])).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) + "</td>" +
				"<td class='td_novo td_checkbox col-xl-1 col-lg-1 col-md-1 col-sm-1 col-1'>"
					+ "<form class='form_remove_img'>"
						+ "<input type='text' hidden/>"
						+ "<button type='submit' class='col-lg-12 btn_excluir_img'>"
							+ "<img src='img/minus-green.png' th:src='@{img/minus-green.png}' class='excluir_img' id='excluir_img'/>"
						+ "</button>"
					+ "</form>"	
					+ "<form class='form_remove_text'>"
						+ "<input type='text' hidden/>"
						+ "<button type='submit' class='col-lg-12 btn_excluir'>"
							+ "<div class='excluir_text' id='excluir_text'>"
								+ "Excluir"
							+ "</div>"
						+ "</button>"
					+ "</form>"
				+ "</td>"

			+ "</tr>");		
	}

	responsive();
}

function removeItemTb(item) {

	var inputEntradas = document.getElementById('input_entradas');

	var entrada = (inputEntradas.value.split("ENTRADA="))[item];

	if(inputEntradas.value.includes(entrada)) {
		inputEntradas.value=inputEntradas.value.replace("ENTRADA=" + entrada, "");
	}

	document.getElementsByClassName('tr_novo_body')[parseInt(item)-1].parentNode.removeChild(document.getElementsByClassName('tr_novo_body')[parseInt(item)-1]);
	calculaInformativos();

	if (document.getElementsByClassName('tr_novo_body').length < 1) {
		document.getElementById('tr_base').hidden=false;
	}
}

/* ====================== Setup de edição ========================= */

function setupEdicao() {
	setupSelects();
}

function setupSelects() {
	var setupTipoNfe = document.getElementById('setup_tipoNfe');
	var setupLoja = document.getElementById('setup_loja');
	var setupStatusRetirada = document.getElementById('setup_statusRetirada');
	var setupTecnico = document.getElementById('setup_tecnico');

	var optionNfe = document.getElementsByClassName('option_nfe');
	var optionLoja = document.getElementsByClassName('option_loja');
	var optionStatusRetirada = document.getElementsByClassName('option_status_retirada');	
	var optionTecnico = document.getElementsByClassName('option_tecnico');	

	if (setup_statusRetirada.value == "ENTREGA_EM_TRANSITO") {
		document.getElementById('label_agendamento_retirada').style.color="#303030";
		document.getElementById('input_agendamento_retirada').style.borderColor="grey";
		document.getElementById('input_agendamento_retirada').style.color="#303030";
		document.getElementById('input_agendamento_retirada').disabled=false;
	}

	if (setupTipoNfe.value != null) {
		for(var i = 0; i < optionNfe.length; i++) {
			if (optionNfe[i].value == setupTipoNfe.value) {
				optionNfe[i].selected=true;
			}
		}
	}

	if (setupLoja.value != null) {
		for(var i = 0; i < optionLoja.length; i++) {
			if (optionLoja[i].value == setupLoja.value) {
				optionLoja[i].selected=true;
			}
		}
	}	

	if (setupStatusRetirada.value != null) {
		for(var i = 0; i < optionStatusRetirada.length; i++) {
			if (optionStatusRetirada[i].value == setupStatusRetirada.value) {
				optionStatusRetirada[i].selected=true;
			}
		}
	}	

	if (setupTecnico.value != null) {
		for(var i = 0; i < optionTecnico.length; i++) {
			if (optionTecnico[i].value == setupTecnico.value) {
				optionTecnico[i].selected=true;
			}
		}
	}	
}

/* ====================== Setups padrão ========================= */

function setupProdutosVazios() {

	var indicadorEdicao = document.getElementById('indicador_edicao');

	var inputTipoProduto = document.getElementById('input_tipo_produto');
	var inputTipoEntrada = document.getElementById('input_tipo_entrada');
	var inputProduto = document.getElementById('select_produto');
	var inputValor = document.getElementById('input_valor');
	var inputQuantidade = document.getElementById('input_quantidade');
	var optionProdutos = document.getElementsByClassName('option_produto');
	var optionServico = document.getElementById('option_servico');
	var labelTipoEntrada = document.getElementById('label_tipo_entrada');
	var labelQuantidade = document.getElementById('label_quantidade');	
	var botaoAddProduto = document.getElementById('botao_add_produto');

	var optionEntradaPadrao = document.getElementById('entrada_padrao');
	var optionEntradaServico = document.getElementById('padrao_servico');
	var optionEntradaGarantia = document.getElementById('entrada_garantia');

	if(optionProdutos.length == 0) {

		inputTipoProduto.value="PADRAO_SERVICO";
		inputTipoEntrada.value="TROCA";
		inputValor.value=0.00;
		inputQuantidade.value=0;

		optionServico.hidden=false;
		optionServico.selected=true;

		labelTipoEntrada.style.color="#4444";
		inputTipoEntrada.style.color="#4444";
		inputTipoEntrada.style.borderColor="#4444";
		inputTipoEntrada.disabled=true;

		labelQuantidade.style.color="#4444";
		inputQuantidade.style.color="#4444";
		inputQuantidade.style.borderColor="#4444";
		inputQuantidade.disabled=true;

		inputQuantidade.style.background="transparent";
		inputQuantidade.value=0;
		botaoAddProduto.disabled=false;
		botaoAddProduto.style.background="#2f3d61";

		optionEntradaPadrao.disabled=true;
		optionEntradaPadrao.style.color="#4444";

		optionEntradaGarantia.disabled=true;
		optionEntradaGarantia.style.color="#4444";

	}

}

function setupTecnicosVazios() {

	var optionTecnico = document.getElementsByClassName('option_tecnico');
	var selectTecnico = document.getElementById('select_tecnico');
	var labelTecnico = document.getElementById('label_tecnico');

	if (optionTecnico.length == 0) {
		selectTecnico.disabled=true;
		selectTecnico.style.borderColor="#4444";
		labelTecnico.style.color="#4444";
	}

}

/* ============================= Miscelânia ================================== */

function avancarBloco() {

	var idAtual = (document.activeElement.id);
	if(idAtual == "input_tipoNfe" 
		|| idAtual == "input_ponto" 
		|| idAtual == "input_tipo_retirada") {
		document.getElementById('input_nome').focus();
	}
	else if(
		idAtual == "input_nome"
		|| idAtual == "input_veiculo"
		|| idAtual == "input_telefone"
		|| idAtual == "input_dataNascimento"
		|| idAtual == "input_cpfCnpj") {
		document.getElementById('cep_input').focus();
	}
	else if(
		idAtual == "cep_input"
		|| idAtual == "estado_input"
		|| idAtual == "cidade_input"
		|| idAtual == "bairro_input"
		|| idAtual == "logradouro_input"
		|| idAtual == "input_numero"
		|| idAtual == "input_complemento") {
		document.getElementById('select_tecnico').focus();
	}
	else if(
		idAtual == "select_tecnico"
		|| idAtual == "input_agendamento_retirada"
		|| idAtual == "input_email"
		|| idAtual == "input_retirada_observacao") {
		document.getElementById('input_tipo_produto').focus();
	}
	else if(
		idAtual == "input_tipo_produto"
		|| idAtual == "input_tipo_entrada"
		|| idAtual == "select_produto"
		|| idAtual == "input_valor"
		|| idAtual == "input_quantidade"
		|| idAtual == "botao_add_produto") {
		document.getElementById('select_forma_pagamento').focus();
	}
	else if(
		idAtual == "select_forma_pagamento"
		|| idAtual == "input_valor_pagamento" 
		|| idAtual == "input_observacao"
		|| idAtual == "input_dt_agendamento"
		|| idAtual == "botao_add_pagamento") {
		document.getElementById('botao_finalizar').focus();
	}
	else {
		document.getElementById('input_tipoNfe').focus();
	}
}

function mudaTipoDaEntrega() {

	var inputTipoRetirada = document.getElementById('input_tipo_retirada');
	var inputAgendamentoRetirada = document.getElementById('input_agendamento_retirada');
	var labelAgendamentoRetirada = document.getElementById('label_agendamento_retirada');
	if(inputTipoRetirada.value == "LOJA_FISICA") {
		inputAgendamentoRetirada.disabled=true;
		inputAgendamentoRetirada.style.borderColor="#4444";
		inputAgendamentoRetirada.style.color="#4444";
		labelAgendamentoRetirada.style.color="#4444"
	}
	else if(inputTipoRetirada.value == "ENTREGA_EM_TRANSITO") {
		inputAgendamentoRetirada.disabled=false;
		inputAgendamentoRetirada.style.borderColor="grey";
		inputAgendamentoRetirada.style.color="#303030";
		labelAgendamentoRetirada.style.color="#303030"
	}
	else if(inputTipoRetirada.value == "ENTREGA_ENTREGUE") {
		inputAgendamentoRetirada.disabled=true;
		inputAgendamentoRetirada.style.borderColor="#4444";
		inputAgendamentoRetirada.style.color="#4444";
		labelAgendamentoRetirada.style.color="#4444"
	}
}

function consultaEndereco() {

	tratamentoCampoCep();

	var cep = document.querySelector('#cep_input');


	if (cep.value.length != 8) {
		return;
	}

	let url = 'https://viacep.com.br/ws/' + cep.value + '/json';

	fetch(url).then(function(response){
		response.json().then(function(data){
			if(data.erro == undefined) {
				mostrarEndereco(data);
			}
		})
	});
}

function mostrarEndereco(dados) {

	var estadoInput = document.getElementById('estado_input');
	var cidadeInput = document.getElementById('cidade_input');
	var logradouroInput = document.getElementById('logradouro_input');
	var bairroInput = document.getElementById('bairro_input');

	estadoInput.value=dados.uf;
	cidadeInput.value = dados.localidade;
	logradouroInput.value=dados.logradouro;
	logradouroInput.style.background="transparent";
	bairroInput.value=dados.bairro;

	if (document.activeElement.id == 'cep_input') {
		document.getElementById('input_numero').focus();
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

function hideMessage() {
	var alertas = document.getElementsByClassName('alert');
	for(var i = 0; i < alertas.length; i++){
		alertas[i].hidden=true;
	}
}

function doALoadOfStuff() {
	document.getElementById('conteudo_container').style.transition="2s";
	responsive();
}