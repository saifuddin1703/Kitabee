const app = require('./app');
const http = require('http');
const db = require('./config/database');
const otpController = require('./controllers/otpController');
const { DATE, INT24 } = require('mysql/lib/protocol/constants/types');
const server = http.createServer(app);
const encryptor = require('./utils/encryptor')

server.listen(process.env.port,()=>{
   console.log("server is listening");
});


   // otpController.generateOTP().then(()=>{
//       console.log(process.env.encryptionKey)
//    // });
// var etext = encryptor.encrypt("hellow this is to be encrypted");
   
// console.log(etext.content);
// console.log(otpController.verifyOTP(2634))
// // String.
// INT24
// const current = new Date().getTime();
// console.log(current)
// setTimeout(() => {
//    const now = new Date().getTime();
//    console.log(now)
//    console.log(now-current);
// },5000);

