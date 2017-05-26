"use strict";
  const
    exp = require('express'),
    app = exp(),
    path = require('path'),
    bodyParser = require('body-parser'),
  	session = require('express-session');

  // application settings
  app.set('port', process.env.PORT || 80);
  app.set('view engine', 'ejs');

  app.use(session({secret: 'face-bananas'}));
  app.use(exp.static(path.join(__dirname, 'semantic')));
  app.use(exp.static(path.join(__dirname, 'public')));
  app.use(bodyParser.json()); // for parsing application/json
  app.use(bodyParser.urlencoded({ extended: true })); // for parsing application/x-www-form-urlencoded


  // main path
  app.get('/', function(req, res){
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
  }).post('/credentials', function(req, res){
    req.session.credentials = req.body;
    res.sendStatus(200);
  }).get('/credentials', function(req, res){
    if(typeof req.session.credentials != 'undefined')
      res.status(200).json(req.session.credentials);
    else
      res.sendStatus(404);
  }).get('*', function(req, res){ // any other paths
    res.status(404).send('404 not found');
  });

  app.listen(app.get('port'), function(){
    console.log('webapp is running on port:' + app.get('port'));
  });
