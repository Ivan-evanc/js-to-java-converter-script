var java = require('js-to-java');
const fs = require('fs');
var raw = require('./raw');


console.log(java.revert(raw));

fs.writeFile("test.java", java.revert(raw), function(err) {

    if(err) {
        return console.log(err);
    }

    console.log("The file was saved!");
}); 

