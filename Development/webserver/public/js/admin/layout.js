function addField(options, admin, client){
  var container = document.createElement('div');
  container.className = 'field-container';
  var field = document.createElement('div'), fieldObj;

  if(options.fieldObj) {
    fieldObj = options.fieldObj;
    if(fieldObj.children == undefined) options.fieldObj.children = [];
  }
  else {
    fieldObj = {
      children: [],
      image: null,
      link: 'javascript:void(0)',
      text: '',
      header: '',
      style: options.style,
      id: 0,
      padding: 0,
      color: ''
    };
  }
  field.className = 'field';
  if(client == undefined) field.className += ' admin';
  var a = createElementMod('a', {
    href: client?fieldObj.link:'javascript:void(0)'
  });

  if(fieldObj.children && fieldObj.children == ''){
    if(fieldObj.image && fieldObj.image != ''){
      var img = createElementMod('img', {
        src: fieldObj.image.link,
        alt: fieldObj.image.name,
        style: 'position: absolute'
      });
      a.appendChild(img);
      setTimeout(function(){
        movableElement(img, fieldObj.image, client);
      }, 100);
    }

    if(fieldObj.color != ''){
      field.style.background = fieldObj.color;
    }
    field.style.padding = fieldObj.padding+'px';
  }

  field.style = fieldObj.style;
  field.appendChild(a);
  if(options.parent != undefined){
    if(client == undefined && admin == undefined) {
      fieldObj.id = options.parent.id+'.'+options.id;
      options.parent.children.push(fieldObj);
    }

    field.setAttribute('id', fieldObj.id);
    // container.appendChild(field);
    document.getElementById(options.parent.id).appendChild(field);
  }
  else { //no parent
    if(client == undefined && admin == undefined && layout.length > 0) fieldObj.id = Number(layout[layout.length-1].id)+1;
    field.setAttribute('id', fieldObj.id);
    //else 0..
    var content = document.getElementById('content');
    container.appendChild(field);


    if(client == undefined){
      var index = Number(options.index);
      if(admin == undefined){
        layout.splice(index, 0, fieldObj);
      }

      var removeBtn = createButton('danger sharp field-remover', '<i class="fa fa-times"></i>', function(){
        content.removeChild(container);
        for(var i = 0; i < layout.length; i++) {
          if(layout[i].id == fieldObj.id) {
            layout.splice(i, 1);
            break;
          }
        }
      });

      var paddingCounter = createElementMod('input', {
        className: 'field-remover',
        value: fieldObj.padding?fieldObj.padding:0,
        change: function(e){
            console.log(fieldObj);
        },
        type: 'number'
      });

      container.appendChild(removeBtn);
      container.appendChild(paddingCounter);

      addFieldBtn(index+1, container);
      content.insertBefore(container, content.children[index]);
    }
    else content.appendChild(container);
  }

    if(client == undefined) {
      var clip = createButton('defualt fieldbuttons', '<i class="fa fa-scissors"></i>', function(){
        Cut(fieldObj);
      });
      var imageBtn = createButton('defualt fieldbuttons', '<i class="fa fa-picture-o"></i>', function(){
        imageHandle(this, fieldObj);
      });
      var linkBtn = createButton('defualt fieldbuttons', '<i class="fa fa-link"></i>', function(){
        $('#field_link').val(fieldObj.link=='javascript:void(0)'?'':fieldObj.link);
        showPopup(1);
        document.getElementById('field_link_btn').addEventListener('click', tempFunc);

        function tempFunc(){
          fieldObj.link = $('#field_link').val();
          console.log(fieldObj);
          hidePopup();
          document.getElementById('field_link_btn').removeEventListener('click', tempFunc);
        }
      });
      var controlls = document.createElement('div');
      controlls.appendChild(clip);
      controlls.appendChild(imageBtn);
      controlls.appendChild(linkBtn);
      controlls.className = 'controller';

      field.insertBefore(controlls, field.children[0]);
    }
}

function createButton(klass, text, func){
  var btn = document.createElement('button');
  btn.className = 'btn sharp btn-'+klass;
  btn.innerHTML = text;
  btn.addEventListener('click', func);
  return btn;
}

function addFieldBtn(index, container){
  var btn = document.createElement('button');
  btn.className = 'btn sharp btn-primary';
  btn.setAttribute('index', index);

  var i = document.createElement('i');
  i.className = 'fa fa-plus';

  btn.appendChild(i)
  btn.textContent = 'Nytt Fönster';
  btn.addEventListener('click', function(){
    addField({
      index: this.getAttribute('index'),
      style: 'position: relative; background-color: white;'
    });
  });

  container.appendChild(btn);
}

function Generate(layout, admin, client){
  var content = document.getElementById('content');
  if(client == undefined) addFieldBtn(1, content);
  GenerateLayout(layout, undefined, admin, client);
}

function GenerateLayout(layout, parent, admin, client){
  for (var i = 0; i < layout.length; i++) {
    addField({fieldObj: layout[i], index: i+1, parent: parent}, admin, client); //add true later for client
    if(layout[i].children && layout[i].children.length > 0){
      GenerateLayout(layout[i].children, layout[i], admin, client);
    }
  }
}

function Cut(field){
  var horizontalBtn = document.createElement('button'), vertivcalBtn = document.createElement('button'), closeBtn = document.createElement('button');
  var arrowL = document.createElement('i'), arrowH = document.createElement('i'), closeIcon = document.createElement('i');
  arrowL.className = 'fa fa-arrows-v';
  arrowH.className = 'fa fa-arrows-h';
  closeIcon.className = 'fa fa-times';

  horizontalBtn.appendChild(arrowH);
  vertivcalBtn.appendChild(arrowL);
  closeBtn.appendChild(closeIcon);

  horizontalBtn.addEventListener('click', function(event){
    //change to horizontal mode
    var s = this.parentNode.parentNode.getElementsByClassName('selected')[0].children[0];
    s.style.top = 0;
    s.style.width = '3px';
    s.style.height = '100%';

    s.parentNode.setAttribute('mode', 'horizontal');
  });
  vertivcalBtn.addEventListener('click', function(event){
    //vhange to vertical mode
      //change to horizontal mode
      var s = this.parentNode.parentNode.getElementsByClassName('selected')[0].children[0];
      s.style.left = 0;
      s.style.width = '100%';
      s.style.height = '3px';

      s.parentNode.setAttribute('mode', 'vertical');
  });
  closeBtn.addEventListener('click', function(event){
    removeCut(event);
  });

  var cutControlles = document.createElement('div');
  cutControlles.className = 'controller cut';
  cutControlles.appendChild(horizontalBtn);
  cutControlles.appendChild(vertivcalBtn);
  cutControlles.appendChild(closeBtn);

  var over = document.createElement('div');
  over.className = 'selected';

  var span = document.createElement('span');
  span.className = 'split';
  over.appendChild(span);
  over.setAttribute('mode', 'horizontal');

  over.addEventListener('mousemove', function(event){
    if(event.target.className == 'selected') {
      // if(this.) //check if horizontal mode or vertical..
      if(this.getAttribute('mode') == 'horizontal'){
        var set = event.offsetX+'px';
        if(event.shiftKey && Math.abs(event.offsetX - event.target.clientWidth/2) <  event.target.clientWidth/6) set = event.target.clientWidth/2+'px';
        this.children[0].style.left = set;
      }
      else {
        var set = event.offsetY+'px';
        if(event.shiftKey && Math.abs(event.offsetY - event.target.clientHeight/2) < event.target.clientHeight/6) set = event.target.clientHeight/2+'px';
        this.children[0].style.top = set;
      }
    }
  });
  over.addEventListener('contextmenu', function(e){
    removeCut(e);
  });
  over.addEventListener('click', function(e){
      var value = {
        left: Number(this.children[0].style.left.replace('px', '')),
        top: Number(this.children[0].style.top.replace('px', ''))
      }, procentVals = {
        x: Math.round((value.left/this.clientWidth)*100),
        y: Math.round((value.top/this.clientHeight)*100)
      }, aVals = {
        x: (procentVals.x==0?100:procentVals.x)+'%;',
        y: (procentVals.y==0?100:procentVals.y)+'%;'
      }, bVals = {
        x: (procentVals.x==0?100:(100-procentVals.x))+'%;',
        y: (procentVals.y==0?100:(100-procentVals.y))+'%;'
      }

      var wh_vals = {
        a: 'width: '+aVals.x+' height: '+aVals.y + 'left: 0; top: 0',
        b: 'width: '+bVals.x+' height: '+bVals.y+' left:'+procentVals.x+'%; top: '+procentVals.y+'%;',
      }

      addField({
        parent: field,
        id: 'a',
        style: 'position: absolute; '+wh_vals.a
      });
      addField({
        parent: field,
        id: 'b',
        style: 'position: absolute; '+wh_vals.b
      });
      removeCut(e);
  });
  function removeCut(e){
    e.preventDefault();
    cutControlles.parentNode.removeChild(cutControlles);
    over.parentNode.removeChild(over);
  }

  // console.log(field);
  var fieldElement = document.getElementById(field.id);
  fieldElement.appendChild(cutControlles);
  fieldElement.appendChild(over);
}

function Map(value, range_min, range_max, map_min, map_max){
  var d1 = range_max-range_min, d2 = map_max-map_min;
  var ratio = d2/(d1!=0?d1:1);

  return value * ratio + map_min;
}

function save(){
  layout.splice(0, 0, 'a');
  $.ajax({
    method: 'post',
    url: '/admin/layout/',
    data: {layout: layout},

    success: function(msg){
      showSuccess(msg);
    },
    error: function(err){
      showError(err.responseText);
    }
  })
}


// image handlement
function imageHandle(elm, fieldObj){
  showPopup(0);
  if(fieldObj.image != null && fieldObj.image != ''){
    console.log(fieldObj);
    $('#img_name').val(fieldObj.image.name);
    $('#file_link').val(fieldObj.image.link);
  }
  var a_container = elm.parentNode.parentNode.children[1];
  document.getElementById('imgupload').addEventListener('click', funct);

  function funct(e){
    hidePopup();
    var name = $('#img_name').val();
    var link = $('#file_link').val();
    if(link != '') {
      var Timg = new Image();
      Timg.onload = function(){
        var img = createElementMod('img', {
          style: 'position: absolute;',
          src: link,
          alt: name,
          className: 'admin'
        });
        a_container.appendChild(img);
        fieldObj.image = {
          delta: {x: 0, y: 0},
          x: 0,
          y: 0,
          bounds: 1,
          link: link,
          name: name,
          style: 'position: absolute;'
        };

        console.log(img);

        setTimeout(function(){
          movableElement(img, fieldObj.image);
        }, 1000);
      }
      Timg.onerror = function(){
        showError('Inte en giltig bild');
      }
      Timg.src = link;
    }
    else {
      uploadFiles($('#file').get(0).files, function(err, links){
      $('#content').show();
      $('.info').hide();
      if(err) showError('Kan ej ladda upp bild, kontakta Henry Pap');
      else if(links.length > 0){
        var style = 'position: absolute;';

        var img = createElementMod('img', {
          style: style,
          src: links[0],
          alt: name,
          className: 'admin'
        });
        a_container.appendChild(img);
        fieldObj.image = {
          delta: {x: 0, y: 0},
    			x: 0,
    			y: 0,
    			bounds: 1,
          link: links[0],
          name: name,
          style: style
        };

        setTimeout(function(){
          movableElement(img, fieldObj.image);
        }, 1000);
      }
    });
    }
    document.getElementById('imgupload').removeEventListener('click', funct);
  }
}

function createElementMod(elmname, propeties){
  var elm = document.createElement(elmname);
  for (var key in propeties) {
    if (propeties.hasOwnProperty(key)) {
   	  if(key == 'options'){
   	  	for(var i = 0; i <  propeties[key].length; i++){
   	  		var option = propeties[key][i];
   	  		var optElm = document.createElement('option');
   	  		optElm.innerHTML = option.text;
   	  		optElm.value = option.value;
   	  		if(option.selected) optElm.setAttribute('selected', 'selected');
   	  		elm.appendChild(optElm);
   	  	}
   	  }
      else if(key == 'click' || key == 'change'){
        elm.addEventListener(key, propeties[key]);
      }
      else if(key != 'style' || key != 'innerHTML'){
        elm[key] = propeties[key];
      }
      else elm.setAttribute(key, propeties[key]);
    }
  }
  return elm;
}

window.onresize = function(event) {
    var movs = document.getElementsByClassName('movable');
    for(var i = 0; i < movs.length; i++){
    	movs[i].setPos();
    }
};

function movableElement(elm, data, client){
	elm.className += 'movable';
	elm.bounds = Number(data.bounds);
	elm.baseWidth = elm.clientWidth;
	elm.baseHeight = elm.clientHeight;
	elm.position = {x: Number(data.x), y: Number(data.y)};
	elm.savePos = {x: Number(data.x), y: Number(data.y)};
	elm.delta = {x: Number(data.delta.y), y: Number(data.delta.y)};
	elm.clickIndex = 'first';

	elm.style.width = (elm.baseWidth*elm.bounds)+'px';

	//calculates the center position
	elm.calcDistanc = function(){
		var imageCenter = {
			x: this.savePos.x + this.bounds*this.baseWidth/2,
			y: this.savePos.y + this.bounds*this.baseHeight/2
		}
		var screenCenter = {
			x: this.parentNode.clientWidth/2,
			y: this.parentNode.clientHeight/2
		}

		this.delta.x = screenCenter.x - imageCenter.x;
		this.delta.y = screenCenter.y - imageCenter.y;

	    this.setPos();}

	//sets the position to elm (css)
	elm.setPos = function(){
		var x = this.parentNode.clientWidth/2 - this.delta.x - this.baseWidth*this.bounds/2,
			y = this.parentNode.clientHeight/2 - this.delta.y - this.baseHeight*this.bounds/2;

		this.style.left = x+'px';
		this.style.top = y+'px';}

	//adding controlls
	elm.elmHandle = function(data){
		if(this.clickIndex == 'first'){
			this.save = {x: this.position.x, y: this.position.y};
			var elm = this;
			this.clickIndex = 'second';
			var optList = [
		    	{text: '100%', value: 1},
		    	{text: '75%', value: 0.75},
		    	{text: '50%', value: 0.5},
		    	{text: '25%', value: 0.25}
		    ];

		    for(var i = 0; i < optList.length; i++){
		    	if(optList[i].value == data.bounds){
		    		optList[i].selected = 'selected';
		    		break;
		    	}

		    }

			var parent = this.parentNode,
				elmControl = createElementMod('div', {className: 'cut controller'}),
		    	confirmBtn = createElementMod('button', {innerHTML: '<i class="fa fa-check-circle-o fa-2x"></i>', style: 'color: blue;'}),
		    	cancleBtn = createElementMod('button', {innerHTML: '<i class="fa fa-ban fa-2x"></<i>', style: 'color: red;'}),
		    	sizeOptions = createElementMod('select', {
		    		options: optList
		    	});

		    sizeOptions.addEventListener('change', function(e){
		    	elm.bounds = this.value;
		    	var w = this.value*elm.baseWidth,
		    		h = this.value*elm.baseHeight;

		    	elm.style.width = w+'px';
		    	elm.delta.x = 0;
		    	elm.delta.y = 0;
		    	elm.position.x = elm.parentNode.clientWidth/2-w/2;
		    	elm.position.y = elm.parentNode.clientHeight/2-h/2;
		    	elm.setPos();
		    });


		    // parent.style.overflow = 'initial';
		    // elm.style.opacity = .6;
		    confirmBtn.addEventListener('click', confirm);
		    cancleBtn.addEventListener('click', cancle);

		    function confirm(e){
		      finish();
		      data.bounds = elm.bounds;
		      data.x = elm.savePos.x = elm.position.x;
		      data.y = elm.savePos.y = elm.position.y;
		      elm.calcDistanc();
		      data.delta = elm.delta;

		      console.log(data);
		    }
		    function cancle(e){
		      finish();
		      elm.bounds = Number(data.bounds);
		      elm.style.width = (elm.bounds*elm.baseWidth)+'px';
			    elm.delta.x = data.delta.x = 0;
		      elm.delta.y = data.delta.y = 0;

          elm.setPos();


		      // data.x = elm.position.y = elm.save.x;
		      // data.y = elm.position.x = elm.save.y;


          elm.position.x = Number(data.x);
          elm.position.y = Number(data.y);

		      elm.calcDistanc();
		    }
		    function finish(){
          elm.removeDrag();
		    	elm.clickIndex = 'first';
		      	elm.style.cursor = 'pointer';
		      	parent.parentNode.removeChild(elmControl);
		    	// parent.style.overflow = 'hidden';
		    	// elm.style.opacity = 1;
		    }

		    elmControl.appendChild(confirmBtn);
		    elmControl.appendChild(cancleBtn);
		    elmControl.appendChild(sizeOptions);
		    this.parentNode.parentNode.appendChild(elmControl);
		}
	    this.dragElement();}

	//drag events
	elm.dragElement = function(){
		this.addEventListener('mousedown', downF);
		this.addEventListener('mouseup', upF);
		if(this.position == undefined) {
			this.position = {x: 0, y: 0};
			this.savePos = {x: 0, y: 0};
		}

		function downF(e){
			if(e.target == this){
				this.clickPosition = {x: e.clientX, y: e.clientY};
				this.savePos.x = this.position.x;
				this.savePos.y = this.position.y;
				this.addEventListener('mousemove', moveF);
		    	this.style.cursor = 'move';
			}
		}
		function upF(e){
			this.removeEventListener('mousemove', moveF);
			this.style.cursor = 'pointer';
		}

    this.removeDrag = function(){
      this.removeEventListener('mousedown', downF);
      this.removeEventListener('mouseup', upF);
    }

		function moveF(e){
			var pos = {x: e.clientX, y: e.clientY};
			var delta = {x: pos.x-this.clickPosition.x, y: pos.y-this.clickPosition.y};

			// var bound = elm.parentNode.getBoundingClientRect();
			// delta.x -= (bound.left + window.scrollX);
			// delta.y -= (bound.top + window.scrollY);

			this.position.x = delta.x+this.savePos.x;
			this.position.y = delta.y+this.savePos.y;

			this.style.left = this.position.x+'px';
			this.style.top = this.position.y+'px';
		}}


	elm.calcDistanc();
	if(client == undefined) {
    elm.addEventListener('click', function(e){
  		this.elmHandle(data);
  	});
  }
}
