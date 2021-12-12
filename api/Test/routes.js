const express = require('express');
const router = express.Router();
const {db} = require('./../index');
const middlewares = require('./../Auth/functions').middlewares;

router.get("/all", async (req, res) => {    
    var questions = await db.getQuestions();
    for (let i = 0; i < questions.length; i++) {
        questions[i]["answers"] = await db.getQuestionAnswers(questions[i].id);
    }
    res.status(200).send(questions);
})

router.use("/answer", middlewares.authenticateUser);
router.post("/answer", async(req, res)=>{
    var questionID = req.body.question_id;
    var answer = req.body.answer;
    var studentID = req.user.student;
    await db.answerQuestion(studentID, questionID, answer);
    res.send(200);
})

module.exports = router;