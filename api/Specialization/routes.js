const express = require('express');
const router = express.Router();
const {db} = require('./../index');
const middlewares = require('./../Auth/functions').middlewares;

router.get("/all", async (req, res) => {    
    var Specializations = await db.getAllSpecifications();
    for (let i = 0; i < Specializations.length; i++) {
        Specializations[i].ege = await db.getSpecializationEge(Specializations[i]["id"]);
        delete Specializations["id"];
    }
    res.status(200).send(Specializations);
})

router.use("/recommendation", middlewares.authenticateUser);
router.get("/recommendation", async(req, res)=>{
    var Specialization = await db.getAllSpecifications();
    var UserInterests = await db.getStudentInterests(req.user.student);
    if(UserInterests.length == 0) return res.status(200).send([]);
    var userI = {};
    UserInterests.forEach(element=>{
        userI[element.id] = element.weight;
    })
    var max = 0;
    for (let i = 0; i < Specialization.length; i++) {
        var specInterests = await db.getSpecificationInterests(Specialization[i]["id"]);
        Specialization[i].weight = 0;
        Specialization[i].ege = await db.getSpecializationEge(Specialization[i]["id"]);
        for (let j = 0; j < specInterests.length; j++) {
            Specialization[i].weight += specInterests[j].weight * userI[specInterests[j].id];
        }
        if(Specialization[i].weight > max) {
            max = Specialization[i].weight;
        }
    }
    for (let i = 0; i < Specialization.length; i++) {
        if(Specialization[i].weight == 0) delete Specialization[i];
        else {
            Specialization[i].weight = Math.trunc(Specialization[i].weight * 100 / max);
        }
    }
    res.status(200).send(Specialization);
})

module.exports = router;