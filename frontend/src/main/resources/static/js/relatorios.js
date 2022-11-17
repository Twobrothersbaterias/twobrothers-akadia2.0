/* =========================== RESPONSIVIDADE DA TELA =========================== */

window.onload = responsive();
window.onresize = doALoadOfStuff;

//console.log(JSON.parse(objetoFaturamentoLiquido[0]));
//console.log(JSON.parse(objetoFaturamentoLiquido[1]));
//console.log(JSON.parse(objetoFaturamentoLiquido[2]));
//console.log(JSON.parse(objetoFaturamentoLiquido[3]));

console.log(faturamentoBrutoPorDiasMes);
console.log(faturamentoLiquidoPorDiasMes);
console.log(ticketMedioPorDiaMes);
console.log(custosPorDiaMes);
console.log(despesasPorDiaMes);

var privilegio = document.getElementById('body').getAttribute('data-privilegio');
var tipoFiltro = document.getElementById('tipo_filtro');

//console.log('Tipo do filtro: ' + tipoFiltro.value);
console.log('Privilégio: ' + privilegio);

if (tipoFiltro != null) {
	tituloResponsivo(tipoFiltro.value);
}	

document.onkeydown=function(){

	var keyCode = window.event.keyCode;
	bind(keyCode);

}

grafico();

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
	    	window.location.href="/precos";
	    }

	    else if(keyCode == '70') {
	    	abrirFiltro();
	    }	 	  

	    else if(keyCode == '192' && privilegio != "Vendedor") {
	    	document.getElementById('relatorio_button').click();
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

		for(var i = 0; i < liA.length; i++){
			liA[i].style.fontSize="0.85rem";
			liA[i].style.padding="6px 10px";			
		}	

		for(var i = 0; i < img.length; i++) {
			img[i].style.width="23px";
			img[i].style.padding="2px 0";
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
			img[i].style.padding="2px 0";
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
			img[i].style.padding="2px 0";			
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

	}
	else if(bodyWidth <= 768 && bodyWidth > 540){
		console.log('Tela: Pequena');	

		conteudoTituloText.style.fontSize="1.1rem";
		sideMenu.style.display="none";
		main.style.width="100%";
		mainRow.style.width = "100%";
		menuMobile.style.display="flex";	
		
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
			img[i].style.padding="2px 0";			
		}

		for(var i = 0; i < aImg.length; i++) {
			aImg[i].style.marginLeft="10px";
		}

		for(var i = 0; i < imgContainer.length; i++) {
			imgContainer[i].style.width="20px";
			imgContainer[i].style.marginLeft="15px";			
		}

		for(var i = 0; i < menuSuperiorMobileItem.length; i++) {
			if (bodyWidth > 670) {
				menuSuperiorMobileItem[i].style.width="7.05%";
			}
			else {
				menuSuperiorMobileItem[i].style.width="8%";
			}
		}

	}
	else if(bodyWidth < 540){
		console.log('Tela: Muito pequena');

		conteudoTituloText.style.fontSize="1rem";
		sideMenu.style.display="none";
		main.style.width="100%";
		mainRow.style.width = "100%";
		menuMobile.style.display="flex";

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
			img[i].style.padding="2px 0";			 
		}

		for(var i = 0; i < aImg.length; i++) {
			aImg[i].style.marginLeft="8px";
		}	

		for(var i = 0; i < menuSuperiorMobileItem.length; i++) {
			if (bodyWidth > 470) {
				menuSuperiorMobileItem[i].style.width="9%";
			}
			else {
				menuSuperiorMobileItem[i].style.width="10%";	
			}
		}

	}

}

/* ================== GRÁFICOS ====================== */

function graficoResumido() {

	var ctx = document.getElementsByClassName("baterias_vendidas_resumido");

	var chartGraph = new Chart(ctx, {
		type: 'line',
		data: {
			labels: ["Jan", "Fev", "Mar", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"],			
			datasets: [
				{
					data: [5, 10, 5, 14, 20, 15, 6, 14, 8, 12, 15, 5, 10],
					borderWidth: 2,
					borderColor: 'rgba(77, 166, 253, 0.85)',
					backgroundColor: 'transparent',
					fill:false,
					color: 'transparent',					
				}									
			]
		},
		options: {			
	         legend: {
	            display: false,
	         },			
			title: {
				display: false,
			},
		}
	});
}

function grafico() {

	if (diasDoMes != null && diasDoMes.length > 20) {
		chartLiquido();
		barChart();
	}

}

function changeGraficoLinha() {

	var select = document.getElementById('grafico_line');
	if (select.value == 'liquido') {
		document.getElementById('chart_liquido').hidden=false;
		document.getElementById('chart_bruto').hidden=true;
		document.getElementById('chart_ticket').hidden=true;
		document.getElementById('chart_custos').hidden=true;
		document.getElementById('chart_despesas').hidden=true;
		chartLiquido();
	}
	else if (select.value == 'bruto') {
		document.getElementById('chart_liquido').hidden=true;
		document.getElementById('chart_bruto').hidden=false;		
		document.getElementById('chart_ticket').hidden=true;
		document.getElementById('chart_custos').hidden=true;
		document.getElementById('chart_despesas').hidden=true;		
		chartBruto();
	}
	else if (select.value == 'ticket') {
		document.getElementById('chart_liquido').hidden=true;
		document.getElementById('chart_bruto').hidden=true;
		document.getElementById('chart_ticket').hidden=false;
		document.getElementById('chart_custos').hidden=true;
		document.getElementById('chart_despesas').hidden=true;				
		chartTicket();
	}
	else if (select.value == 'custos') {
		document.getElementById('chart_liquido').hidden=true;
		document.getElementById('chart_bruto').hidden=true;
		document.getElementById('chart_ticket').hidden=true;
		document.getElementById('chart_custos').hidden=false;
		document.getElementById('chart_despesas').hidden=true;				
		chartCustos();
	}
	else if (select.value == 'despesas') {
		document.getElementById('chart_liquido').hidden=true;
		document.getElementById('chart_bruto').hidden=true;
		document.getElementById('chart_ticket').hidden=true;
		document.getElementById('chart_custos').hidden=true;
		document.getElementById('chart_despesas').hidden=false;				
		chartDespesas();
	}

}

function lineChart() {

	var ctx = document.getElementsByClassName("line-chart");
	var select = document.getElementById('grafico_line');

	var chartGraph = new Chart(ctx, {
		type: 'line',
		data: {
			labels: diasDoMes,
			datasets: [
				{
					label: "Valor",
					data: faturamentoLiquidoPorDiasMes,
					borderWidth: 3,
					borderColor: 'rgba(77, 166, 253, 0.85)',
					backgroundColor: 'rgba(77, 166, 253, 0.1)'
				},
				/*{
					label: "TAXA DE CLIQUES - 2022",
					data: [3, 6, 15, 4, 29, 12, 5, 2, 0, 5, 17, 4, 15],
					borderWidth: 6,
					borderColor: 'rgba(252, 135, 56, 0.85)',
					backgroundColor: 'transparent',
				}*/							
			]
		},
		options: {
			//maintainAspectRatio: false,
			elements: {
				line: {
					tension: 0,
				},
			},
	         legend: {
	            display: false,
	         },				
		}
	});	
}

function chartLiquido() {
	var ctx = document.getElementsByClassName("chart-liquido");
	var chartGraph = new Chart(ctx, {
		type: 'line',
		data: {
			labels: diasDoMes,
			datasets: [
				{
					label: "Valor líquido do dia",
					data: faturamentoLiquidoPorDiasMes,
					borderWidth: 3,
					borderColor: 'rgba(77, 166, 253, 0.85)',
					backgroundColor: 'rgba(77, 166, 253, 0.1)'
				},						
			]
		},
		options: {
			//maintainAspectRatio: false,
			elements: {
				line: {
					tension: 0,
				},
			},
	         legend: {
	            display: false,
	         },				
		}
	});		
}

function chartBruto() {
	var ctx = document.getElementsByClassName("chart-bruto");
	var chartGraph = new Chart(ctx, {
		type: 'line',
		data: {
			labels: diasDoMes,
			datasets: [
				{
					label: "Valor bruto do dia",
					data: faturamentoBrutoPorDiasMes,
					borderWidth: 3,
					borderColor: 'rgba(252, 135, 56, 0.85)',
					backgroundColor: 'rgba(252, 135, 56, 0.1)'
				},						
			]
		},
		options: {
			//maintainAspectRatio: false,
			elements: {
				line: {
					tension: 0,
				},
			},
	         legend: {
	            display: false,
	         },				
		}
	});		
}

function chartTicket() {
	var ctx = document.getElementsByClassName("chart-ticket");
	var chartGraph = new Chart(ctx, {
		type: 'line',
		data: {
			labels: diasDoMes,
			datasets: [
				{
					label: "Ticket médio do dia",
					data: ticketMedioPorDiaMes,
					borderWidth: 3,
					borderColor: 'rgba(201, 175, 44, 0.85)',
					backgroundColor: 'rgba(201, 175, 44, 0.1)'
				},						
			]
		},
		options: {
			//maintainAspectRatio: false,
			elements: {
				line: {
					tension: 0,
				},
			},
	         legend: {
	            display: false,
	         },				
		}
	});		
}

function chartCustos() {
	var ctx = document.getElementsByClassName("chart-custos");
	var chartGraph = new Chart(ctx, {
		type: 'line',
		data: {
			labels: diasDoMes,
			datasets: [
				{
					label: "Custos do dia",
					data: custosPorDiaMes,
					borderWidth: 3,
					borderColor: 'rgba(101, 199, 106, 0.85)',
					backgroundColor: 'rgba(101, 199, 106, 0.1)'
				},						
			]
		},
		options: {
			//maintainAspectRatio: false,
			elements: {
				line: {
					tension: 0,
				},
			},
	         legend: {
	            display: false,
	         },				
		}
	});		
}

function chartDespesas() {
	var ctx = document.getElementsByClassName("chart-despesas");
	var chartGraph = new Chart(ctx, {
		type: 'line',
		data: {
			labels: diasDoMes,
			datasets: [
				{
					label: "Despesas do dia",
					data: despesasPorDiaMes,
					borderWidth: 3,
					borderColor: 'rgba(81, 198, 207, 0.85)',
					backgroundColor: 'rgba(81, 198, 207, 0.1)'
				},						
			]
		},
		options: {
			//maintainAspectRatio: false,
			elements: {
				line: {
					tension: 0,
				},
			},
	         legend: {
	            display: false,
	         },				
		}
	});		
}

function barChart() {
	var ctx = document.getElementsByClassName("bar-chart");

	var chartGraph = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: ["Moura", "Heliar", "Delco", "America", "Baxter", "Eletran", "Freedom"],
			datasets: [
				{
					label: "Vendas",
					data: [5, 10, 5, 14, 20, 15, 6],
					borderWidth: 3,
					borderColor: 'rgba(224, 74, 165, 0.85)',
					backgroundColor: 'rgba(224, 74, 165, 0.5)'
				},
				/*{
					label: "TAXA DE CLIQUES - 2022",
					data: [3, 6, 15, 4, 29, 12, 5, 2, 0, 5, 17, 4, 15],
					borderWidth: 6,
					borderColor: 'rgba(6, 204, 6, 0.85)',
					backgroundColor: 'transparent',
				}*/							
			]
		},
		options: {
			//maintainAspectRatio: false,
			elements: {
				line: {
					tension: 0,
				},
			},
	         legend: {
	            display: false,
	         },				
		}
	});	
}

/* ================== MISC ====================== */


function hideMessage(){
	var alertas = document.getElementsByClassName('alert');
	for(var i = 0; i < alertas.length; i++){
		alertas[i].hidden=true;
	}
}


function doALoadOfStuff() {
	document.getElementById('conteudo_container').style.transition="2s";
	responsive();
}

function tituloResponsivo(filtro) {

	var titulo = document.getElementById('conteudo_titulo_text');	
	var produto = document.getElementById('back_produto');
	var fornecedor = document.getElementById('back_fornecedor');		
	var fornecedorId = document.getElementById('back_fornecedorId');
	var produtoId = document.getElementById('back_produtoId');	

	if(filtro == "todos") {
		titulo.innerText="Todos os preços";
	}
	else if (filtro == "produto") {
		titulo.innerText="Preços do produto " + produto.value;
	}

	else if (filtro == "produtoId") {
		var nomeProduto = produtoId.getAttribute('data-nomeProduto');
		if (produtoId.length > 20) {
			produtoId = nomeProduto.slice(0,20) + "...";
		}
		titulo.innerText="Preços do produto " + nomeProduto;
	}

	else if (filtro == "fornecedor") {
		titulo.innerText="Preços com " + fornecedor.value;
	}		


	else if (filtro == "fornecedorId") {
		var nomeFornecedor = fornecedorId.getAttribute('data-nomeFornecedor');
		if (nomeFornecedor.length > 20) {
			nomeFornecedor = nomeFornecedor.slice(0,20) + "...";
		}
		titulo.innerText="Preços com " + nomeFornecedor;
	}


	else if (filtro == "descricao") {
		titulo.innerText="Colaboradores de nome " + descricao.value;
	}
	else if (filtro == "usuario") {
		titulo.innerText="Colaboradores de usuário " + (usuario.value);
	}

}
