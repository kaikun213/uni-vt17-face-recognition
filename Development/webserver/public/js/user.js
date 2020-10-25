// global vairalbles
var c, ctx, v, w, h, ratio, u, picTaken = false;

// set credentials
$.ajax({
  type: 'get',
  url: 'credentials',
  success: function(credentials){
    $.ajaxSetup({
      url: 'https://lnu-face.herokuapp.com/user',
      crossDomain: true,
      data: credentials
    });

    // ok to run
    RUN();
  },
  error: function(xhr){
    // not logged in
    window.location.href = '/login';
  }
});

// initial function
function RUN(){
  // do stuff when loaded
  window.onload = function(){
  ratio = 6/4;
  c = document.querySelector('canvas');
  v = document.querySelector('video');
  u = document.querySelector('div.upload');
  ctx = c.getContext('2d');

  v.addEventListener( "loadedmetadata", function (e) {

    ratio = this.videoWidth/this.videoHeight;
    if(ctx) {
      size();
      // camera(camError);
    }
  }, false );

  // if canvas not supported
  if(!ctx) camError();
  else {
    // size();
    camera(camError);
  }

  $('.circular.ui.icon.button').click(snap);
}
  window.onresize = size;
}

function camError(e){
  console.log(e);
  $('.nocam').show();
  c.style.display = v.style.display = 'none';
  $('.circular.ui.icon.button').hide();
  u.style['margin-top'] = '100px';

  showError('Your browser cannot access webcam', 'Please choose file instead.');
}
function size(){
  w = window.innerWidth*.8;
  if(w > 600) w = 600;
  if(w < 400) w = 400;
  h = w/ratio;

  c.width  = v.width  = w;
  c.height = v.height = h;
  c.style.left = v.style.left = (window.innerWidth-w)/2+'px';
  u.style.top = h+'px';
  drawSquare();
}

// draw the little square
function drawSquare(){
  ctx.clearRect(0, 0, w, h);

  var s = {
    x: (w-w*.4)/2,
    y: (h-w*.4)/2,
    w: w*.4,
    h: w*.4,
    r: 5
  }
  ctx.beginPath();
    ctx.moveTo(s.x+s.r, s.y);
    ctx.arcTo(s.x+s.w, s.y,     s.x+s.w, s.y+s.h, s.r);
    ctx.arcTo(s.x+s.w, s.y+s.h, s.x,     s.y+s.h, s.r);
    ctx.arcTo(s.x,     s.y+s.h, s.x,     s.y,     s.r);
    ctx.arcTo(s.x,     s.y,     s.x+s.w, s.y,     s.r);
    // ctx.arcTo(s.x,     s.y,     s.x+s.w, s.y,     s.r);
    ctx.lineWidth = s.r*1.2;
    ctx.strokeStyle = 'rgba(200,200,200,0.5)';
    ctx.stroke();
  ctx.closePath();
}

// setup the camera
function camera(errBack){
  // Get access to the camera!
  if(navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
      // Not adding `{ audio: true }` since we only want video now
      navigator.mediaDevices.getUserMedia({ video: true }).then(function(stream) {
          v.src = window.URL.createObjectURL(stream);
          v.play();
      });
  }

  // Legacy code below: getUserMedia
  else if(navigator.getUserMedia) { // Standard
      navigator.getUserMedia({ video: true }, function(stream) {
          v.src = stream;
          v.play();
      }, errBack);
  } else if(navigator.webkitGetUserMedia) { // WebKit-prefixed
      navigator.webkitGetUserMedia({ video: true }, function(stream){
          v.src = window.webkitURL.createObjectURL(stream);
          v.play();
      }, errBack);
  } else if(navigator.mozGetUserMedia) { // Mozilla-prefixed
      navigator.mozGetUserMedia({ video: true }, function(stream){
          v.src = window.URL.createObjectURL(stream);
          v.play();
      }, errBack);
  }
  else errBack();
}


// camera functions
function snap(){
  picTaken = true;
  $('.circular.ui.icon.button')
    .transition('horizontal flip', 200, function(){
      $(this).unbind('click').click(reTake)
        .children().eq(0).removeClass('big camera retro').addClass('reply');
    })
    .transition('horizontal flip', 200);

  v.style.display = 'none';
  ctx.clearRect(0, 0, w, h);
  ctx.beginPath();
    ctx.drawImage(v, 0, 0, w, h);
  ctx.closePath();
}
function reTake(){
  picTaken = false;
  ctx.clearRect(0, 0, w, h);
  drawSquare();
  $('.circular.ui.icon.button')
    .transition('horizontal flip', 200, function(){
      $(this).unbind('click').click(snap)
        .children().eq(0).removeClass('reply').addClass('big camera retro');
    })
    .transition('horizontal flip', 200);

  v.style.display = 'initial';
}


function showError(head, text){
  $('.ui.negative.message > div.header').text(head);
  $('.ui.negative.message > p').text(text);

  // now show this beautiful thing!!
  $('.ui.negative.message').show();
}
function upload(){
  var file = $('#photo')[0].files;
  if(file.length == 1) getimage(file[0],
        function(err, img){
          if(err) showError('Loading image error', 'An error occured while loading picture');
          else {
            sendData(img.substr(img.indexOf(",") + 1, img.length));
          }
      });
  else if (picTaken) {
    var img = c.toDataURL();
    sendData(img.substr(img.indexOf(",") + 1, img.length));
  }
  else {
    showError('Nothing to upload', 'You must select/take picture first.');
  }
}
function sendData(file){
  document.getElementById('upload').innerHTML = '<i class="spinner loading icon"></i>';
  $.ajax({
    type: 'post',
    data: {
      image: file
    },
    success: function(data, textstatus, xhr){
      if(xhr.status == 200) {
          $('.nocam').text('Social Security Number: ' + data).show();
          console.log(data);
      }
      else {
        console.log(data, textstatus, xhr.responseText);
        if(xhr.responseText.length > 100) showError('Error', 'check console for more info');
        else showError('Error', xhr.responseText);
      }
    },
    error: function(xhr){
      console.log(xhr);
      if(xhr.responseText.length > 100) showError('Error', 'check console for more info');
      else showError('Error', xhr.responseText);
    },
    complete: function(){
      // remove the loader
      document.getElementById('upload').innerHTML = 'Upload <i class="level up icon"></i>';
    }
  });
}
function getimage(image, callback){
  var reader = new FileReader();

  reader.onload = function(e){
    if(reader.error) callback(reader.error);
    else callback(null, reader.result);
  }

  reader.readAsDataURL(image); // can be set to different data types
}
