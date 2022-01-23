package com.example.kitabee.retrofit.requestModels

import com.google.gson.annotations.SerializedName

data class UserProfileUpdateRequestBody(
    @SerializedName("profile_name") val profile_name : String?,
    @SerializedName("profile_picture") val profile_picture : String?,
    @SerializedName("phone_number") val phone_number: Int,
)
