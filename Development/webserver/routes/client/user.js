(function(){
	"user strict";
    var express = require('express'),
        sql = require('../helpers/sql'),
        filter = require('../helpers/filter');

    var routes = express.Router();
		routes.use('/', express.static('public'));

    routes.get('/', function(req, res){
      //give the list
      if(req.session.username){
        var s = req.session.username;
        res.render('defualt', {
          type: 'user',
          title: 'USER INFO',
          username: req.session.username
        });
      }
      else res.redirect('/');
    }).post('/', function(req, res){
      // update user
      if(req.session.username){
        var body = req.body, command = 'update user set adress=\''+body.address+'\', name=\''+body.name+'\' where id='+req.session.username.id;

        sql.query(command, function(err, result){
          if(err) res.status(500).end('database error');
          else {
            req.session.username.adress = body.address;
            req.session.username.name = body.name;

            res.status(200).send('Updated');
          }
        });
      }
      else res.status(403).send('Forbidden');
    });

    module.exports = routes;
}());
