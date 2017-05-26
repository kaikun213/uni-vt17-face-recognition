
//when user is clicked in table, get all info from oders about trhis user and list below his/her name
// get('/admin/orders/user/:id')..

var productCount = 0;

$.get('/admin/users/count', function(data){
	productCount = data[0]['COUNT(*)'];
	setcontrols();
}).fail(function(xhr){
  console.log(xhr);
	showError('problems loading count');
});


function setcontrols(){
	var last = productCount + (20 - (productCount%20)) - 20;

	if(productCount < 20) {
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
$(document).ready(function(){
	$('table').attr('order', 'name');
	print(0, 'date');


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
});

function print(offset, order){
	$('table').attr('offset', offset);
	$.ajax({
	  method: "GET",
	  url: "/admin/orders/"+offset+'/'+order,
	}).done(function( data ) {
	  	toTable(data);
      // console.log(data);
 	}).fail(function( xhr ){
      console.log(xhr);
      showError('Database error');
	});
}
function toTable(data){
	console.log(data);
	var body = $('tbody');
	body.empty();
  for(var i = 0; i < data.length; i++){
		var tr = $('<tr></tr>').attr('id', data[i].id);
		tr.attr('order', JSON.stringify(data[i]));
		tr.append($('<td></td').append($('<button></button>').text('TA BORT').click(function(){
			del($(this).parent());
		}).addClass('btn btn-danger sharp')));

		var date = new Date(Number(data[i].date));

		var checkbox = $('<input type="checkbox">');
		if(data[i].checked == 1) checkbox.attr('checked', 'checked');
		tr.append($('<td></td>').text(date.yyyymmdd()));
		tr.append($('<td></td>').append(checkbox));
    tr.append($('<td></td>').text(data[i].price));

		tr.append($('<td></td>').text(data[i].username)); //get name from user
		tr.append($('<td></td>').text(data[i].usermail)); //get mail from user
		tr.append($('<td></td>').text(data[i].adress)); //get adress from user
    tr.append($('<td></td>').text(data[i].id));



		tr.append($('<td></td').append($('<button></button>').text('SPARA').click(function(){
			save($(this).parent());
		}).addClass('btn btn-primary sharp')));

		body.append(tr);
	}
}

function del(elm){
	var order = JSON.parse(elm.parent().attr('order'));
	$.ajax({
	  method: "DELETE",
	  url: "/admin/orders/",
	  data: order
	}).done(function( msg ) {
  	showSuccess('Successfull borttagning');
  	elm.parent().remove();
  }).fail(function( err ){
		console.log(err);
	  showError(err.responseText);
  });
}
function save(elm){
	var id = elm.parent().attr('id'), check = elm.parent().children().eq(2).children().eq(0).is(':checked');

	$.ajax({
	  method: "put",
	  url: "/admin/orders/",
	  data: { id: id, checked: (check?1:0) }
	}).done(function( msg ) {
		console.log(msg);
  	showSuccess('Successfull Updatering');
  }).fail(function( err ){
		console.log(err);
	   showError(err.responseText);
  });
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

Date.prototype.yyyymmdd = function() {
  var mm = this.getMonth() + 1; // getMonth() is zero-based
  var dd = this.getDate();

  return [this.getFullYear(),
          (mm>9 ? '' : '0') + mm,
          (dd>9 ? '' : '0') + dd
        ].join('.');
};
