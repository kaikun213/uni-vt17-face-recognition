window.onload = function(){
  if(user){
    $('input[name=name]').val(user.name); // no need to set adress.. checks it later anyways..
  }
  loadBasket(true);
}


function place_order(){
  // check if user is logged in
  if(user) {
    //check address
    if(user.adress != ""){
      $.ajax({
        method: 'post',
        url: '/order',
        success: function(msg){
          showSuccess('Ordern är placerad, kolla din mail för kvitto');
          console.log('sucess');
          $('#basket_list').empty();
          $('#count').text('0st');
          $('#price').text('0kr');
          $('#varukorgCount').text('0');
        },
        error: function(xhr){
            showError(xhr.responseText);
            console.log(xhr);
        }
      });
    }
    else {
      showPopup(0);
    }
  }
  else {
    console.log('not even logged in.. pathetic');
    showError('Var snäll och logga in först');

    var e = $('header > section > div + div > p');
    e.trigger('mouseenter');
    e.trigger('click');
  }
}


function updateUser(){
  var name = $('input[name=name]').val(),
      addr = $('input[name=address]').val();

  $.ajax({
    type: 'post',
    url: '/user',
    data: {name: name, address: addr},
    success: function(msg){
      $.ajax({
        type: 'post',
        url: '/',
        success: function(msg){
          showSuccess('Order placerad');
        },
        error: function(xhr){
          console.log(xhr);
          showSuccess('kunde inte placera order, kontakta oss');
        }
      });
    },
    error: function(xhr){
      console.log(xhr);
      showError('Kunde inte updatera dina upgifter, försök igen senare eller kontakta oss!');
    }
  });
}
