package com.example.kitabee.services

import com.example.kitabee.models.book
import com.example.kitabee.retrofit.endpointInterfaces.APIBooksInterface
import javax.inject.Inject

class BooksService @Inject constructor(
    private val apiBooksInterface: APIBooksInterface
) {
    fun getAllBooks() = apiBooksInterface.getAllBooks()

    fun uploadBook(book: book) = apiBooksInterface.uploadBook(book)
}