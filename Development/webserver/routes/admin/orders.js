(function(){
	"user strict";
    var express = require('express');
    var filter = require('../helpers/filter');
		var construct = require('./construct');
		var sql = require('../helpers/sql');
		var mail = require('../helpers/sendmail');
    var routes = express.Router();

    routes.use('/', express.static('public'));

    routes.get('/', function(req, res){
			if(construct.check(req)) {
				res.render('admin', {
						username: req.session.username,
						title: 'admin - '+req.session.username.name,
						content: {
								type: 'orders'
						}
				});
			} else res.redirect('/');
    }).get('/user/:id', function(req, res){
			if(construct.check(req)) {
				if(filter.num(req.params.id)){
					construct.RUNSQL('select orders.*, user.adress, user.name as username from orders, user where user.id = orders.userid && orders.userid = ' + req.params.id, function(err, rows){
						if(err) res.status(500).send('database error');
						else res.status(200).send(rows);
					});
				} else res.status(404).send('invalid id');
			} else res.redirect('/');
		}).get('/:offset/:order', function(req, res){
			construct.GET(req, res, function(data) {
					var command = "select orders.*, user.adress, user.name as username from orders, user where user.id = orders.userid order by orders." + data.order + " limit 20 offset " + data.offset;
					construct.RUN(filter.text(data.order) && filter.num(data.offset), command, res, 'get', true);
			});
		}).get('/:id', function(req, res){
			if(construct.check(req)) {
				if(filter.num(req.params.id)){
					construct.RUNSQL('select orders.*, user.adress, user.name as username from orders, user where user.id = orders.userid and orders.id = ' + req.params.id, function(err, row){
						if(err) res.status(500).send('database error');
						else res.status(200).send(row);
					});
				} else res.status(404).send('invalid id');
			} else res.redirect('/');
		}).put('/', function(req, res){
			if(construct.check(req)){
				construct.PUT(req, res, function(data) {
					var command = "update orders set checked="+data.checked+" WHERE id = " + data.id;
					construct.RUN(filter.num(data.id) && filter.num(data.checked), command, res, 'put');
				});
			} else res.status(300).send('no permision');
		}).delete('/', function(req, res){
			if(construct.check(req)) {
				var order = req.body;
				removeOrder(order, res);
			} else res.status(300).send('no permision');
		});

		function removeOrder(order, res){
		  var redeployProducts = 'delete from orders where id = ' + order.id+';';

				var split = order.products.split(",");
				for(var i = 0; i < split.length; i++){
					var m = split[i].split(":");
					redeployProducts += "update product set ";
					if(order.checked == 0) redeployProducts += "stock=stock+"+m[1]+",";  // do not restore if order.checked = 1, cuz then user has actually recived his/her products
					redeployProducts += "inOrder=0 where id="+m[0]+";";
				}

		  sql.MultiQuery(redeployProducts, function(err){
		    if(err) {
					console.log(err);
					res.status(503).send('failure could not reset the stock for products['+order.products+']');}
		    else {
		      mail.send({
		        to: order.usermail,
		        cancle: order.id
		      }, function(err){
		        if(err) res.status(503).send('failed to notify '+order.username+' that his/her order['+order.id+'] was removed');
						else res.status(200).send('Removed');
		      });
		    }
		  });
		}

    module.exports = routes;
}());
