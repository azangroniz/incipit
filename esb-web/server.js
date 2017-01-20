var express = require('express');
var app = express();

app.use( function(req, res, next) {
	res.header('Access-Control-Allow-Origin', '*');
	res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE,OPTIONS');
	res.header('Access-Control-Allow-Headers', 'X-Requested-With, X-HTTP-Method-Override, Content-Type, Accept');
	next();
});

app.use('/', express.static(__dirname + '/src/main/resources/assets')),
app.use(express.static(__dirname + '/'));

app.listen(3000, function () {
	console.log('Example app listening on port 3000!' );
});

