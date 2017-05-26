(function(){
	"user strict";
    var express = require('express'),
        sql = require('../helpers/sql'),
        filter = require('../helpers/filter');

    var routes = express.Router();
		routes.use('/', express.static('public'));

    routes.get('/', function(req, res){
      //give the list
      var basket = req.session.basket;
      if(basket == undefined){
        req.session.basket = [];
				basket = [];
      }

			res.status(200).json({products: basket});

    }).post('/', function(req, res){
      //push to list if not already added
      //else increase count
			var data = req.body;
      if(filter.text(data.image) && filter.num(data.id) && filter.num(data.size) && filter.num(data.color)){
        if(req.session.basket == undefined) {
    			req.session.basket = [];
    		}

        //add even if the same
				var index = getIndex(req.session.basket, {id: data.id});
				if(index != -1){
					var e = req.session.basket[index];
					if(e.stock > e.list.length){
						e.list.push({
							size: data.size,
							color: data.color
						});
						res.status(200).end('added');
					}
					else res.status(500).end('cant add more');
				}
				else {
					sql.query('select * from product where id = ' + data.id, function(err, result){
						if(err) res.status(500).end('cant set product info to basket');
						else {
							req.session.basket.push({
								price: result[0].price,
								stock: result[0].stock,
								image: data.image,
								name: result[0].name,
								info: result[0].info,
								id: data.id,
								list: [{
									size: data.size,
									color: data.color
								}]
							});
			    		res.status(200).end('added');
						}
					});
				}
      } else res.status(404).send('inalid id');
    }).delete('/', function(req, res){
      //delete from list if count is 1
      //else decrease the count
			var data = req.body;
      if(filter.num(data.id) && filter.num(data.size) && filter.num(data.color)){
        if(req.session.basket == undefined) res.status(404).send('no list detected');
        else {
          var index = getIndex(req.session.basket, {id: data.id});
          if(index != -1) {
						var b = req.session.basket[index];
						if(b.list.length > 1){
							var i = getIndex(b.list, {color: data.color, size: data.size});
							if(i != -1) {
								b.list.splice(i, 1);
								res.status(200).send('removed');
							}
							else res.status(404).send('no match');
						}
						else {
							req.session.basket.splice(index, 1);
							res.status(200).send('b removed');
						}
          }
          else res.status(404).send('not found');
        }
      } else res.status(404).send('inalid id');
    });

    function getIndex(list, args){
      for(var i = 0; i < list.length; i++) {
				var k = true;
				for(var m in args){
					if(list[i][m] != args[m]){
						k = false;
						break;
					}
				}

				if(k) return i;
      }
      return -1;
    }
    module.exports = routes;
}());
