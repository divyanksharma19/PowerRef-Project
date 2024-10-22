package com.project.powerref1

import com.project.powerref1.DataClasses.SignupRequest
import com.project.powerref1.DataClasses.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/powerRef/auth/signup")
    fun signup(@Body request: SignupRequest): Call<SignupResponse>



}
