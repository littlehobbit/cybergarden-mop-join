const express = require('express');
const router = express.Router();
const multer = require("multer");
const {db} = require('./../index');
const fs = require("fs");
const middlewares = require('./../Auth/functions').middlewares;

const storageConfig = multer.diskStorage({
    destination:process.env.SERVER_FOLDER+"/images/events",
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

router.use("/all", middlewares.authenticateUser);
router.get("/all", async (req, res) => {
    
    var events = await db.getAllEvents(req.user.id);
    for (let i = 0; i < events.length; i++) {
        events[i].tags = await db.getEventTags(events[i]["id"]);
        delete events[i]["image"];
    }
    res.status(200).send(events);
})

router.use("/setImage", middlewares.authenticateUser);
router.use("/setImage", multer({fileFilter:fileFilter, storage:storageConfig, limits:formLimits}).single("image"));
router.post("/setImage", async (req, res) => {
    var id = req.body.id ? req.body.id : req.user.student;
    if(id === undefined) res.send(400, "User not found");
    req.student = await db.findStudentByID(id);
    if(!req.student){
        fs.unlink(req.file.path, ()=>{});
        return res.status(400).send("User not found");
    }
    if(!req.file) return res.status(400).send("File not received");
    req.body.id = req.body.id ? req.body.id : req.student.id;
    req.student.image = req.file.path;
    try{
        await db.editStudentInfo(req.student);
        res.sendStatus(200);
    }
    catch(e){
        res.status(500).send(e.message);
    }
});

router.get("/getImage", async (req, res)=>{
    var id = req.body.id ? req.body.id :undefined;
    if(id === undefined) res.send(400, "User not found");
    var image = await db.getEventData(id);
    if (fs.existsSync(image["image"])) {
        res.sendFile(image["image"]);    
    }
    else{
        res.status(400).send("File doesn't exist");
    }
})

router.use("/join", middlewares.authenticateUser);
router.post("/join", async (req, res) =>{
    var eventID = req.body.event_id ? req.body.event_id :undefined;
    try{
        db.joinEvent(eventID, req.user.student)
        res.sendStatus(200);
    }
    catch{
        res.sendStatus(400);
    }
})

module.exports = router;