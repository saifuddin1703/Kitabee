const db = require("../config/database");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const userController = require("./userController");
const passport_google_strategy = require('passport-google-oidc');
const passport = require('passport');


passport.use(new passport_google_strategy({
    clientID: "110760655265-q99hed54g8b03rgf27rh4vsfp0ja1oih.apps.googleusercontent.com",
    clientSecret: "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",
    callbackURL: "http://localhost:3000/authentication/google"
},async (issuer,profile,cb)=>{

    var query = `select * from user where username = ${profile.email}`;
    try {
    const result = await db(query);
    if(result.length == 0){
        //no user

       const user_id = await userController.createUser({
            username : profile.email,
            password : ""
        });

        const token = jwt.sign(user_id,process.env.secretKey);
        const user = await userController.updateUser(user_id,"token",token);
        console.log(user)
        cb(null,user);
    }else{
        //user already in the db
        var user_id = result[0]._id;
        const token = jwt.sign(user_id,process.env.secretKey);
        try {
            const user = await userController.updateUser(user_id,"token",token);
            console.log(user)
            cb(null,user)
        } catch (err) {
            console.log(err)
            cb(err);
        }
    }    
    } catch (err) {
        console.log(err)
        cb(err);
    }
}));


    async function signup(username,password){
        try{
            if(!username || !password)  throw new Error("Invalid username or password");
            const encryptedPassword =await bcrypt.hash(password,10);
            
            // checking if user exists or not 
            let result = await userController.getUserByUsername(username);
            if(result.length == 0)
            {
                // user do not exists
            const user_id = await userController.createUser({
                username : username,
                password : encryptedPassword
            })
            console.log(user_id);
            const token = jwt.sign({userId:user_id},process.env.secretKey);
            let user = await userController.updateUser(user_id,"token", token);
            return user;
        }else{
            // user exists
            throw new Error(`username ${username} already exits`);
        }
        }catch(err){
            console.log(err)
            throw err;
        }
    }

    async function login(username , password){
        if(!username || !password)  throw new Error("Invalid username or password");
        try{
            let result =await userController.getUserByUsername(username);
            console.log(result.length == 0)
            if(result.length == 0){
                throw new Error("User not signedup")
            }else{
                let user = result[0];
               let isPasswordCorrect = bcrypt.compare(password,result[0].password);
               if(isPasswordCorrect){
                   // checking if the user already has the token or not
                   if(user.token != ""){
                       //user has the token 
                       try {
                           let decoded = jwt.verify(user.token,process.env.secretKey);
                       } catch (err) {
                           // token expires or not valid
                        let token = jwt.sign(result[0]._id,process.env.secretKey);
                        user = await userController.updateUser(result[0]._id,"token",token);
                       }
                   }else
                    {
                        // user do not has the token 
                        let token = jwt.sign(result[0]._id,process.env.secretKey);
                        user = await userController.updateUser(result[0]._id,"token",token);
                    }
                    return user;
               }else{
                   throw new Error("Invalid password");
               }
            }
        }catch(err){
            throw err;
        }
        
    }

    async function logout(token){
      try {
          let decoded = jwt.decode(token,process.env.secretKey);
          let user_id = decoded;
          let user =await userController.updateUser(user_id,"token","");
          return user;
      } catch (err) {
          throw err;
      }
    }

    async function modbileGoogleAuthentication(username){

        try {
            const isUserExists =await userController.isUserExists(username);
            if(isUserExists){
                const result = await userController.getUserByUsername(username);
                const user = result[0];
                const token = jwt.sign(user._id,process.env.secretKey);
                const user_id = await userController.updateUser(user._id,"token",token);
                return token;
            }
        } catch (err) {
            throw err;
        }

    }
     function authenticate(req,res,next){
         let token = req.headers.token;
        //  console.log(token);
         if(token){
             try {
                // var selectQuery = `select _id from UserProfile where token = '${token}'`
                // var result = await db.query(selectQuery)
                 const decoded = jwt.verify(token ,process.env.secretKey);
                 req.user = decoded;
                 console.log(decoded);

                 next();
             } catch (error) {
                res.status(401).send("Unauthorised access");   
             }
         }
         else{
             res.status(401).send("Unauthorised access");
         }
     }

exports.modbileGoogleAuthentication = modbileGoogleAuthentication
exports.signup = signup;
exports.login = login;
exports.logout = logout;
exports.authenticate = authenticate;
exports.passport = passport;
