package com.example.kitabee.retrofit.endpointInterfaces

import com.example.kitabee.models.user
import com.example.kitabee.retrofit.requestModels.UserProfileUpdateRequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIUserEndpointsInterface {

    @GET("/user/")
    fun getUser(@Header("token") token : String) : Call<user>

    @PUT("/user/updateUser")
    fun updateUser(
        @Header("token") token: String,
        @Query("field") field : String,
        @Query("value") value : String
    ) : Call<String>

    @PUT("/user/createUserProfile")
    fun createUserProfile(
        @Header("token") token : String,
        @Body body: UserProfileUpdateRequestBody
    ) : Call<String>
}