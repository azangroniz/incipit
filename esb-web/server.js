var express = require('express');
var app = express();

app.use('/', express.static(__dirname + '/src/main/resources/assets')),
app.use(express.static(__dirname + '/'));

app.listen(3000, function () {
  console.log('Example app listening on port 3000!' );
}); 
   