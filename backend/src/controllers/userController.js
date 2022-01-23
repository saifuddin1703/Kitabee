const db = require("../config/database");

async function getUserById(user_id){
   var get_query = `select * from user where _id = ${user_id}`;
   try {
      let result = await db(get_query);
      return result[0];
   } catch (err) {
      throw err
   }
}

async function isUserExists(username){
   try {
      let result = await db(get_query);
      return result != 0;
   } catch (err) {
      throw err
   }
}
async function updateUser(userId,field,value){
   var update_query = "";
   if(typeof value == 'number')
   update_query = `update user set ${field} = ${value} where _id = ${userId}`;
   else
   update_query = `update user set ${field} = '${value}' where _id = ${userId}`;

   try {
      let result = await db(update_query);
      let user = await getUserById(userId);
      return user;
   } catch (err) {
      throw err
   }
}


async function deleteUser(
   userId
){
    try {
       const result =await db(`delete from user where _id = ${userId}`);
       return result;
      } catch (err) {
       throw err;
    }
}

async function getAllUsers(){
     let get_query = `select * from user`;
     try {
      let result = await db(get_query);
      return result;
   } catch (err) {
      throw err
   }
}

async function getUserByUsername(username){
   var get_query = `select * from user where username = '${username}'`;

   try {
      let result = await db(get_query);
      console.log(result);
      return result;
   } catch (err) {
      throw err
   }
}


async function createUser (
    userDetails
){
    const {username,password} = userDetails;
    var insert_query = "insert into user(username,password) values (?)";
    var values = [[`${username}`,`${password}`]];
    try {
      let result = await db(insert_query,values);
      console.log(result)
      return result.insertId;
   } catch (err) {
      throw err
   }
}

async function createUserProfile(
   user_id,
   userProfileDetails
){
   const {profile_name,profile_picture,phone_number} = userProfileDetails
   var update_query = `update user set profile_name = ${profile_name},profile_picture = ${profile_picture},phone_number = ${phone_number}) where _id = ${user_id}`;
   try {
     let result = await db(update_query);
     console.log(result)
     return result.insertId;
  } catch (err) {
     throw err
  }
}
exports.createUserProfile = createUserProfile
exports.createUser = createUser
exports.updateUser = updateUser
exports.deleteUser = deleteUser
exports.getAllUsers = getAllUsers
exports.getUserById = getUserById
exports.getUserByUsername = getUserByUsername