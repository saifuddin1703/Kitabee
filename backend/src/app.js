const express = require('express');

const dotenv = require("dotenv");
dotenv.config( {path: 'src/.env'} );
const { authenticate } = require('./controllers/authController');
const app = express();
const authenticationRoute = require("./routes/authentication");
const userRoute = require("./routes/user")
const uploadRoute = require("./routes/fileUpload")
const bookRoute = require("./routes/book");
const otpRoute = require('./routes/otp')
const bodyParser = require("body-parser");

app.use(bodyParser.json());
// authController
app.get("/redirect",(req,res) =>{
    res.redirect("/");
    // res.send('home route');
});
app.get("/",(req,res) =>{
    // res.redirect("/authentication/login");
    res.send('home route');
});
// require('./uploads/images')
// console.log(__dirname)

app.use('/otp',otpRoute)
app.use('/images', express.static(`${__dirname}/uploads/images`));
app.use("/authentication",authenticationRoute);
app.use("/books",bookRoute);
app.use(authenticate);
app.use("/uploadFile",uploadRoute);
app.use("/user",userRoute);


module.exports = app;