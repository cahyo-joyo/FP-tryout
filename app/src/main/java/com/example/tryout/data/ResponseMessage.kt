package com.example.tryout.data

import com.google.gson.annotations.SerializedName

data class ResponseMessage(
    @SerializedName("response") val response : Boolean,
    @SerializedName("message") val message:String
)