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
    var request_data = [newInfo.username, newInfo.email, newInfo.password, newInfo.phone, newInfo.token, userID];
    var query = "UPDATE Users\
                SET username = ?,\
                    email = ?,\
                    password=?,\
                    phone=?,\
                    token=?\
                WHERE id=?"
    var result = await asyncQuery(query, request_data);
}

module.exports.deleteUser = async (userID) => {
    throw "Not implemented yet";
}

module.exports.getStudent = async (studentID) => {
    var request_data = [studentID]
    var query = "Select\
                A.first_name,\
                A.second_name,\
                A.middle_name,\
                A.birthdate,\
                B.id,\
                B.username,\
                B.email,\
                B.phone,\
                C.name\
                From Students A join Users B on A.id = B.student\
                join Roles C on B.role = C.id\
                where A.id=?";
    var result = await asyncQuery(query, request_data);
    var returns = result[0];
    returns["role"] = returns["name"]
    delete  returns["name"]
    return returns;
}

module.exports.findStudentByID = async (studentID) => {
    var request_data = [studentID]
    var query = "Select\
                *\
                From Students\
                where id=?";
    var result = await asyncQuery(query, request_data);
    var returns = result[0];
    return returns;
}

module.exports.editStudentInfo = async(student)=>{
    var request_data = [student.first_name, student.second_name, student.middle_name, student.birthdate, student.image, student.id]
    var query = "Update Students SET\
	                first_name=?,\
                    second_name=?,\
                    middle_name=?,\
                    birthdate=?,\
                    image=?\
                where id=?"
    var result = await asyncQuery(query, request_data);
}