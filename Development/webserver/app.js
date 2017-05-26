// MODULES
var express = require('express'),
	http = require('http'),
	path = require('path'),
	bodyParser = require('body-parser'),
	app = express(),
	session = require('express-session');

// CUSTOM MODULES
var routes = require('./routes/index');

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(session({secret: 'banan'}));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
app.use(express.static(__dirname + '/public'));
app.use('/', routes);

require('./routes/helpers/start')();

var server = http.createServer(app);
server.listen(process.env.PORT || 88, function(){
	console.log("Running server", server.address());
});
