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
  res.status(200).render('index', {
    type: 'login'
  });
}).get('*', function(req, res){ // any other paths
  res.status(404).send('404 not found');
});

server.listen(server.get('port'), function(){
  console.log('webserver is running on port:' + server.get('port'));
});
