var categories = [];
var productCount = 0;

$.get('/category', function(data){
	categories = data;
	var list = $('#category');
	
	for(var i = 1; i < data.length; i++){
		var opt = $('<option>');
		opt.val(data[i].id).text(data[i].name);

		list.append(opt);
	}
	init();
}).fail(function(err){
	console.log(err);
	categories = undefined;
});
$.get('/admin/count', function(data){
	productCount = data[0]['COUNT(*)'];
	setcontrols();
}).fail(function(err){
	console.log(err);
	categories = undefined;
});


function setcontrols(){
	var last = productCount + (6 - (productCount%6)) - 6;

	if(productCount < 6) {
		$('.next').addClass('btn-info');
		$('.last').addClass('btn-info');
	}
	else if(Number($('table').attr('offset')) >= last) {
		$('.next').addClass('btn-info');
		$('.last').addClass('btn-info');
	}
	else {
		$('.next').removeClass('btn-info');
		$('.last').removeClass('btn-info');
	}
}
function init(){
	$('#upload').click(add);


	$('table').attr('order', 'name');
	print(0, 'name');


	$('th').click(function(){

		if($(this).hasClass('not')) {
      console.log('not clickable');
    }
		else if($(this).children('i').hasClass('fa-caret-down')) {
			print(0, $(this).attr('type') + ' desc');
			$(this).children('i').removeClass('fa-caret-down');
			$(this).children('i').addClass('fa-caret-up');
			$('table').attr('order', $(this).attr('type') + ' desc');
		}
		else {
			var current = $('.fa-caret-down');
			if(current.length == 0) current = $('.fa-caret-up');
			current.removeClass('fa-caret-down');
			current.removeClass('fa-caret-up');
			current.addClass('fa-caret-left');

			$(this).children('i').removeClass('fa-caret-left');
			$(this).children('i').addClass('fa-caret-down');
			print(0, $(this).attr('type'));
			$('table').attr('order', $(this).attr('type'));
		}

	});
}

function print(offset, order){
	$('table').attr('offset', offset);
	$.ajax({
	  method: "GET",
	  url: "/admin/product/"+offset+'/'+order,
	})
	  .done(function( data ) {
	  	toTable(data);
 	})
	  .fail(function( err ){
	  	console.log('err', err);
	});
}
function toTable(data){
	var body = $('tbody');
	body.empty();

	for(var i = 0; i < data.length; i++){
		var tr = $('<tr></tr>').attr('id', data[i].id);
		tr.append($('<td></td').append($('<button></button>').text('TA BORT').click(function(){
			del($(this).parent());
		}).addClass('btn btn-danger sharp')));

		var select = getCat(data[i].category);

		tr.append($('<td></td>').text(data[i].name).attr('contenteditable', 'true'));
		if(select) tr.append($('<td></td>').append(select));
		else tr.append($('<td></td>').text(data[i].category));
		tr.append($('<td></td>').text(data[i].info)
			.addClass('info2')
			.click(function(){
				$('#info-edit').val($(this).text());
				showPopup(1);
				var current = $(this);

				$('#info-edit').on('change keyup paste', function(){
					current.text($(this).val());
				})
			})); //too long make when click to a box!
		tr.append($('<td></td>').text(data[i].price).attr('contenteditable', 'true'));
		tr.append($('<td></td>').text(data[i].rea).attr('contenteditable', 'true'));
		tr.append($('<td></td>').text(data[i].stock).attr('contenteditable', 'true'));

		tr.append($('<td></td').append($('<button></button>').text('SPARA').click(function(){
			save($(this).parent());
		}).addClass('btn btn-primary sharp')));

		body.append(tr);
	}
}

function getCat(current) {
	if(categories != undefined) {
		var select = $('<select>');
		for(var i = 1; i < categories.length; i++) {
			var option = $('<option>').text(categories[i].name).val(categories[i].id);
			if(current == categories[i].id) option.attr('selected', 'selected');
			select.append(option);
		}
		return select;
	} else return null;
}


function del(elm){
	var id = elm.parent().attr('id');
	$.ajax({
	  method: "DELETE",
	  url: "/admin/product/",
	  data: { id: id }
	})
  .done(function( msg ) {
	showSuccess('Successfull borttagning');
	productCount--;
	setcontrols();
	print($('table').attr('offset'), $('table').attr('order'));
  })
  .fail(function( err ){
	showError(err.responseText);
  });
}
function save(elm){
	var tr = elm.parent();

	var data = {
		id: tr.attr('id'),
		name: tr.children().eq(1).text(),
		category: tr.children().eq(2).children('select').val(),
		info: tr.children().eq(3).text(),
		price: tr.children().eq(4).text(),
		rea: tr.children().eq(5).text(),
		stock: tr.children().eq(6).text(),
		images: "" //will not be saved
	};

	$.ajax({
		method: 'PUT',
		url: '/admin/product/',
		data: {data:data}
	}).done(function(msg) {
		showSuccess('Successfull updatering av produkt');
	}).fail(function(err){
		showError(err.responseText);
	});
}
function add() {

	var data = {
		name: $('#name').val(),
		category: $('#category').val(),
		price: Number($('#price').val()),
		stock: Number($('#stock').val()),
		rea: Number($('#rea').val()),
		info: $('#info').val(),
		images: ""
	};

	if(data.name == '') {
		showError('Produkt måste ha ett namn!');
	}
	else {
		showLoad();
		upload(function(err, images) {
			data.images = images.toString();

			$.ajax({
			  method: "POST",
			  url: "/admin/product/",
			  data: { data: data }
			})
			  .done(function( msg ) {
			  	showSuccess('Successfullt!');
				productCount++;
				setcontrols();
			  	print($('table').attr('offset'), $('table').attr('order'));
			  })
			  .fail(function(err){
			  	showError(err.responseText);
			  });
		});
	}
}



function first() {

	if(Number($('table').attr('offset')) > 0) {
		print(0, $('table').attr('order'));

		$('.first').addClass('btn-info');
		$('.prev').addClass('btn-info');

		$('.last').removeClass('btn-info');
		$('.next').removeClass('btn-info');
	}

}
function prev() {
	var o = Number($('table').attr('offset'));

	if((o - 6) >= 0) {
		print(o - 6, $('table').attr('order'));

		$('.last').removeClass('btn-info');
		$('.next').removeClass('btn-info');

		if(Number($('table').attr('offset')) == 0) {

			$('.first').addClass('btn-info');
			$('.prev').addClass('btn-info');
		}
	}
}
function next() {
	var o = Number($('table').attr('offset'));


	var last = productCount + (6 - (productCount%6)) - 6;
	if((o+6) <= last) {
		print(o + 6, $('table').attr('order'));

		$('.first').removeClass('btn-info');
		$('.prev').removeClass('btn-info');

		if(Number($('table').attr('offset')) >= last) {

			$('.last').addClass('btn-info');
			$('.next').addClass('btn-info');
		}
	}

}
function last() {
	var last = productCount + (6 - (productCount%6)) - 6;
	if(Number($('table').attr('offset')) != last) {
		print(last, $('table').attr('order'));

		$('.next').addClass('btn-info');
		$('.last').addClass('btn-info');

		$('.first').removeClass('btn-info');
		$('.prev').removeClass('btn-info');
	}

}
