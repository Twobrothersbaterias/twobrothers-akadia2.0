/* =========================== RESPONSIVIDADE DA TELA =========================== */

window.onload = responsive();
window.onresize = doALoadOfStuff;

function responsive(){

	document.getElementById('main_loader').style.display="none";
	document.getElementById('main_row').style.display="flex";

	bodyWidth = document.getElementById('body').clientWidth;
	bodyHeight = document.getElementById('body').clientHeight;

	/* Ajustando content */
	var tamanhoMain = document.getElementById('main');
	var mainRow = document.getElementById('main_row');

	if(bodyWidth > 1200){
		console.log("Muito grande");
	
	}
	else if(bodyWidth <= 1200 && bodyWidth > 992){
		console.log("Grande");
	
	}
	else if(bodyWidth <= 992 && bodyWidth > 768){
		console.log('Médio');
		
	}
	else if(bodyWidth <= 768 && bodyWidth > 540){
		console.log('Pequeno');

	}
	else if(bodyWidth < 540){
		console.log('Muito pequeno');

	}

}

function doALoadOfStuff() {
	responsive();
}

/* ================== FECHA MENSAGENS INFORMATIVAS ====================== */

function hideMessage(){
	var alertas = document.getElementsByClassName('alert');
	for(var i = 0; i < alertas.length; i++){
		alertas[i].hidden=true;
	}
}