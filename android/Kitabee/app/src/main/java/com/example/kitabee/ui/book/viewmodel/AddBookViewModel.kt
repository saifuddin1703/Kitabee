package com.example.kitabee.ui.book.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kitabee.models.book
import com.example.kitabee.repositories.UserRepository
import javax.inject.Inject


class AddBookViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    fun uploadBook(book: book) = userRepository.uploadBook(book)
}