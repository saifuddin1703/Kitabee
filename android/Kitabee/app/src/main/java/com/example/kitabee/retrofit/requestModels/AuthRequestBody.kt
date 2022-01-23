package com.example.kitabee.retrofit.requestModels

import com.google.gson.annotations.SerializedName

data class AuthRequestBody(
    @SerializedName("username") val username:String,
    @SerializedName("password") val password:String
)
