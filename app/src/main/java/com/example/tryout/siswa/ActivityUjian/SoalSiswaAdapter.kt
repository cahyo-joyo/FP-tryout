package com.example.tryout.siswa.ActivityUjian

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tryout.R
import com.example.tryout.data.Soal.DataSoalSiswa
import com.example.tryout.guru.Soal.ActivityListSoal.ListSoalAdapter
import kotlinx.android.synthetic.main.cardsiswa_soal.view.*

class SoalSiswaAdapter (val context : Context, var dataSoal : ArrayList<DataSoalSiswa>):
    RecyclerView.Adapter<SoalSiswaAdapter.ViewHolder>() {
    lateinit var onPilihanA : OnItemClickCallback
    lateinit var onPilihanB : OnItemClickCallback
    lateinit var onPilihanC : OnItemClickCallback
    lateinit var onPilihanD : OnItemClickCallback
    lateinit var onPilihanE : OnItemClickCallback

    fun setOnPilihanACallback(onItemClickCallback: OnItemClickCallback){
        this.onPilihanA = onItemClickCallback
    }
    fun setOnPilihanBCallback(onItemClickCallback: OnItemClickCallback){
        this.onPilihanB = onItemClickCallback
    }
    fun setOnPilihanCCallback(onItemClickCallback: OnItemClickCallback){
        this.onPilihanC = onItemClickCallback
    }
    fun setOnPilihanDCallback(onItemClickCallback: OnItemClickCallback){
        this.onPilihanD = onItemClickCallback
    }
    fun setOnPilihanECallback(onItemClickCallback: OnItemClickCallback){
        this.onPilihanE = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cardsiswa_soal, parent, false)
        )
    override fun getItemCount() = dataSoal.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataSoal[position])
        holder.view.pilihanA.setOnClickListener { onPilihanA.onItemClicked(dataSoal[position],position) }
        holder.view.pilihanB.setOnClickListener { onPilihanB.onItemClicked(dataSoal[position],position) }
        holder.view.pilihanC.setOnClickListener { onPilihanC.onItemClicked(dataSoal[position],position) }
        holder.view.pilihanD.setOnClickListener { onPilihanD.onItemClicked(dataSoal[position],position) }
        holder.view.pilihanE.setOnClickListener { onPilihanE.onItemClicked(dataSoal[position],position) }
    }

    class ViewHolder(view : View): RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(dataSoal: DataSoalSiswa) {
            view.pilihanA.text = dataSoal.jawab_a
            view.pilihanB.text = dataSoal.jawab_b
            view.pilihanC.text = dataSoal.jawab_c
            view.pilihanD.text = dataSoal.jawab_d
            view.pilihanE.text = dataSoal.jawab_e
            view.soalSiswa.text = dataSoal.soal
        }
    }

    fun setData(newDataSoal: List<DataSoalSiswa>) {
        dataSoal.clear()
        dataSoal.addAll(newDataSoal)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: DataSoalSiswa, position: Int)
    }
}