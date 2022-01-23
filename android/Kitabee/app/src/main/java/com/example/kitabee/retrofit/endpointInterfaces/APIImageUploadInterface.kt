package com.example.kitabee.retrofit.endpointInterfaces

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface APIImageUploadInterface {

    @Multipart
    @POST("uploadFile/uploadImage")
    fun uploadImage(@Part imageBody : MultipartBody.Part) : Call<String>
}