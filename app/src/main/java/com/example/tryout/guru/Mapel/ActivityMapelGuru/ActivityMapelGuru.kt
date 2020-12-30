package com.example.tryout.guru.Mapel.ActivityMapelGuru

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tryout.R
import com.example.tryout.data.Mapel.DataMapel
import com.example.tryout.data.Mapel.ResponseListDataMapel
import com.example.tryout.data.Soal.DataSoal
import com.example.tryout.network.ApiService
import kotlinx.android.synthetic.main.activity_mapel_guru.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityMapelGuru : AppCompatActivity() {
    lateinit var mapelGuruAdapter: MapelGuruAdapter
    lateinit var SP : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapel_guru)
        SP = getSharedPreferences("TryoutOnline", Context.MODE_PRIVATE)
        mapelGuruAdapter = MapelGuruAdapter(applicationContext, arrayListOf())

        getMapelGuru(SP.getString("iduser","").toString())

        rvMapelGuru.apply {
            layoutManager = LinearLayoutManager(applicationContext)

            mapelGuruAdapter.setOnDetailCallback(object  : MapelGuruAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DataMapel) {
                    detailMapel(data)
                }
            })

//            mapelGuruAdapter.setOnEditCallback(object  : MapelGuruAdapter.OnItemClickCallback{
//                override fun onItemClicked(data: DataMapel) {
//                    showMessage("edit")
//                }
//            })

            adapter = mapelGuruAdapter
        }
    }

    fun getMapelGuru(id : String){
        ApiService.endpoint.mapelGuru(id)
            .enqueue(object : Callback<ResponseListDataMapel>{
                override fun onFailure(call: Call<ResponseListDataMapel>, t: Throwable) {
                    t.printStackTrace()
                    showMessage("Server error")
                    loading.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<ResponseListDataMapel>,
                    response: Response<ResponseListDataMapel>
                ) {
                    if(response.isSuccessful){
                        loading.visibility = View.GONE
                        val data = response.body()
                        if(data!!.response){
                            mapelGuruAdapter.setData(data!!.mapel)
                        }else{
                            kosongMapel.visibility = View.VISIBLE
                        }
                    }
                }

            })
    }

    private fun detailMapel(data: DataMapel){
        val dialog = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.dialog_detail_mapel, null)

        val nama = view.findViewById<TextView>(R.id.namaMapel)
        val deskripsi = view.findViewById<TextView>(R.id.deskripsiMapel)
        val kkm = view.findViewById<TextView>(R.id.kkmTryout)
        val waktu = view.findViewById<TextView>(R.id.waktuTryout)

        nama.text = data.nama_mapel
        deskripsi.text = data.deskripsi
        kkm.text = data.kkm.toString()
        waktu.text = "${data.durasi.toString()} Menit"

        dialog.setView(view)
        dialog.show()

    }
    fun showMessage(msg : String){
        Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT).show()
    }
}