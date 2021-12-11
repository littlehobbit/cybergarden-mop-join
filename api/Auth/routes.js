const express = require('express');
const authFunctions = require("./functions.js");

const router = express.Router();

router.post("/register", authFunctions.routes.register);

router.post("/login", authFunctions.routes.login);

router.use("/logout", authFunctions.middlewares.authenticateUser);
router.post("/logout", authFunctions.routes.logout);

router.use("/edit", authFunctions.middlewares.authenticateUser);
router.use("/edit", authFunctions.middlewares.isAdminOrSelf);
router.post("/edit", authFunctions.routes.editUser);

//TODO: delete user route
router.use("/delete", authFunctions.middlewares.authenticateUser);
router.post("/delete", (req, res)=>{
    res.sendStatus(501);
})

router.use("/get", authFunctions.middlewares.authenticateUser);
router.use("/get", authFunctions.middlewares.isAdminOrSelf);
router.post("/get", authFunctions.routes.getUser);

router.post("/forgotPassword", authFunctions.routes.sendForgotMessage);
router.post("/forgotPassword", authFunctions.routes.unlockAccountForgot);

module.exports = router;