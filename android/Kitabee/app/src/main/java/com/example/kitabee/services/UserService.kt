package com.example.kitabee.services

import com.example.kitabee.retrofit.endpointInterfaces.APIUserEndpointsInterface
import com.example.kitabee.retrofit.requestModels.UserProfileUpdateRequestBody
import javax.inject.Inject

class UserService @Inject constructor(
   private val apiUserEndpointsInterface: APIUserEndpointsInterface
) {
    fun getUser(token : String) = apiUserEndpointsInterface.getUser(token)

    fun createUserProfile(
        token: String,
        updatedUserBody : UserProfileUpdateRequestBody
    ) = apiUserEndpointsInterface.createUserProfile(
        token,updatedUserBody
    )

    fun updateUser(
        token : String,
        field : String,
        value : String
    ) = apiUserEndpointsInterface.updateUser(token, field, value)
}