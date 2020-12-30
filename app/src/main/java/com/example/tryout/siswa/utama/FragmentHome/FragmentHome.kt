package com.example.tryout.siswa.utama.FragmentHome

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tryout.R
import com.example.tryout.data.Jadwal.DataJadwalSiswa
import com.example.tryout.data.Jadwal.ResponseListDataJadwalSiswa
import com.example.tryout.data.ResponseMessage
import com.example.tryout.network.ApiService
import com.example.tryout.siswa.ActivityUjian.ActivityUjian
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentHome : Fragment() {
    lateinit var recyclerHomeS : RecyclerView
    lateinit var loading : ProgressBar
    lateinit var jadwalAdapter: JadwalSiswaAdapter
    lateinit var SP : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_siswa, container, false)

        SP = activity!!.getSharedPreferences("TryoutOnline", Context.MODE_PRIVATE)
        getJadwal(SP.getString("iduser","").toString())
        recyclerHomeS = view.findViewById(R.id.recyclerHomeS)
        loading = view.findViewById(R.id.loading)
        jadwalAdapter = JadwalSiswaAdapter(activity!!.applicationContext, arrayListOf())

        recyclerHomeS.apply {
            layoutManager = LinearLayoutManager(activity!!.applicationContext)
            jadwalAdapter.setOnMengerjakanCallback(object : JadwalSiswaAdapter.OnItemClickCallback {
                override fun onItemClicked(data: DataJadwalSiswa, durasi : Int) {
                    val intent = Intent(activity!!.applicationContext, ActivityUjian::class.java)
                    intent.putExtra("id_mapel", data.id_mapel)
                    intent.putExtra("durasi", durasi)
                    startActivity(intent)
                }
            })
            adapter = jadwalAdapter
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        getJadwal(SP.getString("iduser","").toString())
    }

    fun getJadwal(id : String){
        ApiService.endpoint.listJadwal(id).enqueue(object : Callback<ResponseListDataJadwalSiswa> {
            override fun onFailure(call: Call<ResponseListDataJadwalSiswa>, t: Throwable) {
                t.printStackTrace()
                loading.visibility = View.GONE
            }
            override fun onResponse(call: Call<ResponseListDataJadwalSiswa>, response: Response<ResponseListDataJadwalSiswa>) {
                if(response.isSuccessful) {
                    loading.visibility = View.GONE
                    val data = response.body()
                    if(data!!.response){
                        jadwalAdapter.setData(data.jadwal)
                    }else{
                        //datakosong
                    }


                }
            }
        })
    }
}