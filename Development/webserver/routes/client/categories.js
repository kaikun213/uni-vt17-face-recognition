(function(){
	"user strict";
    var express = require('express'),
        filter = require('../helpers/filter'),
        nestedsql = require('../helpers/NestedSQL');

    var routes = express.Router();
		routes.use('/', express.static('public'));
    routes.get('/', function(req, res){
      //get them all
      nestedsql.getSubDepth('nested_categories', 'name', 'root', function(err, rows){
        if(err) res.status(404).send(err);
        else res.status(200).send(rows);
      });
    }).get('/:id', function(req, res){
      if(filter.text(req.params.id)) {
        nestedsql.getSubDepth('nested_categories', 'id', req.params.id, function(err, rows){
          if(err) res.status(404).send(err);
          else {
						res.render('defualt', {
								headId: req.params.id,
								categories: rows,
								type: 'products',
								title: 'Norr',
								username: req.session.username
						});
					}
        });
      }
      else res.status(404).send('invalid id');
    }).get('/list/:id', function(req, res){
      if(filter.text(req.params.id)) {
        nestedsql.getSubDepth('nested_categories', 'id', req.params.id, function(err, rows){
          if(err) res.status(404).send(err);
          else if(rows.length == 0) res.status(404).send(req.params.id+' is no id of any category');
          else res.status(200).send(rows);
        });
      }
      else res.status(404).send('invalid id');
    }).get('/products/:id/:limit/:offset', function(req, res){
			if(filter.num(req.params.id) && filter.num(req.params.limit) && filter.num(req.params.offset)){
				nestedsql.getProduct('nested_categories', req.params.id, req.params.limit, req.params.offset,
					function(err, rows){
						if(err) res.status(404).send(err);
						else res.status(200).send(rows);
					});
			} else res.status(404).send('invalid id');
		});

    module.exports = routes;
}());
