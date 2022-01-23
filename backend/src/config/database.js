const mysql = require('mysql');
const {Pool} = require('pg');
const remoteURL = 'postgres://atidqttmazloom:fb0adea5bbd3ca8a7c85eded09bc6c2cde6ddd9899e40a28351b7d2a9064d271@ec2-3-232-22-121.compute-1.amazonaws.com:5432/d3421dutj91l30'
const localURL =  'postgres://postgres:1431432@localhost:5432/postgres'
const pool = new Pool({
    connectionString : localURL,
    ssl : false
});
// pool.connect();

pool.connect().then((client)=>{
    console.log('database connected')
})
// const connection = mysql.createConnection({
//     host : "localhost",
//     user : "root",
//     password : "1431432"
// });
// const util = require('util');
// const query = util.promisify(connection.query).bind(connection);

//  let connectDB = async function(){
//     connection.connect(async function(err){
//         try {
//             if(err) console.log(err.message);
//             else{
//             console.log("database connected");
//             await query("USE test");
//             return connection
//             }
//         } catch (err) {
//             console.log(err)
//         }
//     });
// }

// connectDB();
module.exports = pool;