const otp_generator = require('otp-generator');
const db = require('../config/database')
async function generateOTP(){
    const otp = otp_generator.generate(
        4,
        {
            digits : true,
            lowerCaseAlphabets : false,
            upperCaseAlphabets : false,
            specialChars : false
        }
    );
    // console.log
    var otpInt = parseInt(otp)
    var createdAt = Math.round(new Date().getTime()/1000)
    var expiresIn = 10;
    var insertionQuery = `insert into otp(otp,createdat,expiresin) values(${otpInt},${createdAt},${expiresIn})`
    console.log(insertionQuery)
    const result = await db.query(insertionQuery)
    console.log(result);
    return otp;
}
async function verifyOTP(otp){
    var searchQuery = `select * from otp where otp = ${otp}`;
    var result = await db.query(searchQuery)
    console.log(result)
    if(result.rowCount==0) throw new Error("Invalid OTP")

    var currentTime = Math.round(new Date().getTime()/1000)
    var expiresIn = result.rows[0].expiresIn
    var createdAt = result.rows[0].createdAt 
    var time = currentTime - createdAt
    var duration = expiresIn * 60
    if(time>duration) throw new Error("Invalid OTP")
    
    var deleteQuery = `delete from otp where otp = ${otp}`
     result = await db.query(deleteQuery)
    console.log(result)
}

exports.generateOTP = generateOTP;
exports.verifyOTP = verifyOTP;