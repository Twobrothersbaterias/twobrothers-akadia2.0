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
	var nomePerfil = document.getElementById('nome_perfil');
	var menuMobile = document.getElementById('menu_superior_mobile');
	var excluirText = document.getElementById('excluir_text');

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

	var informativoChave = document.getElementsByClassName('informativo_chave');

	if(bodyWidth > 1200){
		console.log("Muito grande");

		conteudoTituloText.style.fontSize="1.5rem";
		sideMenu.style.display="block";
		main.style.width="95.5%";
		sideMenu.style.width="4.5%";
		menuMobile.style.display="none";

		containerNovo.style.fontSize="1rem";		
		novoTitulo.style.fontSize="1.15rem";	
		novoItemSubmit.style.marginTop="0px";

		containerEdita.style.fontSize="1rem";		
		editaTitulo.style.fontSize="1.15rem";	
		editaItemSubmit.style.marginTop="0px";

		filtroTitulo.style.fontSize="1.5rem";

		for(var i = 0; i < informativoChave.length; i++) {
			informativoChave[i].style.padding="9px 0px";
		}		

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.80rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.80rem";
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
	else if(bodyWidth <= 1200 && bodyWidth > 992){
		console.log("Grande");

		conteudoTituloText.style.fontSize="1.3rem";
		sideMenu.style.display="block";
		main.style.width="94%";
		sideMenu.style.width="6%";		
		menuMobile.style.display="none";

		containerNovo.style.fontSize="1rem";
		novoTitulo.style.fontSize="1.15rem";	
		novoItemSubmit.style.marginTop="0px";

		containerEdita.style.fontSize="1rem";
		editaTitulo.style.fontSize="1.15rem";	
		editaItemSubmit.style.marginTop="0px";	

		filtroTitulo.style.fontSize="1.4rem";

		for(var i = 0; i < informativoChave.length; i++) {
			informativoChave[i].style.padding="8px 0px";
		}												

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.75rem";
		}		
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.75rem";		
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
			main.style.width="93%";
			sideMenu.style.width="7%";
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

		filtroTitulo.style.fontSize="1.3rem";

		for(var i = 0; i < informativoChave.length; i++) {
			informativoChave[i].style.padding="8px 0px";
		}															

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.70rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.70rem";
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

		conteudoTituloText.style.fontSize="1.1rem";
		sideMenu.style.display="none";
		main.style.width="100%";
		menuMobile.style.display="flex";	
		containerNovo.style.fontSize="0.90rem";	
		novoTitulo.style.fontSize="1.2rem";		
		novoItemSubmit.style.marginTop="0px";

		containerEdita.style.fontSize="0.90rem";	
		editaTitulo.style.fontSize="1.2rem";		
		editaItemSubmit.style.marginTop="0px";

		filtroTitulo.style.fontSize="1.2rem";

		for(var i = 0; i < informativoChave.length; i++) {
			informativoChave[i].style.padding="8px 0px";
		}														

		for(var i = 0; i < th.length; i++) {
			th[i].style.fontSize="0.65rem";	
		}
		for(var i = 0; i < td.length; i++) {
			td[i].style.fontSize="0.65rem";	
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

		conteudoTituloText.style.fontSize="1rem";
		sideMenu.style.display="none";
		main.style.width="100%";
		menuMobile.style.display="flex";
		containerNovo.style.fontSize="0.90rem";
		novoTitulo.style.fontSize="1rem";
		novoItemSubmit.style.marginTop="5px";

		containerEdita.style.fontSize="0.90rem";
		editaTitulo.style.fontSize="1rem";
		editaItemSubmit.style.marginTop="5px";

		filtroTitulo.style.fontSize="1.1rem";

		for(var i = 0; i < informativoChave.length; i++) {
			informativoChave[i].style.padding="8px 0px";
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

function paletasDeCores(paleta) {

	if(paleta == 1) {

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
	document.getElementById('descricao_fornecedor_input').value="";
	document.getElementById('email_fornecedor_input').value="";
	document.getElementById('telefone_fornecedor_input').value="";
	document.getElementById('data_nascimento_input').value="";
	document.getElementById('cpfCnpj_input').value="";
	document.getElementById('cep_input').value="";
	document.getElementById('estado_input').value="SP";
	document.getElementById('cidade_input').value="";
	document.getElementById('logradouro_input').value="";
	document.getElementById('numero_input').value="";
	document.getElementById('bairro_input').value="";
	document.getElementById('complemento_input').value="";
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

	var dataInicio = document.getElementById('filtro_data_inicio_block');
	var dataInicioInput = document.getElementById('data_inicio_filtro_input');

	var dataFim = document.getElementById('filtro_data_fim_block');
	var dataFimInput = document.getElementById('data_fim_filtro_input');

	var mesBlock = document.getElementById('filtro_mes_block');
	var mesInput = document.getElementById('mes_filtro_input');

	var anoBlock = document.getElementById('filtro_ano_block');
	var anoInput = document.getElementById('ano_filtro_input');

	var telefoneBlock = document.getElementById('filtro_telefone_block');
	var telefoneInput = document.getElementById('telefone_filtro_input');

	var cpfBlock = document.getElementById('filtro_cpf_block');
	var cpfInput = document.getElementById('cpf_filtro_input');	

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

		telefoneInput.value="";
		telefoneBlock.hidden=true;

		cpfInput.value="";
		cpfBlock.hidden=true;

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

		telefoneInput.value="";
		telefoneBlock.hidden=true;

		cpfInput.value="";
		cpfBlock.hidden=true;
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

		telefoneInput.value="";
		telefoneBlock.hidden=true;

		cpfInput.value="";
		cpfBlock.hidden=true;
		
	}
	else if (filtroTipo.value == 'TELEFONE') {

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

		telefoneBlock.hidden=false;

		cpfInput.value="";
		cpfBlock.hidden=true;
		
	}

	else if (filtroTipo.value == 'CPF') {

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

		telefoneInput.value="";
		telefoneBlock.hidden=true;

		cpfBlock.hidden=false;
		
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

	document.getElementById('tipo_filtro_option_telefone').hidden=false;
	document.getElementById('filtro_telefone_block').hidden=true;
	document.getElementById('filtro_telefone_tag').hidden=true;
	document.getElementById('telefone_filtro_input').value="";
	document.getElementById('input_telefone_backend').value="";	

	document.getElementById('tipo_filtro_option_cpf').hidden=false;
	document.getElementById('filtro_cpf_block').hidden=true;
	document.getElementById('filtro_cpf_tag').hidden=true;	
	document.getElementById('cpf_filtro_input').value="";
	document.getElementById('input_cpf_backend').value="";	
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

	var optionTelefone = document.getElementById('tipo_filtro_option_telefone');
	var telefoneBlock = document.getElementById('filtro_telefone_block');	
	var telefoneTag = document.getElementById('filtro_telefone_tag');	

	var optionCpf = document.getElementById('tipo_filtro_option_cpf');
	var cpfBlock = document.getElementById('filtro_cpf_block');	
	var cpfTag = document.getElementById('filtro_cpf_tag');	

	var inputDescricaoFiltro = document.getElementById('descricao_filtro_input');
	var inputDataInicioFiltro = document.getElementById('data_inicio_filtro_input');
	var inputDataFimFiltro = document.getElementById('data_fim_filtro_input');
	var inputMesFiltro = document.getElementById('mes_filtro_input');
	var inputAnoFiltro = document.getElementById('ano_filtro_input');
	var inputTelefoneFiltro = document.getElementById('telefone_filtro_input');
	var inputCpfFiltro = document.getElementById('cpf_filtro_input');

	var inputDescricaoBackend = document.getElementById('input_descricao_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputTelefoneBackend = document.getElementById('input_telefone_backend');
	var inputCpfBackend = document.getElementById('input_cpf_backend');

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
	else if (filtroTipo.value == 'TELEFONE') {
		if (inputTelefoneFiltro.value != "") {		
			optionTelefone.hidden=true;
			telefoneBlock.hidden=true;

			telefoneTag.hidden=false;
			telefoneTag.innerText = 'Telefone: ' + inputTelefoneFiltro.value;

			filtroTipo.value="";
			inputTelefoneBackend.value=inputTelefoneFiltro.value;

			filtroBt.hidden=true;
			buscarBt.hidden=false;
			filtroTipo.style.border="1px solid grey";
			filtroTipo.disabled=true;
			filtroBt.disabled=true;
			filtroBt.style.pointerEvents="none";		
		}
	}
	else if (filtroTipo.value == 'CPF') {
		if (inputCpfFiltro.value != "") {
			optionCpf.hidden=true;
			cpfBlock.hidden=true;

			cpfTag.hidden=false;
			cpfTag.innerText = 'CPF: ' + inputCpfFiltro.value;

			filtroTipo.value="";
			inputCpfBackend.value=inputCpfFiltro.value;

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

	var optionTelefone = document.getElementById('tipo_filtro_option_telefone');
	var telefoneBlock = document.getElementById('filtro_telefone_block');	
	var telefoneTag = document.getElementById('filtro_telefone_tag');

	var optionCpf = document.getElementById('tipo_filtro_option_cpf');
	var cpfBlock = document.getElementById('filtro_cpf_block');	
	var cpfTag = document.getElementById('filtro_cpf_tag');		

	var inputDescricaoBackend = document.getElementById('input_descricao_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputTelefoneBackend = document.getElementById('input_telefone_backend');	
	var inputCpfBackend = document.getElementById('input_cpf_backend');	
	
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
		telefoneBlock.hidden=true;
		cpfBlock.hidden=true;

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
		telefoneBlock.hidden=true;
		cpfBlock.hidden=true;

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
		telefoneBlock.hidden=true;
		cpfBlock.hidden=true;

		periodoTag.hidden=true;
		filtroTipo.value="PERIODO";

		inputMesBackend.value="";
		inputAnoBackend.value="";
	}
	else if (filtro == 'telefone') {
		optionTelefone.hidden=false;

		descricaoBlock.hidden=true;

		dataInicioBlock.hidden=true;
		dataFimBlock.hidden=true;
		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;
		cpfBlock.hidden=true;

		telefoneBlock.hidden=false;
		telefoneTag.hidden=true;

		filtroTipo.value="TELEFONE";

		inputTelefoneBackend.value="";
	}
	else if (filtro == 'cpf') {
		optionTelefone.hidden=false;

		descricaoBlock.hidden=true;

		dataInicioBlock.hidden=true;
		dataFimBlock.hidden=true;
		periodoMesBlock.hidden=true;
		periodoAnoBlock.hidden=true;
		telefoneBlock.hidden=true;

		cpfBlock.hidden=false;
		cpfTag.hidden=true;

		filtroTipo.value="CPF";

		inputCpfBackend.value="";
	}	

	buscarBt.hidden=true;		
	filtroBt.hidden=false;
}

function efeitoRemoverFiltro(filtro) {

	var filtroDescricao = document.getElementById('filtro_descricao_tag');
	var filtroData = document.getElementById('filtro_data_tag');	
	var filtroPeriodo = document.getElementById('filtro_periodo_tag');	
	var filtroTelefone = document.getElementById('filtro_telefone_tag');
	var filtroCpf = document.getElementById('filtro_cpf_tag');


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
	else if (filtro == 'telefone') {
		filtroTelefone.style.transition="0.5s"
		filtroTelefone.style.background="#AA3C3C";
		filtroTelefone.style.border="1px solid #AA3C3C";
		filtroTelefone.style.color="#212121";
		filtroTelefone.innerText="Remover";
		filtroTelefone.style.cursor="pointer";
	}
	else if (filtro == 'cpf') {
		filtroCpf.style.transition="0.5s"
		filtroCpf.style.background="#AA3C3C";
		filtroCpf.style.border="1px solid #AA3C3C";
		filtroCpf.style.color="#212121";
		filtroCpf.innerText="Remover";
		filtroCpf.style.cursor="pointer";
	}			
}

function efeitoRemoverFiltroLeave(filtro) {

	var filtroDescricao = document.getElementById('filtro_descricao_tag');
	var filtroData = document.getElementById('filtro_data_tag');
	var filtroPeriodo = document.getElementById('filtro_periodo_tag');	
	var filtroTelefone = document.getElementById('filtro_telefone_tag');
	var filtroCpf = document.getElementById('filtro_cpf_tag');	

	var inputDescricaoBackend = document.getElementById('input_descricao_backend');
	var inputDataInicioBackend = document.getElementById('input_data_inicio_backend');
	var inputDataFimBackend = document.getElementById('input_data_fim_backend');
	var inputMesBackend = document.getElementById('input_periodo_mes_backend');
	var inputAnoBackend = document.getElementById('input_periodo_ano_backend');
	var inputTelefoneBackend = document.getElementById('input_telefone_backend');
	var inputCpfBackend = document.getElementById('input_cpf_backend');

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
		filtroData.innerText = inputDataInicioBackend.value + ' a ' + inputDataFimBackend.value;
	}
	else if (filtro == 'periodo') {
		filtroPeriodo.style.transition="1s"
		filtroPeriodo.style.background="transparent";
		filtroPeriodo.style.border="1px solid #212121"
		filtroPeriodo.style.color="#212121";
		filtroPeriodo.innerText = 'Mês ' + inputMesBackend.value + ' de ' + inputAnoBackend.value;
	}
	else if (filtro == 'telefone') {
		filtroTelefone.style.transition="1s"
		filtroTelefone.style.background="transparent";
		filtroTelefone.style.border="1px solid #212121"
		filtroTelefone.style.color="#212121";
		filtroTelefone.innerText = 'Telefone: ' + inputTelefoneBackend.value;
	}
	else if (filtro == 'cpf') {
		filtroCpf.style.transition="1s"
		filtroCpf.style.background="transparent";
		filtroCpf.style.border="1px solid #212121"
		filtroCpf.style.color="#212121";
		filtroCpf.innerText = 'Telefone: ' + inputCpfBackend.value;
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

	if (dataCadastro == null) dataCadastro = "1998-07-21"
	var dataCadastroSplitada = dataCadastro.split("-");
	if (dataCadastroSplitada.length == 3) {
		var dataUsParaDataBr = dataCadastroSplitada[2] + "-" + dataCadastroSplitada[1] + "-" + dataCadastroSplitada[0];
		document.getElementById('edita_info').title="Fornecedor cadastrado dia " + dataUsParaDataBr + " por " + usuarioResponsavel;
	}

	document.getElementById('id_input_edicao').value=id;
	document.getElementById('edita_descricao_fornecedor_input').value=nomeCompleto;
	document.getElementById('edita_email_fornecedor_input').value=email;
	document.getElementById('edita_telefone_fornecedor_input').value=telefone;
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
	document.getElementById('edita_descricao_fornecedor_input').value="";
	document.getElementById('edita_email_fornecedor_input').value="";
	document.getElementById('edita_telefone_fornecedor_input').value="";
	document.getElementById('edita_data_nascimento_input').value="";
	document.getElementById('edita_cpfCnpj_input').value="";
	document.getElementById('edita_cep_input').value="";
	document.getElementById('edita_estado_input').value="SP";
	document.getElementById('edita_cidade_input').value="";
	document.getElementById('edita_logradouro_input').value="";
	document.getElementById('edita_numero_input').value="";
	document.getElementById('edita_bairro_input').value="";
	document.getElementById('edita_complemento_input').value="";
}

/* ================== MISC ====================== */

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
		var estadoInput = document.getElementById('estado_input');
		var cidadeInput = document.getElementById('cidade_input');
		var logradouroInput = document.getElementById('logradouro_input');
		var bairroInput = document.getElementById('bairro_input');

		estadoInput.value=dados.uf;
		cidadeInput.value = dados.localidade;
		logradouroInput.value=dados.logradouro;
		bairroInput.value=dados.bairro;

		document.getElementById('novo_item_label_numero').focus();
	}
	else if(tipo == "edita") {
		var estadoInput = document.getElementById('edita_estado_input');
		var cidadeInput = document.getElementById('edita_cidade_input');
		var logradouroInput = document.getElementById('edita_logradouro_input');
		var bairroInput = document.getElementById('edita_bairro_input');

		estadoInput.value=dados.uf;
		cidadeInput.value = dados.localidade;
		logradouroInput.value=dados.logradouro;
		bairroInput.value=dados.bairro;

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
	var columnEndereco = document.getElementsByClassName('td_endereco');	

	for(var i = 0; i < line.length; i++) {

		if (columnEndereco[i].innerText.includes('null,')) {
			columnEndereco[i].innerText='';
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