(function(){
	"user strict";
    var express = require('express');
    var construct = require('./construct');
    var admin = require('../admin');
    var filter = require('../helpers/filter');
    var sql = require('../helpers/sql');
    var routes = express.Router();

    routes.use('/', express.static('public'));

    routes.get('/c', function(req, res){
			if(construct.check(req)){
        sql.query('SELECT * FROM category', function(err, rows){
            if(err) res.status(404).send('database error');
            else res.status(200).json(rows);
        });
			}else res.redirect('/');
    }).post('/', function(req, res) {
        //add category
			if(construct.check(req)){
        construct.POST(req, res, function(data) {
            var command = "INSERT INTO `category`(`name`) VALUES ('"+data.name+"')";
						if(data.parent) command = "INSERT INTO `category`(`name`, `parent`) VALUES ('"+data.name+"','"+data.parent+"')";
						if(data.superparent) command = "INSERT INTO `category`(`name`, `parent`, `superparent`) VALUES ('"+data.name+"','"+data.parent+"','"+data.superparent+"')";

            if(check(data)) {
							sql.query(command, function(err, rows){
								if(err) res.status(404).send('Database error');
								else {
									res.status(200).json(rows);}
							});
						} else {
							res.status(404).send('invalid charactures');
						}
        });
			}else res.status(300).send('no permision');

    }).put('/', function(req, res) {
        //edit category
			if(construct.check(req)){
        construct.PUT(req, res, function(data) {
					if(filter.text(data.name) && filter.num(data.id)){
						var command = "update category set superParent='"+data.name+"' where superParent='"+data.old+"'";
						sql.query(command, function(err, row){
							if(err) res.status(404).send('Database error');
							else {
								command = "update category set parent='"+data.name+"' where parent='"+data.old+"'";
								sql.query(command, function(err, row){
									if(err) res.status(404).send('Database error');
									else {
										command = "update category, product set category.name='"+data.name+"',product.category='"+data.name+"' where product.category='"+data.old+"' and category.id="+data.id;
										sql.query(command, function(err, rows){
											if(err) console.log(err); //res.status(503).send('Database error');
											else res.status(200).send('Successfully updated');
										});
									}
								});
							}
						});
					} else res.status(404).send('Invalid charactures');
        });
			}else res.status(300).send('no permision');

    }).delete('/force', function(req, res) {
        //remove category
			if(construct.check(req)){
        construct.DELETE(req, res, function(data) {
            var command = "DELETE category, product FROM category INNER JOIN product ON category.id = " + data.id + " and product.category = category.name";
            if(filter.num(data.id)){
							sql.query(command, function(err, rows){
								if(err){
									res.status(404).send('Database error');
								}
								else res.status(200).send('Category removed and all the products');
							});
						}
						else res.status(404).send('Invalid id');
        });
			} else res.status(300).send('no permision');

    }).delete('/', function(req, res) {
        //remove category
			if(construct.check(req)){
        construct.DELETE(req, res, function(data) {
					data = req.body;
            if(filter.num(data.id)){
	            var command = "DELETE FROM `category` WHERE id = " + data.id + " and not exists(select name from product where product.category = '"+data.name+"') and not exists(select name from category where parent = '"+data.name+"')";
							sql.query(command, function(err, rows){
								if(err){
									err.command = command;
									res.status(404).send(err);
								}
								else {
									if(rows.affectedRows > 0) res.status(200).send('Category ' + data.name + ' removed');
									else res.status(405).send('Can\'t remove beacuse products are still connected to this category');
								}
							});
						}else res.status(404).send('Invalid id');
        });
			}else res.status(300).send('no permision');

    }).get('/childs/:name', function(req, res){
			//get every child
			if(construct.check(req)){
				if(filter.text(req.params.name)){
					var command = 'select * from category where parent = "'+req.params.name+'" or superParent = "'+req.params.name+'"';
					sql.query(command, function(err, rows){
						if(err) res.status(404).send('Database error');
						else res.status(200).send(rows);
					});
				}
				else res.status(404).send('invalid name');
			}else res.status(300).send('no permision');

		}).get('/', function(req, res) {
        //get products
        admin.check(req, res, function(sess) {

            res.render('admin', {
                username: sess.username,
                title: 'admin - '+sess.username.name,
                content: {
                    type: 'category'
                }
            });
        });
    });

    function check(data) {
        return filter.text(data.name) && filter.text(data.parent) &&
                filter.text(data.superparent);
    }


    module.exports = routes;
}());
