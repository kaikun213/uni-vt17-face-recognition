var sql = require('../helpers/sql');
var admin = require('../admin');


function put(req, res, callback) {
    admin.check(req, res, function(sess) {
        var data = req.body.data || req.body;
        if(data != undefined) {
            // now call the run function to query
            callback(data);
        } else res.status(404).send('no content');
    });
}
function delet(req, res, callback) {
    admin.check(req, res, function(sess) {
        var id = req.body.id;
        if(id) {
            callback({id: id});

        } else res.status(204).send('no id');
    });
}
function get(req, res, callback) { // only assigned to product
    admin.check(req, res, function(sess) {
        var order = req.params.order,
            offset = req.params.offset;
        if(order != undefined && offset != undefined) {
            callback({offset: offset, order: order});

        } else {
            res.status(404).send('no data');
        }
    });
}
function post(req, res, callback) {
    admin.check(req, res, function(sess) {
        var data = req.body.data;
        if(data) {
            callback(data);
        } else res.status(404).send('no content');
    });
}

function run(ok, command, res, type, retErr){
    if(ok) {
        sql.query(command, function(err, rows) {
            if(err) {
                // console.log(err);
                if(retErr == undefined) res.status(404).send('database error');
                else
                  err.command = command;
                  res.status(404).send(err);
            }
            else {
                if(type == 'post') res.status(201).send('created');
                if(type == 'put') res.status(201).send('updated');
                if(type == 'delete') res.status(201).send('deleted');
                if(type == 'get') res.status(200).json(rows);
            }
        });
    } else res.status(404).send('invalid charactures');
}

exports.RUN = function(ok, command, res, type, retErr) {
    run(ok, command, res, type, retErr);
}
exports.RUNSQL = function(command, callback){
  sql.query(command, callback);
}
exports.GET = function(req, res, callback){
    get(req, res, callback);
}
exports.PUT = function(req, res, callback){
    put(req, res, callback);
}
exports.POST = function(req, res, callback){
    post(req, res, callback);
}
exports.DELETE = function(req, res, callback){
    delet(req, res, callback);
}
exports.check = function(req, callback){
  var sess = req.session;
  return sess.username && sess.username.admin == 1;
}
