const {app} = require('./index');
const auth = require('./Auth/routes');
const student = require('./User/routes');

app.use("/auth", auth);
app.use("/user", student);