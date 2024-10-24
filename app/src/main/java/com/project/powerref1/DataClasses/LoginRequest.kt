package com.project.powerref1.DataClasses


data class LoginRequest(val email: String, val password: String)
data class LoginResponse(
    val token: String,
    val user: User
)

data class User(
    val email: String,
    val firstname: String,
    val lastname: String,
    val userId: String,
    val createdAt: String,
    val updatedAt: String
)
