package com.project.powerref1.DataClasses

import com.google.gson.annotations.SerializedName

data class ImgModel(
    @SerializedName("title") val title: String,
    @SerializedName("image") val Image: String,
    @SerializedName("response") val Response: String
)