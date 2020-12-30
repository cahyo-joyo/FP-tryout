package com.example.tryout.guru.ActivitySiswaGuru

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tryout.R
import com.example.tryout.Util.Variabel
import com.example.tryout.data.Siswa.DataSiswa
import com.example.tryout.data.Siswa.ResponseListDataSiswa
import com.example.tryout.network.ApiService
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_siswa_guru.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivitySiswaGuru : AppCompatActivity() {
    lateinit var siswaGuruAdapter: SiswaGuruAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_siswa_guru)
        siswaGuruAdapter = SiswaGuruAdapter(applicationContext, arrayListOf())

        toolbar.title = "Daftar Siswa Kelas ${intent.getStringExtra("kelas")} (${intent.getStringExtra("mapel")})"
        getSiswa(intent.getIntExtra("idmapel",0))

        rvSiswa.apply {
            layoutManager = LinearLayoutManager(applicationContext)

            siswaGuruAdapter.setOnDetailCallback(object : SiswaGuruAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DataSiswa) {
                    detailSiswa(data)
                }

            })

            adapter = siswaGuruAdapter
        }
    }

    fun getSiswa(id_mapel : Int){
        ApiService.endpoint.siswaGuru(id_mapel)
            .enqueue(object : Callback<ResponseListDataSiswa>{
                override fun onFailure(call: Call<ResponseListDataSiswa>, t: Throwable) {
                    t.printStackTrace()
                    loading.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<ResponseListDataSiswa>,
                    response: Response<ResponseListDataSiswa>
                ) {
                    if(response.isSuccessful){
                        loading.visibility = View.GONE
                        val data = response.body()
                        if(data!!.response){
                            siswaGuruAdapter.setData(data.siswa)
                        }else{
                            kosongSiswa.visibility = View.VISIBLE
                        }
                    }
                }

            })
    }

    fun detailSiswa(dataSiswa: DataSiswa){
        val dialog = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.dialog_detail_siswa, null)

        val foto = view.findViewById<CircleImageView>(R.id.fotoSiswa)
        val nisn = view.findViewById<TextView>(R.id.nisnSiswa)
        val nama = view.findViewById<TextView>(R.id.namaSiswa)
        val jenkel = view.findViewById<TextView>(R.id.jenkelSiswa)
        val telp = view.findViewById<TextView>(R.id.telpSiswa)
        val alamat = view.findViewById<TextView>(R.id.alamatSiswa)
        val ttl = view.findViewById<TextView>(R.id.ttlSiswa)
        val email = view.findViewById<TextView>(R.id.emailSiswa)
        val kelas = view.findViewById<TextView>(R.id.kelasSiswa)

        Picasso.get().load(Variabel().URL_FOTO_SISWA+dataSiswa.foto).into(foto)
        nama.text = dataSiswa.nama
        nisn.text = dataSiswa.nisn
        jenkel.text = dataSiswa.jenis_kelamin
        telp.text = "+62 ${dataSiswa.telp}"
        alamat.text = dataSiswa.alamat
        ttl.text = "${dataSiswa.tempat_lahir}, ${dateFormat(dataSiswa.tanggal_lahir)}"
        email.text = dataSiswa.email
        kelas.text = dataSiswa.nama_kelas

        dialog.setView(view)
        dialog.show()

    }

    fun dateFormat (date : String): String {
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