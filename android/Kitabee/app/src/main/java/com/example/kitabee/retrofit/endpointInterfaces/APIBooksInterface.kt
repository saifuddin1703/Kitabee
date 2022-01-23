package com.example.kitabee.retrofit.endpointInterfaces

import com.example.kitabee.models.book
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIBooksInterface {

    @GET("/books")
    fun getAllBooks() : Call<List<book>>

    @POST("books/postBook")
    fun uploadBook(@Body book: book) : Call<String>
}