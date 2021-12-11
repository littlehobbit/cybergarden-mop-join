const express = require('express');
const router = express.Router();
const multer = require("multer");
const {db} = require('./../index');
const fs = require("fs");
const middlewares = require('./../Auth/functions').middlewares;

const storageConfig = multer.diskStorage({
    destination:process.env.SERVER_FOLDER+"/images/news",
    filename:(req, file, cb)=>{
        req.body = JSON.parse(JSON.stringify(req.body));
        var nameparts = file.originalname.split(".");
        var datatype = nameparts[nameparts.length - 1];
        cb(null, req.user.student + "." + datatype);
    },
})

const formLimits = {
    fileSize: 15728640
}

const fileFilter = (req, file, cb) => {
    if(file.mimetype === "image/png" || 
        file.mimetype === "image/jpg"|| 
        file.mimetype === "image/jpeg")
            cb(null, true);
};

router.get("/all", async (req, res) => {
    
    var news = await db.getAllNews();
    for (let i = 0; i < news.length; i++) {
        news[i].tags = await db.getNewsTags(news[i]["id"]);
        delete news[i]["picture"];
        delete news[i]["description"];
    }
    res.status(200).send(news);
})

router.use("/setImage", middlewares.authenticateUser);
router.use("/setImage", multer({fileFilter:fileFilter, storage:storageConfig, limits:formLimits}).single("image"));
router.post("/setImage", async (req, res) => {
    var id = req.body.id ? req.body.id : undefined;
    if(id === undefined) res.send(400, "News not found");
    req.news = await db.getNewsData(id);
    if(!req.news){
        fs.unlink(req.file.path, ()=>{});
        return res.status(400).send("News not found");
    }
    if(!req.file) return res.status(400).send("File not received");
    req.news.picture = req.file.path;
    try{
        await db.editStudentInfo(req.news);
        res.sendStatus(200);
    }
    catch(e){
        res.status(500).send(e.message);
    }
});

router.get("/getImage", async (req, res)=>{
    var id = req.body.id ? req.body.id :undefined;
    if(id === undefined) res.send(400, "News not found");
    var image = await db.getNewsData(id);
    if (fs.existsSync(image["picture"])) {
        res.sendFile(image["picture"]);    
    }
    else{
        res.status(400).send("File doesn't exist");
    }
})

router.use("/read", middlewares.authenticateUser);
router.post("/read", async (req, res) =>{
    var newsID = req.body.news_id ? req.body.news_id :undefined;
    try{
        var news = await db.getNewsData(newsID);
        delete news["picture"]
        news.parapgraphs = await db.getParagraphs(newsID);
        res.status(200).send(news);
        db.readNews(newsID, req.user.student)
    }
    catch{
        res.sendStatus(400);
    }
})

module.exports = router;