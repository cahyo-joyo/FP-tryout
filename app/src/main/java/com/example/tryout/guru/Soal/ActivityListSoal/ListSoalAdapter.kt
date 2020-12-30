package com.example.tryout.guru.Soal.ActivityListSoal

import android.content.Context
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tryout.R
import com.example.tryout.data.Mapel.DataMapel
import com.example.tryout.data.Nilai.DataNilai
import com.example.tryout.data.Soal.DataSoal
import com.example.tryout.guru.ActivityNilaiGuru.NilaiGuruAdapter
import kotlinx.android.synthetic.main.cardguru_nilai.view.*
import kotlinx.android.synthetic.main.cardmapel_kelas.view.*
import kotlinx.android.synthetic.main.cardsoal.view.*

class ListSoalAdapter (val context : Context, var dataSoal : ArrayList<DataSoal>):
    RecyclerView.Adapter<ListSoalAdapter.ViewHolder>() {
    lateinit var onDetail : OnItemClickCallback
    lateinit var onDelete : OnItemClickCallback
    lateinit var onEdit : OnItemClickCallback

    fun setOnDetailCallback(onItemClickCallback: OnItemClickCallback){
        this.onDetail = onItemClickCallback
    }

    fun setOnDeleteCallback(onItemClickCallback: OnItemClickCallback){
        this.onDelete = onItemClickCallback
    }

    fun setOnEditCallback(onItemClickCallback: OnItemClickCallback){
        this.onEdit = onItemClickCallback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cardsoal, parent, false)
        )

    override fun getItemCount()= dataSoal.size

    override fun onBindViewHolder(holder: ListSoalAdapter.ViewHolder, position: Int) {
        holder.bing(dataSoal[position])
        holder.view.detailSoal.setOnClickListener{ onDetail.onItemClicked(dataSoal[position],position) }
        holder.view.btn_delete_soal.setOnClickListener{ onDelete.onItemClicked(dataSoal[position],position) }
        holder.view.btn_edit_soal.setOnClickListener{ onEdit.onItemClicked(dataSoal[position], position) }
    }

    class ViewHolder(view : View): RecyclerView.ViewHolder(view){
        val view = view

        fun bing(dataSoal: DataSoal){
            view.soal.text = dataSoal.soal
            view.kunci.text = "Kunci : ${dataSoal.kunci}"
        }
    }

    fun setData(newDataSoal: List<DataSoal>){
        dataSoal.clear()
        dataSoal.addAll(newDataSoal)
        notifyDataSetChanged()
    }

    fun removeData(position: Int){
        dataSoal.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataSoal.size)
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: DataSoal, position: Int)
    }

}