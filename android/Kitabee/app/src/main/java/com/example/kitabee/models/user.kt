package com.example.kitabee.models

import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName


/*
     _id: 8,
  username: '108941467306354352100',
  password: '',
  profile_name: '',
  profile_picture: '',
  token: 'eyJhbGciOiJIUzI1NiJ9.OA.rco9BI_w1c5QaAIyOwKhSEpjGmGcdt5iIZeXsSRrT10',
  phone_number: 0,
  address: ''
*/

data class user(
    @NonNull @SerializedName("_id")  val _id: Int =0,
    @NonNull @SerializedName("username") val username:String,
    @SerializedName("password") val password:String,
    @SerializedName("profile_name") val profile_name : String?,
    @SerializedName("profile_picture") val profile_picture : String?,
    @SerializedName("token") val token : String,
    @SerializedName("phone_number") val phone_number: Int,
    @SerializedName("address") val address : String
)
