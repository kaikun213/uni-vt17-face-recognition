(function(){
	"user strict";
    var express = require('express'),
        filter = require('../helpers/filter'),
        nested = require('../helpers/NestedSQL');

    var routes = express.Router();

    routes.use('/', express.static('public'));

    routes.post('/', function(req, res){
      check(req, res, function(){
        var id = req.body.id,
            name = req.body.name;
        if(filter.num(req.body.id) && filter.text(req.body.name)){
          nested.addTo('nested_categories', name, id, function(err, result){
            if(err) res.status(404).send(err);
            else res.status(200).send(result);
          });
        } else res.status(404).send('illegal id/name');
      });
    }).put('/', function(req, res){
      check(req, res, function(){
        var command = 'update nested_categories set name = "'+req.body.name+'" where id = ' + req.body.id;
        if(filter.num(req.body.id) && filter.text(req.body.name)){
          nested.query(command, function(err, result){
            if(err) res.status(404).send(err);
            else res.status(200).send(result);
          });
        } else res.status(404).send('illegal id/name');
      });
    }).delete('/leaf/:id', function(req, res){ //delete all products assigned to this category as well
      check(req, res, function(){
        var id = req.params.id;
        if(filter.num(id)){
          nested.deleteLeaf('nested_categories', id, function(err, result){
            if(err) res.status(404).send(err);
            else res.status(200).send(result);
          });
        } else res.status(404).send('illegal id');
      });
    }).delete('/parent/:id', function(req, res){ //delete all products assigned to this category as well
      check(req, res, function(){
        var id = req.params.id;
        if(filter.num(id)){
          nested.deleteParent('nested_categories', id, function(err, result){
            if(err) res.status(404).send(err);
            else res.status(200).send(result);
          });
        } else res.status(404).send('illegal id');
      });
    }).get('/', function(req, res) {
      if(req.session.username && req.session.username.admin == 1) {
        res.render('admin', {
            username: req.session.username,
            title: 'admin - '+req.session.username.name,
            content: {
                type: 'category'
            }
        });
      } else res.redirect('/');
    });


    function check(req, res, callback){
      if(req.session.username && req.session.username.admin == 1) callback();
      else res.status(403).send('Permision denied');
    }
    module.exports = routes;
}());
