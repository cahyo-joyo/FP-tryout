package com.example.tryout.guru.utama.FragmentHome

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tryout.R
import com.example.tryout.data.Jadwal.DataJadwal
import com.example.tryout.data.Mapel.DataMapel
import kotlinx.android.synthetic.main.cardjadwal_guru.view.*
import kotlinx.android.synthetic.main.cardmapel_kelas.view.namaMapel

class JadwalAdapter (val context : Context, var dataJadwal : ArrayList<DataJadwal>):
    RecyclerView.Adapter<JadwalAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cardjadwal_guru, parent, false)
        )

    override fun getItemCount() = dataJadwal.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position == 0){
            holder.bing(dataJadwal[position],"")
        }else{
            holder.bing(dataJadwal[position],dataJadwal[position-1].nama_kelas)
        }
    }

    class ViewHolder(view : View): RecyclerView.ViewHolder(view){
        val view = view

        fun bing(dataJadwal : DataJadwal, kelas : String){
            view.namaMapel.text = dataJadwal.nama_mapel
            view.namaKelas.text = dataJadwal.nama_kelas
            view.tanggal.text = dateFormat(dataJadwal.tanggal)
            view.jam.text = "${dataJadwal.waktu.subSequence(0,5)} WIB"
            if(dataJadwal.nama_kelas != kelas){
                view.headTanggal.visibility = View.VISIBLE
                view.headTanggal.text = dateFormat(dataJadwal.tanggal)
            }
        }
        private fun dateFormat (date : String): String {
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
    }

    fun setData(newDataJadwal : List<DataJadwal>){
        dataJadwal.clear()
        dataJadwal.addAll(newDataJadwal)
        notifyDataSetChanged()
    }


}