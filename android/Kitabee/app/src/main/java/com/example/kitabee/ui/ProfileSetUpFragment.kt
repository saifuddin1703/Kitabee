package com.example.kitabee.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kitabee.R
import com.example.kitabee.databinding.FragmentProfileSetUpBinding
import com.example.kitabee.models.user
import com.example.kitabee.repositories.UserRepository
import com.example.kitabee.retrofit.RetrofitInstance
import com.example.kitabee.retrofit.endpointInterfaces.APIImageUploadInterface
import com.example.kitabee.retrofit.endpointInterfaces.APIUserEndpointsInterface
import com.example.kitabee.retrofit.requestModels.UserProfileUpdateRequestBody
import com.example.kitabee.services.ImageUploadingService
import com.example.kitabee.services.UserService
import com.example.kitabee.utils.IMAGE_REQUEST_CODE
import com.example.kitabee.utils.TAG
import com.example.kitabee.utils.getFileFromUri
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class ProfileSetUpFragment : Fragment() {

    private lateinit var binding: FragmentProfileSetUpBinding
    private var imageUrl : String = ""
    private lateinit var token : String
    @Inject lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        token = arguments?.getString("token").toString()
        binding = FragmentProfileSetUpBinding.inflate(inflater)
        binding.editProfilePicture.setOnClickListener {
            choosePicture()
        }
        binding.doneButton.setOnClickListener {
            createProfile()
        }
        return binding.root
    }

    private fun createProfile() {
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val phoneNumber = binding.phoneNumber.text.toString()

        if (firstName.isEmpty()){
            Toast.makeText(requireContext(),"FirstName can not be empty",Toast.LENGTH_SHORT).show()
        }else if (phoneNumber.length > 10){
            Toast.makeText(requireContext(),"Phone number cannot be greater than 10 digits",Toast.LENGTH_SHORT).show()
        }else {
            val profileName = "$firstName $lastName"

            val updatedUserBody = UserProfileUpdateRequestBody(
                profileName, imageUrl, phoneNumber.toInt()
            )

            userRepository.createUserProfile(token,profileName,imageUrl,phoneNumber.toInt())
                .enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            Log.d(TAG, response.body().toString())
                            findNavController().navigate(R.id.action_profileSetUpFragment_to_homeFragment)
                        }
                        else{
                            Log.d(TAG, response.body().toString())
                        }

                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d(TAG, t.message.toString())
                    }

                })
        }
    }

    private fun choosePicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent,"Select Picture From "),
            IMAGE_REQUEST_CODE)
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == IMAGE_REQUEST_CODE){
            binding.progressBar.visibility = View.VISIBLE
            binding.editProfilePicture.visibility = View.GONE
            val selectedImageUri = data?.data
            selectedImageUri?.let {
               val selectedImageFile = getFileFromUri(selectedImageUri,requireContext())
                selectedImageFile?.let { file ->
                    uploadImage(file)
//                   imageUploadingService.uploadImage(file)
//                       .enqueue(object : Callback<String>{
//                           override fun onResponse(call: Call<String>, response: Response<String>) {
//                               binding.progressBar.visibility = View.GONE
//                               binding.editProfilePicture.visibility = View.VISIBLE
//
//                               imageUrl = response.body()
//
//                           }
//
//                           override fun onFailure(call: Call<String>, t: Throwable) {
//                               binding.progressBar.visibility = View.GONE
//                               binding.editProfilePicture.visibility = View.VISIBLE
//                               Log.d(TAG,"error while uploading the image")
//                           }
//
//                       })
                }
              // file is null i.e something is wrong
                binding.progressBar.visibility = View.GONE
                binding.editProfilePicture.visibility = View.VISIBLE

                Log.d(TAG,"error uploading the image")
            }
            // data is null i.e something is wrong
            Log.d(TAG,"error getting image")
            binding.progressBar.visibility = View.GONE
            binding.editProfilePicture.visibility = View.VISIBLE

        }

    }

    private fun uploadImage(file: File) {
        userRepository.uploadImage(file).enqueue(
            object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    binding.progressBar.visibility = View.GONE
                    binding.editProfilePicture.visibility = View.VISIBLE
                    if (response.isSuccessful) {
                        imageUrl = response.body().toString()
                    }else{
                        Toast.makeText(requireContext(),response.message(),Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    binding.editProfilePicture.visibility = View.VISIBLE
                    Log.d(TAG,"error while uploading the image")
                }

            }
        )
    }

}