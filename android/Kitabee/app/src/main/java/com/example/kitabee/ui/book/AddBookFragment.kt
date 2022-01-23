package com.example.kitabee.ui.book

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.kitabee.R
import com.example.kitabee.databinding.AddBookFragmentBinding
import com.example.kitabee.ui.book.viewmodel.AddBookViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddBookFragment : Fragment() {

    val viewModel: AddBookViewModel by viewModels()
    private lateinit var binding : AddBookFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddBookFragmentBinding.inflate(inflater)

        binding.uploadBookButton.setOnClickListener {
            uploadBook()
        }
        binding.addImage.setOnClickListener {
            takeImage()
        }
        return binding.root
    }

    private fun takeImage() {
        val intent = Intent(Intent.ACTION_CAMERA_BUTTON)
    }

    private fun uploadBook() {

    }

}