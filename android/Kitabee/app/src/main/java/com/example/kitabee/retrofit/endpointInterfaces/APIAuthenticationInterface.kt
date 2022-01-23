package com.example.kitabee.retrofit.endpointInterfaces

import com.example.kitabee.models.user
import com.example.kitabee.retrofit.requestModels.AuthRequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIAuthenticationInterface {

    @POST("/authentication/login")
    fun login(@Body authRequestBody: AuthRequestBody) : Call<user>

    @POST("/authentication/signup")
    fun signup(@Body authRequestBody: AuthRequestBody) : Call<user>

    @DELETE("/authentication/logout")
    fun logout(@HeaderMap header:Map<String,String>) : Call<String>


    @GET("authentication/google")
    fun authenticationWithGoogle(@Header("accept") type:String) : Call<String>

}