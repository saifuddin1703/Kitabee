const app = require("express");
const session = require("express-session");
const authController = require("../controllers/authController");
const router = app.Router();
const {passport} = require('../controllers/authController')

router.use(session({secret:"haha",resave : false,saveUninitialized : false}));
router.use(passport.initialize());
router.use(passport.session())
passport.serializeUser(function(user, done) {
    done(null, user);
  });
  
  passport.deserializeUser(function(user, done) {
    done(null, user);
  });
router.post("/login",async function (req,res,next){

    const username = req.body.username;
    const password = req.body.password;
        try {
            let user = await authController.login(username,password);
            console.log(user)
            res.status(200);
            res.json(user)
        } catch (err) {
            console.log(err)
            res.status(401).send(err);
        }
})

router.post("/signup",async function(req,res,next){
    const username = req.body.username;
    const password = req.body.password;
    try {
        let user =await authController.signup(username,password);
        
        res.status(200).send(user);
    } catch(err) {
        res.status(501);
        next();
    }
})

router.delete("/logout",async function(req,res,next){
    let token = req.headers.token;
    console.log(token)
    try {
        authController.logout(token);
        res.status(200).send("user successfully logged out")
    } catch (error) {
        res.status(501);
        next(); 
    }
})


router.post("/mobileGoogleAuthentication",async function(req,res,next){
    const username = req.headers.username;

    try {
        const token =await authController.modbileGoogleAuthentication(username);
    res.status(200).send(token);
    } catch (err) {
        res.status(403).send(err);
    }
   
    
})
router.get("/google",
passport.authenticate('google',
{ failureRedirect : "/authentication/google",failureMessage : "Login failed",failureMessage: true }),
function(req,res){
    res.send(req.user.token);
});
module.exports = router;