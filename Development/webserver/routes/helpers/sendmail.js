var mail = require('./mail');
var site = 'http://localhost';


function getWelcomeMail(data) {
	var a = '<a href=\"'+(site+'\\').toString()+'\">'+site+'</a>';
	return "<h1>Hej och välkommen till NORR!</h1><p>Vi ser framemot att se mer av dig!</p><hr>"+a;
}

function getRegistrationMail(data){
	var reglink = site+'\\login\\register\\'+data.register;

	var h = '<h3>Hej!</h3>'+
			'<br><p>Ett konto i ditt namn har skapats följ länk fall du vill fortsätta, annars ignorera så tas kontot bort</p>'+
			'<a href=\"'+reglink.toString()+'\">'+reglink+'</a>';

	return h;
}

function getOrderMail(order){
	var h = '<h3>Hej, tack för du har valt oss!</h3>'+
			'<p>Du har gjort en order på <strong>'+order.basket.length+'</strong>st varor, totol pris: <strong>'+order.price+'</strong>.</p>'+
			'<p>Ditt order id: <em><strong>'+order.id+'</strong></em></p><ul style="list-style:none;">';

		for(var i = 0; i < order.basket.length; i++){
			var li = '<li style="padding:8px; font-size:12pt;"><p>Namn: '+order.basket[i].name+'</p><p>Pris:'+order.basket[i].price+'kr/st</p><p>Antal'+order.basket[i].list.length+'st</p>';
			li += '</li>';

			h+=li;
		}

		var address = 'Anna trolles väg 4, 35239 Växjö Sverige';
		return h+'</ul><p>Du kan hämta dina varor <em>'+address+'</em>.<br> Du har 2 dagar på dig</p></hr><p>MVH <em>NORR</em></p>';
}

exports.send = function(content, callback){
	var data = {
		from: '',
		to: content.to,
		subject: '',
		html: ''
	}

	if(content.order != undefined){
		data.from = 'Norr1945 <kontakt.norr1945@gmail.com>';
		data.subject = 'Tack för du handlat hos oss';
		data.html = getOrderMail(content.order);
	}
	else if(content.cancle != undefined){
		data.from = 'noreply <kontakt.norr1945@gmail.com>';
		data.subject = 'Order borttagen';
		data.html = '<h3>Din order['+content.cancle+'] togs bort p.g.a. du hämtade aldrig dina varor</h3>'
	}
	else if(content.register != undefined) { //registration
		data.from = "noreply <kontakt.norr1945@gmail.com>";
		data.subject = "Registrering länk";
		data.html = getRegistrationMail(content);
	}
	else { //welcome
		data.from = "Norr1945 <kontakt.norr1945@gmail.com>";
		data.subject = "Välkommen till Norr!";
		data.html = getWelcomeMail(content);
	}

	mail.send(data, callback);
}
