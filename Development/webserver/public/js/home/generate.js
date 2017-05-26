function Generate(layout, admin){
	for(var i = 0; i < layout.length; i++){
		createField(layout[i], admin);
	}
}

function createField(field, admin){
	var fieldelm = $('<div></div>').addClass('field ' + field.size);
	var box = $('<div></div>').addClass('hidden-xs');

	for(var i = 0; i < field.fields.length; i++){

		var div = $('<div></div>').addClass(field.fields[i].class);
		generateChildren(field.fields[i], div, admin);

		box.append(div);
	}


	fieldelm.append(box);
	$('#content').append(fieldelm);
}

function generateChildren(field, div, admin){
	var children = field.children;
	if(children){
		//it contains children
		for(var j = 0; j < children.length; j++) {
			var div2 = $('<div></div>').addClass(children[j].class);
			div.append(div2);

			generateChildren(children[j], div2, admin);
			// div.append(createFieldContent(children[j], div2));

		}

		// box.append(div);
	} else {
		//its a body
		div.append(createFieldContent(field, admin));
	}
}

function createFieldContent(field, admin){
	var ahref = $('<a></a>').attr('href', field.link)
		.css({'display': 'block', 'background-color': field.color});

	var img = $('<img>').attr('src', field.image);
	img.attr('arc', field.header);
	img.load(function(){
		var img = $(this);
		if((img.width()-100) > img.height()){
			$(this).css('height', '100%');
		}
		else{
			 $(this).css('width', '100%');
		 }
	});

	ahref.append(img);
	ahref.append($('<h3></h3>').text(field.header));
	ahref.append($('<p></p>').text(field.caption));

	return ahref;
}
