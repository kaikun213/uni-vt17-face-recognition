"use strict";
const
  exp = require('express'),
  server = exp(),
  path = require('path');

// application settings
server.set('port', process.env.PORT || 80);
server.set('view engine', 'ejs');

server.use(exp.static(path.join(__dirname, 'semantic')));
server.use(exp.static(path.join(__dirname, 'public')));

// main path
server.get('/', function(req, res){
  /**
  * is this one nessecary doe?, maybe client can check
  * if they are logged in go directly to user or admin instead of
  * going to login page
  */
  // how to do it
  // res.status(200).render('index', {
  //   title: 'home'
  // });

  // but for now, the login page is the simple way
  res.redirect('/login');
}).get('/admin', function(req, res){
  res.status(200).render('index', {
    type: 'admin',
    title: 'Face - admin'
  });
}).get('/user', function(req, res){
  res.status(200).render('index', {
    type: 'user',
    title: 'Face - user'
  });
}).get('/login', function(req, res){
  res.status(200).render('index', {
    type: 'login',
    title: 'Face - login'
  });
}).get('*', function(req, res){ // any other paths
  res.status(404).send('404 not found');
});

server.listen(server.get('port'), function(){
  console.log('webserver is running on port:' + server.get('port'));
});
