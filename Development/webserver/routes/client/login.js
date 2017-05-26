(function(){
	"user strict";
    var express = require('express');
    var crypte = require('../helpers/crypte');
    var sql = require('../helpers/sql');
    var filter = require('../helpers/filter');
    var sendmail = require('../helpers/sendmail');

    var routes = express.Router();

    routes.post('/', function(req, res){
    	var sess = req.session;
		var mail = req.body.mail,
			password = req.body.pass;


		if(filter.mail(mail) && filter.text(password)){
			var command = "SELECT * FROM `user` WHERE `mail` = '"+mail+"'";
			sql.query(command, function(err, rows){
	            if(err) res.status(500).send('database error');
				else {
					if(rows[0] !== undefined) {
						if(crypte.isSame(rows[0].pass, password)){
							//allt e som de ska
							sess.username = rows[0];

							if(sess.admin) res.status(200).send({login: true, admin: true});
							else res.status(200).send({login: true, admin: false});
						}
						else res.status(500).send({error: 'Username or password do not match'});
					} else {
						res.status(500).send({error: 'Username or password do not match'}); }
	        	}
	        });
		} else res.status(500).send({error: 'invalid charactures'});
	});

	routes.post('/register', function(req, res){
		var sess = req.session;

		var mail = req.body.mail,
			pass = req.body.pass;
		if(filter.mail(mail)) {
			var password = crypte.enCrypte(pass);
			var command = "INSERT INTO `user`(`mail`, `pass`) VALUES ('"+mail+"', '"+password+"')";

			sql.query(command, function(err, result){
	      if(err) {
					if(err.code == 'ER_DUP_ENTRY') res.status(404).send('MAIL UPPTAGEN');
		      else res.status(500).send('Databse error');
	      }
				else {
					//send mail
					sendmail.send({
						to: mail,
						register: result.insertId //lite fancy stuff
					}, function(err){
						if(err) {
							deleteAccount(result.insertId);
							res.status(404).send('Kunde inte skicka mail');
						}
						else {
							deleteAccOnTime(result.insertId, mail);
							res.status(200).send('kolla mail för info om registrering');
						}
					});
	      }
	    });
		} else {
			res.status(500).send('not a valid mail')
		}
	});

	routes.get('/register/:id', function(req, res){
		var id = req.params.id;
		if(filter.num(id)) {
			// UPDATE `user` SET `registerd` = 1 (SELECT `mail` FROM `history`);
			var command = "UPDATE `user` SET `registerd`= 1 WHERE `id` = "+id;

			sql.query(command, function(err, result){
				if(err) {
					res.render('clean', {
              type: 'error',
              title: 'Error 800', //sql error
              msg: 'Problem upstod med databas testa igen senare!'
          });
				}
				else {
					sql.query('select mail from user where id = '+id, function(err, row){
						if(!err) 	sendmail.send({to: row[0].mail}, function(err, success){});
						res.redirect('/');
					});
				}
			});
		} else res.render('clean', {
                type: 'error',
                title: 'Error 605', //sql error
                msg: 'invalid parameter'
            });
	});
	routes.get('logout', function(req, res) {
		res.session.username = undefined;
		res.redirect('/');
	});

	function deleteAccount(id){
		var command = "DELETE FROM `user` WHERE `id` = '"+id+"'";

		sql.query(command, function(err, result){
			if(err) deleteAccOnTime(id);
		});
	}

	function deleteAccOnTime(id, mail){
		setTimeout(function() {
			var command = "DELETE FROM `user` WHERE `id` = '"+id+"' AND `registerd` = 0 AND `mail` = '"+mail+"'";

			sql.query(command, function(err, result){
				if(err) deleteAccOnTime(id);
			});
		}, 259200000);
	}

    module.exports = routes;
}());
