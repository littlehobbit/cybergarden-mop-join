const {db, env} = require('./../index');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');

const {mailer, messageDataTemplate, plainResetMessage, htmlResetMessage} = require("./mail.js");
const {userStructure} = require("./user.js");

let resetMails = {};

module.exports.routes = {}
module.exports.middlewares = {}
module.exports.utils = {}

function isEmailCorrect(email){
    var re = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i
    return re.test(email);
}

function isPhoneCorrect(phone){
    var re = /^([0|\+[0-9]{1,5})?([7-9][0-9]{9})$/
    return re.test(phone); 
}

function isUsernameCorrect(username){
    var re = /.+/
    return re.test(username);
}

function isPasswordCorrect(password){
    var re = /.+/
    return re.test(password);
}

async function isEmailUsed(email, id){
    var user = await db.findUserByEmail(email);
    if(!user) return false;
    return user.id !== id
}

async function isPhoneUsed(phone, id){
    var user = await db.findUserByPhone(phone);
    if(!user) return false;
    return user.id !== id
}

async function isUsernameUsed(username, id){
    var user = await db.findUserByUsername(username)
    if(!user) return false;
    return user.id !== id
}

module.exports.routes.register = async (req, res) =>{

    if(!req.body.email) return res.status(400).send("Email not received");
    if(!req.body.password) return res.status(400).send("Password not received");

    if(!isEmailCorrect(req.body.email, undefined)){
        return res.send(400, "Incorrect e-mail");
    }
    if(!isPasswordCorrect(req.body.password, undefined)){
        return res.send(400, "Incorrect password");
    }
    
    if(await isEmailUsed(req.body.email, undefined)){
        return res.send(400, "Account already exist");
    }
    
    var user = userStructure;

    user.email = req.body.email;
    var hashedpassword = bcrypt.hashSync(req.body.password, parseInt(env.HASH_ROUNDS));
    user.password = hashedpassword;
    await db.createUser(user);
    res.sendStatus(200);
}

function createJWTToken(object) {
    return jwt.sign(object, env.ACCESS_TOKEN_SECRET);
}

module.exports.routes.login = async (req, res) => {
    if(!req.body.identifier) return res.status(400).send("Identifier not received");
    if(!req.body.password) return res.status(400).send("Password not received");
    var user = await db.findUserByUsername(req.body.identifier);
    if(user === undefined || !bcrypt.compareSync(req.body.password, user.password)) user = await db.findUserByEmail(req.body.identifier);
    if(user === undefined || !bcrypt.compareSync(req.body.password, user.password)) user = await db.findUserByPhone(req.body.identifier);
    if(user === undefined || !bcrypt.compareSync(req.body.password, user.password)) return res.status(400).send("User not found");
    const token = createJWTToken({
            id: user.id,
            student:user.student
        })
    res.status(200).json({access_token: token, "token_type": "Bearer"});
    user.token = token;
    await db.editUserInfo(user, user.id);
}

module.exports.middlewares.authenticateUser = async function authenticateUser(req, res, next){
    const accessHeader = req.headers['authorization'];
    const accessToken = accessHeader && accessHeader.split(' ')[1];
    if(accessToken == null) return res.sendStatus(401);
    else{
        await jwt.verify(accessToken, process.env.ACCESS_TOKEN_SECRET, async (err, user)=>{
            if(err){
                return res.sendStatus(401);
            }
            else{
                req.body.token = accessToken;
                let usr = await db.findUserByID(user.id);
                if(usr.token !== accessToken) return res.sendStatus(401);
                else req.user = usr; 
                next();
            }
        });
    }
}

module.exports.routes.logout = async (req, res) => {
    req.user.token = "";
    await db.editUserInfo(req.user, req.user.id);
    res.sendStatus(200);
}

module.exports.middlewares.isAdmin = (req, res, next) => {
    if(req.user.isAdmin) next();
    else return res.sendStatus(403);
}

module.exports.middlewares.isAdminOrSelf = (req,res,next) => {
    if(req.body.id && (req.user.isAdmin || req.body.id === req.user.id) || !req.body.id) next();
    else return res.sendStatus(403);
}

module.exports.routes.editUser = async (req, res) => {
    var answer = {};
    var id = req.body.id ? req.body.id : req.user.id;
    if(req.body.username){
        if(isUsernameCorrect(req.body.username)){
            if(await isUsernameUsed(req.body.username, id)){
                answer.username = { "code": 400, "description": "Username already in use" }
            }
            else {
                answer.username = { "code": 200, "description":"OK" }
                //TODO: use separate function
                req.user.username = req.body.username;
            }
        }
        else answer.username = { "code": 400, "description": "Incorrect username" }
    }
    if(req.body.email) {
        if(isEmailCorrect(req.body.email)){
            if(await isEmailUsed(req.body.email, id)){
                answer.email = { "code": 400, "description": "E-mail already in use" }
            }
            else {
                answer.email = { "code": 200, "description":"OK" }
                //TODO: use separate function
                req.user.email = req.body.email;
            }
        }
        else answer.email = { "code": 400, "description": "Incorrect e-mail" }
    }
    if(req.body.password) {
        if(isPasswordCorrect(req.body.password)) {
            answer.password = { "code": 200, "description":"OK" }
            //TODO: use separate function
            req.user.password = bcrypt.hashSync(req.body.password, parseInt(env.HASH_ROUNDS));
        }
        else answer.password = { "code": 400, "description": "Incorrect password" }
    }
    if(req.body.phone) {
        if(isPhoneCorrect(req.body.phone)){
            if(await isPhoneUsed(req.body.phone, id)){
                answer.phone = { "code": 400, "description": "Phone already in use" }
            }
            else {
                answer.phone = { "code": 200, "description":"OK" }
                //TODO: use separate function
                req.user.phone = req.body.phone;
            }
        }
        else answer.phone = { "code": 400, "description": "Incorrect Phone" }
    }
    req.user.role = req.body.role ? req.body.role : req.user.role
    res.status(207).json(answer);
    await db.editUserInfo(req.user, id);
}

module.exports.routes.getUser = async (req, res) => {
    var id = req.body.id ? req.body.id : req.user.id;
    var user = await db.findUserByID(id);
    var answer = {
        "id": user.id,
        "username": user.username,
        "email":user.email, 
        "phone": user.phone,
        "role": user.isAdmin,
        "student_id":user.student
    }
    res.status(200).json(answer);
}

function generateCode(length) {
    var result           = '';
    var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;
    for ( var i = 0; i < length; i++ ) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
}

module.exports.routes.sendForgotMessage = async (req, res) => {
    req.user = await db.findUserByEmail(req.body.email);
    if(req.user === undefined){
        return res.status(400).send("Unknown user");
    }
    var code = generateCode(10);
    var mail = messageDataTemplate;
    mail.to = req.user.email;
    mail.subject = "Forgot password code";
    mail.text = plainResetMessage(code);
    mailer.sendMail(mail, (err)=>{
        if(err){
            console.log(err);
            res.sendStatus(503);
        }
        else{
            res.sendStatus(200)
        }
    })
    resetMails[req.body.email] = code;
}

module.exports.routes.unlockAccountForgot = async (req, res) => {
    if(resetMails[req.body.email] === undefined){
        return res.send(400, "Message not found");
    }
    if(resetMails[req.body.email] !== req.body.code){
        return res.send(400, "Wrong reset code");
    }
    var user = await db.findUserByEmail(req.body.email);
    if(user === undefined){
        return res.send(400, "User not found");
    }
    const token = createJWTToken({
        id: user.id,
        isAdmin: user.isAdmin
    })
    res.status(200).json({access_token: token, "token_type": "Bearer"});
    user.token = token;
    await db.editUserInfo(user, user.id);
}

module.exports.utils.isEmailCorrect = isEmailCorrect;
module.exports.utils.isPhoneCorrect = isPhoneCorrect;
module.exports.utils.isUsernameCorrect = isUsernameCorrect;
module.exports.utils.isEmailUsed = isEmailUsed;
module.exports.utils.isPhoneUsed = isPhoneUsed;
module.exports.utils.isPasswordCorrect = isPasswordCorrect;
module.exports.utils.createJWTToken = createJWTToken;
module.exports.utils.generateCode = generateCode;