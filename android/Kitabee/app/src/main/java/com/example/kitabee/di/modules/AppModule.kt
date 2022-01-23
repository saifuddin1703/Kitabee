package com.example.kitabee.di.modules

import com.example.kitabee.retrofit.RetrofitInstance
import com.example.kitabee.retrofit.endpointInterfaces.APIAuthenticationInterface
import com.example.kitabee.retrofit.endpointInterfaces.APIBooksInterface
import com.example.kitabee.retrofit.endpointInterfaces.APIImageUploadInterface
import com.example.kitabee.retrofit.endpointInterfaces.APIUserEndpointsInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAPIAuthenticationInterface(): APIAuthenticationInterface {
        return RetrofitInstance().getInstance().create(APIAuthenticationInterface::class.java)
    }

    @Provides
    fun provideAPIBooksInterface(): APIBooksInterface {
        return RetrofitInstance().getInstance().create(APIBooksInterface::class.java)
    }
    @Provides
    fun provideAPIUserEndpointsInterface(): APIUserEndpointsInterface {
        return RetrofitInstance().getInstance().create(APIUserEndpointsInterface::class.java)
    }

    @Provides
    fun provideAPIImageUploadInterface(): APIImageUploadInterface {
        return RetrofitInstance().getInstance().create(APIImageUploadInterface::class.java)
    }
}