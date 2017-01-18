var express = require('express');
var app = express();


// var server = express();
// server.use('/src', express.static(__dirname + '/src/main/resources/assets/dist/'));

// server.get('/*', function(req, res){
//   res.sendFile(__dirname + '/index.html');
// });

 
app.get('/', function (req, res) {
  res.sendFile('index.html', { root: __dirname + "/src/main/resources/assets/dist/" } );
});

app.listen(3000, function () {
  console.log('Example app listening on port 3000!' );
}); 