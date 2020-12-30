package com.example.tryout

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.tryout.data.CekLevel
import com.example.tryout.network.ApiService
import com.example.tryout.siswa.utama.ActivityUtama
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashScreen : AppCompatActivity() {
    private lateinit var SP: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        SP = getSharedPreferences("TryoutOnline", Context.MODE_PRIVATE)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val backgrond = object : Thread(){
            override fun run() {
                try {
                    sleep(500)
                    //tambah loading
                    getStatusLogin(SP.getString("iduser", "").toString())
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }

        backgrond.start()
    }

    private fun getStatusLogin(id: String) {
        if (id != "") {
            ApiService.endpoint.cekLevel(id)
                .enqueue(object : Callback<CekLevel> {
                    override fun onFailure(call: Call<CekLevel>, t: Throwable) {
                        t.printStackTrace()
                    }

                    override fun onResponse(call: Call<CekLevel>, response: Response<CekLevel>) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            if (data!!.level == "G") {
                                startActivity(Intent(applicationContext,com.example.tryout.guru.utama.ActivityUtama::class.java)
                                )
                                finish()
                            } else if (data!!.level == "S") {
                                startActivity(Intent(applicationContext, ActivityUtama::class.java))
                                finish()
                            }
                        }
                    }
                })
        }else{
            startActivity(Intent(applicationContext, ActivityLogin::class.java))
        }
    }
}