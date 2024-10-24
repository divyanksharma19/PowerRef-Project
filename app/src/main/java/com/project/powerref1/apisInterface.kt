package com.project.powerref1

import com.project.powerref1.DataClasses.ImgModel
import com.project.powerref1.DataClasses.LoginRequest
import com.project.powerref1.DataClasses.LoginResponse
import com.project.powerref1.DataClasses.SignupRequest
import com.project.powerref1.DataClasses.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @POST("/powerRef/auth/signup")
    fun signup(@Body request: SignupRequest): Call<SignupResponse>

    @POST("powerRef/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @FormUrlEncoded
    @POST("powerRef/images/uploadDL")
    fun uploadDl(
        @Field("token") token: String,
        @Field("docnumber") docNumber: String,
        @Field("title") title: String,
        @Field("image") image: String
    ): Call<ImgModel>
}
