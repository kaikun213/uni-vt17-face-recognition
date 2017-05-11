$(document).ready(function(){
  $('.column').hide();

  $('.column.list').show();


  $('.item').click(function(){
    $('.column').hide();
    $('.item.active').removeClass('active');

    $(this).addClass('active');
    $('.'+$(this).attr('show')).show();
  });
});

function removeBtn(){
  $('.ui.basic.modal.remove').modal('show');
  ErrorMSG('remove');
}

function ErrorMSG(classname, msg){
   if(typeof msg != 'undefined') $('.'+classname+'.column div.ui.error.message').text(msg).show();
   else $('.'+classname+'.column div.ui.error.message').hide();
}

function remove(){
  var id = $('#update_id').val();
  if(id == '') ErrorMSG('remove', 'ID must have a value');
  else {
    $.ajax({
      type: 'post',
      url: '',
      data: {id: id},
      success: function(data){

      },
      error: function(xhr){
        ErrorMSG('remove', xhr.responseText);
        console.log(xhr);
      }
    });
  }
}

function update(){

}

function list(){

}

function create(){

}
