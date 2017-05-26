function print(rows, index, ul, depth, updatef, deletef){
  if(index < rows.length){
    var row = rows[index];
    if(row.name == 'root' && row.depth == 0 && index == 0) print(rows, 1, ul, 1, updatef, deletef);

    var li = $('<li>');
    li.attr('id', row.id);
    if(updatef) {
      var upt = $('<button>'), rmv = $('<button>'), a = $('<a>'), div = $('<div>');
      upt.click(updatef).addClass('btn btn-primary sharp').text('UPDATERA');
      rmv.click(deletef).addClass('btn btn-danger sharp').text('TA BORT');
      a.text(row.name).attr('contenteditable', true);

      div.append(rmv, a, upt);
      li.append(div);
    }
    else li.append($('<a>').text(row.name).attr('href', '/category/'+row.id));

    var delta = row.depth-depth;

    if(delta < 0){ //step back;
      print(rows, index, ul.parent().parent(), --depth, updatef, deletef);
    }
    else if(row.lft+1 < row.rgt) { //has childs
      ul.append(li);
      var nUl = $('<ul>');
      li.append(nUl);
      print(rows, index+1, nUl, ++depth, updatef, deletef);
    }
    else {
      ul.append(li);
      print(rows, index+1, ul, depth, updatef, deletef);
    }
  }
}


function Catslide(){
  $('#Catslide').slideToggle();
}
