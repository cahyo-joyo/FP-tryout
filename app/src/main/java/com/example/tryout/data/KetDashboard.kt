package com.example.tryout.data

import com.google.gson.annotations.SerializedName

data class KetDashboard(
    @SerializedName("response") val response : Boolean,
    @SerializedName("message") val message : String,
    @SerializedName("jumlah_kelas") val jumlah_kelas : Int,
    @SerializedName("jumlah_siswa") val jumlah_siswa : Int,
    @SerializedName("jumlah_guru") val jumlah_guru : Int,
    @SerializedName("jumlah_mapel") val jumlah_mapel : Int
)