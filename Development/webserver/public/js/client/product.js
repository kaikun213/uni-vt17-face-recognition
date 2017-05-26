$(document).ready(function(){

	var images = product.images.split(",");

	$('.slide-container').attr('count', images.length)


	for(var i = 0; i < images.length; i++){
		var img = $('<img/>').attr('src', images[i]).attr('alt', product.name+i);
		var div = $('<div></div>').append(img);

		var j = $('<i></i>').addClass('fa fa-circle');
		var t = $('<span></span>').append($('<img/>').attr('src', images[i]).attr('alt', 'thumbail#'+i));
		if(i == 0){
			j.addClass('slide-selected');
			t.addClass('slide-thumbnail-select');
		}
		$('.slide-thumbnails').append(t);
		$('.slide-circles').append(j);

		$('.slide').append(div);
	}


	$('.slide-circles > i').click(function() {
		//circle press
		var current = Number($('.slide-container').attr('index'));
		animate($(this).index(), $(this).index() - current);
	});
	$('.slide-thumbnails > span').click(function() {
		//circle press
		var current = Number($('.slide-container').attr('index'));
		animate($(this).index(), $(this).index() - current);
	});

	getHearts();
});

function setpos() {
	var side = $('.slide-container').children('.slide').children('div');
	var loaded = side.parent().parent().attr('loaded');

	if(loaded == 0) {

		for(var i = 0; i < side.parent().parent().attr('count'); i++){

			side.eq(i).css({position: 'absolute', top: 0, left: (side.width() * i)+'px'});
		}

		$('.slide-container').attr('loaded', 1);
	}
}

function controll(side){
	var index = Number($('.slide-container').attr('index'));
	var count = $('.slide-container').attr('count');

	index += side;
	if(index >= 0 && index < count) {
		animate(index, side);
	}
}

function animate(index, side) {
	setpos();
	var count = $('.slide-container').attr('count'),
		divs = $('.slide').children('div'),
		w = divs.width();


	$('.slide-container').attr('index', index);
	var pos = divs.parent().position().left;
	for(var i = 0; i < count; i++) {

		divs.eq(i).animate({
			left: '+='+(w * side * -1)+'px'
		}, 300);

	}
	var fa = $('.slide-container').children('.slide-circles').children('.fa');
	$('.slide-selected').removeClass('slide-selected');
	fa.eq(index).addClass('slide-selected');

	var thumbnail = $('.slide-thumbnails').children('span');
	$('.slide-thumbnail-select').removeClass('slide-thumbnail-select');
	thumbnail.eq(index).addClass('slide-thumbnail-select');

	if(index <= 0) $('.left').hide();
	else $('.left').show();
	if(index >= count-1) $('.right').hide();
	else $('.right').show();
}
