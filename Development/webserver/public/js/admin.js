// global vairable
let clientLength = null; //init value

$.ajax({
  type: 'get',
  url: 'credentials',
  success: function(credentials){
    $.ajaxSetup({
      url: 'https://lnu-face.herokuapp.com/admin',
      crossDomain: true,
      data: credentials
    });

    RUN();
  },
  error: function(xhr){
    // not logged in
    window.location.href = '/login';
  }
});

var RUN = function(){
  $(document).ready(function(){
  $('.loaders').hide();
  initList(0);

  $('.column').hide();
  $('.column.list').show();


  $('header > a.item').click(function(){
    $('.column').hide();
    $('.item.active').removeClass('active');

    $(this).addClass('active');
    $('.'+$(this).attr('show')).show();
    $('.loaders').hide();
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
}

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

    var tfoot = $('tfoot > tr > th > div');
    var before = Number(tfoot.attr('index')) || 0;

    tfoot.attr('index', index);
    tfoot.empty();

    clientLength = 8; //clientCount
    var l = 4;

    var prev = $('<a>').addClass('icon item')
        .append($('<i>').addClass('left chevron icon'))
        .attr('index', index-1 >= 0 ? index-1 : 0),

        next = $('<a>').addClass('icon item')
        .append($('<i>').addClass('right chevron icon'))
        .attr('index', index+1 < clientLength ? index+1 : clientLength);

    tfoot.append(prev);
    for(i=0; i<l; i++){
      tfoot.append($('<a>').addClass('item').attr('index', index+i).text(index+i));
    }
    tfoot.append(next);

    $('div.menu > a.item').click(function(){
      initList(Number($(this).attr('index')));
    });
    list(index);
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
    $('.create.loaders').show();
    $('#crt_file_load').show();
    $('#crt_load').hide();
    getImage(image[0], function(err, data){
      if(err) {
        $('.create.loaders').hide();
        console.error(err);
        MSG('create', 'There was an error reading your image');
      }
      else {
        $('#crt_file_load').hide();
        $('#crt_load').show();

        $.ajax({
          type: 'post',
          data: {
            personalNumber: pn,
            file: data.substr(data.indexOf(",") + 1, data.length)
          },

          success: function(response){
            // do something
            $('.create.loaders').hide();
            console.log(response);
            MSG('create', 'Client was successfully created', 'success');
          },
          error: function(xhr){
            $('.create.loaders').hide();
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
  $('.list.loaders').show();
  $.ajax({
    type: 'get',
    data: {
      page: index,
      size: 8
    },

    success: function(clients){
      $('.list.loaders').hide();
      $('tbody').empty(); // drain the table
      console.log(clients);

      // add the values
      for(var i = 0; i < clients.content.length; i++){
        var tr = $('<tr>');
        // for the sake of design
        if(i == 0) tr.append($('<th>').append($('<div>').addClass('ui ribbon label').text(clients.content[i].id)));
        else tr.append($('<th>').text(clients.content[i].id));

        tr.append($('<th>').text(clients.content[i].personalNumber));
        tr.append($('<th>').text(clients.content[i].photoLink));

        $('tbody').append(tr);
      }
    },
    error: function(xhr){
      $('.list.loaders').hide();
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
    $('#upd_load').hide();
    $('#upd_file_load').show();
    $('.update.loaders').show();
    getImage(image[0], function(err, data){
      if(err) {
        $('.update.loaders').hide();
        console.error(err);
        MSG('update', 'There was an error reading your image');
      }
      else {
        $('#upd_file_load').hide();
        $('#upd_load').show();

        $.ajax({
          type: 'put',
          data: {
            id: id,
            personalNumber: pn,
            file: data.substr(data.indexOf(",") + 1, data.length)
          },

          success: function(response){
            // do something
            $('.update.loaders').hide();
            console.log(response);
            MSG('update', 'Client was successfully updated', 'success');
          },
          error: function(xhr){
            $('.update.loaders').hide();
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
    $('.del.loaders').show();
    $.ajax({
      type: 'delete',
      url: 'https://lnu-face.herokuapp.com/admin/'+id, // update later
      success: function(response){
        // do something
        $('.del.loaders').hide();
        console.log(response);
        MSG('remove', 'Client was successfully removed', 'success');
      },
      error: function(xhr){
        $('.del.loaders').hide();
        console.error(xhr);
        MSG('remove', xhr.responseText);
      }
    });
  }
}
