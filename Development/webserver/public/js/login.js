function login(){
  var usn = document.getElementById('company').value,
      psw = document.getElementById('password').value,
      url = 'Future-Face-Server-URL';

  var http = new XMLHttpRequest();
  http.open('POST', url, true);
  http.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
  http.onload = function(){
    if(http.readyState == 4 && http.status == 200) {
      if (http.responseText == '2') location.href = '/admin';
      else if (http.responseText == '1') location.href = '/user';
      else document.querySelectorAll('div.ui.error.message').innerHTML = 'Username or Password is incorrect';
    }
    else {
      document.querySelectorAll('div.ui.error.message').innerHTML = http.responseText;
    }
  }
  http.send(JSON.stringify({company:usn,password:psw}));
}
