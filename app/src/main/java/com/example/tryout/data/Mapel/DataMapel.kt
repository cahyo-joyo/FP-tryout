package com.example.tryout.data.Mapel

import com.google.gson.annotations.SerializedName

data class DataMapel(
    @SerializedName("id") val id: Int,
    @SerializedName("nama_mapel") val nama_mapel : String,
    @SerializedName("deskripsi") val deskripsi : String,
    @SerializedName("kkm") val kkm : Int,
    @SerializedName("durasi") val durasi : Int,
    @SerializedName("nama_guru") val nama_guru : String,
    @SerializedName("nama_kelas") val nama_kelas : String
)