package com.example.tryout.data

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("response") val response:Boolean,
    @SerializedName("level") val level: String,
    @SerializedName("message") val message: String,
    @SerializedName("id") val id: String
)