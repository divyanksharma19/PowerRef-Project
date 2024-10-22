package com.project.powerref1.DataClasses

data class SignupRequest(
    val email: String,
    val password: String
)

data class SignupResponse(
    val token: String
)
