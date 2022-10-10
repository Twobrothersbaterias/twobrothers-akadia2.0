/* =========================== RESPONSIVIDADE DA TELA =========================== */

window.onload = responsive();
window.onresize = doALoadOfStuff;
ajustaTabela();

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

	var novaDespesaSubmit = document.getElementById('novo_item_submit');

	var containerEdita = document.getElementById('conteudo_container_edita');	
	var editaTitulo	= document.getElementById('edita_titulo');
	var editaDespesaSubmit = document.getElementById('edita_item_submit');

	var informativo = document.getElementsByClassName('informativo');
	var th = document.getElementsByClassName('th');
	var td = document.getElementsByClassName('td');
	var tdUsuario = document.getElementsByClassName('td_usuario');
	var thUsuario = document.getElementsByClassName('th_usuario');
	var tdData = document.getElementsByClassName('td_data');
	var thData = document.getElementsByClassName('th_data');
	var thTipo = document.getElementsByClassName('th_tipo');
	var tdTipo = document.getElementsByClassName('td_tipo');
	var tdPagamento = document.getElementsByClassName('td_pagamento');
	var thPagamento = document.getElementsByClassName('th_pagamento');
	var tdRemove = document.getElementsByClassName('td_checkbox');
	var thRemove = document.getElementsByClassName('th_remove');	
	var btnExcluir = document.getElementsByClassName('btn_excluir');
	var formRemoveImg = document.getElementsByClassName('form_remove_img');
	var formRemoveText = document.getElementsByClassName('form_remove_text');
	var liA = document.getElementsByClassName('li_a');
	var menuSuperiorMobileItem = document.getElementsByClassName('menu_superior_mobile_item');
	var novaDespesaInput = document.getElementsByClassName('novo_item_input');
	var editaItemInput = document.getElementsByClassName('edita_item_input');
	var filtroBlock = document.getElementsByClassName('filtro_block');
	var filtroBuscarBt = document.getElementById('filtro_buscar_bt');

	

	if(bodyWidth > 1200){
		console.log("Muito grande");

		conteudoTituloText.style.fontSize="1.5rem";
		sideMenu.style.display="block";
		main.style.width="95.5%";
		sideMenu.style.width="4.5%";
		menuSuperior.style.height="55px";
		menuSuperiorUl.style.padding="12px 0";
		menuMobile.style.display="none";
		informativoRow.style.justifyContent="center";
		
		containerNovo.style.fontSize="1rem";		
		novoTitulo.style.fontSize="1.15rem";	
		novaDespesaSubmit.style.marginTop="0px";

		containerEdita.style.fontSize="1rem";		
		editaTitulo.style.fontSize="1.15rem";	
		editaDespesaSubmit.style.marginTop="0px";		

		for(var i = 0; i < thTipo.length; i++) {
			thTipo[i].hidden=false;
		}

		for(var i = 0; i < tdTipo.length; i++) {
			tdTipo[i].hidden=false;
		}			

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.80rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.80rem";
		}

		for(var i = 0; i < informativo.length; i++){
			informativo[i].style.fontSize="1rem";
			informativo[i].style.width="17%";
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
		for(var i = 0; i < filtroBlock.length; i++) {
			filtroBlock[i].style.marginBottom="0";
		}
		filtroBuscarBt.style.marginTop="0px";
		filtroBuscarBt.style.justifyContent="left";		
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
		informativoRow.style.justifyContent="center";	
		containerNovo.style.fontSize="1rem";
		novoTitulo.style.fontSize="1.15rem";	
		novaDespesaSubmit.style.marginTop="0px";

		containerEdita.style.fontSize="1rem";
		editaTitulo.style.fontSize="1.15rem";	
		editaDespesaSubmit.style.marginTop="0px";	

		for(var i = 0; i < thTipo.length; i++) {
			thTipo[i].hidden=false;
		}

		for(var i = 0; i < tdTipo.length; i++) {
			tdTipo[i].hidden=false;
		}								

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.75rem";			
		}		
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.75rem";		
		}

		for(var i = 0; i < informativo.length; i++){
			informativo[i].style.fontSize="0.95rem";
			informativo[i].style.width="25%";
			informativo[i].style.margin="0 7px";
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

		conteudoTituloText.style.fontSize="1.2rem";
		menuSuperior.style.height="50px";
		menuSuperiorUl.style.padding="12px 0";		
		menuMobile.style.display="none";
		informativoRow.style.justifyContent="center";
		containerNovo.style.fontSize="1rem";		
		novoTitulo.style.fontSize="1.1rem";
		novaDespesaSubmit.style.marginTop="0px";

		containerEdita.style.fontSize="1rem";		
		editaTitulo.style.fontSize="1.1rem";
		editaDespesaSubmit.style.marginTop="0px";	

		for(var i = 0; i < thTipo.length; i++) {
			thTipo[i].hidden=false;
		}

		for(var i = 0; i < tdTipo.length; i++) {
			tdTipo[i].hidden=false;
		}	

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.70rem";		
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.70rem";	
		}	
			
		for(var i = 0; i < informativo.length; i++){
			informativo[i].style.fontSize="0.85rem";
			informativo[i].style.width="28.9%";	
			informativo[i].style.margin="0 7px";					
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
		informativoRow.style.justifyContent="center";
		containerNovo.style.fontSize="0.90rem";	
		novoTitulo.style.fontSize="1.2rem";		
		novaDespesaSubmit.style.marginTop="0px";

		containerEdita.style.fontSize="0.90rem";	
		editaTitulo.style.fontSize="1.2rem";		
		editaDespesaSubmit.style.marginTop="0px";

		for(var i = 0; i < thTipo.length; i++) {
			thTipo[i].hidden=true;
		}

		for(var i = 0; i < tdTipo.length; i++) {
			tdTipo[i].hidden=true;
		}		

		for(var i = 0; i < th.length; i++) {
			th[i].style.fontSize="0.65rem";		
		}
		for(var i = 0; i < td.length; i++) {
			td[i].style.fontSize="0.65rem";	
		}

		for(var i = 0; i < informativo.length; i++) {
			informativo[i].style.fontSize="0.68rem";
			informativo[i].style.width="31.8%";
			informativo[i].style.margin="0 auto";			
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
		novaDespesaSubmit.style.marginTop="5px";

		containerEdita.style.fontSize="0.90rem";
		editaTitulo.style.fontSize="1rem";
		editaDespesaSubmit.style.marginTop="5px";

		for(var i = 0; i < thTipo.length; i++) {
			thTipo[i].hidden=true;
		}

		for(var i = 0; i < tdTipo.length; i++) {
			tdTipo[i].hidden=true;
		}						

		for(var i = 0; i < thPagamento.length; i++) {
			thPagamento[i].style.paddingLeft="0px"
			thPagamento[i].style.justifyContent="center";
		}	
		for(var i = 0; i < tdPagamento.length; i++) {
			tdPagamento[i].style.paddingLeft="0px";
			tdPagamento[i].style.justifyContent="center";
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

function doALoadOfStuff() {
	document.getElementById('conteudo_container').style.transition="2s";
	responsive();
}

/* ================== FECHA MENSAGENS INFORMATIVAS ====================== */

function hideMessage() {
	var alertas = document.getElementsByClassName('alert');
	for(var i = 0; i < alertas.length; i++){
		alertas[i].hidden=true;
	}
}

function abrirNovaDespesa() {

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
	data_pagamento_input.value=hoje;

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

function fechaNovaDespesa() {

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

	var descricaoDespesaInput = document.getElementById('descricao_despesa_input');
	var valorDespesaInput = document.getElementById('valor_despesa_input');
	var tipoDespesaInput = document.getElementById('tipo_despesa_input');
	var statusDespesaInput = document.getElementById('status_despesa');
	var dataPagamentoInput = document.getElementById('data_pagamento_input');
	var dataAgendamentoInput = document.getElementById('data_agendamento_input');
	var persistenciaInput = document.getElementById('persistencia_input');


	descricaoDespesaInput.value="";
	valorDespesaInput.value="";
	statusDespesaInput.value="PAGO";
	dataAgendamentoInput.value="";
	persistenciaInput.value="NAO";
	tipoDespesaInput.value="VARIAVEL";

	dataAgendamentoInput.style.color="#4444";
	dataAgendamentoInput.disabled=true;	

	dataPagamentoInput.style.color="#121212";
	dataPagamentoInput.disabled=false;
	dataPagamentoInput.value=hoje;	
	
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

function changeStatus() {

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

	var statusDespesa = document.getElementById('status_despesa');

	var dataAgendamentoInput = document.getElementById('data_agendamento_input');
	var dataPagamentoInput = document.getElementById('data_pagamento_input');	

	if (statusDespesa.value == "PAGO") {
		dataAgendamentoInput.style.color="#4444";
		dataAgendamentoInput.value="";
		dataAgendamentoInput.disabled=true;

		dataPagamentoInput.style.color="#121212";
		dataPagamentoInput.disabled=false;
		dataPagamentoInput.value=hoje;
	} 

	else if(statusDespesa.value == "PENDENTE") {
		dataAgendamentoInput.style.color="#121212";
		dataAgendamentoInput.disabled=false;
		dataAgendamentoInput.value=hoje;

		dataPagamentoInput.style.color="#4444";
		dataPagamentoInput.value="";
		dataPagamentoInput.disabled=true;
	}
}

function reloadNovaDespesa() {

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

	var descricaoDespesaInput = document.getElementById('descricao_despesa_input');
	var tipoDespesaInput = document.getElementById('tipo_despesa_input');
	var valorDespesaInput = document.getElementById('valor_despesa_input');
	var statusDespesaInput = document.getElementById('status_despesa');
	var dataPagamentoInput = document.getElementById('data_pagamento_input');
	var dataAgendamentoInput = document.getElementById('data_agendamento_input');
	var persistenciaInput = document.getElementById('persistencia_input');

	descricaoDespesaInput.value="";
	valorDespesaInput.value="";
	tipoDespesaInput.value="VARIAVEL";
	statusDespesaInput.value="PAGO";
	dataPagamentoInput.value=hoje;
	dataAgendamentoInput.value="";
	persistenciaInput.value="NAO";	
}

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

	var dataInicio = document.getElementById('filtro_data_inicio_block');
	var dataInicioInput = document.getElementById('data_inicio_filtro_input');

	var dataFim = document.getElementById('filtro_data_fim_block');
	var dataFimInput = document.getElementById('data_fim_filtro_input');


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

		dataInicioInput.value=data;
		dataInicio.hidden=true;

		dataFimInput.value=data;
		dataFim.hidden=true;

		mesInput.value=mes;
		mesBlock.hidden=true;

		anoInput.value=ano;
		anoBlock.hidden=true;

		tipoInput.value="VARIAVEL";
		tipo.hidden=true;

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

		tipoInput.value="VARIAVEL";
		tipo.hidden=true;		
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

		tipoInput.value="VARIAVEL";
		tipo.hidden=true;
		
	}
	else if (filtroTipo.value == 'TIPO') {

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

		tipoInput.value="VARIAVEL";
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

	document.getElementById('tipo_filtro_option_data').hidden=false;
	document.getElementById('filtro_data_inicio_block').hidden=true;
	document.getElementById('filtro_data_fim_block').hidden=true;	
	document.getElementById('filtro_data_tag').hidden=true;	

	document.getElementById('tipo_filtro_option_periodo').hidden=false;
	document.getElementById('filtro_mes_block').hidden=true;	
	document.getElementById('filtro_ano_block').hidden=true;	
	document.getElementById('filtro_periodo_tag').hidden=true;		

	document.getElementById('tipo_filtro_option_tipo').hidden=false;
	document.getElementById('filtro_tipo_block').hidden=true;	
	document.getElementById('filtro_tipo_tag').hidden=true;	

	document.getElementById('descricao_filtro_input').value="";
	document.getElementById('data_inicio_filtro_input').value=data;
	document.getElementById('data_fim_filtro_input').value=data;
	document.getElementById('mes_filtro_input').value=mes;
	document.getElementById('ano_filtro_input').value=ano;
	document.getElementById('tipo_filtro_input').value="VARIAVEL";

	document.getElementById('input_descricao_backend').value="";
	document.getElementById('input_data_inicio_backend').value="";
	document.getElementById('input_data_fim_backend').value="";
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

	var optionData = document.getElementById('tipo_filtro_option_data');
	var dataInicioBlock = document.getElementById('filtro_data_inicio_block');	
	var dataFimBlock = document.getElementById('filtro_data_fim_block');	
	var dataTag = document.getElementById('filtro_data_tag');	

	var optionPeriodo = document.getElementById('tipo_filtro_option_periodo');
	var periodoMesBlock = document.getElementById('filtro_mes_block');	
	var periodoAnoBlock = document.getElementById('filtro_ano_block');	
	var periodoTag = document.getElementById('filtro_periodo_tag');		

	var optionTipo = document.getElementById('tipo_filtro_option_tipo');
	var tipoBlock = document.getElementById('filtro_tipo_block');	
	var tipoTag = document.getElementById('filtro_tipo_tag');	

	var inputDescricaoFiltro = document.getElementById('descricao_filtro_input');
	var inputDataInicioFiltro = document.getElementById('data_inicio_filtro_input');
	var inputDataFimFiltro = document.getElementById('data_fim_filtro_input');
	var inputMesFiltro = document.getElementById('mes_filtro_input');
	var inputAnoFiltro = document.getElementById('ano_filtro_input');
	var inputTipoFiltro = document.getElementById('tipo_filtro_input');

	var inputDescricaoBackend = document.getElementById('input_descricao_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');
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

	var optionData = document.getElementById('tipo_filtro_option_data');
	var dataInicioBlock = document.getElementById('filtro_data_inicio_block');	
	var dataFimBlock = document.getElementById('filtro_data_fim_block');	
	var dataTag = document.getElementById('filtro_data_tag');	

	var optionPeriodo = document.getElementById('tipo_filtro_option_periodo');
	var periodoMesBlock = document.getElementById('filtro_mes_block');	
	var periodoAnoBlock = document.getElementById('filtro_ano_block');	
	var periodoTag = document.getElementById('filtro_periodo_tag');		

	var optionTipo = document.getElementById('tipo_filtro_option_tipo');
	var tipoBlock = document.getElementById('filtro_tipo_block');	
	var tipoTag = document.getElementById('filtro_tipo_tag');	

	var inputDescricaoBackend = document.getElementById('input_descricao_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');
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

		dataInicioBlock.hidden=true;
		dataFimBlock.hidden=true;
		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;
		tipoBlock.hidden=true;

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
		tipoBlock.hidden=true;

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
		tipoBlock.hidden=true;

		periodoTag.hidden=true;
		filtroTipo.value="PERIODO";

		inputMesBackend.value="";
		inputAnoBackend.value="";
	}
	else if (filtro == 'tipo') {
		optionTipo.hidden=false;

		descricaoBlock.hidden=true;

		dataInicioBlock.hidden=true;
		dataFimBlock.hidden=true;
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
	var filtroData = document.getElementById('filtro_data_tag');	
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
	var filtroData = document.getElementById('filtro_data_tag');
	var filtroPeriodo = document.getElementById('filtro_periodo_tag');	
	var filtroTipo = document.getElementById('filtro_tipo_tag');		

	var inputDescricaoBackend = document.getElementById('input_descricao_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');
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
	else if (filtro == 'tipo') {
		filtroTipo.style.transition="1s"
		filtroTipo.style.background="transparent";
		filtroTipo.style.border="1px solid #212121"
		filtroTipo.style.color="#212121";
		filtroTipo.innerText = 'Tipo: ' + inputTipoBackend.value;
	}		
}

function abrirEditaDespesa(
							id, 
							tipo, 
							descricao, 
							valor, 
							status, 
							dataPagamento, 
							dataAgendamento, 
							responsavel, 
							dataCadastro, 
							persistencia) {

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

	var subtitulo = document.getElementById('edita_info');

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
		subtitulo.title="Despesa criada dia " + dataUsParaDataBr + " por " + responsavel;
	}	

	// SETANDO VALORES NOS CAMPOS
	document.getElementById('id_input_edicao').value=id;
	document.getElementById('tipo_despesa_input_edicao').value=tipo;
	document.getElementById('descricao_despesa_input_edicao').value=descricao;
	document.getElementById('valor_despesa_input_edicao').value=valor;
	document.getElementById('status_despesa_edicao').value=status;

	document.getElementById('persistencia_input_edicao').style.color="#4444";
	document.getElementById('persistencia_input_edicao').value=persistencia;
	document.getElementById('persistencia_input_edicao').disabled=true;

	if (status == 'PAGO') {
		document.getElementById('data_pagamento_input_edicao').value=dataPagamento;
		document.getElementById('data_pagamento_input_edicao').style.color="#212121";
		document.getElementById('data_agendamento_input_edicao').style.color="#4444";
		document.getElementById('data_agendamento_input_edicao').value="";
		document.getElementById('data_agendamento_input_edicao').disabled=true;
		document.getElementById('data_pagamento_input_edicao').disabled=false;;
	}
	else if (status == 'PENDENTE') {
		document.getElementById('data_agendamento_input_edicao').value=dataAgendamento;
		document.getElementById('data_pagamento_input_edicao').style.color="#4444";
		document.getElementById('data_agendamento_input_edicao').style.color="#212121";
		document.getElementById('data_pagamento_input_edicao').value="";
		document.getElementById('data_pagamento_input_edicao').disabled=true;
		document.getElementById('data_agendamento_input_edicao').disabled=false;
	}
	
	document.getElementById('persistencia_input_edicao').value=persistencia;
}

function fecharEditaDespesa() {

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

	var descricaoDespesaInput = document.getElementById('descricao_despesa_input_edicao');
	var valorDespesaInput = document.getElementById('valor_despesa_input_edicao');
	var tipoDespesaInput = document.getElementById('tipo_despesa_input_edicao');
	var statusDespesaInput = document.getElementById('status_despesa_edicao');
	var dataPagamentoInput = document.getElementById('data_pagamento_input_edicao');
	var dataAgendamentoInput = document.getElementById('data_agendamento_input_edicao');
	var persistenciaInput = document.getElementById('persistencia_input_edicao');


	descricaoDespesaInput.value="";
	valorDespesaInput.value="";
	statusDespesaInput.value="PAGO";
	dataAgendamentoInput.value="";
	persistenciaInput.value="NAO";
	tipoDespesaInput.value="VARIAVEL";

	dataAgendamentoInput.style.color="#4444";
	dataAgendamentoInput.disabled=true;	

	dataPagamentoInput.style.color="#C3C8C8";
	dataPagamentoInput.disabled=false;
	dataPagamentoInput.value=hoje;	
	
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

function editaDespesaChangeStatus() {

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

	var statusDespesa = document.getElementById('status_despesa_edicao');
	var dataAgendamentoInput = document.getElementById('data_agendamento_input_edicao');
	var dataPagamentoInput = document.getElementById('data_pagamento_input_edicao');	

	if (statusDespesa.value == "PAGO") {
		dataAgendamentoInput.style.color="#4444";
		dataAgendamentoInput.value="";
		dataAgendamentoInput.disabled=true;

		dataPagamentoInput.style.color="#121212";
		dataPagamentoInput.disabled=false;
		dataPagamentoInput.value=hoje;
	} 

	else if(statusDespesa.value == "PENDENTE") {
		dataAgendamentoInput.style.color="#121212";
		dataAgendamentoInput.disabled=false;
		dataAgendamentoInput.value=hoje;

		dataPagamentoInput.style.color="#4444";
		dataPagamentoInput.value="";
		dataPagamentoInput.disabled=true;
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

/* ================== REALIZA OS AJUSTES DE TEXTO NA TABELA ====================== */
function ajustaTabela(){

	// Definindo propriedades
	var line = document.getElementsByClassName('tr');
	var columnScheduling = document.getElementsByClassName('td_scheduling');
	var columnPayment = document.getElementsByClassName('td_pagamento');
	var columnData = document.getElementsByClassName('td_data');
	var columnValor = document.getElementsByClassName('td_value');

	for(var i = 0; i < line.length; i++) {

		if (!columnValor[i].innerText.includes('R$') && !columnValor[i].innerText.includes('...')) {
			columnValor[i].innerText=
				parseFloat(columnValor[i].innerText).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
		}


		var columnSchedulingSplitted = columnScheduling[i].innerText.split("-");
		if (columnSchedulingSplitted.length == 3) {
			var convertedDate = columnSchedulingSplitted[2] + "/" + columnSchedulingSplitted[1] + "/" + columnSchedulingSplitted[0];
			columnScheduling[i].innerText=convertedDate;
		}

		var columnPaymentSplitted = columnPayment[i].innerText.split("-");
		if (columnPaymentSplitted.length == 3) {
			var convertedDate = columnPaymentSplitted[2] + "/" + columnPaymentSplitted[1] + "/" + columnPaymentSplitted[0];
			columnPayment[i].innerText=convertedDate;
		}				

		if(columnScheduling[i].innerText == "Nulo" || columnScheduling[i].innerText == "Não possui") {
			
			line[i].style.borderLeft="4px solid #5eff00";
			columnScheduling[i].innerText="Não possui";
			
			if (columnPayment[i].innerText != "Em aberto" 
				&& !columnPayment[i].innerText.includes("Pago ")) {

				var dataPagamento = columnPayment[i].innerText;
				columnPayment[i].innerText=dataPagamento
			}

		}

		else {

			const d = new Date();
			var ano = d.getFullYear();
			var mes = d.getMonth()+1;
			var dia = d.getDate();

			if(mes < 10){
				var mes = '0' + mes;
			}

			if (dia < 10) {
				var dia = '0' + dia;
			}

			var hoje = (dia + '/' + mes + '/' + ano); 

			if(columnScheduling[i].innerText != "...") {
				if(columnScheduling[i].innerText == hoje || columnScheduling[i].innerText == "Hoje"){
					line[i].style.borderLeft="4px solid #ff5900";
					columnPayment[i].style.color="#ff5900";
					columnPayment[i].innerText="Vence hoje";
					columnScheduling[i].style.color="#ff5900"
					columnScheduling[i].innerText="Hoje"
				}
				else if(columnScheduling[i].innerText.split("/")[2] <= ano && 
					columnScheduling[i].innerText.split("/")[1] <= mes && 
					columnScheduling[i].innerText.split("/")[0] <= dia || 
					columnScheduling[i].innerText == "Atrasado"){
					line[i].style.borderLeft="4px solid #f20a0a";
					columnScheduling[i].style.color="#f20a0a";
					columnPayment[i].style.color="#f20a0a";
					columnPayment[i].style.color="#F20a0a";
					columnPayment[i].innerText="Atrasado";
				}
				else{
					line[i].style.borderLeft="4px solid #ffdd00";
					columnScheduling[i].style.color="#ffdd00";
					columnPayment[i].innerText="Agendado";
				}
			}

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
}