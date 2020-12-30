package com.example.tryout.guru.ActivityNilaiGuru

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tryout.R
import com.example.tryout.data.Nilai.ResponseListDataNilai
import com.example.tryout.network.ApiService
import kotlinx.android.synthetic.main.activity_nilai_guru.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityNilaiGuru : AppCompatActivity() {
    lateinit var nilaiGuruAdapter: NilaiGuruAdapter
    lateinit var SP : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nilai_guru)
        SP = getSharedPreferences("TryoutOnline", Context.MODE_PRIVATE)
        toolbar.title = "Daftar Nilai ${intent.getStringExtra("mapel")} (${intent.getStringExtra("kelas")})"

        nilaiGuruAdapter = NilaiGuruAdapter(
            applicationContext, arrayListOf()
        )
        val rvNilai = findViewById<RecyclerView>(R.id.rvNilai)

        getNilai(SP.getString("iduser","").toString(),intent.getIntExtra("idmapel",0))

        rvNilai.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = nilaiGuruAdapter

        }
    }

    fun getNilai(id : String, idmapel : Int){
        ApiService.endpoint.guruNilai(id, idmapel)
            .enqueue(object : Callback<ResponseListDataNilai>{
                override fun onFailure(call: Call<ResponseListDataNilai>, t: Throwable) {
                    t.printStackTrace()
                    showMessage("gagal load data")
                }

                override fun onResponse(
                    call: Call<ResponseListDataNilai>,
                    response: Response<ResponseListDataNilai>
                ) {
                    if(response.isSuccessful){
                        loading.visibility = View.GONE
                        val data = response.body()
                        if(data!!.response){
                            nilaiGuruAdapter.setData(data.nilai)
                        }else{
                            kosongNilai.visibility = View.VISIBLE
                        }
                    }
                }

            })
    }

    fun showMessage(msg : String){
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}