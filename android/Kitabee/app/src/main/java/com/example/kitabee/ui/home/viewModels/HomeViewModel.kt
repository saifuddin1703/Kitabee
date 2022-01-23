package com.example.kitabee.ui.home.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kitabee.models.book
import com.example.kitabee.models.user
import com.example.kitabee.repositories.UserRepository
import com.example.kitabee.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(){
    val listOfBooks : MutableLiveData<List<book>> by lazy {
        MutableLiveData<List<book>>().also {
            loadListOfBooks().enqueue(
                object : Callback<List<book>>{
                    override fun onResponse(
                        call: Call<List<book>>,
                        response: Response<List<book>>
                    ) {
                       if (response.isSuccessful){
                           it.value = response.body()
                       }else{
                           Log.d(TAG,"Error in getting list of books ${response.errorBody()}")
                       }
                    }

                    override fun onFailure(call: Call<List<book>>, t: Throwable) {
                        Log.d(TAG,"Error in getting list of books ${t.message}")

                    }

                }
            )
        }
    }


    private fun loadListOfBooks(): Call<List<book>> {
        return userRepository.getAllBooks()
    }
}