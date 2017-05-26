var mysql = require('mysql'),
    dump = require('mysqldump'),
    jsonfs = require('jsonfile'),
		database = {host: 'localhost', password: '', user: 'root', database: ''};

var pool = null;

function updateDatabase(callback){ //updates the database info file
  jsonfs.readFile(__dirname + '/database.json', function(err, obj){
    if(!err) {
      database = obj;
      pool = mysql.createPool({
        connectionLimit : 10,
        host            : database.host,
        user            : database.user,
        password        : database.password,
        database        : database.database,
        multipleStatements : 1
      });
      if(typeof callback == 'function') callback(null);
    }
    else {
      console.log('Could not load database info');
      if(typeof callback == 'function') callback(err);
    }
  });
}
updateDatabase(); //updates the database object

function getConnection(multible){ // get the mysql connection
  if(multible) database.multipleStatements = 1;
	var connection = mysql.createConnection(database);

	connection.connect(function(err){
		if(err){
      console.log("error");
			connection.error = err;
		}
	});
	return connection;
}
function query(command, multible, callback) { //runs the query
	pool.query(command, callback);
}
function downloadDB(dest, callback){ //makes a dump of the database
	database.dest = dest;
	dump(database, function(error){
		// create data.sql file;
		if(typeof callback == 'function') downloadDB(error);
	});
}

function initialize(sqlpath, callback){
  updateDatabase(function(err){
    if(err) callback('could not read database info');
    else {
      var fs = require('fs');
      if(fs.existsSync(sqlpath)){
        var lines = fs.readFileSync(sqlpath, 'utf-8').split('\n');
        insert({
          lines: lines,
          index: 0,
          info: {
            total: 0,
            failed: 0
          },
          command: ''
        }, callback);
      }
      else callback(sqlpath+' does not exist');
    }
  });
}

function insert(data, callback){
  if(data.index == data.lines.length-1) callback(null, data.info);
  else {
    var line = data.lines[data.index];
    data.index++;
    if(line != '') data.command += line+'\n';

    if(line.endsWith(';')) {
      // query
      query(data.command, false, function(err){
        if(err) {
          // console.log(data.command+" - failed");
          data.info.failed++;
        }

        data.info.total++;
        data.command = ''; // reset
        insert(data, callback);
      });
    }
    else insert(data, callback);
  }
}



// export functions
exports.download = downloadDB;
exports.updateDatabase = updateDatabase; //updates the database info
exports.start = function(path, time) { //start interval timer to download database
	setInterval(function(){
		downloadDB(path);

	}, time || 43200000);
}
exports.initialize = function(path, callback){
  initialize(path, callback);
}
exports.query = function(command, callback) { //does a simple query
	query(command, false, callback);
}
exports.MultiQuery = function(command, callback) { //does a query with multible statements
	query(command, true, callback);
}
exports.setDatabase = function(destination, host, user, password, db, callback){
	var temp = {
		host: host,
		user: user,
		password: password,
		database: db
	};

	jsonfs.writeFile(__dirname+'/'+destination, temp, function(err){
		if(err && typeof callback == 'function') callback(err);
		else if(!err) {
			database = temp;
			if(typeof callback == 'function') callback(err);
		}
	})
}
