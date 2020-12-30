package com.example.tryout.data.Jadwal

import com.google.gson.annotations.SerializedName

data class DataJadwal(
    @SerializedName("nama_mapel") val nama_mapel : String,
    @SerializedName("nama_kelas") val nama_kelas : String,
    @SerializedName("waktu") val waktu : String,
    @SerializedName("tanggal") val tanggal : String
)