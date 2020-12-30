package com.example.tryout.data

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class ProfilSiswa(

    @SerializedName("id") val id:String,
    @SerializedName("nama") val nama:String,
    @SerializedName("jenkel") val jenkel:String,
    @SerializedName("tempat_lahir") val tempat_lahir:String,
    @SerializedName("tanggal_lahir") val tanggal_lahir:String,
    @SerializedName("telp") val telp:String,
    @SerializedName("alamat") val alamat:String,
    @SerializedName("email") val email:String,
    @SerializedName("username") val username:String,
    @SerializedName("nisn") val nisn:String,
    @SerializedName("foto") val foto:String,
    @SerializedName("kelas") val kelas:String,
    @SerializedName("jurusan") val jurusan:String
)