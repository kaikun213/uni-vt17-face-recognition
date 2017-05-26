
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
});

function print(offset, order){
	$('table').attr('offset', offset);
	$.ajax({
	  method: "GET",
	  url: "/admin/users/"+offset+'/'+order,
	}).done(function( data ) {
	  	toTable(data);
 	}).fail(function( err ){
      showError(xhr.responseText);
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


		tr.append($('<td></td>').text(data[i].name));

		tr.append($('<td></td>').text(data[i].mail));
		tr.append($('<td></td>').text(data[i].id).click(function(){
      var elm = $(this);
      $.ajax({
        method: 'get',
        url: '/admin/orders/user/'+$(this).text(),
        success: function(orders){
          var ul = $('<ul>');
          for(var i = 0; i < orders.length; i++){
            var li = $('<li>');

            var d = new Date(orders[i].date);
            li.text('<label>Order '+(i+1)+'</label>: <strong>Date</strong></<em>'+d.yyyymmdd()+'</em><strong>Product Count</strong><em>'+orders[i].products.split(',').length+'</em><strong>Total Price</strong><em>'+orders[i].price+'</em>');
            li.val(orders[i].id);
            li.click(function(){
              window.location.href = '/admin/orders/'+$(this).val();
            });
            ul.append(li);
          }
          if(orders.length != 0) {
            var rbtn = $('<button>');
            rbtn.addClass('btn sharp btn-defualt')
                .text('<i class="fa fa-times fa-2x"></i>')
                .click(function(){
                  ul.remove();
                  $(this).remove();
                });
            elm.parent().append(rbtn, ul);
          }
        },
        error: function(xhr){
          console.log(xhr);
          showError(xhr.responseText);
        }
      });
    }).css('cursor', 'pointer'));
		tr.append($('<td></td>').text(data[i].adress));

		var option2 = $('<select>'), option1 = $('<select>'),
			opt1 = $('<option>').text('yes').val('1'),
			opt2 = $('<option>').text('no').val('0');

		if(data[i].registerd == '1') option2.append(opt1.clone(), opt2.clone());
		else option2.append(opt2.clone(), opt1.clone());

		if(data[i].admin == '1') option1.append(opt1, opt2);
    else option1.append(opt2, opt1);

		tr.append($('<td></td>').append(option2));
		tr.append($('<td></td>').append(option1));


		tr.append($('<td></td').append($('<button></button>').text('SPARA').click(function(){
			save($(this).parent().parent());
		}).addClass('btn btn-primary sharp')));

		body.append(tr);
	}
}

function del(elm){
	var id = elm.parent().attr('id');
	$.ajax({
	  method: "DELETE",
	  url: "/admin/users/",
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
	var admin = elm.children().eq(6).children('select').val(),
			regis = elm.children().eq(5).children('select').val();
	$.ajax({
		method: 'put',
		url: '/admin/users/'+elm.attr('id'),
		data: {admin: admin, registerd: regis},
		success: function(){
			showSuccess('User updaterad');
		},
		error: function(xhr){
			console.log(xhr);
			showError('Kan inte updatera user');
		}
	})
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
