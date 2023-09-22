package com.example.pemesanan_hotel.View

import HotelViewModel
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apk_pn.Helper.MediaHelper
import com.example.pemesanan_hotel.Helper.AlertDialogHelper
import com.example.pemesanan_hotel.Helper.IntentHelper
import com.example.pemesanan_hotel.Model.HotelModel
import com.example.pemesanan_hotel.databinding.ActivityHotelBinding


class HotelActivity : AppCompatActivity() {
    private lateinit var b: ActivityHotelBinding
    private lateinit var viewModel: HotelViewModel
    private lateinit var intentHelper: IntentHelper
    private lateinit var alertDialogHelper: AlertDialogHelper

    lateinit var mediaHealper: MediaHelper
    var imStr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityHotelBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.setTitle("Input Data Hotel")

        viewModel = ViewModelProvider(this).get(HotelViewModel::class.java)
        mediaHealper = MediaHelper(this)
        intentHelper = IntentHelper(this)
        alertDialogHelper = AlertDialogHelper(this)

        b.btnChoose.setOnClickListener {
            val intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(intent, mediaHealper.RcGallery())
        }

        b.btnSimpan.setOnClickListener {
            alertDialogHelper.showConfirmationDialog(
                "Konfirmasi!", "Apakah Anda ingin menyimpan data?", "Ya", "Tidak",
                { dialogInterface, i ->
                    val hotelModel = HotelModel(
                        id_hotel = "",
                        nama_hotel = b.insNama.text.toString(),
                        kamar = b.insKamar.text.toString(),
                        alamat = b.insAlamat.text.toString(),
                        fasilitas = b.insFasilitas.text.toString(),
                        harga = b.insHarga.text.toString(),
                        image = imStr,
                        file = ""
                    )

                    viewModel.insertHotel(hotelModel)
                        .observe(this, Observer<Boolean> { isSuccess ->
                            intentHelper.startActivity(intentHelper.intentSimpanHotel())
                            Toast.makeText(this, "Berhasil menambah data!", Toast.LENGTH_SHORT)
                                .show()
                        })
                },
                { dialogInterface, i ->
                    Toast.makeText(this, "Berhasil Membatalkan!", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == mediaHealper.RcGallery()){
                imStr = mediaHealper.getBitmapToString(data!!.data,b.insImg)
            }
        }
    }
}