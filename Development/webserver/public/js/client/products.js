var PRODUCTOFFSET = 0, PRODUCTLOAD = false;

function init(){
  getProducts();
  $(window).scroll(function() {
     if($(window).scrollTop() + $(window).height() == $(document).height() - 80 && PRODUCTLOAD) {
         getProducts(8);
     }
  });
}

function getProducts(limit){
  var limit = limit || 16;
  PRODUCTLOAD = false;
  $.ajax({
    method: 'get',
    url: '/category/products/'+CATEGORYID+'/'+limit+'/'+PRODUCTOFFSET,
    success: function(products){
      setProducts(products, function(){
          setTimeout(function(){
          PRODUCTLOAD = true;
          PRODUCTOFFSET += limit;
          getHearts();
        }, 150);
      });
    },
    error: function(xhr){
      console.log(xhr);
    }
  })
}
function setProducts(products, callback, start, limit){
  var startIndex = start || 0;
  var limitLength = limit || products.length;
  // if($('.container').children().length > 0){ //added the <hr>
  //   var currentRow = $('.container').last();
  //   if(currentRow.children().length < 4){
  //     var l = 4-currentRow.children().length;
  //     for(var i = startIndex; i < l; i++){
  //       currentRow.append(addProduct(products[i]));
  //     }
  //     startIndex += l;
  //   }
  // }
  var row = $('.productsList');
  for(var i = startIndex; i < limitLength; i++){

    if(i < products.length) row.append(addProduct(products[i]));
  }

  callback();
}
function addProduct(product){
  //Add elemnt data
  var div = $('<div>');
  div.addClass('product');
  div.attr('productid', product.id).addClass('product col-md-4 col-lg-3 col-sm-6 col-xs-10 col-xs-offset-1 col-sm-offset-0');
  var a_container = $('<a>'), imgCon = $('<a>'), img = $('<img>'), name = $('<h3>'), a_heart = $('<a>');

  a_container.attr('href', '/product/'+product.id).addClass('img_container');


  img.attr('src', product.images.split(',')[0]);
  img.attr('alt', product.name+'#0');
  name.text(product.name+' ').attr('productid', product.id);
  a_heart.attr('href', 'javascript:void(0)');
  a_heart.click(function(){heartItem($(this))});
  a_heart.append('<i class="fa fa-heart-o fa-2x"></i>');

  name.append(a_heart);
  imgCon.append(img);
  a_container.append(imgCon, name);
  div.append(a_container);

  return div;
}
