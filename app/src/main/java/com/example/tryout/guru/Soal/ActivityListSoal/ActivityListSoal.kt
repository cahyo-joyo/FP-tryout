package com.example.tryout.guru.Soal.ActivityListSoal

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tryout.R
import com.example.tryout.data.Mapel.DataMapel
import com.example.tryout.data.ResponseMessage
import com.example.tryout.data.Soal.DataSoal
import com.example.tryout.data.Soal.ResponseListDataSoal
import com.example.tryout.guru.ActivityMapelSoal.MapelSoalAdapter
import com.example.tryout.guru.Soal.ActivityEditSoal
import com.example.tryout.network.ApiService
import com.example.tryout.guru.Soal.ActivityAddSoal
import kotlinx.android.synthetic.main.activity_list_soal.*
import kotlinx.android.synthetic.main.dialog_detail_soal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityListSoal : AppCompatActivity() {
    lateinit var listSoalAdapter: ListSoalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_soal)

        listSoalAdapter = ListSoalAdapter(applicationContext, arrayListOf())

        toolbar.title = "Data Soal "+intent.getStringExtra("mapel")+" ("+intent.getStringExtra("kelas")+")"
        getSoal(intent.getIntExtra("idmapel",0))

        rvSoal.apply {
            layoutManager = LinearLayoutManager(applicationContext)

            listSoalAdapter.setOnDetailCallback(object : ListSoalAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DataSoal, position : Int ) {
                    detailSoal(data)
                }
            })

            listSoalAdapter.setOnDeleteCallback(object : ListSoalAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DataSoal, position : Int ) {
                    hapusSoal(data.id,position,data)
                }

            })

            listSoalAdapter.setOnEditCallback(object : ListSoalAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DataSoal, position : Int ) {
                    val intent = Intent(applicationContext, ActivityEditSoal::class.java)
                    intent.putExtra("id", data.id)
                    intent.putExtra("soal", data.soal)
                    intent.putExtra("kunci", data.kunci)
                    intent.putExtra("uraianA", data.jawab_a)
                    intent.putExtra("uraianB", data.jawab_b)
                    intent.putExtra("uraianC", data.jawab_c)
                    intent.putExtra("uraianD", data.jawab_d)
                    intent.putExtra("uraianE", data.jawab_e)
                    startActivity(intent)
                }

            })

            adapter = listSoalAdapter
        }

        btn_tambahSoal.setOnClickListener {
            val test = Intent(applicationContext, ActivityAddSoal::class.java)
            test.putExtra("idmapel",intent.getIntExtra("idmapel",0))
            startActivity(test)
        }
    }

    override fun onResume() {
        super.onResume()
        kosongSoal.visibility =  View.GONE
        getSoal(intent.getIntExtra("idmapel",0))
    }

    fun getSoal(id : Int){
        ApiService.endpoint.mapelSoal(id)
            .enqueue(object : Callback<ResponseListDataSoal>{
                override fun onFailure(call: Call<ResponseListDataSoal>, t: Throwable) {
                    t.printStackTrace()
                    showMessage("Server Error")
                    loading.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<ResponseListDataSoal>,
                    response: Response<ResponseListDataSoal>
                ) {
                    if(response.isSuccessful){
                        loading.visibility = View.GONE
                        val data = response.body()
                        if(data!!.response){
                            listSoalAdapter.setData(data.soal)
                        }else{
                            kosongSoal.visibility = View.VISIBLE
                        }
                    }
                }

            })
    }

    fun hapusSoal(id : Int, position : Int, dataSoal : DataSoal){
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi Hapus")
        dialog.setMessage("Apakah anda yakin ingin menghapus soal '${dataSoal.soal}' ?")

        dialog.setPositiveButton("Hapus"){dialog, which ->
            ApiService.endpoint.deleteSoal(id)
                .enqueue(object : Callback<ResponseMessage>{
                    override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                        t.printStackTrace()
                        showMessage("Server Error")
                    }

                    override fun onResponse(
                        call: Call<ResponseMessage>,
                        response: Response<ResponseMessage>
                    ) {
                        if(response.isSuccessful){
                            val data = response.body()
                            showMessage(data!!.message)
                            listSoalAdapter.removeData(position)
                            if(listSoalAdapter.itemCount == 0){
                                kosongSoal.visibility = View.VISIBLE
                            }
                        }
                    }

                })
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal"){dialog, which ->
            dialog.dismiss()
        }

        dialog.show()

    }

    fun detailSoal(data: DataSoal){
        val dialog = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.dialog_detail_soal, null)

        val soal = view.findViewById<TextView>(R.id.soalAdd)
        val uraianA = view.findViewById<TextView>(R.id.uraianA)
        val uraianB = view.findViewById<TextView>(R.id.uraianB)
        val uraianC = view.findViewById<TextView>(R.id.uraianC)
        val uraianD = view.findViewById<TextView>(R.id.uraianD)
        val uraianE = view.findViewById<TextView>(R.id.uraianE)
        val kunci = view.findViewById<TextView>(R.id.kunci)

        soal.text = data.soal
        uraianA.text = data.jawab_a
        uraianB.text = data.jawab_b
        uraianC.text = data.jawab_c
        uraianD.text = data.jawab_d
        uraianE.text = data.jawab_e
        kunci.text = data.kunci

        dialog.setView(view)
        dialog.show()

    }

    fun showMessage(msg : String){
        Toast.makeText(applicationContext, msg , Toast.LENGTH_SHORT).show()
    }
}