package com.example.tryout.siswa.ActivityUjian

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tryout.R
import com.example.tryout.data.ResponseMessage
import com.example.tryout.data.Soal.DataSoalSiswa
import com.example.tryout.data.Soal.ResponseListDataSoalSiswa
import com.example.tryout.network.ApiService
import kotlinx.android.synthetic.main.activity_ujian_siswa.*
import kotlinx.android.synthetic.main.cardsiswa_soal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class ActivityUjian : AppCompatActivity() {
    lateinit var recyclerSoalS : RecyclerView
    lateinit var soalSiswaAdapter: SoalSiswaAdapter
    lateinit var SP : SharedPreferences
    lateinit var alertDialog: AlertDialog.Builder
    val jawab : ArrayList<String> = arrayListOf()
    val jum_soal : Int = 100
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ujian_siswa)

        alertDialog = AlertDialog.Builder(this)
        SP = getSharedPreferences("TryoutOnline", Context.MODE_PRIVATE)

        recyclerSoalS = findViewById(R.id.recyclerSoalS)
        soalSiswaAdapter = SoalSiswaAdapter(applicationContext, arrayListOf())
        getSoal(SP.getString("iduser","").toString(), intent.getIntExtra("id_mapel",0))






        recyclerSoalS.apply {

            Log.v("ini test", soalSiswaAdapter.itemCount.toString())

            layoutManager = LinearLayoutManager(applicationContext)
            soalSiswaAdapter.setOnPilihanACallback(object : SoalSiswaAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DataSoalSiswa, position: Int) {
                    jawab[position] = "${data.id},A"

                }
            })
            soalSiswaAdapter.setOnPilihanBCallback(object : SoalSiswaAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DataSoalSiswa, position: Int) {
                    jawab[position] = "${data.id},B"
                }
            })

            soalSiswaAdapter.setOnPilihanCCallback(object : SoalSiswaAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DataSoalSiswa, position: Int) {
                    jawab[position] = "${data.id},C"
                }
            })

            soalSiswaAdapter.setOnPilihanDCallback(object : SoalSiswaAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DataSoalSiswa, position: Int) {
                    jawab[position] = "${data.id},D"
                }
            })

            soalSiswaAdapter.setOnPilihanECallback(object : SoalSiswaAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DataSoalSiswa, position: Int) {
                    jawab[position-1] = "${data.id},E"
                }
            })

            adapter = soalSiswaAdapter
        }

        finishSoalS.setOnClickListener {
            onBackPressed()
        }

        val minute:Long = 1000 * 60
        val millisInFuture:Long = (minute * intent.getIntExtra("durasi",0))
        val countDownInterval:Long = 1000
        timer(millisInFuture, countDownInterval).start()

    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    fun getSoal(id : String, id_mapel : Int){
        ApiService.endpoint.soalSiswa(id, id_mapel).enqueue(object : Callback<ResponseListDataSoalSiswa> {
            override fun onFailure(call: Call<ResponseListDataSoalSiswa>, t: Throwable) {
                t.printStackTrace()
            }
            override fun onResponse(call: Call<ResponseListDataSoalSiswa>, response: Response<ResponseListDataSoalSiswa>) {
                if(response.isSuccessful) {
                    val data = response.body()
                    soalSiswaAdapter.setData(data!!.soal)
                    for(i in 0 until data.soal.size){
                        jawab.add(i,"")
                    }
                }
            }
        })
    }

    private fun timer(millisInFuture:Long,countDownInterval:Long):CountDownTimer{
        return object: CountDownTimer(millisInFuture,countDownInterval){
            override fun onTick(millisUntilFinished: Long){
                val timeRemaining = timeString(millisUntilFinished)
                timerCount.text = timeRemaining
            }
            override fun onFinish() {
                alertDialog.setTitle("Ujian Berakhir")
                alertDialog.setMessage("Ujian Selesai, tekan OK untuk kembali ke Home")
                    .setCancelable(false)
                    .setPositiveButton("OK", object: DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, id:Int) {
                            submitJawaban()
                        }
                    }).create().show()
            }
        }
    }

    private fun timeString(millisUntilFinished:Long):String{
        var millisUntilFinished:Long = millisUntilFinished
        val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
        millisUntilFinished -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
        millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)

        return String.format(Locale.getDefault(), "%02d : %02d : %02d", hours, minutes,seconds)
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Tombol Kembali ditekan", Toast.LENGTH_SHORT).show()
        alertDialog.setTitle("Akhiri Ujian")
        alertDialog.setMessage("Apa kamu yakin untuk mengakhiri ujian ?")
            .setCancelable(false)
            .setPositiveButton("YA", object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, id:Int) {
                    submitJawaban()

                }
            })
            .setNegativeButton("TIDAK", object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, id:Int) {
                    dialog.cancel()
                }
            }).create().show()
    }

    fun submitJawaban(){
        ApiService.endpoint.siswaJawab(SP.getString("iduser","").toString(),intent.getIntExtra("id_mapel",0),jawab)
            .enqueue(object : Callback<ResponseMessage>{
                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    if(response.isSuccessful){
                        val data = response.body()
                        Toast.makeText(applicationContext,data!!.message,Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

            })
    }


}