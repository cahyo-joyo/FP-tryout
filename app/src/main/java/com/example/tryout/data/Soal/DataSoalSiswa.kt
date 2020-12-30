package com.example.tryout.data.Soal

import com.google.gson.annotations.SerializedName

data class DataSoalSiswa (
    @SerializedName("id") val id : String,
    @SerializedName("id_mapel") val id_mapel : String,
    @SerializedName("soal") val soal : String,
    @SerializedName("jawab_a") val jawab_a : String,
    @SerializedName("jawab_b") val jawab_b : String,
    @SerializedName("jawab_c") val jawab_c : String,
    @SerializedName("jawab_d") val jawab_d : String,
    @SerializedName("jawab_e") val jawab_e : String,
    @SerializedName("kunci") val kunci : String
)