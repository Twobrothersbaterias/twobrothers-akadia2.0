/* =========================== RESPONSIVIDADE DA TELA =========================== */

window.onload = responsive();
window.onresize = doALoadOfStuff;

document.onkeydown=function(){
    if(window.event.keyCode=='121') {
        validacaoCampos();
    }
    else if(window.event.keyCode=="120") {
    	avancarBloco();
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
	var menuSuperior = document.getElementById('menu_superior');
	var menuSuperiorUl = document.getElementById('menu_superior_ul');
	var nomePerfil = document.getElementById('nome_perfil');
	var menuMobile = document.getElementById('menu_superior_mobile');

	var divIcones = document.getElementsByClassName('img_div');
	var selectSuperiorContainer = document.getElementsByClassName('select_superior_container');

	if(bodyWidth > 1200){
		console.log("Muito grande");

		sideMenu.style.display="block";
		main.style.width="95.5%";
		sideMenu.style.width="4.5%";
		menuSuperior.style.height="55px";
		menuSuperiorUl.style.padding="12px 0";		
		menuMobile.style.display="none";

		for(var i = 0; i < divIcones.length; i++) {
			divIcones[i].style.padding="9px 0";
		}

		for (var i = 0; i < selectSuperiorContainer.length; i++) {
			selectSuperiorContainer[i].style.margin="0";
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
		console.log("Grande");

		sideMenu.style.display="block";
		main.style.width="94%";
		sideMenu.style.width="6%";
		menuSuperior.style.height="50px";
		menuSuperiorUl.style.padding="9px 0";		
		menuMobile.style.display="none";

		for(var i = 0; i < divIcones.length; i++) {
			divIcones[i].style.padding="8px 0";
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

		menuSuperior.style.height="50px";
		menuSuperiorUl.style.padding="12px 0";		
		menuMobile.style.display="none";	

		for(var i = 0; i < divIcones.length; i++) {
			divIcones[i].style.padding="6px 0";
		}

  		const parent = document.getElementById('container_all');
  		parent.insertBefore(
  			document.getElementById("container_endereco"), parent.children[1]);		


	}
	else if(bodyWidth <= 768 && bodyWidth > 540){
		console.log('Pequeno');	

		sideMenu.style.display="none";
		main.style.width="100%";
		menuSuperior.style.height="45px";
		menuSuperiorUl.style.padding="9px 0";		
		menuMobile.style.display="flex";

		for(var i = 0; i < divIcones.length; i++) {
			divIcones[i].style.paddingTop="20px";
			divIcones[i].style.paddingRight="10px";
		}		

  		const parent = document.getElementById('container_all');
  		parent.insertBefore(
  			document.getElementById("container_endereco"), parent.children[1]);			

	}
	else if(bodyWidth < 540){
		console.log('Muito pequeno');

		sideMenu.style.display="none";
		main.style.width="100%";
		menuSuperior.style.height="40px";
		menuMobile.style.display="flex";

		for(var i = 0; i < divIcones.length; i++) {
			divIcones[i].style.paddingTop="10px";
			divIcones[i].style.paddingRight="20px";
		}

		for (var i = 0; i < selectSuperiorContainer.length; i++) {
			selectSuperiorContainer[i].style.marginBottom="10px";
		}	

  		const parent = document.getElementById('container_all');
  		parent.insertBefore(
  			document.getElementById("container_endereco"), parent.children[1]);			

	}

	pageResponsiva();
	calculaInformativos();
	ajustaMinMaxDosInputsData();
}


/* ================== TRATAMENTO DE ADIÇÃO DE PRODUTOS ====================== */

function trocaTipoDaEntrada() {

	var tipoDaEntrada = document.getElementById('input_tipo_produto');

	var selectProduto = document.getElementById('select_produto');

	var optionServico = document.getElementById('option_servico');
	var optionProdutos = document.getElementsByClassName('option_produto');

	var labelTipoEntrada = document.getElementById('label_tipo_entrada');
	var inputTipoEntrada = document.getElementById('input_tipo_entrada');

	var labelQuantidade = document.getElementById('label_quantidade')
	var inputQuantidade = document.getElementById('input_quantidade');

	var botaoAddProduto = document.getElementById('botao_add_produto');	

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

		labelQuantidade.style.color="#4444";
		inputQuantidade.style.color="#4444";
		inputQuantidade.style.borderColor="#4444";
		inputQuantidade.disabled=true;

		inputQuantidade.style.background="transparent";
		inputQuantidade.value=0;
		botaoAddProduto.disabled=false;
		botaoAddProduto.style.background="#2f3d61";		

	}
	else {

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

	var inputTipoProduto = document.getElementById('input_tipo_produto');
	var inputTipoEntrada = document.getElementById('input_tipo_entrada');
	var inputProduto = document.getElementById('select_produto');
	var inputValor = document.getElementById('input_valor');
	var inputQuantidade = document.getElementById('input_quantidade');
	var optionProdutos = document.getElementsByClassName('option_produto');
	var optionServico = document.getElementById('option_servico');
	var labelTipoEntrada = document.getElementById('label_tipo_entrada');
	var labelQuantidade = document.getElementById('label_quantidade');

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
		+ ";" + inputValor.value 
		+ ";" + inputQuantidade.value + ";";

		inputEntradas.value = inputEntradas.value + string;

		resetNovoProduto();
		calculaInformativos();

		inputValor.style.background="transparent";

		document.getElementById('sucessoCadastro').hidden=false;
		document.getElementById('sucessoCadastro').innerText="Nova entrada inserida à ordem com sucesso";		

		inputTipoProduto.focus();
		AjustaTabelaDeProdutos(string);

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
		inputFormaPagamento.focus();
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
}

function resetaCliente() {
	document.getElementById('input_nome').value="";
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
	var inputCpfCnpj = document.getElementById('input_cpfCnpj');

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
	if(inputPagamentos.value == "") {
		erros += "- Nenhum pagamento adicionado à ordem\n";
	}
	if(inputEntradas.value == "") {
		erros += "- Nenhuma entrada adicionada à ordem\n";
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

	var containerInformativoAll = document.getElementById('container_informativo_all');
	
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
	}	
}

function AjustaTabelaDeProdutos(inputEntradas) {

	var totalEntradas = 0;

	var inputEntradasSplitPai = inputEntradas.split("ENTRADA=");

	document.getElementById('tr_base').hidden=true;

	for(var i = 1; i < inputEntradasSplitPai.length; i++) {

		console.log(inputEntradasSplitPai[i]);

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
				"<td class='td_novo td_quantidade col-xl-3 col-lg-3 col-md-3 col-sm-3 col-3'>" 
					+ (inputEntradasSplitPai[i].split(";")[4]) + "</td>" +
				"<td class='td_novo td_valor col-xl-3 col-lg-3 col-md-3 col-sm-3 col-3'>" 
					+ parseFloat((inputEntradasSplitPai[i].split(";")[3])).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) + "</td>" +
			"</tr>");		
	}
}

function removeItemTb(item) {

	var inputEntradas = document.getElementById('input_entradas');

	var entrada = (inputEntradas.value.split("ENTRADA="))[item];

	console.log(entrada);

	if(inputEntradas.value.includes(entrada)) {
		inputEntradas.value=inputEntradas.value.replace("ENTRADA=" + entrada, "");
	}

	document.getElementsByClassName('tr_novo_body')[parseInt(item)-1].parentNode.removeChild(document.getElementsByClassName('tr_novo_body')[parseInt(item)-1]);
	calculaInformativos();

	console.log(document.getElementsByClassName('tr_novo_body').length);
	if (document.getElementsByClassName('tr_novo_body').length < 1) {
		document.getElementById('tr_base').hidden=false;
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

	document.getElementById('input_numero').focus();
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