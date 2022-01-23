package com.example.kitabee.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.kitabee.R
import com.example.kitabee.databinding.FragmentLoginBinding
import com.example.kitabee.models.user
import com.example.kitabee.repositories.UserRepository
import com.example.kitabee.retrofit.endpointInterfaces.APIAuthenticationInterface
import com.example.kitabee.retrofit.requestModels.AuthRequestBody
import com.example.kitabee.services.AuthenticationService
import com.example.kitabee.utils.BASE_URL
import com.example.kitabee.utils.Client_ID
import com.example.kitabee.utils.DEFAULT_TOKEN_VALUE
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val RC_SIGN_IN = 121
    private lateinit var mGoogleSignInClient : GoogleSignInClient
    private lateinit var navController: NavController
    @Inject lateinit var userRepository : UserRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        navController = this.findNavController()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(Client_ID)
            .requestEmail()
            .build()

       mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
        binding.googleButton.setOnClickListener {
            signIn()
            TODO("Needed to be implemented at the last")
        }

        binding.orTemplate.setOnClickListener{
            navController.navigate(R.id.action_loginFragment_to_signupFragment)
        }

        binding.doneButton.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            userRepository.loginUser(
                username, password
            ).enqueue(object : Callback<user> {
                override fun onResponse(call: Call<user>, response: Response<user>) {
                     val token = response.body()?.token
                    if (response.isSuccessful) {
                        userRepository.insertOrUpdateTokenInSharePreferences(token)
                        Log.d("TAG", response.isSuccessful.toString())

                        val action =
                            LoginFragmentDirections.actionLoginFragmentToHomeFragment(token = token)
                        navController.navigate(action)
                    }else{
                        Toast.makeText(requireContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<user>, t: Throwable) {
                    Log.d("TAG",t.toString())
                }

            })

        }


        return binding.root
    }


        override fun onStart() {
            super.onStart()
            if (userRepository.loadTokenFromSharePreferences() != DEFAULT_TOKEN_VALUE){
                navController.navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }


    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            Toast.makeText(requireContext(),account.email, Toast.LENGTH_SHORT).show()
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.localizedMessage)
//            updateUI(null)
        }
    }
}