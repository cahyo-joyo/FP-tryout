package com.example.tryout.guru.Soal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.tryout.R
import com.example.tryout.data.ResponseMessage
import com.example.tryout.network.ApiService
import kotlinx.android.synthetic.main.activity_add_soal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityEditSoal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_soal)
        setContentSoal()

        tambahSoal.setOnClickListener {
            editSoal(intent.getIntExtra("id",0))
        }
    }

    fun editSoal(id_soal : Int){
        if(soalAdd.text.isEmpty()){
            showMessage("Harap isi soal")
        }else if(uraianA.text.isEmpty() ||uraianB.text.isEmpty() ||uraianC.text.isEmpty() ||uraianD.text.isEmpty() ||uraianE.text.isEmpty() ){
            showMessage("Harap isi jawaban secara lengkap")
        }else if(radioKunci.checkedRadioButtonId == -1) {
            showMessage("Harap pilih kunci jawaban")
        }else {
            val radioButton = findViewById<RadioButton>(radioKunci.checkedRadioButtonId)

            ApiService.endpoint.editSoal(id_soal,
                soalAdd.text.toString(),
                radioButton.text.toString(),
                uraianA.text.toString(),
                uraianB.text.toString(),
                uraianC.text.toString(),
                uraianD.text.toString(),
                uraianE.text.toString())
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
                            finish();
                        }
                    }

                })
        }
    }

    fun setContentSoal(){
        toolbar.title = "Perbarui Soal"
        tambahSoal.text = "Perbarui"
        soalAdd.setText(intent.getStringExtra("soal"))
        uraianA.setText(intent.getStringExtra("uraianA"))
        uraianB.setText(intent.getStringExtra("uraianB"))
        uraianC.setText(intent.getStringExtra("uraianC"))
        uraianD.setText(intent.getStringExtra("uraianD"))
        uraianE.setText(intent.getStringExtra("uraianE"))

        when(intent.getStringExtra("kunci")){
            "A"-> radioKunci.check(R.id.kunciA)
            "B"-> radioKunci.check(R.id.kunciB)
            "C"-> radioKunci.check(R.id.kunciC)
            "D"-> radioKunci.check(R.id.kunciD)
            "E"-> radioKunci.check(R.id.kunciE)
        }
    }

    fun showMessage(msg : String){
        Toast.makeText(applicationContext, msg , Toast.LENGTH_SHORT).show()
    }
}