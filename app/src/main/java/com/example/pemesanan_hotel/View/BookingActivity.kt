package com.example.pemesanan_hotel.View

import HotelViewModel
import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pemesanan_hotel.Adapter.AdapterBooking
import com.example.pemesanan_hotel.Helper.AlertDialogHelper
import com.example.pemesanan_hotel.Helper.DatePickerHelper
import com.example.pemesanan_hotel.Helper.IntentHelper
import com.example.pemesanan_hotel.Model.BookingModel
import com.example.pemesanan_hotel.Model.GetHotel
import com.example.pemesanan_hotel.ViewModel.BookingViewModel
import com.example.pemesanan_hotel.databinding.ActivityBookingBinding

class BookingActivity : AppCompatActivity() {
    private lateinit var b: ActivityBookingBinding
    private lateinit var viewModel: HotelViewModel
    private lateinit var bookingVM: BookingViewModel
    private lateinit var datePickerHelper: DatePickerHelper
    private lateinit var alertDialogHelper: AlertDialogHelper
    private lateinit var intentHelper: IntentHelper

    private lateinit var adapter: AdapterBooking

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.setTitle("Booking Hotel")

        viewModel = ViewModelProvider(this).get(HotelViewModel::class.java)
        bookingVM = ViewModelProvider(this).get(BookingViewModel::class.java)
        alertDialogHelper = AlertDialogHelper(this)
        datePickerHelper = DatePickerHelper(this)
        intentHelper = IntentHelper(this)

        val hotelListObserver = Observer<List<GetHotel>> { hotelList ->
            val spinnerAdapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item,
                hotelList.map {
                    it.nama_hotel
                })
            b.spHotel.adapter = spinnerAdapter
        }
        viewModel.getHotelList().observe(this, hotelListObserver)

        adapter = AdapterBooking(ArrayList(), this)
        b.recyclerView.layoutManager = LinearLayoutManager(this)
        b.recyclerView.adapter = adapter

        b.btnDate.setOnClickListener {
            datePickerHelper.showDatePickerDialog(b.insTanggal)
        }

        b.btnSubmit.setOnClickListener {
            insert()
        }
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData() {
        bookingVM.loadData().observe(this, Observer<List<BookingModel>> { hotelList ->
            adapter.setData(hotelList)
        })
    }

    fun insert() {
        alertDialogHelper.showConfirmationDialog(
            "Konfirmasi!", "Apakah Anda ingin menyimpan data?", "Ya", "Tidak",
            { dialogInterface, i ->
                val data = BookingModel(
                    id_booking = "",
                    nama_hotel = b.spHotel.selectedItem.toString(),
                    nama_pelanggan = b.insNama.text.toString(),
                    hari = b.insHari.text.toString(),
                    tgl_pemakaian = b.insTanggal.text.toString(),
                    status_bayar = "",
                    bukti_bayar = ""
                )

                bookingVM.insertBooking(data)
                    .observe(this, Observer<Boolean> {
                        loadData()
                        b.insNama.setText("")
                        b.insHari.setText("")
                        b.insTanggal.setText("")
                        Toast.makeText(this, "Berhasil menambah data!", Toast.LENGTH_SHORT).show()
                    })
            },
            { dialogInterface, i ->
                Toast.makeText(this, "Berhasil Membatalkan!", Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun delete(id: String) {
        bookingVM.delete(id).observe(this, Observer<Boolean> { success ->
            if (success) {
                Toast.makeText(this, "Berhasil menghapus data!", Toast.LENGTH_SHORT).show()
                recreate()
            } else {
                Toast.makeText(this, "Gagal menghapus data!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}