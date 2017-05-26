(function(){
	"user strict";
    var express = require('express'),
				jsonfile = require('jsonfile');
    var routes = express.Router(), filename = __dirname + '/../content/layout.json';

    routes.use('/', express.static('public'));

    routes.get('/', function(req, res){
			check(req, res, function(){
				jsonfile.readFile(filename, function(err, obj){ //readFileSync
						res.render('admin', {
							username: req.session.username,
			        title: 'Layout edit',
			        content: {
			            type: 'layout',
									layout: obj
			        }
						});
				});
			});
    }).post('/', function(req, res){
			check(req, res, function(){
				var layout = req.body.layout;
				
				jsonfile.writeFile(filename, layout, function(err){
					if(err) res.status(404).send(err);
					else res.status(200).send('layout updated');
				});
			});
		});


		function check(req, res, callback) {
        var sess = req.session;
        if(sess.username && sess.username.admin == 1){
            callback(sess);
        }
        else res.redirect('/');
    }
    module.exports = routes;
}());
