package com.example.tryout.siswa.utama

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.tryout.ActivityLogin
import com.example.tryout.R
import com.example.tryout.Util.Variabel
import com.example.tryout.data.ProfilSiswa
import com.example.tryout.data.ResponseMessage
import com.example.tryout.network.ApiService
import com.example.tryout.siswa.ActivityProfil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_list_soal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentAkun : Fragment() {
    lateinit var SP : SharedPreferences
    lateinit var namaAkunS : TextView
    lateinit var nisnAkunS : TextView
    lateinit var usernameAkunS : TextView
    lateinit var emailAkunS : TextView
    lateinit var ttlAkunS : TextView
    lateinit var tgllahirAkunS : TextView
    lateinit var genderAkunS : TextView
    lateinit var alamatAkunS : TextView
    lateinit var telpAkunS : TextView
    lateinit var kelasAkunS : TextView
    lateinit var jurusanAkunS : TextView
    lateinit var gambarAkunS : ImageView
    lateinit var editAkunS : TextView
    lateinit var data : ProfilSiswa

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_akun_siswa, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val actionBar = activity!!.findViewById(R.id.toolbarAkunS) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(actionBar)

        SP = activity!!.getSharedPreferences("TryoutOnline", Context.MODE_PRIVATE)
        namaAkunS = view!!.findViewById(R.id.namaAkunS)
        nisnAkunS = view!!.findViewById(R.id.nisnAkunS)
        usernameAkunS = view!!.findViewById(R.id.usernameAkunS)
        emailAkunS = view!!.findViewById(R.id.emailAkunS)
        ttlAkunS = view!!.findViewById(R.id.ttlAkunS)
        genderAkunS = view!!.findViewById(R.id.genderAkunS)
        alamatAkunS = view!!.findViewById(R.id.alamatAkunS)
        telpAkunS = view!!.findViewById(R.id.telpAkunS)
        kelasAkunS = view!!.findViewById(R.id.kelasAkunS)
        jurusanAkunS = view!!.findViewById(R.id.jurusanAkunS)
        gambarAkunS = view!!.findViewById(R.id.gambarAkunS)
        editAkunS = view!!.findViewById(R.id.editAkunS)

        editAkunS.setOnClickListener {
            val intent = Intent(activity!!.applicationContext, ActivityProfil::class.java)
            intent.putExtra("nisn", data.nisn)
            intent.putExtra("nama", data.nama)
            intent.putExtra("username", data.username)
            intent.putExtra("email", data.email)
            intent.putExtra("tempatLahir", data.tempat_lahir)
            intent.putExtra("tanggalLahir", data.tanggal_lahir)
            intent.putExtra("jenkel", data.jenkel)
            intent.putExtra("alamat", data.alamat)
            intent.putExtra("telp", data.telp)
//            intent.putExtra("kelas", kelasAkunS.text.toString())
//            intent.putExtra("jurusan", jurusanAkunS.text.toString())
            intent.putExtra("foto", data.foto)
            startActivity(intent)
        }
        getContent(SP.getString("iduser","").toString())
    }

    override fun onResume() {
        super.onResume()
        getContent(SP.getString("iduser", "").toString())
    }

    fun doLogout(){
        val dialog = AlertDialog.Builder(activity)
        dialog.setTitle("Konfirmasi Logout Akun")
        dialog.setMessage("Apakah anda yakin ingin logout akun ?")

        dialog.setPositiveButton("Iya"){dialog, which ->
            val editor = SP.edit()
            editor.putString("iduser","")
            editor.apply()
            startActivity(Intent(activity!!.applicationContext, ActivityLogin::class.java))
            activity!!.finish()
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal"){dialog, which ->
            dialog.dismiss()
        }

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

    private fun getContent(id : String){
        ApiService.endpoint.profilSiswa(id).enqueue(object : Callback<ProfilSiswa> {
            override fun onFailure(call: Call<ProfilSiswa>, t: Throwable) {
                t.printStackTrace()
            }
            override fun onResponse(call: Call<ProfilSiswa>, response: Response<ProfilSiswa>) {
                if(response.isSuccessful) {
                    val dataProfil  = response.body()
                    data = dataProfil!!
                    namaAkunS.text = dataProfil.nama
                    nisnAkunS.text = dataProfil.nisn
                    usernameAkunS.text = dataProfil.username
                    emailAkunS.text = dataProfil.email
                    ttlAkunS.text = "${dataProfil.tempat_lahir}, ${dateFormat(dataProfil.tanggal_lahir)}"
                    genderAkunS.text = dataProfil.jenkel
                    alamatAkunS.text = dataProfil.alamat
                    telpAkunS.text = "+62 ${dataProfil.telp}"
                    kelasAkunS.text = dataProfil.kelas
                    jurusanAkunS.text = dataProfil.jurusan
                    Picasso.get().load(Variabel().URL_FOTO_SISWA+dataProfil.foto).into(gambarAkunS)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val inflater: MenuInflater = activity!!.menuInflater
        inflater.inflate(R.menu.option_menu_logout, menu)
        return
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuLogout -> {
                doLogout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}