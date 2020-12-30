package com.example.tryout.guru.utama.FragmentHome

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tryout.R
import com.example.tryout.data.Jadwal.ResponseListDataJadwal
import com.example.tryout.data.KetDashboard
import com.example.tryout.guru.ActivityAjukanMapel.ActivityAjukanMapel
import com.example.tryout.guru.ActivityMapelSoal.ActivityMapelSoal
import com.example.tryout.guru.Mapel.ActivityMapelGuru.ActivityMapelGuru
import com.example.tryout.network.ApiService
import kotlinx.android.synthetic.main.fragment_home_guru.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentHome : Fragment() {
    lateinit var jadwalAdapter: JadwalAdapter
    lateinit var SP : SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home_guru, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        SP = activity!!.getSharedPreferences("TryoutOnline", Context.MODE_PRIVATE)
        jadwalAdapter = JadwalAdapter(activity!!.applicationContext, arrayListOf())



        getContent()
        getJadwal(SP.getString("iduser","").toString())

        rvJadwal.apply {
            layoutManager = LinearLayoutManager(activity!!.applicationContext)

            adapter = jadwalAdapter
        }

        btn_mapelSaya.setOnClickListener {
            startActivity(Intent(activity!!.applicationContext, ActivityMapelGuru::class.java))
        }
        btn_ajukanMapel.setOnClickListener {
            startActivity(Intent(activity!!.applicationContext, ActivityAjukanMapel::class.java))
        }

        btn_tambahSoal.setOnClickListener {
            startActivity(Intent(activity!!.applicationContext, ActivityMapelSoal::class.java))
        }
    }

    fun getContent(){
        ApiService.endpoint.dashboard()
            .enqueue(object :Callback<KetDashboard>{
                override fun onFailure(call: Call<KetDashboard>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<KetDashboard>,
                    response: Response<KetDashboard>
                ) {
                    if(response.isSuccessful){
                        val data = response.body()
                        jumGuru.text = data!!.jumlah_guru.toString()
                        jumKelas.text = data.jumlah_kelas.toString()
                        jumMapel.text = data.jumlah_mapel.toString()
                        jumSiswa.text = data.jumlah_siswa.toString()
                    }
                }

            })
    }

    fun getJadwal(id : String){
        ApiService.endpoint.loadJadwalGuru(id)
            .enqueue(object : Callback<ResponseListDataJadwal>{
                override fun onFailure(call: Call<ResponseListDataJadwal>, t: Throwable) {
                    t.printStackTrace()
                    loading.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<ResponseListDataJadwal>,
                    response: Response<ResponseListDataJadwal>
                ) {
                    if(response.isSuccessful){
                        loading.visibility = View.GONE
                        val data = response.body()
                        if(data!!.response) {
                            jadwalAdapter.setData(data.jadwal)
                        }else{
                            kosongJadwal.visibility = View.VISIBLE
                        }


                    }
                }

            })
    }
}