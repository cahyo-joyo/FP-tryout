package com.example.tryout.data.Jadwal

import com.google.gson.annotations.SerializedName

data class ResponseListDataJadwal(
    @SerializedName("response") val response : Boolean,
    @SerializedName("message") val message : String,
    @SerializedName("jadwal") val jadwal : List<DataJadwal>
)