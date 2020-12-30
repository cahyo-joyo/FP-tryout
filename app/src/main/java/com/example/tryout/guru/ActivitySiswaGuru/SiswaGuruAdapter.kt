package com.example.tryout.guru.ActivitySiswaGuru

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tryout.R
import com.example.tryout.Util.Variabel
import com.example.tryout.data.Siswa.DataSiswa
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cardguru_siswa.view.*

class SiswaGuruAdapter (val context : Context, var dataSiswa : ArrayList<DataSiswa>):
    RecyclerView.Adapter<SiswaGuruAdapter.ViewHolder>(){
    lateinit var onDetail : OnItemClickCallback

    fun setOnDetailCallback(onItemClickCallback: OnItemClickCallback){
        this.onDetail = onItemClickCallback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cardguru_siswa, parent, false)
        )

    override fun getItemCount() = dataSiswa.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataSiswa[position])
        holder.view.cardguruSiswa.setOnClickListener { onDetail.onItemClicked(dataSiswa[position]) }
    }

    class ViewHolder(view : View): RecyclerView.ViewHolder(view){
        val view = view

        fun bing(dataSiswa: DataSiswa){
            view.namaSiswa.text = dataSiswa.nama
            view.nisnSiswa.text = dataSiswa.nisn
            Picasso.get().load(Variabel().URL_FOTO_SISWA+dataSiswa.foto).into(view.fotoSiswa)
        }
    }

    fun setData(newDataSiswa : List<DataSiswa>){
        dataSiswa.clear()
        dataSiswa.addAll(newDataSiswa)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: DataSiswa)
    }
}