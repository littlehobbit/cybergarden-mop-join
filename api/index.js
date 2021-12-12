const express = require('express');

const app = express();

//TODO: Add CORS

//TODO: Add https and certificates

app.use(express.urlencoded({ extended: true }));

app.use(express.json());
app.use((err, req, res, next)=>{
    console.error(err);
    if(!res.status) return res.send(500, "Something went wrong");
    else next();
})


const multerFix = (req,res,next)=>{
    req.body = JSON.parse(JSON.stringify(req.body));
    next();
}
app.use((req,res,next)=>{
    for (const key in req.query) {
        if(req.body[key]===undefined) req.body[key] = req.query[key];
    }
    next();
});



app.listen(3737);

require('dotenv').config()
var env = process.env;

if (env.DEBUG){
    app.use((req, res, next)=>{
        console.log({
            "url":req.originalUrl, 
            "method": req.method, 
            "headers":req.headers, 
            "body":req.body, 
            "params": req.params, 
            "query":req.query,
            "rawTrailers":req.rawTrailers
        });
        next();
    })
}

db = require("./DB/externalBase");

module.exports = {app, db, env, multerFix};