const express = require('express');
const router = express.Router();
const {db} = require('./../index');
const middlewares = require('./../Auth/functions').middlewares;

router.get("/all", async (req, res) => {    
    var QnA = await db.getAllQuestions();
    res.status(200).send(QnA);
})

module.exports = router;