package com.example.tryout.data.Soal

import com.google.gson.annotations.SerializedName

data class ResponseListDataSoalSiswa(
    @SerializedName("response") val response : Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("soal") val soal : List<DataSoalSiswa>
)