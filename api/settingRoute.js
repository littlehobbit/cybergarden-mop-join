const {app} = require('./index');
const auth = require('./Auth/routes');
const student = require('./User/routes');
const events = require('./Events/routes');

app.use("/auth", auth);
app.use("/user", student);
app.use("/events", events);