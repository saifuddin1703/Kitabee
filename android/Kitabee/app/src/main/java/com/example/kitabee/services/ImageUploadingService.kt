package com.example.kitabee.services

import com.example.kitabee.retrofit.endpointInterfaces.APIImageUploadInterface
import okhttp3.MediaType
import okhttp3.MultipartBody
import retrofit2.Call
import java.io.File
import javax.inject.Inject

class ImageUploadingService @Inject constructor(
     private val apiImageUploadInterface: APIImageUploadInterface
) {
    fun uploadImage(imageFile : File): Call<String> {
       val imageBody = MultipartBody.Part.createFormData("imageFile",
            imageFile.name,
            MultipartBody.create(MediaType.parse("image/*"),imageFile)
        )

       return apiImageUploadInterface.uploadImage(imageBody)
    }
}