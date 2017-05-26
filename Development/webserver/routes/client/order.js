(function(){
	"user strict";
    var express = require('express'),
        sql = require('../helpers/sql'),
        filter = require('../helpers/filter'),
				mail = require('../helpers/sendmail');

    var routes = express.Router();
		routes.use('/', express.static('public'));

    routes.get('/', function(req, res){
      //give the list
      res.render('defualt', {
        type: 'order',
        title: (req.session.username ? req.session.username.name+'\'s ':'')+'varukorg',
				username: req.session.username
      })
    }).post('/', function(req, res){
      //place an order
			var products = '', price = 0, reducecommand = '', basket = req.session.basket;
			if(basket && basket.length > 0) {
			// store the id:length,
			// also reduce the stock in the different products
			for(var i = 0; i < basket.length; i++) { // get the total price, the id list
				if(i != 0) products += ",";
				products += basket[i].id+":"+basket[i].list.length;
				price += basket[i].price*basket[i].list.length;

				// as mentioned in admin/product.js, add field infield and set to 1
				reducecommand += "update product set stock=stock-"+basket[i].list.length+", inOrder=1 where id="+basket[i].id+";";
			}
			// add field to order: checked, initilized with 0
			var time = new Date().getTime();
			var command = 'insert into orders (products, price, userid, date, usermail) values ("'+products+'","'+price+'",'+req.session.username.id+','+time+', "'+req.session.username.mail+'")'

			sql.query(command, function(err, result){
				if(err) res.status(500).send('database error');
				else {
					req.session.basket = [];
					var id = result.insertId;
					// send email
					// add to users history

					reducecommand += 'update user set history=history+"'+id+'," where id='+req.session.username.id;
					sql.MultiQuery(reducecommand, function(err2, result2){
						if(err) res.status(500).send('could not add into history');
						else {
							mail.send({
								to: req.session.username.mail,
								order: {
									price: price,
									id: id,
									basket: basket
								}
							}, function(e, r){
								if(e) res.status(204).send('Could not send recipte-mail, heres your order id: '+id);
								else res.status(200).send('Order placed, and mail sent');
							});
						}
					});
				}
			});
		} else res.status(404).send('You must have some items in your basket');
    });

    module.exports = routes;
}());
