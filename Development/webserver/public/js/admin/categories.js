var CATEGORIES = null;
$(document).ready(function(){
  getCategories();
});


function updateCategory(e){
  $.ajax({
    method: 'put',
    url: '/admin/categories',
    data: {
      name: $(this).parent().children('a').text(),
      id: $(this).parent().parent().attr('id')
    },
    success: function(){
      showSuccess('kategori updaterad');
      getCategories(true);
    },
    error: function(xhr){
      showSuccess('kategori kunde ej updateras');
      console.log(xhr);
    }
  });
}
function removeCategory(e){
  var mode = 'leaf';
  if($(this).parent().parent().children('ul').length > 0) mode = 'parent';
  $.ajax({
    method: 'delete',
    url: '/admin/categories/'+mode+'/'+$(this).parent().parent().attr('id'),
    success: function(){
      showSuccess('kategori borttagen');
      getCategories(true);
    },
    error: function(xhr){
      showSuccess('kategori kunde ej tas bort');
      console.log(xhr);
    }
  });
}

function addCategory(){
  var name = $('#name').val(), parent = $('#parent').val();
  // console.log('ok');

  if(name != ''){
    // console.log('bajs');
    $.ajax({
      method: 'POST',
      url: '/admin/categories',
      data: {
        name: name,
        id: parent
      },
      success: function(){
        showSuccess('kategori tillagd');
        getCategories();
      },
      error: function(err){
        console.log(err);
        showError('Kan inte lägga till kategori');
      }
    })
  } else showError('Du måste välja ett kategorinamn');
}

function getCategories(noprint){
  $.ajax({
    method: 'get',
    url: '/category',
    success: function(categories){
      var select = $('#parent');
      select.empty();

      select.append('<option value="1" selected="selected">INGEN</option>');
      for(var i = 1; i < categories.length; i++){
        $('#parent').append($('<option>').text(categories[i].name).val(categories[i].id));
      }

      if(noprint == undefined) {
        var list = $('#categoriesList');
        list.empty();
        print(categories, 1, list, 1, updateCategory, removeCategory);
      }
    },
    error: function(err){
      console.log(err);
      showError('Database error whie loading categories');
    }
  });
}
