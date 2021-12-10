const {app} = require('./index');
const auth = require('./Auth/routes');

app.use("/auth", auth);