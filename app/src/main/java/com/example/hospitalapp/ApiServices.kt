package com.example.hospitalapp


import com.example.hospitalapp.models.Creation
import com.example.hospitalapp.models.ModelAllCalls
import com.example.hospitalapp.models.ShowCall
import com.example.hospitalapp.models.UserModel
import com.vitatrack.hospitalsystem.models.ModelAllUser

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {



    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("Device Token") type: String
    ): UserModel



    @FormUrlEncoded
    @POST("register")
   suspend fun register(
       @Field("email") email: String,
       @Field("password") password: String,
       @Field("first_name") fName: String,
       @Field("last_name") lName: String,
       @Field("gender") gender: String,
       @Field("specialist") specialist: String,
       @Field("birthday") birthday: String,
       @Field("status") status: String,
       @Field("address") address: String,
       @Field("mobile") mobile: String,
       @Field("type") type: String
   ): UserModel

    @GET("doctors")
    suspend fun getEmployee(
        @Query("type") type: String,
        @Query("name") name: String
    ): ModelAllUser

    @FormUrlEncoded
    @POST("show-profile")
    suspend fun showProfile(
        @Field("user_id") userId: Int
    ): UserModel


    @FormUrlEncoded
    @POST("calls")
    suspend fun createCallReceptionist(
        @Field("patient_name") name: String,
        @Field("age") age: String,
        @Field("doctor_id") doctorId: Int,
        @Field("phone") phone: String,
        @Field("description") description: String
    ):Creation

    @GET("calls")
    suspend fun getAllCalls(@Query("date") date: String): ModelAllCalls

    @GET("calls/{id}")
    suspend fun showCall(@Path("id") id: Int): ShowCall


    @PUT("calls/{id}")
    suspend fun logoutCall(@Path("id") id: Int): Creation







}
