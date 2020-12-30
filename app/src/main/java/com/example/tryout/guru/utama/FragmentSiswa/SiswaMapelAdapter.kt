package com.example.tryout.guru.utama.FragmentSiswa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tryout.R
import com.example.tryout.data.Mapel.DataMapel
import kotlinx.android.synthetic.main.cardmapel_kelas.view.*

class SiswaMapelAdapter (val context : Context, var dataMapel: ArrayList<DataMapel>):
    RecyclerView.Adapter<SiswaMapelAdapter.ViewHolder>(){
    lateinit var onDetail : OnItemClickCallback

    fun setOnDetailCallback(onItemClickCallback: OnItemClickCallback){
        this.onDetail = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cardmapel_kelas, parent, false)
        )

    override fun getItemCount() = dataMapel.size

    override fun onBindViewHolder(holder: SiswaMapelAdapter.ViewHolder, position: Int) {
        if(position == 0){
            holder.bing(dataMapel[position], "")
        }else{
            holder.bing(dataMapel[position], dataMapel[position-1].nama_kelas.toString())
        }
        holder.view.Mapel.setOnClickListener { onDetail.onItemClicked(dataMapel[position]) }
    }

    class ViewHolder(view : View): RecyclerView.ViewHolder(view){
        val view = view
        fun bing(dataMapel: DataMapel, kelas : String){
            view.namaMapel.text = dataMapel.nama_mapel
            view.namaKelas.text = dataMapel.nama_kelas
            if(dataMapel.nama_kelas != kelas){
                view.headKelas.visibility = View.VISIBLE
                view.headKelas.text = "kelas ${dataMapel.nama_kelas}"
            }
        }
    }

    fun setData(newDataMapel: List<DataMapel>){
        dataMapel.clear()
        dataMapel.addAll(newDataMapel)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: DataMapel)
    }
}