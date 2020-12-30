package com.example.tryout.siswa.utama.FragmentHome

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tryout.R
import com.example.tryout.data.Jadwal.DataJadwalSiswa
import kotlinx.android.synthetic.main.cardsiswa_jadwal.view.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class JadwalSiswaAdapter (val context : Context, var dataJadwal: ArrayList<DataJadwalSiswa>):
    RecyclerView.Adapter<JadwalSiswaAdapter.ViewHolder>() {
    lateinit var onMengerjakan : OnItemClickCallback

    fun setOnMengerjakanCallback(onItemClickCallback: OnItemClickCallback) {
        this.onMengerjakan = onItemClickCallback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cardsiswa_jadwal, parent, false)
        )
    override fun getItemCount() = dataJadwal.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position == 0){
            holder.bing(dataJadwal[position], "")

        }else{
            holder.bing(dataJadwal[position], dataJadwal[position-1].tanggal)
        }
        holder.view.btn_mengerjakan.setOnClickListener { onMengerjakan.onItemClicked(dataJadwal[position], holder.durasi) }
    }

    class ViewHolder(view : View): RecyclerView.ViewHolder(view) {
        val view = view
        var durasi : Int = 0
        @SuppressLint("NewApi")

        fun bing(
            dataJadwal: DataJadwalSiswa, tanggal: String?) {

            val date = getCurrentDateTime()
            val localDate = LocalDate.parse(date.toString("yyyy-MM-dd"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val dbDate = LocalDate.parse(dataJadwal.tanggal, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val diffDate = ChronoUnit.DAYS.between(localDate, dbDate)
            val localTime = LocalTime.parse(date.toString("HH:mm:ss"), DateTimeFormatter.ofPattern("HH:mm:ss")).toSecondOfDay()
            val dbTime = LocalTime.parse(dataJadwal.waktu, DateTimeFormatter.ofPattern("HH:mm:ss")).toSecondOfDay()
            val diffTime = (dbTime - localTime)
            val batasWaktu = dbTime+(dataJadwal.durasi!!.toInt()*60)
            val diffTimeBatas = batasWaktu-localTime

            if(dataJadwal.tanggal != tanggal){
                view.headTanggal.visibility = View.VISIBLE
                view.headTanggal.text = dateFormat(dataJadwal.tanggal.toString())
            }

            if(diffDate == 0.toLong()){
                view.mapelJadwalS.text = dataJadwal.mapel
                view.kelasJadwalS.text = dataJadwal.kelas
                view.jamJadwalS.text = dataJadwal.waktu!!.subSequence(0,5)
                view.tanggalJadwalS.text = dateFormat(dataJadwal.tanggal.toString())
                if(dataJadwal.nilai == null){ //belum mengerjakan
                    if(diffTime <= 0){
                        if(diffTimeBatas < 0){
                            //status ujian berlalu
                            view.sisaWaktu.visibility = View.GONE
                            view.btn_mengerjakan.visibility = View.GONE
                            view.status_akan_dimulai.visibility = View.GONE
                            view.status_sudah.visibility = View.GONE
                            view.status_berlalu.visibility = View.VISIBLE
                        }else{
                            //status ujian sedang berjalan
                            view.status_akan_dimulai.visibility = View.GONE
                            view.status_sudah.visibility = View.GONE
                            view.status_berlalu.visibility = View.GONE
                            view.btn_mengerjakan.visibility = View.VISIBLE
                            view.sisaWaktu.visibility = View.VISIBLE
                            view.sisaWaktu.text = "Sisa Waktu : ${detikToMenit(diffTimeBatas.toFloat())} Menit"
                            durasi = detikToMenit(diffTimeBatas.toFloat())
                        }
                    }else{
                        //status ujian akan dimulai
                        view.sisaWaktu.visibility = View.GONE
                        view.btn_mengerjakan.visibility = View.GONE
                        view.status_sudah.visibility = View.GONE
                        view.status_berlalu.visibility = View.GONE
                        view.status_akan_dimulai.visibility = View.VISIBLE
                    }
                }else{// sudah mengerjakan
                    view.sisaWaktu.visibility = View.GONE
                    view.btn_mengerjakan.visibility = View.GONE
                    view.status_akan_dimulai.visibility = View.GONE
                    view.status_berlalu.visibility = View.GONE
                    view.status_sudah.visibility = View.VISIBLE
                }
            }else if(diffDate > 0) {
                //tampilkan biasa
                view.mapelJadwalS.text = dataJadwal.mapel
                view.kelasJadwalS.text = dataJadwal.kelas
                view.jamJadwalS.text = dataJadwal.waktu!!.subSequence(0,5)
                view.tanggalJadwalS.text = dateFormat(dataJadwal.tanggal.toString())
            }else{
                view.cardJadwalS.visibility = View.GONE
            }

        }
        fun detikToMenit(detik : Float) : Int{
            val float : Float = detik/60
            return float.roundToInt()
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
        fun timeFormat(time: String): String {
            val df: DateFormat = SimpleDateFormat("HH:mm:ss")
            val outputformat: DateFormat = SimpleDateFormat("hh:mm aa")
            try {
                val date: Date = df.parse(time)
                val output: String = outputformat.format(date)
                return output
            } catch (pe: ParseException) {
                pe.printStackTrace()
                return ""
            }
        }
        fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
            val formatter = SimpleDateFormat(format, locale)
            return formatter.format(this)
        }

        fun getCurrentDateTime(): Date {
            return Calendar.getInstance().time
        }
    }

    fun setData(newDataJadwal: List<DataJadwalSiswa>) {
        dataJadwal.clear()
        dataJadwal.addAll(newDataJadwal)
        notifyDataSetChanged()
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: DataJadwalSiswa, durasi : Int)
    }
}