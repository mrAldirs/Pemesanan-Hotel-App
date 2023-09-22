package com.example.pemesanan_hotel.View

import HotelViewModel
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pemesanan_hotel.Adapter.AdapterHotel
import com.example.pemesanan_hotel.R
import com.example.pemesanan_hotel.databinding.ActivityDataHotelBinding

class DataHotelActivity : AppCompatActivity() {
    private lateinit var b: ActivityDataHotelBinding
    private lateinit var hotelViewModel: HotelViewModel

    private lateinit var adapter: AdapterHotel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDataHotelBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.setTitle("Data Hotel")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        b.btnTambah.setOnClickListener {
            startActivity(Intent(this, HotelActivity::class.java))
        }

        hotelViewModel = ViewModelProvider(this).get(HotelViewModel::class.java)
        adapter = AdapterHotel(ArrayList(), this)

        b.recyclerView.layoutManager = LinearLayoutManager(this)
        b.recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        loadData("")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                loadData(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                loadData(newText)
                return true
            }
        })
        return true
    }

    private fun loadData(nama: String) {
        hotelViewModel.loadData(nama).observe(this, { hotelList ->
            adapter.setData(hotelList)
        })
    }

    fun delete(id: String) {
        hotelViewModel.delete(id).observe(this, { success ->
            if (success) {
                Toast.makeText(this, "Berhasil menghapus data!", Toast.LENGTH_SHORT).show()
                recreate()
            } else {
                Toast.makeText(this, "Gagal menghapus data!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}