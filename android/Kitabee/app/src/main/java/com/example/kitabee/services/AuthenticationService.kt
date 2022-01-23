package com.example.kitabee.services

import com.example.kitabee.models.user
import com.example.kitabee.retrofit.endpointInterfaces.APIAuthenticationInterface
import com.example.kitabee.retrofit.requestModels.AuthRequestBody
import retrofit2.Call
import javax.inject.Inject


class AuthenticationService @Inject constructor(
    private val authenticationEndpointsInterface: APIAuthenticationInterface
    ) {

    fun login(credential:AuthRequestBody): Call<user> {
        return authenticationEndpointsInterface.login(credential)
    }

    fun signup(credential: AuthRequestBody): Call<user> {
       return authenticationEndpointsInterface.signup(credential)
    }

}