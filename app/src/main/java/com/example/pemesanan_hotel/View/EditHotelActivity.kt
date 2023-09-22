package com.example.pemesanan_hotel.View

import HotelViewModel
import android.R
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apk_pn.Helper.MediaHelper
import com.example.pemesanan_hotel.Model.HotelModel
import com.example.pemesanan_hotel.databinding.ActivityHotelBinding
import com.squareup.picasso.Picasso

class EditHotelActivity : AppCompatActivity() {
    private lateinit var b: ActivityHotelBinding
    private lateinit var viewModel: HotelViewModel

    lateinit var mediaHealper: MediaHelper
    var imStr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityHotelBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.setTitle("Edit Data Oli")

        viewModel = ViewModelProvider(this).get(HotelViewModel::class.java)
        mediaHealper = MediaHelper(this)

        showDetail()

        b.btnChoose.setOnClickListener {
            val intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(intent, mediaHealper.RcGallery())
        }

        b.btnSimpan.setOnClickListener {
            AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_input_get)
                .setTitle("Konfirmasi!")
                .setMessage("Apakah Anda ingin mengedit data ini?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface, i ->
                    edit()
                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface, i ->
                    Toast.makeText(this,"Berhasil Membatalkan!", Toast.LENGTH_SHORT).show()
                })
                .show()
            true
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

    private fun showDetail() {
        var paket : Bundle? = intent.extras
        var id = paket?.getString("id").toString()
        viewModel.getDetail(id).observe(this, Observer<HotelModel> { hotel ->
            b.insNama.setText(hotel.nama_hotel)
            b.insKamar.setText(hotel.kamar)
            b.insAlamat.setText(hotel.alamat)
            b.insFasilitas.setText(hotel.fasilitas)
            b.insHarga.setText(hotel.harga)
            Picasso.get().load(hotel.image).into(b.insImg)
        })
    }

    private fun edit() {
        var paket : Bundle? = intent.extras
        val hotelModel = HotelModel(
            id_hotel = paket?.getString("id").toString(),
            nama_hotel = b.insNama.text.toString(),
            kamar = b.insKamar.text.toString(),
            alamat = b.insAlamat.text.toString(),
            fasilitas = b.insFasilitas.text.toString(),
            harga = b.insHarga.text.toString(),
            image = imStr,
            file = ""
        )

        viewModel.editHotel(hotelModel)
            .observe(this, Observer<Boolean> { success ->
                if (success) {
                    val intent = Intent(this, DataHotelActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    startActivity(intent)
                    Toast.makeText(this, "Berhasil mengedit data!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Gagal mengedit data!", Toast.LENGTH_SHORT).show()
                }
            })
    }
}