// global vairable
let clientLength = null; //init value


$(document).ready(function(){
  initList(0);

  $('.column').hide();
  $('.column.list').show();


  $('header > a.item').click(function(){
    $('.column').hide();
    $('.item.active').removeClass('active');

    $(this).addClass('active');
    $('.'+$(this).attr('show')).show();
  });

  $('input[type=file]').change(function(){
    if(this.files.length == 0) this.parentNode.children[0].children[1].innerHTML = 'Open Image';
    else {
      var name = this.files[0].name;
      if(name.length > 15){
        name = '..'+name.slice(name.length-13);
      }

      this.parentNode.children[0].children[1].innerHTML = name;
    }
  });
});


function removeBtn(){
  $('.ui.basic.modal.remove').modal('show');
  MSG('remove');
}

function MSG(classname, msg, type){
  $('.message').hide();

  if(typeof type == 'undefined') type = 'negative';
  else type = 'success'

  if(typeof msg != 'undefined' && msg != '') $('.'+classname+'.column div.ui.message.'+type).text(msg).show();
  else $('.'+classname+'.column div.ui.message.'+type).hide();
}
function getImage(image, callback){
  var reader = new FileReader();

  reader.onloadend = function(){
    if(reader.error) callback(reader.error);
    else callback(null, reader.result);
  }

  reader.readAsDataURL(image); // can be set to different data types
}

function initList(index){
  if(clientLength == null || index >= 0 && index < clientLength){
    // $.ajax({
    //   type: 'get',
    //   url: '', // comes later
    //   success: function(clientCount){
        var tfoot = $('tfoot > tr > th > div');
        var before = Number(tfoot.attr('index')) || 0;

        tfoot.attr('index', index);
        tfoot.empty();

        clientLength = 8; //clientCount

        var l = clientLength-index;
        if(before > index) {
          index--;
          if (index < 0) index = 0;
        }
        else if (l > 4) l = 4;
        else if(clientLength > 4) {
           index = clientLength-4;
        }

        var prev = $('<a>').addClass('icon item').append($('<i>').addClass('left chevron icon')).attr('index', index-1 >= 0 ? index-1 : 0),
            next = $('<a>').addClass('icon item').append($('<i>').addClass('right chevron icon')).attr('index', index+1 < clientLength ? index+1 : clientLength);

        tfoot.append(prev);
        for(i=0; i<l; i++){
          tfoot.append($('<a>').addClass('item').attr('index', index+i).text(index+i));
        }
        tfoot.append(next);

        $('div.menu > a.item').click(function(){
          initList(Number($(this).attr('index')));
        });
    //   },
    //   error: function(xhr){
    //     MSG('list', 'Could not initialize table');
    //     console.error(xhr);
    //   }
    // });
  }
  else MSG('list', 'Already reached max or min');
}


// The main 4 methods: CRUD [create, read, update, delete]
// CREATE function
function create(){
  var pn = $('#create_pn').val(),
  image = $('#create_photo')[0].files;
  if(pn == '') MSG('create', 'You must specify the social security number');
  else if (typeof image == 'undefined' || image.length == 0) MSG('create', 'You must include a picture');
  else {
    // get content from image, then send
    getImage(image[0], function(err, data){
      if(err) {
        console.error(err);
        MSG('create', 'There was an error reading your image');
      }
      else {
        $.ajax({
          type: 'post',
          url: '', // comes later
          data: {
            pn: pn,
            image: data
          },

          success: function(response){
            // do something
            MSG('create', 'Client was successfully created', 'success');
          },
          error: function(xhr){
            console.error(xhr);
            MSG('create', xhr.responseText);
          }
        });
      }
    });
  }
}
// READ function
function list(index){
  $.ajax({
    type: 'get',
    url: '', // comes later
    data: {
      index: index*8,
      limit: 8
    },

    success: function(clients){
      $('tbody').empty(); // drain the table

      // add the values
      for(var i = 0; i < clients.length; i++){
        var tr = $('<tr>');
        // for the sake of design
        if(i == 0) tr.append($('<th>').append($('<div>').addClass('ui ribbon label').text(clients[i].id)));
        else tr.append($('<th>').text(clients[i].id));

        tr.append($('<th>').text(clients[i].pn));
        tr.append($('<th>').text(clients[i].link));

        $('tbody').append(tr);
      }
    },
    error: function(xhr){
      MSG('list', xhr.responseText);
      console.error(xhr);
    }
  });
}
// UPDATE function
function update(){
  var id = $('#update_id').val(),
      pn = $('#update_pn').val(),
      image = $('#update_photo')[0].files;

  if(id == '') MSG('update', 'ID must have a value');
  else if(pn == '') MSG('update', 'You must specify the social security number');
  else if (typeof image == 'undefined' || image.length == 0) MSG('update', 'You must include a picture');
  else {
    // get content from image, then send
    getImage(image[0], function(err, data){
      if(err) {
        console.error(err);
        MSG('create', 'There was an error reading your image');
      }
      else {
        $.ajax({
          type: 'put',
          url: '', // comes later,
          data: {
            id: id,
            pn: pn,
            image: data
          },

          success: function(response){
            // do something
          },
          error: function(xhr){
            console.error(xhr);
            MSG('update', xhr.responseText);
          }
        });
      }
    });
  }
}
// DELETE function
function remove(){
  var id = $('#remove_id').val();
  if(id == '') MSG('remove', 'ID must have a value');
  else {
    $.ajax({
      type: 'delete',
      url: '', // update later
      data: {id: id},
      success: function(response){
        // do something
      },
      error: function(xhr){
        console.error(xhr);
        MSG('remove', xhr.responseText);
      }
    });
  }
}
