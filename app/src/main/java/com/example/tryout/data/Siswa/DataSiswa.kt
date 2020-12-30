package com.example.tryout.data.Siswa

import com.google.gson.annotations.SerializedName

data class DataSiswa(
    @SerializedName("nisn") val nisn : String,
    @SerializedName("nama") val nama : String,
    @SerializedName("foto") val foto : String,
    @SerializedName("jenis_kelamin") val jenis_kelamin : String,
    @SerializedName("telp") val telp : String,
    @SerializedName("alamat") val alamat : String,
    @SerializedName("tempat_lahir") val tempat_lahir : String,
    @SerializedName("tanggal_lahir") val tanggal_lahir : String,
    @SerializedName("email") val email : String,
    @SerializedName("nama_kelas") val nama_kelas : String

)