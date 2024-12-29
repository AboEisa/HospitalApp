package com.vitatrack.hospitalsystem.di


import com.example.hospitalapp.ApiServices
import com.example.hospitalapp.utils.Constants
import com.example.hospitalapp.utils.Constants.Companion.BASE_URL
import com.example.hospitalapp.utils.Constants.Companion.BEARER_TOKEN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

import javax.inject.Singleton
@InstallIn(SingletonComponent::class)
@Module
object Module {

    @Singleton
    @Provides
    fun getOkHttp():OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(150, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .callTimeout(50, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val originalRequest = chain.request()
                    val originalUrl = originalRequest.url
                    val url = originalUrl.newBuilder().build()
                    val requestBuilder = originalRequest.newBuilder().url(url)
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", "Bearer $BEARER_TOKEN"
                        )
                    val request = requestBuilder.build()
                    val response = chain.proceed(request)
                    response.code
                    return response
                }
            })
            .build()
    }


    @Singleton
    @Provides
    fun  getRetrofit(okHttp:OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttp)
            .baseUrl("https://hospital.elhossiny.net/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun getApiServices(retrofit: Retrofit) : ApiServices {
        return  retrofit.create(ApiServices::class.java)
    }




}