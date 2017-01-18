var express = require('express');
var app = express();

app.get('/', function (req, res) {
  res.sendFile('index.html', { root: __dirname + "/src/main/resources/assets/dist/" } );
});

app.listen(4000, function () {
  console.log('Example app listening on port 4000!');
}); 