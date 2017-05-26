(function(){
	"user strict";
    var express = require('express');
    var routes = express.Router();


	routes.use('/admin', require('./admin'));
	routes.use('/product', require('./client/product'));
	routes.use('/login', require('./client/login'));
	// routes.use('/category', require('./category'));


	routes.get('/logout', function(req, res){
		req.session.destroy(function(err){
			if(!err) res.redirect('/');
		});
	});
	routes.get('/sqlerror', function(req, res){
		res.send('we are having some problems with database right now');
	});


	routes.use('/', require('./client/home'));
	routes.get('*', function(req, res){
		res.render('clean', {
			type: 'bad',
			title: '404 - page not found'
		});
	});

    module.exports = routes;
}());
