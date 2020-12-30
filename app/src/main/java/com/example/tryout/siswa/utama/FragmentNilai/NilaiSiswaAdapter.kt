package com.example.tryout.siswa.utama.FragmentNilai

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tryout.R
import com.example.tryout.data.Nilai.DataNilai
import kotlinx.android.synthetic.main.cardsiswa_nilai.view.*
import java.text.DecimalFormat

class NilaiSiswaAdapter (val context : Context, var dataNilai : ArrayList<DataNilai>):
    RecyclerView.Adapter<NilaiSiswaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cardsiswa_nilai, parent, false)
        )
    override fun getItemCount() = dataNilai.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataNilai[position])
    }

    class ViewHolder(view : View): RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(dataNilai: DataNilai){
            view.mapelNilaiS.text = dataNilai.nama_mapel
            view.guruNilaiS.text = "Guru : ${dataNilai.nama_guru}"
            view.hasilNilaiS.text = DecimalFormat("#.##").format(dataNilai.nilai).toString()
        }
    }

    fun setData(newDataNilai: List<DataNilai>) {
        dataNilai.clear()
        dataNilai.addAll(newDataNilai)
        notifyDataSetChanged()
    }
}