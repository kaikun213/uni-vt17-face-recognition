(function(){
	"user strict";
    var express = require('express');
    var jsonfile = require('jsonfile');
    var filter = require('../helpers/filter');
    var sql = require('../helpers/sql');

    var routes = express.Router();

    routes.use('/', express.static('public'));
		routes.use('/heart', require('./hearts'));
		routes.use('/order', require('./order'));
		routes.use('/category', require('./categories'));
		routes.use('/basket', require('./basket'));
		routes.use('/user', require('./user'));

    routes.get('/', function(req, res){
        jsonfile.readFile(__dirname + '/../content/layout.json', function(err, obj){ //readFileSync
            if(!err){
                res.render('defualt', {
                    layout: obj,
                    type: 'home',
                    title: 'Norr',
										username: req.session.username
                });
            }
            else {
                res.render('clean', {
                    type: 'error',
                    title: 'Error 500',
                    msg: 'Error loading layout'
                });
            }
        });
	}).post('/search', function(req, res){
		var value = req.body.value;
		if(filter.text(value)){
			var command = 'select * from product where name like "%'+value+'%" OR category = "' + value+'"';
			sql.query(command, function(err, rows){
				if(err) res.status(404).send(err);
				else res.status(200).json(rows);
			})
		} else res.status(404).send('Invalid charactures');
	}).get('*', function(req, res){
		res.render('clean', {
			type: 'bad',
			title: '404 - page not found'
		});
	});


    module.exports = routes;
}());
