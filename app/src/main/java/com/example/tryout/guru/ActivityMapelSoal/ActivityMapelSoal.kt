package com.example.tryout.guru.ActivityMapelSoal

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tryout.R
import com.example.tryout.data.Mapel.DataMapel
import com.example.tryout.data.Mapel.ResponseListDataMapel
import com.example.tryout.guru.ActivityAjukanMapel.MapelTersediaAdapter
import com.example.tryout.guru.Soal.ActivityListSoal.ActivityListSoal
import com.example.tryout.network.ApiService
import kotlinx.android.synthetic.main.activity_mapel_soal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityMapelSoal : AppCompatActivity() {
    lateinit var mapelSoalAdapter: MapelSoalAdapter
    lateinit var SP : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapel_soal)
        SP = getSharedPreferences("TryoutOnline", Context.MODE_PRIVATE)
        mapelSoalAdapter = MapelSoalAdapter(applicationContext, arrayListOf())

        getMapelGuru(SP.getString("iduser","").toString())
        rvMapel.apply {
            layoutManager = LinearLayoutManager(applicationContext)

            mapelSoalAdapter.setOnDetailCallback(object : MapelSoalAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DataMapel) {
                    val intent = Intent(applicationContext,ActivityListSoal::class.java)
                    intent.putExtra("idmapel",data.id)
                    intent.putExtra("mapel", data.nama_mapel)
                    intent.putExtra("kelas", data.nama_kelas)
                    startActivity(intent)
                }

            })

            adapter = mapelSoalAdapter
        }


    }

    fun getMapelGuru(id : String){
        ApiService.endpoint.mapelGuru(id)
            .enqueue(object : Callback<ResponseListDataMapel>{
                override fun onFailure(call: Call<ResponseListDataMapel>, t: Throwable) {
                    t.printStackTrace()
                    showMessage("Server Error")
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
                            mapelSoalAdapter.setData(data.mapel)
                        }else{
                            kosongMapel.visibility = View.VISIBLE
                        }

                    }
                }

            })
    }

    fun showMessage(msg : String){
        Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT).show()
    }
}