package com.example.pemesanan_hotel.Model

data class BookingModel(
    val id_booking : String,
    val nama_hotel : String,
    val nama_pelanggan : String,
    val hari : String,
    val tgl_pemakaian : String,
    val status_bayar : String,
    val bukti_bayar : String
)
