package com.example.kitabee.repositories

import android.content.Context
import com.example.kitabee.models.book
import com.example.kitabee.models.user
import com.example.kitabee.retrofit.requestModels.AuthRequestBody
import com.example.kitabee.retrofit.requestModels.UserProfileUpdateRequestBody
import com.example.kitabee.services.AuthenticationService
import com.example.kitabee.services.BooksService
import com.example.kitabee.services.ImageUploadingService
import com.example.kitabee.services.UserService
import com.example.kitabee.utils.DEFAULT_TOKEN_VALUE
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authenticationService : AuthenticationService,
    private val userService: UserService,
    private val imageUploadingService: ImageUploadingService,
    private val booksService: BooksService
) {
    private var token : String? = null
    fun loginUser(
        username : String,
        password : String
    ): Call<user> {
        val credential = AuthRequestBody(username, password)
        return authenticationService.login(credential)
    }

    fun signupUser(
        username : String,
        password : String
    ): Call<user> {
        val credential = AuthRequestBody(username, password)
        return authenticationService.signup(credential)
    }

    fun getUser(token : String): Call<user> {
        return userService.getUser(token)
    }

    fun createUserProfile(
        token : String,
        profile_name : String,
        profile_picture : String,
        phone_number : Int
    ): Call<String> {
        val updatedUserProfile = UserProfileUpdateRequestBody(
            profile_name,
            profile_picture,
            phone_number
        )
       return userService.createUserProfile(token,updatedUserProfile)
    }

    fun updateUser(
        token: String,
        field : String,
        value : String
    ): Call<String> {
        return userService.updateUser(token, field, value)
    }

    fun uploadImage(imageFile : File): Call<String> {
        return imageUploadingService.uploadImage(imageFile)
    }

    fun loadTokenFromSharePreferences() : String? {
        if (token==null) {
            val sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
            return sharedPreferences.getString("token", DEFAULT_TOKEN_VALUE)
        }
            return token
    }

    fun insertOrUpdateTokenInSharePreferences(token: String?){
        val sharedPreferences = context.getSharedPreferences("token",Context.MODE_PRIVATE)
        sharedPreferences.edit().apply{
            putString("token",token)
            apply()
        }
        this.token = token
    }

    fun getAllBooks() = booksService.getAllBooks()

    fun uploadBook(book: book) = booksService.uploadBook(book)
}