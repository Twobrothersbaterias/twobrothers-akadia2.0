window.onresize = responsive();
window.onload = responsive()

function responsive(){

	document.getElementById('global').style.display="flex";
	document.getElementById('main_loader').style.display="none";

	var all = document.getElementById('global')	
	var body = document.getElementById('body')
	bodyWidth = document.getElementById('body').clientWidth;
	bodyHeight = document.getElementById('body').clientHeight;	

	var prenchimento = document.getElementById('prenchimento');

	if(bodyHeight < prenchimento.clientHeight){
		all.style.alignItems="initial";
		body.style.paddingTop="50px";
	}
	else{
		all.style.alignItems="center";
		body.style.paddingTop="0";
	}

	if(bodyWidth > 1200){
		console.log("Muito grande");
		document.getElementById('prenchimento').style.padding="40px 5px";

	}

	else if(bodyWidth <= 1200 && bodyWidth > 992){
		console.log("Grande");		
		document.getElementById('prenchimento').style.padding="50px";

	}

	else if(bodyWidth <= 992 && bodyWidth > 768){
		console.log('Médio');
		document.getElementById('prenchimento').style.padding="50px";

	}

	else if(bodyWidth <= 768 && bodyWidth > 540){
		console.log('Pequeno');
		document.getElementById('prenchimento').style.padding="50px";


	}
	
	else if(bodyWidth < 540){
		console.log('Muito pequeno');
		document.getElementById('prenchimento').style.padding="50px";

	}							
}