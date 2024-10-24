package com.project.powerref1.DataClasses

import com.google.gson.annotations.SerializedName

data class EmployeeInfo(
    @SerializedName("name") val name: String,
    @SerializedName("position") val position: String,
    @SerializedName("email") val email: String,
    @SerializedName("linkedin") val linkedin: String,
    @SerializedName("company") val company: String,
    @SerializedName("imageUrl") val imageUrl: String?
)