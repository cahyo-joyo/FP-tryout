package com.example.tryout.guru.utama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tryout.R
import com.example.tryout.guru.utama.FragmentHome.FragmentHome
import com.example.tryout.guru.utama.FragmentNilai.FragmentNilai
import com.example.tryout.guru.utama.FragmentSiswa.FragmentSiswa
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_utama_guru.*

class ActivityUtama : AppCompatActivity() {
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when(item.itemId) {
            R.id.homeGuru -> {
                replaceFragment(FragmentHome())
                return@OnNavigationItemSelectedListener true
            }
            R.id.siswaGuru -> {
                replaceFragment(FragmentSiswa())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nilaiGuru -> {
                replaceFragment(FragmentNilai())
                return@OnNavigationItemSelectedListener true
            }
            R.id.akunGuru -> {
                replaceFragment(FragmentAkun())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_utama_guru)

        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(FragmentHome())
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer, fragment)
        fragmentTransition.commit()
    }
}