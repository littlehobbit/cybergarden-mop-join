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
                A.image,\
                A.first_name,\
                A.second_name,\
                A.middle_name,\
                A.birthdate,\
                A.id,\
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

module.exports.editEventData = async(event) => {
    var request_data = [event.title, event.description, event.start_date, event.end_date, event.place, event.image, event.id]
    var query = "Update Events SET\
	                title=?,\
                    description=?,\
                    start_date=?,\
                    end_date=?,\
                    place=?,\
                    image=?\
                where id=?"
    var result = await asyncQuery(query, request_data);    
}

module.exports.getAllEvents = async(userID) => {
    var request_data = [userID];
    var query = "Select B.id,\
                    if(exists(Select * from VisistedEvents where student_id=4 and event_id=B.id),1,0) as 'visited',\
                    B.title,\
                    B.description,\
                    B.start_date,\
                    B.end_date,\
                    B.place,\
                    B.image\
                From Users C right join PossibleParticipants A on C.role=role_id join Events B on A.event_id=B.id\
                Where C.id = ?"
    var result = await asyncQuery(query, request_data);
    return result;
}

module.exports.getEventData = async (eventID) =>{
    var request_data = [eventID];
    var query = "Select id,\
                        image\
                From Events\
                Where id = ?"
    var result = await asyncQuery(query, request_data);
    return result[0];
}

module.exports.getEventTags = async(eventID) => {
    var request_data = [eventID];
    var query = 'Select B.weight,\
                    C.name as "tag"\
                    From Events A right join EventInterests B on A.id=B.event_id\
                    right join Interests C on B.interest_id=C.id\
                    Where A.id=?'
    var result = await asyncQuery(query, request_data);
    return result;
}

module.exports.joinEvent = async (eventID, studentID) => {
    var request_data = [studentID, eventID];
    var query = 'call JoinEvent(?, ?)'
    var result = await asyncQuery(query, request_data);
}

module.exports.getAllNews = async() => {
    var query = 'Select id,\
                    title,\
                    date,\
                    picture,\
                    description\
                From News\
                Order By id desc;'
    var result = await asyncQuery(query, []);
    return result;
}

module.exports.getNewsTags = async(newsID) => {
    var request_data = [newsID];
    var query = 'Select A.weight,\
                C.name as "tag"\
                From Interests C right join NewsInterests A on C.id=A.interest_id left join News B on A.news_id = B.id\
                Where B.id = ?'
    var result = await asyncQuery(query, request_data);
    return result;
}

module.exports.getNewsData = async(newsID) => {
    var request_data = [newsID];
    var query = 'Select id,\
                    title,\
                    date,\
                    picture,\
                    description\
                From News\
                Where id=?'
    var result = await asyncQuery(query, request_data);
    return result[0];
}

module.exports.editNewsData = async(news) => {
    var request_data = [newsID];
    var query = 'Select id,\
                    title,\
                    date,\
                    picture,\
                    description\
                From News\
                Where id=?'
    var result = await asyncQuery(query, request_data);
    return result[0];
}

module.exports.readNews = async (newsID, studentID)=>{
    var request_data = [studentID, newsID];
    var query = 'call readNews(?, ?)'
    var result = await asyncQuery(query, request_data);
}

module.exports.getAllSpecifications = async() => {
    var query = "SELECT id,\
                    codificator,\
                    title,\
                    tag_grad,\
                    tag_type,\
                    description\
                FROM hackathon_garden_winter.Specialization;"
    var result = await asyncQuery(query,[]);
    return result;
}

module.exports.getSpecificationInterests = async(specificationID) => {
    var request_data = [specificationID];
    var query = "SELECT I.id,\
                    SI.weight,\
                    I.name\
                FROM SpecializationInterests SI left join Interests I on SI.id_interests=I.id\
                Where SI.id_specialization=?"
    var result = await asyncQuery(query, request_data);
    return result;
}

module.exports.getStudentInterests = async(studentID) => {
    var request_data = [studentID];
    var query = "SELECT I.id,\
                    UI.weight,\
                    I.name\
                FROM UserInterests UI left join Interests I on UI.interest_id=I.id\
                Where UI.student_id=?"
    var result = await asyncQuery(query, request_data);
    return result;
}

module.exports.getParagraphs = async (newsID)=>{
    var request_data = [newsID];
    var query = "Select number,\
                    title,\
                    content\
                    from Paragraphs\
                    where news_id=?"
    var result = await asyncQuery(query, request_data);
    return result;
}

module.exports.getAllQuestions = async() => {
    var query = "SELECT question,\
                        shortAnswer,\
                        fullAnswer\
                    FROM hackathon_garden_winter.QnA;"
    var result = await asyncQuery(query, []);
    return result;
}