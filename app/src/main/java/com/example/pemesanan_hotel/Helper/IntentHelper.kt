package com.example.pemesanan_hotel.Helper

import android.content.Context
import android.content.Intent
import com.example.pemesanan_hotel.View.BookingActivity
import com.example.pemesanan_hotel.View.DataHotelActivity
import com.example.pemesanan_hotel.View.EditHotelActivity
import com.example.pemesanan_hotel.View.HotelActivity
import com.example.pemesanan_hotel.View.LoginActivity
import com.example.pemesanan_hotel.View.MapsActivity

class IntentHelper(private val context: Context) {

    fun bookingIntent(): Intent {
        return Intent(context, BookingActivity::class.java)
    }

    fun hotelIntent(): Intent {
        return Intent(context, HotelActivity::class.java)
    }

    fun intentEditHotel(data : String): Intent {
        val intent = Intent(context, EditHotelActivity::class.java)
        intent.putExtra("id", data)
        return intent
    }

    fun intentMaps(data : String): Intent {
        val intent = Intent(context, MapsActivity::class.java)
        intent.putExtra("alm", data)
        return intent
    }

    fun dataHotelIntent(): Intent {
        return Intent(context, DataHotelActivity::class.java)
    }

    fun bookingIntent(data1: String, data2: String): Intent {
        val intent = Intent(context, BookingActivity::class.java)
        intent.putExtra("data1", data1)
        intent.putExtra("data2", data2)
        return intent
    }

    fun intentWithFlags(destinationClass: Class<*>): Intent {
        val intent = Intent(context, destinationClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        return intent
    }

    fun intentSimpanHotel() : Intent {
        return Intent(intentWithFlags(DataHotelActivity::class.java))
    }

    fun intentLogout() : Intent {
        return Intent(context, LoginActivity::class.java)
    }

    fun startActivity(intent: Intent) {
        context.startActivity(intent)
    }
}
