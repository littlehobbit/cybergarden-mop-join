const express = require('express');
const router = express.Router();
const multer = require("multer");
const {db, env} = require('./../index');
const fs =require("fs");
const middlewares = require('./../Auth/functions').middlewares;

const storageConfig = multer.diskStorage({
    destination:process.env.SERVER_FOLDER+"/images/students",
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


router.use("/user", middlewares.authenticateUser);
router.get("/user", async (req, res)=>{
    req.student = await db.getStudent(req.user.student);
    res.status(200).send(req.student);
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
    var image = await db.findStudentByID(id);
    if (fs.existsSync(image["image"])) {
        res.sendFile(image["image"]);    
    }
    else{
        res.status(400).send("File doesn't exist");
    }
})

module.exports = router;

