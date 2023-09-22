package com.example.pemesanan_hotel.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.pemesanan_hotel.Model.BookingModel
import com.example.pemesanan_hotel.Repository.BookingRepository

class BookingViewModel : AndroidViewModel {

    private val bookingRepository : BookingRepository

    constructor(application: Application) : super(application) {
        bookingRepository = BookingRepository(application)
    }

    fun loadData(): LiveData<List<BookingModel>> {
        return bookingRepository.loadData()
    }

    fun insertBooking(booking: BookingModel): LiveData<Boolean> {
        return bookingRepository.insert(booking)
    }

    fun delete(id: String): LiveData<Boolean> {
        return bookingRepository.delete(id)
    }
    fun bayar(booking: BookingModel): LiveData<Boolean> {
        return bookingRepository.bayar(booking)
    }
}