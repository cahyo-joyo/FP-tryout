package com.example.tryout.guru.utama

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.tryout.ActivityLogin
import com.example.tryout.R
import com.example.tryout.Util.Variabel
import com.example.tryout.data.ProfilGuru
import com.example.tryout.guru.Mapel.ActivityMapelGuru.ActivityMapelGuru
import com.example.tryout.guru.ActivityProfil
import com.example.tryout.network.ApiService
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentAkun : Fragment() {
    lateinit var SP : SharedPreferences
    lateinit var textNama : TextView
    lateinit var textNIP : TextView
    lateinit var textUsername : TextView
    lateinit var textEmail : TextView
    lateinit var textTTL : TextView
    lateinit var textJenKel : TextView
    lateinit var textAlamat : TextView
    lateinit var textTelp : TextView
    lateinit var gambarAkun : ImageView
    lateinit var editAkun : TextView
    lateinit var data : ProfilGuru
    lateinit var mapelSaya : CardView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_akun_guru, container, false)
        setHasOptionsMenu(true)
        val actionBar = view.findViewById(R.id.toolbarAkun) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(actionBar)

        SP = activity!!.getSharedPreferences("TryoutOnline", Context.MODE_PRIVATE)
        textNama = view.findViewById(R.id.namaAkun)
        textNIP = view.findViewById(R.id.nipAkun)
        textUsername = view.findViewById(R.id.usernameAkun)
        textEmail = view.findViewById(R.id.emailAkun)
        textTTL = view.findViewById(R.id.ttlAkun)
        textJenKel = view.findViewById(R.id.genderAkun)
        textAlamat = view.findViewById(R.id.alamatAkun)
        textTelp = view.findViewById(R.id.telpAkun)
        gambarAkun = view.findViewById(R.id.gambarAkun)
        editAkun = view.findViewById(R.id.editAkun)
        mapelSaya = view.findViewById(R.id.akunMapel)


        mapelSaya.setOnClickListener {
            startActivity(Intent(activity!!.applicationContext, ActivityMapelGuru::class.java))
        }

        editAkun.setOnClickListener {
            val intent = Intent(activity!!.applicationContext, ActivityProfil::class.java)
            intent.putExtra("nama", data.nama)
            intent.putExtra("nip", data.nip)
            intent.putExtra("email", data.email)
            intent.putExtra("alamat", data.alamat)
            intent.putExtra("jenkel", data.jenkel)
            intent.putExtra("username", data.username)
            intent.putExtra("telp", data.telp)
            intent.putExtra("foto", data.foto)
            intent.putExtra("tempatLahir", data.tempat_lahir)
            intent.putExtra("tanggalLahir", data.tanggal_lahir)
            startActivity(intent)
        }
        getContent(SP.getString("iduser","").toString())
        return view
    }
    override fun onResume() {
        super.onResume()
        getContent(SP.getString("iduser","").toString())
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
        ApiService.endpoint.profilGuru(id)
            .enqueue(object : Callback<ProfilGuru>{
                override fun onFailure(call: Call<ProfilGuru>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<ProfilGuru>, response: Response<ProfilGuru>) {
                    if(response.isSuccessful){
                        val dataProfil : ProfilGuru? = response.body()
                        data = dataProfil!!
                        textNama.text = dataProfil.nama
                        textNIP.text = dataProfil.nip
                        textUsername.text = dataProfil.username
                        textEmail.text = dataProfil.email
                        textTTL.text = dataProfil.tempat_lahir+", "+dateFormat(dataProfil.tanggal_lahir)
                        textJenKel.text = dataProfil.jenkel
                        textAlamat.text = dataProfil.alamat
                        textTelp.text = "+62 "+dataProfil.telp.toString()

                        Picasso.get().load(Variabel().URL_FOTO_GURU+dataProfil.foto).into(gambarAkun)
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