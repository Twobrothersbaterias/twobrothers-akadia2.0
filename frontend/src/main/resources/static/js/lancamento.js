/* =========================== RESPONSIVIDADE DA TELA =========================== */

window.onload = responsive();
window.onresize = doALoadOfStuff;

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
	var menuSuperior = document.getElementById('menu_superior');
	var menuSuperiorUl = document.getElementById('menu_superior_ul');
	var nomePerfil = document.getElementById('nome_perfil');
	var menuMobile = document.getElementById('menu_superior_mobile');
	var informativoRow = document.getElementById('informativo_row');
	var excluirText = document.getElementById('excluir_text');
	var containerNovo = document.getElementById('conteudo_container_novo');	
	var novoTitulo	= document.getElementById('novo_titulo');

	var novoItemSubmit = document.getElementById('novo_item_submit');

	var containerEdita = document.getElementById('conteudo_container_edita');	
	var editaTitulo	= document.getElementById('edita_titulo');
	var editaItemSubmit = document.getElementById('edita_item_submit');

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

	if(bodyWidth > 1200){
		console.log("Muito grande");

		conteudoTituloText.style.fontSize="1.5rem";
		sideMenu.style.display="block";
		main.style.width="95.5%";
		sideMenu.style.width="4.5%";
		menuSuperior.style.height="55px";
		menuSuperiorUl.style.padding="12px 0";		
		menuMobile.style.display="none";
		if (informativoRow != null) {
			informativoRow.style.justifyContent="center";
		}

		if(listaVaziaTitulo != null) {
			listaVaziaTitulo.style.fontSize="1.75rem";
			listaVaziaTexto.style.fontSize="1rem";
			listaVaziaBotao.style.fontSize="1rem";
		}

		for(var i = 0; i < thUsuario.length; i++) {
			thUsuario[i].style.display="flex";
		}

		for(var i = 0; i < tdUsuario.length; i++) {
			tdUsuario[i].style.display="flex";
		}

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.80rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.80rem";
		}

		for(var i = 0; i < novoTh.length; i++){
			novoTh[i].style.fontSize="1rem";
		}
		for(var i = 0; i < novoTd.length; i++){
			novoTd[i].style.fontSize="1rem";
		}		
		
		for(var i = 0; i < informativo.length; i++){
			informativo[i].style.fontSize="1rem";
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
			btnExcluir[i].style.fontSize="0.95rem";
			btnExcluir[i].style.padding="4px 6px";
			btnExcluir[i].innerText="Excluir";
		}		

	}
	else if(bodyWidth <= 1200 && bodyWidth > 992){
		console.log("Grande");

		conteudoTituloText.style.fontSize="1.3rem";
		sideMenu.style.display="block";
		main.style.width="94%";
		sideMenu.style.width="6%";
		menuSuperior.style.height="50px";
		menuSuperiorUl.style.padding="9px 0";		
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
		novoTitulo.style.fontSize="1.15rem";	

		containerEdita.style.fontSize="1rem";
		editaTitulo.style.fontSize="1.15rem";	
		editaItemSubmit.style.marginTop="0px";

		filtroTitulo.style.fontSize="1.4rem";

		for(var i = 0; i < thUsuario.length; i++) {
			thUsuario[i].style.display="flex";
		}

		for(var i = 0; i < tdUsuario.length; i++) {
			tdUsuario[i].style.display="flex";
		}					

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.75rem";		
		}		
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.75rem";			
		}

		for(var i = 0; i < informativo.length; i++){
			informativo[i].style.fontSize="0.90rem";
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
			btnExcluir[i].style.fontSize="0.95rem";
			btnExcluir[i].style.padding="4px 6px";
			btnExcluir[i].innerText="Excluir";
		}		
		for(var i = 0; i < filtroBlock.length; i++) {
			filtroBlock[i].style.marginBottom="0";
		}	

		filtroBuscarBt.style.marginTop="0px";
		filtroBuscarBt.style.justifyContent="left";					
	}
	else if(bodyWidth <= 992 && bodyWidth > 768){
		console.log('Médio');	

		sideMenu.style.display="block";
		if (bodyWidth > 870) {
			main.style.width="93%";
			sideMenu.style.width="7%";
		}
		else {
			main.style.width="92.5%";
			sideMenu.style.width="7.5%";	
		}

		for(var i = 0; i < thUsuario.length; i++) {
			thUsuario[i].style.display="none";
		}

		for(var i = 0; i < tdUsuario.length; i++) {
			tdUsuario[i].style.display="none";
		}		

		conteudoTituloText.style.fontSize="1.2rem";
		menuSuperior.style.height="50px";
		menuSuperiorUl.style.padding="12px 0";		
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

		filtroTitulo.style.fontSize="1.3rem";	

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.70rem";	
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.70rem";
		}	

		for(var i = 0; i < informativo.length; i++){
			informativo[i].style.fontSize="0.80rem";
			informativo[i].style.width="40%";	
			informativo[i].style.margin="7px";					
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

		filtroBuscarBt.style.marginTop="0px";
		filtroBuscarBt.style.justifyContent="left";		
	}
	else if(bodyWidth <= 768 && bodyWidth > 540){
		console.log('Pequeno');	

		conteudoTituloText.style.fontSize="1.1rem";
		sideMenu.style.display="none";
		main.style.width="100%";
		menuSuperior.style.height="45px";
		menuSuperiorUl.style.padding="9px 0";		
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

		filtroTitulo.style.fontSize="1.2rem";

		for(var i = 0; i < thUsuario.length; i++) {
			thUsuario[i].style.display="none";
		}

		for(var i = 0; i < tdUsuario.length; i++) {
			tdUsuario[i].style.display="none";
		}		


		for(var i = 0; i < th.length; i++) {
			th[i].style.fontSize="0.65rem";
		}
		for(var i = 0; i < td.length; i++) {
			td[i].style.fontSize="0.65rem";
		}
				
		for(var i = 0; i < informativo.length; i++) {
			informativo[i].style.fontSize="0.68rem";
			informativo[i].style.width="46%";
			informativo[i].style.margin="7px";			
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

		for(var i = 0; i < filtroBlock.length; i++) {
			filtroBlock[i].style.marginBottom="20px";
		}

		filtroBuscarBt.style.marginTop="20px";
		filtroBuscarBt.style.justifyContent="center";
	}
	else if(bodyWidth < 540){
		console.log('Muito pequeno');

		conteudoTituloText.style.fontSize="1rem";
		sideMenu.style.display="none";
		main.style.width="100%";
		menuSuperior.style.height="40px";
		menuMobile.style.display="flex";
		containerNovo.style.fontSize="0.90rem";
		novoTitulo.style.fontSize="1rem";

		containerEdita.style.fontSize="0.90rem";
		editaTitulo.style.fontSize="1rem";
		editaItemSubmit.style.marginTop="5px";

		filtroTitulo.style.fontSize="1.1rem";

		if(listaVaziaTitulo != null) {
			listaVaziaTitulo.style.fontSize="1.2rem";
			listaVaziaTexto.style.fontSize="0.90rem";
			listaVaziaBotao.style.fontSize="0.90rem";
		}


		for(var i = 0; i < thUsuario.length; i++) {
			thUsuario[i].style.display="none";
		}

		for(var i = 0; i < tdUsuario.length; i++) {
			tdUsuario[i].style.display="none";
		}		

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.60rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.60rem";
		}

		for(var i = 0; i < informativo.length; i++){
			informativo[i].style.fontSize="0.58rem";
			informativo[i].style.width="55%";
			informativo[i].style.margin="0 auto 10px auto";
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

	pageResponsiva();
}


/* ================== TRATAMENTO DE ADIÇÃO DE PRODUTOS ====================== */

function trocaTipoDaEntrada() {

	var tipoDaEntrada = document.getElementById('input_tipo_produto');

	var selectProduto = document.getElementById('select_produto');

	var optionServico = document.getElementById('option_servico');
	var optionProdutos = document.getElementsByClassName('option_produto');
	var inputQuantidade = document.getElementById('input_quantidade');	

	if(tipoDaEntrada.value == "PADRAO_SERVICO") {

		optionServico.hidden=false;
		for(var i = 0; i < optionProdutos.length; i++) {
			optionProdutos[i].hidden=true;
		}

		optionServico.selected=true;

	}
	else {

		optionServico.hidden=true;
		for(var i = 0; i < optionProdutos.length; i++) {
			optionProdutos[i].hidden=false;
		}

		optionProdutos[0].selected=true;

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
	if(parseInt(inputQuantidade) < 1) {
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

	var inputTipoProduto = document.getElementById('input_tipo_produto');
	var inputTipoEntrada = document.getElementById('input_tipo_entrada');
	var inputProduto = document.getElementById('select_produto');
	var inputValor = document.getElementById('input_valor');
	var inputQuantidade = document.getElementById('input_quantidade');
	var optionProdutos = document.getElementsByClassName('option_produto');
	var optionServico = document.getElementById('option_servico');

	inputTipoProduto.value="PADRAO_PRODUTO";
	inputTipoEntrada.value="TROCA";
	inputValor.value=0.00;
	inputQuantidade.value=0;

	optionServico.hidden=true;
	for(var i = 0; i < optionProdutos.length; i++) {
		optionProdutos[i].hidden=false;
	}

	optionProdutos[0].selected=true;
}

function addNovoProduto() {

	if (validaNovoProduto()) {

		var string = "ENTRADA=";

		var inputTipoProduto = document.getElementById('input_tipo_produto');
		var inputTipoEntrada = document.getElementById('input_tipo_entrada');
		var inputProduto = document.getElementById('select_produto');
		var inputValor = document.getElementById('input_valor');
		var inputQuantidade = document.getElementById('input_quantidade');

		var inputEntradas = document.getElementById('input_entradas');

		string += inputTipoProduto.value
		+ ";" + inputTipoEntrada.value
		+ ";" + inputProduto.value 
		+ ";" + inputValor.value 
		+ ";" + inputQuantidade.value + ";";

		inputEntradas.value = inputEntradas.value + string;

		resetNovoProduto();
		calculaInformativos();

		document.getElementById('sucessoCadastro').hidden=false;
		document.getElementById('sucessoCadastro').innerText="Nova entrada inserida à ordem com sucesso";		

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

	inputDataAgendamento.disabled=false;

	inputDataAgendamento.style.color="#303030";
	inputDataAgendamento.style.border="1px solid grey";

	labelDataAgendamento.style.color="#303030";

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

		string += inputFormaPagamento.value
		+ ";" + inputValorPagamento.value
		+ ";" + inputDataAgendamento.value 
		+ ";" + inputObservacao.value + ";";

		inputPagamentos.value = inputPagamentos.value + string;

		resetNovoPagamento();
		calculaInformativos();

		document.getElementById('sucessoCadastro').hidden=false;
		document.getElementById('sucessoCadastro').innerText="Novo pagamento inserido à ordem com sucesso";

	}	
}

/* ================== TRATAMENTO DE INFORMATIVOS ====================== */

function calculaInformativos() {

	var totalEntradas = 0;
	var totalPagamentos = 0;

	var inputPagamentos = document.getElementById('input_pagamentos');
	var inputEntradas = document.getElementById('input_entradas');

	console.log(inputPagamentos.value);
	console.log(inputEntradas.value);

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



/* ============================= Miscelânia ================================== */

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