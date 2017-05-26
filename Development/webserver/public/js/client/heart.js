$(document).ready(function(){
  if(error) {
    showError('Could not show Favorites');
    console.log(error);
  }
  else {
    setProducts(productList, function(){setHearts(heartList)}, 0, 15);
    $(window).scroll(function() {
       if($(window).scrollTop() + $(window).height() == $(document).height() - 80 && PRODUCTLOAD) {
           setProducts(productList, 16, 8);
       }
    });
  }
});
