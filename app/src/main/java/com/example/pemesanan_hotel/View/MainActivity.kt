package com.example.pemesanan_hotel.View

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.example.pemesanan_hotel.Helper.IntentHelper
import com.example.pemesanan_hotel.Helper.SharedPreferencesHelper
import com.example.pemesanan_hotel.R
import com.example.pemesanan_hotel.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var b: ActivityMainBinding
    private lateinit var intentHelper: IntentHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.setTitle("Dashboard")

        val sharedPreferencesHelper = SharedPreferencesHelper(this)
        b.textView.setText("Selamat Datang di Aplikasi "+sharedPreferencesHelper.getString("nama", ""))

        intentHelper = IntentHelper(this)

        b.btnHotel.setOnClickListener {
            intentHelper.startActivity(intentHelper.dataHotelIntent())
        }

        b.btnBooking.setOnClickListener {
            intentHelper.startActivity(intentHelper.bookingIntent())
        }

        b.bottomNavigasi.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.nav_home -> {
                supportActionBar?.setTitle("Dashboard")
                b.frameLayout.visibility = View.GONE
            }
            R.id.nav_profil -> {
                supportActionBar?.setTitle("Profil")
                var frag = ProfilFragment()

                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, frag).commit()
                b.frameLayout.setBackgroundColor(Color.argb(255,255,255,255))
                b.frameLayout.visibility = View.VISIBLE
            }
        }
        return true
    }
}