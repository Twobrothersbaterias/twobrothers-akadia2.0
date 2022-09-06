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
	var menuSuperior = document.getElementById('menu_superior')
	var tituloSessao = document.getElementById('conteudo_titulo_text');
	var nomePerfil = document.getElementById('nome_perfil');
	var menuMobile = document.getElementById('menu_superior_mobile');
	var informativoRow = document.getElementById('informativo_row');
	var excluirText = document.getElementById('excluir_text');

	var informativo = document.getElementsByClassName('informativo');
	var th = document.getElementsByClassName('th');
	var td = document.getElementsByClassName('td');
	var tdRemove = document.getElementsByClassName('td_checkbox');
	var thRemove = document.getElementsByClassName('th_remove');	
	var btnExcluir = document.getElementsByClassName('btn_excluir');
	var formRemoveImg = document.getElementsByClassName('form_remove_img');
	var formRemoveText = document.getElementsByClassName('form_remove_text');
	var liA = document.getElementsByClassName('li_a');
	var menuSuperiorMobileItem = document.getElementsByClassName('menu_superior_mobile_item');

	if(bodyWidth > 1200){
		console.log("Muito grande");

		sideMenu.style.display="block";
		main.style.width="95.5%";
		sideMenu.style.width="4.5%";
		tituloSessao.style.fontSize="1.5rem";
		nomePerfil.style.fontSize="1.25rem";
		menuSuperior.style.height="55px";
		menuMobile.style.display="none";
		informativoRow.style.justifyContent="center";

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.95rem";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.95rem";
		}
		for(var i = 0; i < informativo.length; i++){
			informativo[i].style.fontSize="1rem";
			informativo[i].style.width="22%";
			informativo[i].style.margin="0 7px";
		}		
		for(var i = 0; i < liA.length; i++){
			liA[i].style.fontSize="1rem";
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

		sideMenu.style.display="block";
		main.style.width="95%";
		sideMenu.style.width="5%";
		tituloSessao.style.fontSize="1.35rem";
		nomePerfil.style.fontSize="1.15rem";
		menuSuperior.style.height="50px";
		menuMobile.style.display="none";
		informativoRow.style.justifyContent="center";

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.95rem";
			th[i].style.width="15.5%";				
		}		
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.95rem";
			td[i].style.width="15.5%";				
		}
		for(var i = 0; i < thRemove.length; i++){
			thRemove[i].style.width="7%";
		}		
		for(var i = 0; i < tdRemove.length; i++){
			tdRemove[i].style.width="7%";
		}		
		for(var i = 0; i < informativo.length; i++){
			informativo[i].style.fontSize="0.95rem";
			informativo[i].style.width="25%";
			informativo[i].style.margin="0 7px";
		}			
		for(var i = 0; i < liA.length; i++){
			liA[i].style.fontSize="0.90rem";
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
	
	}
	else if(bodyWidth <= 992 && bodyWidth > 768){
		console.log('Médio');

		sideMenu.style.display="block";
		if (bodyWidth > 870) {
			main.style.width="94.5%";
			sideMenu.style.width="5.5%";
		}
		else {
			main.style.width="94.0%";
			sideMenu.style.width="6%";	
		}

		tituloSessao.style.fontSize="1.35rem";
		nomePerfil.style.fontSize="1.15rem";
		menuSuperior.style.height="50px";
		menuMobile.style.display="none";
		informativoRow.style.justifyContent="center";

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.85rem";
			th[i].style.width="15.66%";			
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.85rem";
			td[i].style.width="15.66%";			
		}	
		for(var i = 0; i < thRemove.length; i++){
			thRemove[i].style.width="6%";
		}		
		for(var i = 0; i < tdRemove.length; i++){
			tdRemove[i].style.width="6%";
		}				
		for(var i = 0; i < informativo.length; i++){
			informativo[i].style.fontSize="0.85rem";
			informativo[i].style.width="28.9%";	
			informativo[i].style.margin="0 7px";					
		}			
		for(var i = 0; i < liA.length; i++){
			liA[i].style.fontSize="0.80rem";
			liA[i].style.padding="5px 10px";			
		}		
		for(var i = 0; i < formRemoveImg.length; i++) {
			formRemoveImg[i].style.display="block";
			formRemoveImg[i].style.maxWidth="70%";			
		}	

		for(var i = 0; i < btnExcluir.length; i++) {
			btnExcluir[i].style.display="none";
		}				
	}
	else if(bodyWidth <= 768 && bodyWidth > 540){
		console.log('Pequeno');

		sideMenu.style.display="none";
		main.style.width="100%";
		tituloSessao.style.fontSize="1rem";
		nomePerfil.style.fontSize="1rem";
		menuSuperior.style.height="45px";
		menuMobile.style.display="flex";
		informativoRow.style.justifyContent="center";

		for(var i = 0; i < th.length; i++) {
			th[i].style.fontSize="0.75rem";
			th[i].style.width="15.66%";			
		}
		for(var i = 0; i < td.length; i++) {
			td[i].style.fontSize="0.75rem";
			td[i].style.width="15.66%";			
		}
		for(var i = 0; i < thRemove.length; i++){
			thRemove[i].style.width="6%";
		}		
		for(var i = 0; i < tdRemove.length; i++){
			tdRemove[i].style.width="6%";
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
			formRemoveImg[i].style.maxWidth="65%";
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

	}
	else if(bodyWidth < 540){
		console.log('Muito pequeno');

		sideMenu.style.display="none";
		main.style.width="100%";
		tituloSessao.style.fontSize="1rem";
		nomePerfil.style.fontSize="1rem";
		menuSuperior.style.height="40px";
		menuMobile.style.display="flex";

		for(var i = 0; i < th.length; i++){
			th[i].style.fontSize="0.65rem";
			th[i].style.width="15.66%";
		}
		for(var i = 0; i < td.length; i++){
			td[i].style.fontSize="0.65rem";
			td[i].style.width="15.66%";
		}
		for(var i = 0; i < thRemove.length; i++){
			thRemove[i].style.width="6%";
		}		
		for(var i = 0; i < tdRemove.length; i++){
			tdRemove[i].style.width="6%";
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
		}	

		for(var i = 0; i < btnExcluir.length; i++) {
			btnExcluir[i].style.display="none";
		}		

		for(var i = 0; i < menuSuperiorMobileItem.length; i++) {
			if (bodyWidth > 470) {
				menuSuperiorMobileItem[i].style.width="9%";
			}
			else {
				menuSuperiorMobileItem[i].style.width="11.11%";	
			}
		}		

	}

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

function novaDespesa() {

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

function fechaNovaDespesa() {
	var containerPrincipal = document.getElementById('conteudo_container');
	var menuSuperior = document.getElementById('menu_superior');
	var menuSuperiorMobile = document.getElementById('menu_superior_mobile');
	var sideMenu = document.getElementById('side_menu');	

	var containerNovo = document.getElementById('conteudo_container_novo');	
	
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