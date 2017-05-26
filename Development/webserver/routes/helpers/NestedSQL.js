// http://mikehillyer.com/articles/managing-hierarchical-data-in-mysql/   /* TUT */
var sql = require('./sql');

// recive all in the list
function getAll(db, head){
  return "SELECT node.name, node.id"+
    " FROM "+db+" AS node, "+
    db+" AS parent"+
    " WHERE node.lft BETWEEN parent.lft AND parent.rgt"+
    " AND parent.name = '"+head+"'"+
    " ORDER BY node.lft;";
}
// recive all in the list with depth and without head assingment
function getDepth(db){
  return "SELECT node.name, node.id, node.lft, node.rgt, (COUNT(parent.name) - 1) AS depth"+
  " FROM "+db+" AS node, "+
  db+" AS parent"+
  " WHERE node.lft BETWEEN parent.lft AND parent.rgt"+
  " GROUP BY node.name"+
  " ORDER BY node.lft;";
}
// recive the leafs (those without children)
function getleaf(db){
  return "SELECT name, id"+
    " FROM "+db+
    " WHERE rgt = lft + 1;";
}
// add to parent that has child nodes
function addToWith(db, name, parent){
  return "LOCK TABLE "+db+" WRITE; "+
    "SELECT @myRight := rgt FROM "+db+
    " WHERE name = '"+parent+"'; "+
    "UPDATE "+db+" SET rgt = rgt + 2 WHERE rgt > @myRight; "+
    "UPDATE "+db+" SET lft = lft + 2 WHERE lft > @myRight; "+
    "INSERT INTO "+db+"(name, lft, rgt) VALUES('"+name+"', @myRight + 1, @myRight + 2); "+
    "UNLOCK TABLES;";
}
// add to parent that has not child nodes
function addTo(db, name, parent){
  return "LOCK TABLE "+db+" WRITE; "+
    "SELECT @myLeft := lft FROM "+db+
    " WHERE id = '"+parent+"'; "+
    "UPDATE "+db+" SET rgt = rgt + 2 WHERE rgt > @myLeft; "+
    "UPDATE "+db+" SET lft = lft + 2 WHERE lft > @myLeft; "+
    "INSERT INTO "+db+"(name, lft, rgt) VALUES('"+name+"', @myLeft + 1, @myLeft + 2); "+
    "UNLOCK TABLES;";
}
// add to db with no nodes
function add(db, name){
  return "INSERT INTO "+db+"(name, lft, rgt) VALUES('"+name+"', 1, 2)";
}

// recive depth from sub
function subDepth(db, type, value){
  return "SELECT node.id, node.name, node.lft, node.rgt, (COUNT(parent.id) - (sub_tree.depth + 1)) AS depth"+
      " FROM "+db+" AS node, "+
      db+" AS parent, "+
      db+" AS sub_parent, "+
      " ("+
      " SELECT node.id, (COUNT(parent.id) - 1) AS depth"+
      " FROM "+db+" AS node, "+
      db+" AS parent"+
      " WHERE node.lft BETWEEN parent.lft AND parent.rgt"+
      " AND node."+type+" = '"+value+"'"+
      " GROUP BY node.id"+
      " ORDER BY node.lft"+
      " )AS sub_tree"+
      " WHERE node.lft BETWEEN parent.lft AND parent.rgt"+
      " AND node.lft BETWEEN sub_parent.lft AND sub_parent.rgt"+
      " AND sub_parent.id = sub_tree.id"+
      " GROUP BY node.id"+
      " ORDER BY node.lft;";
}
function getSubDepthProduct(db, value){
  return "SELECT product.name AS productName product.id AS productID, node.id AS categoryId, node.name AS categoryName, (COUNT(parent.id) - (sub_tree.depth + 1)) AS depth "+
    "FROM "+
    "product, "+
    db+" AS node, "+
    db+" AS parent, "+
    db+" AS sub_parent, "+
    "( "+
    "SELECT node.id, (COUNT(parent.id) - 1) AS depth " +
    "FROM "+
    db+" AS node, "+
    db+" AS parent "+
    "WHERE node.lft BETWEEN parent.lft AND parent.rgt "+
    "AND node.id = '"+value+"'"+
    "GROUP BY node.id"+
    "ORDER BY node.lft "+
    ") AS sub_tree "+
    "WHERE node.lft BETWEEN parent.lft AND parent.rgt "+
    "AND node.lft BETWEEN sub_parent.lft AND sub_parent.rgt "+
    "AND sub_parent.id = sub_tree.id AND product.category = node.id "+
    "GROUP BY product.name "+
    "ORDER BY node.lft";
}
// recive sub with no sub-sub
function immediatesub(db, type, value){
  return "SELECT node.name, (COUNT(parent.name) - (sub_tree.depth + 1)) AS depth "+
    "FROM "+db+" AS node, "+
    db+" AS parent, "+
    db+" AS sub_parent, "+
    "( "+
    "SELECT node.name, (COUNT(parent.name) - 1) AS depth "+
    "FROM "+db+" AS node, "+
    db+" AS parent "+
    "WHERE node.lft BETWEEN parent.lft AND parent.rgt "+
    "AND node."+type+" = '"+value+"'"+
    " GROUP BY node.name "+
    "ORDER BY node.lft "+
    ")AS sub_tree "+
    "WHERE node.lft BETWEEN parent.lft AND parent.rgt "+
    "AND node.lft BETWEEN sub_parent.lft AND sub_parent.rgt "+
    "AND sub_parent.name = sub_tree.name "+
    "GROUP BY node.name "+
    "HAVING depth <= 1 "+
    "ORDER BY node.lft;";
}
// delete leaf (child with no childs)
function delLeaf(db, id){
  return "LOCK TABLE "+db+" WRITE; "+
    "SELECT @myLeft := lft, @myRight := rgt, @myWidth := rgt - lft + 1 "+
    "FROM "+db+
    " WHERE id = "+id+"; "+
    "DELETE FROM "+db+" WHERE lft BETWEEN @myLeft AND @myRight; "+
    "UPDATE "+db+" SET rgt = rgt - @myWidth WHERE rgt > @myRight; "+
    "UPDATE "+db+" SET lft = lft - @myWidth WHERE lft > @myRight; "+
    "UNLOCK TABLES; "+
    "DELETE FROM product where category = "+id+";";
}
// delete parent
function delParent(db, id){
  return "LOCK TABLE "+db+" WRITE; "+
    "SELECT @myLeft := lft, @myRight := rgt, @myWidth := rgt - lft + 1 "+
    "FROM "+db+
    " WHERE id = "+id+"; "+
    "DELETE FROM "+db+" WHERE lft = @myLeft; "+
    "UPDATE "+db+" SET rgt = rgt - 1, lft = lft - 1 WHERE lft BETWEEN @myLeft AND @myRight; "+
    "UPDATE "+db+" SET rgt = rgt - 2 WHERE rgt > @myRight; "+
    "UPDATE "+db+" SET lft = lft - 2 WHERE lft > @myRight; "+
    "UNLOCK TABLES; "+
    "DELETE FROM product where category = "+id+";";
}
// delete node and all children
function delSub(db, id){
  return "LOCK TABLE "+db+" WRITE; "+
    "SELECT @myLeft := lft, @myRight := rgt, @myWidth := rgt - lft + 1 "+
    "FROM "+db+
    " WHERE id = "+id+"; "+
    "DELETE FROM "+db+" WHERE lft BETWEEN @myLeft AND @myRight; "+
    "UPDATE "+db+" SET rgt = rgt - @myWidth WHERE rgt > @myRight; "+
    "UPDATE "+db+" SET lft = lft - @myWidth WHERE lft > @myRight; "+
    "UNLOCK TABLES;";
}
function getProduct(db, id, limit, offset){
  return ""+
    "SELECT product.* "+
    "FROM product, "+
    db+" AS node, "+
    db+" As parent, "+
    db+" AS sub_parent, "+
    "( "+
    "    SELECT node.* "+
    "    FROM  "+
         db+" AS node, "+
         db+" AS parent "+
    "    WHERE node.lft BETWEEN parent.lft AND parent.rgt "+
    "    AND node.id = "+id+
    "    GROUP BY node.id "+
    "    ORDER BY node.lft "+
    ") AS sub_tree "+

     "WHERE node.lft BETWEEN parent.lft AND parent.rgt "+
     "AND node.lft BETWEEN sub_parent.lft AND sub_parent.rgt "+
     "AND sub_parent.id = sub_tree.id AND product.category = node.id "+
     "GROUP BY product.id "+
     "ORDER BY node.lft "+
     "LIMIT " + limit+
     " OFFSET "+offset;
}


function doWork(command, callback){
  sql.MultiQuery(command, callback);
}
exports.query = function(command, callback){
  sql.query(command, callback);
}
exports.getProduct = function(db, id, limit, offset, callback){
  var command = getProduct(db, id, limit, offset);
  // console.log(command);
  doWork(command, callback);
}
exports.add = function(db, name, callback){
  doWork(add(db, name), callback);
}
exports.addTo = function(db, name, parent, callback){
  doWork(addTo(db, name, parent), callback);
}
exports.getAll = function(db, root, callback){
  doWork(getAll(db, root), callback);
}
exports.getDepth = function(db, callback){
  doWork(getDepth(db), callback);
}
exports.getLeafs = function(db, callback){
  doWork(getleaf(db), callback);
}
exports.getSubDepth = function(db, type, value, callback){
  doWork(subDepth(db, type, value), callback);
}
exports.getImmediateSub = function(db, type, value, callback){
  doWork(immediatesub(db, id), callback);
}
exports.getSubDepthProduct = function(db, id, callback){
  doWork(getSubDepthProduct(db, type, id), callback);
}
exports.deleteLeaf = function(db, id, callback){
  doWork(delLeaf(db, id), callback);
}
exports.deleteParent = function(db, id, callback){
  doWork(delParent(db, id), callback);
}
exports.deleteSubset = function(db, id, callback){
  doWork(delSub(db, id), callback);
}
