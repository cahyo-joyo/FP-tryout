package com.example.tryout.siswa

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.tryout.R
import com.example.tryout.Util.GalleryHelper
import com.example.tryout.Util.Variabel
import com.example.tryout.data.ResponseMessage
import com.example.tryout.network.ApiService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profil_guru.*
import kotlinx.android.synthetic.main.activity_profil_guru.alamatProfil
import kotlinx.android.synthetic.main.activity_profil_guru.btnSaveProfil
import kotlinx.android.synthetic.main.activity_profil_guru.dataDesign
import kotlinx.android.synthetic.main.activity_profil_guru.genderProfil
import kotlinx.android.synthetic.main.activity_profil_guru.namaProfil
import kotlinx.android.synthetic.main.activity_profil_guru.telpProfil
import kotlinx.android.synthetic.main.activity_profil_guru.tempatlahirProfil
import kotlinx.android.synthetic.main.activity_profil_guru.tgllahirProfil
import kotlinx.android.synthetic.main.activity_profil_guru.usernameProfil
import kotlinx.android.synthetic.main.activity_profil_siswa.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

class ActivityProfil : AppCompatActivity() {

    lateinit var toolbarProfil: Toolbar
    lateinit var SP : SharedPreferences
    private var uriImage: Uri? = null
    private val pickImage = 1
    private var tanggalLahir = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_siswa)
        //init
        toolbarProfil = findViewById(R.id.toolbarProfil)
        val dataProfil = intent.extras
        SP = applicationContext.getSharedPreferences("TryoutOnline", Context.MODE_PRIVATE)
        var jenkel = arrayOf("Laki - Laki","Perempuan")
        genderProfil.adapter = ArrayAdapter<String>(applicationContext,R.layout.spinner_item, jenkel)
        //endinit

        getProfil(dataProfil)

        setSupportActionBar(toolbarProfil)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        btnSaveProfil.setOnClickListener {
            if(uriImage == null){
                editProfil(SP.getString("iduser","")!!.toString(),
                    namaProfil.text.toString(),
                    alamatProfil.text.toString(),
                    telpProfil.text.toString().toLong(),
                    tempatlahirProfil.text.toString(),
                    tanggalLahir,
                    genderProfil.selectedItem.toString(),
                    null)
            }else{
                editProfil(SP.getString("iduser","")!!.toString(),
                    namaProfil.text.toString(),
                    alamatProfil.text.toString(),
                    telpProfil.text.toString().toLong(),
                    tempatlahirProfil.text.toString(),
                    tanggalLahir,
                    genderProfil.selectedItem.toString(),
                    com.example.tryout.Util.FileUtils.getFile(this, uriImage))
            }

        }
        toolbarProfil.setNavigationOnClickListener {
            finish()
        }

        tgllahirProfil.setOnClickListener {
            val c = Calendar.getInstance()
            val year = dataProfil!!.getString("tanggalLahir")!!.subSequence(0,4).toString().toInt()
            val month = dataProfil!!.getString("tanggalLahir")!!.subSequence(5,7).toString().toInt()-1
            val day = dataProfil!!.getString("tanggalLahir")!!.subSequence(8,10).toString().toInt()
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, tahun, bulan, hari ->
                tanggalLahir = "$tahun-${bulan+1}-$hari"
                tgllahirProfil.text = dateFormatFromInt(tahun,bulan,hari)
            },year,month,day)
            datePicker.show()

        }

        dataDesign.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            dataDesign.setImageURI(uriImage)
        }
    }

    fun dateFormatFromInt(tahun : Int, bulan : Int, hari : Int) : String{
        return when(bulan+1){
            1-> "$hari Januari $tahun"
            2-> "$hari Februari $tahun"
            3-> "$hari Maret $tahun"
            4-> "$hari April $tahun"
            5-> "$hari Mei $tahun"
            6-> "$hari Juni $tahun"
            7-> "$hari Juli $tahun"
            8-> "$hari Agustus $tahun"
            9-> "$hari September $tahun"
            10-> "$hari Oktober $tahun"
            11-> "$hari November $tahun"
            12-> "$hari Desember $tahun"
            else -> ""
        }
    }


    fun dateFormat (date : String): String {
        val tahun = date.subSequence(0,4).toString()
        val bulan = date.subSequence(5,7).toString()
        val hari = date.subSequence(8,10).toString()
        return when(bulan.toInt()){
            1-> "$hari Januari $tahun"
            2-> "$hari Februari $tahun"
            3-> "$hari Maret $tahun"
            4-> "$hari April $tahun"
            5-> "$hari Mei $tahun"
            6-> "$hari Juni $tahun"
            7-> "$hari Juli $tahun"
            8-> "$hari Agustus $tahun"
            9-> "$hari September $tahun"
            10-> "$hari Oktober $tahun"
            11-> "$hari November $tahun"
            12-> "$hari Desember $tahun"
            else -> ""
        }
    }

    fun getProfil(data : Bundle?){
        namaProfil.setText(data!!.getString("nama"))
        usernameProfil.text = data.getString("username")
        nisnProfil.text = data.getString("nisn")
        if(data.getString("jenkel")!!.toLowerCase() == "laki - laki"){
            genderProfil.setSelection(0)
        }else{
            genderProfil.setSelection(1)
        }
        alamatProfil.setText(data.getString("alamat"))
        telpProfil.setText(data.getString("telp"))
        tempatlahirProfil.setText(data.getString("tempatLahir"))
        tgllahirProfil.text = dateFormat(data.getString("tanggalLahir").toString())
        tanggalLahir = data.getString("tanggalLahir").toString()

        Picasso.get().load(Variabel().URL_FOTO_SISWA+data.getString("foto")).into(dataDesign)
    }

    fun editProfil(id: String, nama: String, alamat: String, telp: Long,  tmp_lahir : String ,tgl_lahir : String, jenkel : String, foto : File?){
        val multipartBody : MultipartBody.Part?
        val requestBody : RequestBody
        if(foto != null){
            requestBody  = RequestBody.create("image/*".toMediaTypeOrNull(), foto!!)
            multipartBody = MultipartBody.Part.createFormData("image",
                foto.name, requestBody)
        }else{
            requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), "")
            multipartBody = MultipartBody.Part.createFormData("image",
                "", requestBody)
        }

        ApiService.endpoint.editProfilSiswa(id,nama,alamat,telp,tmp_lahir,tgl_lahir,jenkel,multipartBody)
            .enqueue(object : Callback<ResponseMessage>{
                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                    if(response.isSuccessful){
                        val data = response.body()
                        Toast.makeText(applicationContext, data!!.message, Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

            })
    }




}