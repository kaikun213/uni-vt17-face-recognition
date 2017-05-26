function basket(){
  var body = document.body,
      html = document.documentElement;

  var height = Math.max( body.scrollHeight, body.offsetHeight,
                         html.clientHeight, html.scrollHeight, html.offsetHeight );
  $('.basket-container').css('height', height+'px');
  $('.basket-container').show("slide", { direction: "right" }, 300);

  loadBasket();
}

function hideBasket(){
  $('.basket-container').hide("slide", { direction: "right" }, 300);
}

window.onresize = function(e){
  var body = document.body,
      html = document.documentElement;

  var height = Math.max( body.scrollHeight, body.offsetHeight,
                         html.clientHeight, html.scrollHeight, html.offsetHeight );
  $('.basket').css('height', height+'px');
}

function loadBasket(detail){ // show detail (info) or not
  $.ajax({
    method: 'get',
    url: '/basket',
    success: function(data){
      $('basket-container > div.basket > h3.error').text('');
      $('#basket_list').empty();

      $('#count').text(data.products.length+'st');

      var price = 0;

      for(var i = 0; i <  data.products.length; i++){
        price += addProductToBasket(data.products[i], detail); //, data.basket
      }
      $('#price').text(price+'kr');
    },
    error: function(xhr){
      console.log(xhr);
      $('basket-container > div.basket > h3.error').text('Kunde inte updatera varukorg listan');
    }
  });
}

function updateHeadPrice(price){

  var price_elm = $('#price');
  var temp = Number(price_elm.text().split('kr')[0])+Number(price);
  price_elm.text(temp+'kr');
}

function addProductToBasket(product, detail){
  var li = $('<li>');
  var count = product.list.length;
  // for(var i = 0; i < info.length; i++){
  //   if(info[i].id == product.id) {
  //     count = info[i].list.length;
  //     break;
  //   }
  // }
  var m = '';
  if(detail) {
    m = $('<p>').text(product.info);
  }
  //.html('<i class="fa fa-plus"></i>')
  //.html('<i class="fa fa-minus"></i>')
  var btn_plus = $('<button>').addClass('btn btn-default').text('add')
      .click(function(e){
        var input = $(this).parent().parent().children('input');
        var count = Number(input.val())+1;
        if(count > product.stock){
          showError('Det finns inte mer i lager');
        }
        else {
          var data = {
            id: product.id,
            price: product.price,
            name: product.name,
            image: product.image,
            stock: product.stock,
            info: product.info,
            color: 0,
            size: 0
          };

          addToBasket(data, function(err){
            if(!err) {
              input.val(count);
              var p = input.parent().parent().children().eq(3).children();
              p.eq(0).text(count+'st');
              p.eq(1).children().eq(0).text(count*product.price);

              updateHeadPrice(product.price);
            }
          });
        }
      });
  var btn_minus = $('<button>').addClass('btn btn-default').text('remove')
          .click(function(e){
            var input = $(this).parent().parent().children('input');
            removeFromBasket({
              id: product.id,
              color: 0,
              size: 0,
            }, function(err){
              if(!err) {
                var count = Number(input.val())-1;
                input.val(count);
                var p = input.parent().parent().children().eq(3).children();
                p.eq(0).text(count+'st');
                p.eq(1).children().eq(0).text(count*product.price);

                updateHeadPrice(-product.price);

                if(count == '0'){
                  input.parent().parent().remove();
                }
                else {
                  input.next().next().text(count > product.stock ? 'Det finns inte tillräkligt ('+(count-product.stock)+')' : '');
                }
              }
            });
          });


    li.append(
      $('<h3>').text(product.name),
      $('<a>').attr('href', '/product/'+product.id).addClass('btn btn-link').append('<i class="fa fa-location-arrow" aria-hidden="true"></i>'),
      $('<img>').attr('src', product.image),
      $('<p>').text('Antal').append($('<span>').text(count +'st'), $('<span>').text('Total').append($('<span>').text(count*product.price), 'kr')),
      $('<p>').text('Pris').append($('<span>').text(product.price+'kr/st')),
      $('<div>').addClass('input-group col-sm-5 col-md-4 col-xs-8').append(
        $('<span>').addClass('input-group-btn').append(btn_minus),
        $('<input>').addClass('form-control').attr('type', 'number').val(count).attr('readonly', 'true'),
        $('<span>').addClass('input-group-btn').append(btn_plus),
        $('<p>').addClass('error').text(count > product.stock ? 'Det finns inte tillräkligt ('+(count-product.stock)+')' : '')
      ),
      m,
      $('<br>'),
      $('<hr>')
      );
    $('#basket_list').append(li);
    return count * product.price;
}

function addToBasket(data, callback){
  doWork(data, 'post', callback);
}

function removeFromBasket(data, callback){
  doWork(data, 'delete', callback);
}

function doWork(data, type, callback){
  $.ajax({
    method: type,
    url: '/basket',
    data: data,
    error: function(xhr){
      console.log(xhr);
      if(callback) callback(xhr);
    },
    success: function(){
      if(callback) callback();
    }
  });
}
