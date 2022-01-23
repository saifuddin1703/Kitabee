const app = require("express");
const userController = require("../controllers/userController");
const router = app.Router();

router.get("/",async function(req,res,next){
    try {
        let user_id = req.user.userId;
        let users = await userController.getUserById(user_id);
        res.status(200).send(users);
    } catch (err) {
        res.status(501).send(err)
    }
})

router.get("/userById",async function(req,res,next){
    let userId = req.params.userId;
    try {
        let users = await userController.getUserById(userId);
        res.status(200).send(users);
    } catch (err) {
        res.status(501).send(err)
    }
})

router.get("/allUsers",async function(req,res,next){
    try {
        let users = await userController.getAllUsers();
        res.status(200).send(users);
    } catch (err) {
        res.status(501).send(err)
    }
});

router.put("/createUserProfile",async function(req,res,next){
    const user = req.body;
    const user_id = req.user.userId;
    try {
       const result = await userController.createUserProfile(user_id,user);
       res.status(200).send("profile creation successful");

    } catch (err) {
        res.status(501).send(err)
        
    }
})
router.put("/updateUser",async function(req,res,next){
    const field = req.query.field;
    const value = req.query.value;
    const number_value = parseInt(value);
    try {
        if(number_value == NaN){
            await userController.updateUser(req.user.userId,field,value);
         }else{
            await userController.updateUser(req.user.userId,field,number_value);
         }
         res.status(200).send("profile updated successfully");
    } catch (err) {
        res.status(501);
    }
    
})

module.exports = router

