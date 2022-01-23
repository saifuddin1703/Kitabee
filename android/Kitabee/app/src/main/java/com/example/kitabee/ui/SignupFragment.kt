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
import com.example.kitabee.databinding.FragmentSignupBinding
import com.example.kitabee.models.user
import com.example.kitabee.retrofit.RetrofitInstance
import com.example.kitabee.retrofit.endpointInterfaces.APIAuthenticationInterface
import com.example.kitabee.retrofit.requestModels.AuthRequestBody
import com.example.kitabee.services.AuthenticationService
import com.example.kitabee.utils.Client_ID
import com.example.kitabee.utils.TAG
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignupFragment : Fragment() {

    private lateinit var binding : FragmentSignupBinding
    private val RC_SIGN_IN = 121
    private lateinit var mGoogleSignInClient : GoogleSignInClient
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater)

        val username = binding.username.text.toString()
        val password = binding.password.text.toString()
        navController = findNavController()
        binding.doneButton.setOnClickListener {
            signUpUser(username = username , password = password)
        }

        binding.googleButton.setOnClickListener {
            signUpWithGoogle()
        }
        return binding.root
    }

    private fun signUpUser(username: String, password: String) {
        val retrofit = RetrofitInstance().getInstance()
        val apiAuthenticationInterface = retrofit.create(APIAuthenticationInterface::class.java)
        val authenticationService = AuthenticationService(apiAuthenticationInterface)
        val credential = AuthRequestBody(username, password)
        authenticationService.signup(credential).enqueue(
            object : Callback<user>{
                override fun onResponse(call: Call<user>, response: Response<user>) {
                    Log.d(TAG,"signup successful")
                    if (response.isSuccessful) {
                        val token = response.body()?.token
                        val action =
                            SignupFragmentDirections.actionSignupFragmentToProfileSetUpFragment(
                                token
                            );
                        navController.navigate(action)
                    }else{
                        Toast.makeText(requireContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<user>, t: Throwable) {
                    Log.d(TAG,"signup failed")
                }

            }
        )
    }

    private fun signUpWithGoogle() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(Client_ID)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
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

            TODO("Update the account token to the server")
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.localizedMessage)
//            updateUI(null)
        }
    }
}