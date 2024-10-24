package com.project.powerref1.DataClasses

data class SignupRequest(
    val email: String,
    val firstname : String,
    val lastname : String?,
    val password: String
)

data class SignupResponse(
    val token: String
)
