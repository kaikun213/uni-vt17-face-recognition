function showPopup(index, hide) {

	$('#popup').children().hide();
	$('#popup-container').height($(document).height());
	$('#popup-container').click(function(e){
		if(!$(this).children().is(e.target)  && $(this).has(e.target).length === 0) {
			$(this).hide();
		}
	});

	if(hide == undefined) $('#popup').addClass('strip');
	else $('#popup').removeClass('strip');

	$('#popup-container').show();
	$('#popup').children().eq(index).show();
}

function hidePopup() {
	$('#popup-container').hide();
}

function showError(msg) {
	var index = $('#popup').children().length - 2;
	showPopup(index, true);

	$('#popup').children().eq(index).children().eq(1).text(msg);
	setTimeout(function(){
		if($('#popup').children().eq(index).is(':visible'))
			hidePopup();
	}, 2400);
}

function showSuccess(msg) {
	var index = $('#popup').children().length - 1;
	showPopup(index, true);

	$('#popup').children().eq(index).children().eq(1).text(msg);
	setTimeout(function(){
		if($('#popup').children().eq(index).is(':visible'))
			hidePopup();
	}, 2400);
}

function showLoad() {
	var index = $('#popup').children().length - 3;
	showPopup(index, true);
}


window.onresize = function() {
	$('#popup-container').height($(document).height());
}


$(document).ready(function(){

	$('#file').on('change', function(){
		var files = $(this).prop('files');
		$('#file_link').val('');
		if(files.length == 1)
			$('#file + label > span').text("1 BILD");
		else if (files.length > 1)
			$('#file + label > span').text(files.length + " BILDER");
		else $('#file + label > span').text("BILDER");
	});


	var loader = $('<div></div>').addClass('well').css('text-align', 'center')
		.append($('<img>')
			.attr('src', 'images/loader.gif')
			.attr('alt', 'loading icon'))
		.append($('<p></p>').text('Loading..').css('text-align', 'center'));

	var error = $('<div></div>').addClass('panel panel-danger')
		.append($('<div></div>').addClass('panel-heading').text('Error'))
		.append($('<div></div>').addClass('panel-body'));

	var success = $('<div></div>').addClass('panel panel-success')
		.append($('<div></div>').addClass('panel-heading').text('Success'))
		.append($('<div></div>').addClass('panel-body'));

	$('#popup').append(loader, error, success);

});
