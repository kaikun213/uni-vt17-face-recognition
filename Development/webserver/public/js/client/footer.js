window.onload = function(){
  var footer = document.getElementsByTagName('footer')[0];
  setFooter(footer);

  //resize event
  window.onresize = function(){
    setFooter(footer);
  }
}
function setFooter(footer){
  var body = document.body,
    html = document.documentElement;
    // console.log(body.scrollHeight, body.offsetHeight,
                        //  html.clientHeight, html.scrollHeight, html.offsetHeight);
  footer.style.bottom = -footer.clientHeight+'px';
  // console.log(document.body.height, window.innerHeight, window.scrollY);
}
