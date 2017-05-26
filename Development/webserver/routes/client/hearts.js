(function(){
	"user strict";
    var express = require('express'),
        sql = require('../helpers/sql')
        filter = require('../helpers/filter');

    var routes = express.Router();
		routes.use('/', express.static('public'));

    routes.get('/', function(req, res){
      //get them all
      var list = req.session.heartList, command = '';
			if(list == undefined) {
				req.session.heartList = [];
				list = [];
			}
      command = 'select * from product where ';
      for(var i = 0; i < list.length; i++){
        if(i > 0) command += ' or ';
        command += 'id = ' + list[i];
      }

      sql.query(command, function(err, products){
				var pro = products || null;
        res.render('defualt', {
          type: 'heart',
          error: err,
          products: products,
					title: 'Favorites',
          heartList: req.session.heartList,
					username: req.session.username
        });
      });
    }).post('/:id', function(req, res){
      if(filter.num(req.params.id)){
        if(req.session.heartList == undefined) {
    			req.session.heartList = [];
    		}
    		var index = getIndex(req.session.heartList, req.params.id);
    		if(index == -1) {
    			req.session.heartList.push(req.params.id);
    			res.status(200).send('added');
    		}
    		else res.status(404).send('value already added');
      } else res.status(404).send('inalid id');
    }).delete('/:id', function(req, res){
        if(filter.num(req.params.id)) {
          if(req.session.heartList == undefined) res.status(404).send('no list detected');
          else {
    				var index = getIndex(req.session.heartList, req.params.id);
            if(index != -1) {
    					req.session.heartList.splice(index, 1);
              res.status(200).send('removed');
            }
    				else res.status(404).send('not found');
          }
        } else res.status(404).send('inalid id');
    }).get('/list', function(req, res){
      res.status(200).json(req.session.heartList);
    });


    function getIndex(arr, value){
    	for(var i = 0; i < arr.length; i++){
    		if(arr[i] == value) return i;
    	}

    	return -1;
    }

    module.exports = routes;
}());
