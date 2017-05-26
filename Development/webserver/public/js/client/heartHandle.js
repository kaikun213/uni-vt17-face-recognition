
function heartItem(elm){
  var i = elm.children().eq(0);
  var state = 'post';
  if(i.hasClass('fa-heart')){
    state = 'delete';
    i.removeClass('fa-heart');
    i.addClass('fa-heart-o');
  } else {
    i.removeClass('fa-heart-o');
    i.addClass('fa-heart');
  }

  $.ajax({
    method: state,
    url: '/heart/'+elm.parent().attr('productid'),

    success: function(status){
      var heartItem = $('.badge1');
      var heartI = heartItem.children('i');
      if(state == 'post'){
        if(heartI.hasClass('fa-heart-o')){
          heartI.removeClass('fa-heart-o');
          heartI.addClass('fa-heart');
          heartItem.attr('href', '/heart');
        }
        heartItem.attr('data-badge', Number(heartItem.attr('data-badge'))+1);
      }
      else {
          var num =  Number(heartItem.attr('data-badge'))-1;
          heartItem.attr('data-badge', num);

          if(num < 1){
            if(heartI.hasClass('fa-heart')) {
              heartI.removeClass('fa-heart');
              heartI.addClass('fa-heart-o');
              heartItem.attr('href', 'javascript:void(0)');
            }
          }
      }
    }
  });
}
function getHearts(){
  $.ajax({
    method: 'get',
    url: '/heart/list',
    success: setHearts
  });
}
function setHearts(list){
  if(list){
    $('.badge1').attr('data-badge', list.length);
    var products = $('.product');
    for(var i = 0; i < products.length; i++){
      var n = products.eq(i).attr('productid');

      for(var j = 0; j < list.length; j++){
        if(list[j] == n){
          var select = products.eq(i).children().eq(0).children().eq(1).children().eq(0).children().eq(0);
          if(select.length == 0) select = products.eq(i).children().eq(0).children().eq(0);

          select.removeClass('fa-heart-o').addClass('fa-heart');
          break;
        }
      }
    }
  }
}
