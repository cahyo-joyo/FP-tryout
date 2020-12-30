package com.example.tryout.data

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class CekLevel(
    @SerializedName("rensponse") val response:Boolean,
    @SerializedName("level") val level:String,
    @SerializedName("message") val message:String
)