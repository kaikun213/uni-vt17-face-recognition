(function(){
	"user strict";
    var express = require('express');
    var filter = require('../helpers/filter');
		var construct = require('./construct');
    var routes = express.Router();

    routes.use('/', express.static('public'));

    routes.get('/', function(req, res){
			if(construct.check(req)) {
				res.render('admin', {
						username: req.session.username,
						title: 'admin - '+req.session.username.name,
						content: {
								type: 'users'
						}
				});
			}
			else res.redirect('/');
    }).get('/count', function(req, res){
			var command = "SELECT COUNT(*) FROM user WHERE 1";
			construct.RUNSQL(command, function(err, rows){
					if(err) res.status(500).send('database error');
					else res.status(200).send(rows);
			});
		}).get('/:offset/:order', function(req, res){
			construct.GET(req, res, function(data) {
					var command = "select * from user order by " + data.order + " limit 20 offset " + data.offset;
					construct.RUN(filter.text(data.order) && filter.num(data.offset), command, res, 'get');
			});
		}).delete('/:id', function(req, res){
			if(construct.check(req)){
			construct.DELETE(req, res, function(data) {
					var command = "DELETE FROM `user` WHERE id = " + data.id;
					construct.RUN(filter.num(data.id), command, res, 'delete');
				});
			} else res.status(300).send('no permision');
		}).get('/:id', function(req, res){
			if(construct.check(req)){
				if(filter.num(req.params.id)){
					var command = 'select * from user where id = ' + req.params.id;
					construct.RUNSQL(command, function(err, row){
						if(err) res.status(404).send(err);
						else res.status(200).send(row);
					});
				} else res.status(404).send('invalid id');
			} else res.status(404).send('no permision');
		}).put('/:id', function(req, res){
			var id = req.params.id;
			construct.PUT(req, res, function(data){
				var command = 'UPDATE `user` SET `admin`='+data.admin+', `registerd`='+data.registerd+' WHERE id = ' + id;
				construct.RUN(filter.num(data.admin) && filter.num(data.registerd) && filter.num(id), command, res, 'put', true);
			});
		});

    module.exports = routes;
}());
