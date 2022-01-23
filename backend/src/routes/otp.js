const express = require('express');
const res = require('express/lib/response');
const app = require('../app');
const router = express.Router();
const otpController = require('../controllers/otpController')
const encryptor = require('../utils/encryptor')
const db = require('../config/database')
const accountSid = process.env.TWILIO_ACCOUNT_SID;
const authToken = process.env.TWILIO_AUTH_TOKEN;
const jwt = require('jsonwebtoken')
const client = require('twilio')(accountSid, authToken);


router.post('/getOTP',async function(req,res){
    const phoneNumber = req.body.phoneNumber;

    try {
    const otp = await otpController.generateOTP();
    // console.log(otp)
    const verificationKeyObject = encryptor.encrypt(JSON.stringify({
        otp : otp,
        phoneNumber : phoneNumber
    }))
    
    // TODO send otp to phone number
    client.messages
  .create({
     body: `OTP for your kitabee account is ${otp}`,
     from: '+16072842031',
     to: `+91${phoneNumber}`
   })
  .then(message => console.log(message.sid));

    res.send({
        message : `Otp has been sended to ${phoneNumber}`,
        verificationKeyObject : verificationKeyObject
    })

    } catch (err) {
        console.log(err)
        res.status(501);
    }
})

router.get('/verifyOTP',async function(req,res){
    const {otp,verificationKeyObject} = req.body;

    try {
        var decryptedORP = JSON.parse(encryptor.decrypt(verificationKeyObject));
        console.log(otp)
        console.log(decryptedORP)
        if(decryptedORP.otp != otp){
            res.status(401).send("invalid otp")
        }
        await otpController.verifyOTP(otp);
        var selectQuery = `select _id from UserProfile where username = '${decryptedORP.phoneNumber}'`
        var result = await db.query(selectQuery)
        if(result.rowCount==0){
            var insertQuery = `insert into userprofile(username,phone_number) values('${decryptedORP.phoneNumber}',${decryptedORP.phoneNumber}) returning _id`
            var id = await (await db.query(insertQuery)).rows[0]._id
            let token = jwt.sign(id,process.env.secretKey);
            res.status(200).send(token)
        }else{
            var _id = result.rows[0]._id ;
            let token = jwt.sign(_id,process.env.secretKey);
            res.status(200).send(token);
        }
    } catch (err) {
        console.log(err)
        res.status(401).send(err);
    }
})
module.exports = router