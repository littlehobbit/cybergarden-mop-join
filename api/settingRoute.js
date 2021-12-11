const {app} = require('./index');
const auth = require('./Auth/routes');
const student = require('./User/routes');
const events = require('./Events/routes');
const news = require('./News/routes');
const specialization = require('./Specialization/routes');
const qna = require('./QnA/routes');

app.use("/auth", auth);
app.use("/user", student);
app.use("/events", events);
app.use("/news", news);
app.use("/specialization", specialization);
app.use("/qna", qna);