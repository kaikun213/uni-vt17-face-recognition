var sql = require('./sql'), mail = require('./sendmail');

var shift = new Date();

shift.setHours(24);
shift.setMinutes(0);

var start = new Date();
var delta = shift.getTime()-start.getTime();

setTimeout(function(){
  checkOrders();
  setInterval(function(){
    checkOrders();
  }, 86400000); // 24 hours
}, delta);

function removeOrder(order){
  var redeployProducts = 'delete from orders where id = ' + order.id+';';
  var split = order.products.split(",");
  for(var i = 0; i < split.length; i++){
    var m = split[i].split(":");
    redeployProducts += "update product set stock=stock+"+m[1]+", inOrder=0 where id="+m[0]+";";
  }

  sql.MultiQuery(redeployProducts, function(err){
    if(err) console.log('failure could not reset the stock for products['+order.products+']');
    else {
      mail.send({
        to: order.usermail,
        cancle: order.id
      }, function(err){
        if(err) console.log('failed to notify '+order.username+' that his/her order['+order.id+'] was removed');
      });
    }
  });
}

function checkOrders(){
  var command = 'select * from orders where 1';
  var now = new Date();
  sql.query(command, function(err, orders){
    if(err) console.log('failure, could not update the orders!');
    else {
      for(var i = 0; i < orders.length; i++){
        if(orders[i].checked == 0){
          var d = new Date(orders[i].date);
          if(now.getTime()-d.getTime() >= 172800000){
            // remove
            removeOrder(orders[i]);
          }
        }
      }
    }
  });
}

module.exports = function(){

  setTimeout(function(){
    console.log('server started');
    sql.start('norr.sql');
    sql.initialize('norr.sql', function(err, info){
      if(err) console.log(err + ' [initialize database]');
      else console.log(info.failed+'/'+info.total+' failed [initialize database]');
    });
  }, 500);
}
