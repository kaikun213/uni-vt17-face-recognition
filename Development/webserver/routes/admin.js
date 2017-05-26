(function(){
	"user strict";
    var express = require('express');
    var sql = require('./helpers/sql');
    var filter = require('./helpers/filter');
    var routes = express.Router();


    routes.use('/product', require('./admin/product'));
    routes.use('/users', require('./admin/users'));
    routes.use('/layout', require('./admin/layout'));
    routes.use('/orders', require('./admin/orders'));
    routes.use('/categories', require('./admin/newCategory'));

    routes.use('/', express.static('public'));

    routes.get('/', function(req, res){
        check(req, res, function(sess){
            res.render('admin', {
                username: sess.username,
                title: 'admin - '+sess.username.name
            });
        });
    }).get('/count', function(req, res) {
        var command = "SELECT COUNT(*) FROM product WHERE 1";
        sql.query(command, function(err, rows){
            if(err) res.status(500).send('database error');
            else res.status(200).send(rows);
        });
    });

    routes.post('/query', function(req, res) {
        var from = req.body.from,
            order = req.body.order,
            limit = req.body.limit,
            offset = req.body.offset;

        if(filter.text(from) && filter.text(from) && filter.num(limit) && filter.num(offset)) {
            var command = "SELECT * FROM " + from + " ORDER BY " + order + " LIMIT " + limit + " OFFSET " + offset;

            sql.query(command, function(err, rows) {
                if(err) res.status(500).send('database errors');
                else res.status(200).send(rows);
            });
        } else res.status(500).send('invalid charactures');
    });


    function check(req, res, callback) {
        var sess = req.session;
        if(sess.username && sess.username.admin == 1){
            callback(sess);
        }
        else res.redirect('/');
    }

    exports.check = function(req, res, callback){
        check(req, res, callback);
    }

    module.exports = routes;
}());
