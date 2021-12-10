const mysql = require("mysql2");

function createConnection(){
    const connection = mysql.createConnection({
        host: process.env.DB_HOST,
        user: process.env.DB_USER,
        database: process.env.DB_DATABASE,
        password: process.env.DB_PASSWORD,
        port: process.env.DB_PORT
    })
    return connection;
}

function asyncQuery(query, params) {
    return new Promise((resolve, reject) =>{
        var con = createConnection();
        con.query(query, params, (err, result, fields) => {
            if (err){
                    con.rollback(()=>{
                        con.end();
                        reject(err);
                });
            }
            else{
                con.end();
                resolve(result, fields);
            }
        });
    });

}

module.exports.createUser = async (user) => {
    var request_data = [user.email, user.password];
    query = 'SELECT Register(?, ?);'
    response = await asyncQuery(query, request_data);
}

module.exports.findUserByID = async (id) => {
    var request_data = [id];
    var query = "SELECT * FROM Users WHERE id=?";
    var result = await asyncQuery(query, request_data);
    var user = result[0];
    if(user){
        user.role = user.role[0] === 1;
    }
    return user;
}

module.exports.findUserByUsername = async (username) => {
    var request_data = [username];
    var query = "SELECT * FROM Users WHERE username=?";
    var result = await asyncQuery(query, request_data);
    var user = result[0];
    if(user){
        user.role = user.role[0] === 1;
    }
    return user;
}

module.exports.findUserByEmail = async (email) => {
    var request_data = [email];
    var query = "SELECT * FROM Users WHERE email=?";
    var result = await asyncQuery(query, request_data);
    var user = result[0];
    if(user){
        user.role = user.role[0] === 1;
    }
    return user;
}

module.exports.findUserByPhone = async (phone) => {
    var request_data = [phone];
    var query = "SELECT * FROM Users WHERE phone=?";
    var result = await asyncQuery(query, request_data);
    var user = result[0];
    if(user){
        user.role = user.role[0] === 1;
    }
    return user;
}

//TODO: Create separate functions
module.exports.editUserInfo = async (newInfo, userID) => {
    var request_data = [newInfo.username, newInfo.email, newInfo.password, newInfo.phone, newInfo.role, newInfo.token, userID];
    var query = "UPDATE Users\
                SET username = ?,\
                    email = ?,\
                    password=?,\
                    phone=?,\
                    role=?,\
                    token=?\
                WHERE id=?"
    var result = await asyncQuery(query, request_data);
}

module.exports.deleteUser = async (userID) => {
    throw "Not implemented yet";
}