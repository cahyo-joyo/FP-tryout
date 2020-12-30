package com.example.tryout.data.Mapel

import com.example.tryout.data.Mapel.DataMapel
import com.google.gson.annotations.SerializedName

data class ResponseListDataMapel (
    @SerializedName("response") val response : Boolean,
    @SerializedName("message") val message : String,
    @SerializedName("mapel") val mapel : List<DataMapel>
)