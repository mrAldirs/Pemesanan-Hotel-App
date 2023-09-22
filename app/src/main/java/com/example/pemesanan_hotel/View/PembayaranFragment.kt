package com.example.pemesanan_hotel.View

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.pemesanan_hotel.Helper.AlertDialogHelper
import com.example.pemesanan_hotel.Model.BookingModel
import com.example.pemesanan_hotel.PhotoHelper
import com.example.pemesanan_hotel.ViewModel.BookingViewModel
import com.example.pemesanan_hotel.databinding.FragmentPembayaranBinding
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import org.json.JSONObject
import java.util.HashMap

class PembayaranFragment : DialogFragment() {
    private lateinit var b: FragmentPembayaranBinding
    lateinit var v: View
    lateinit var parent: BookingActivity
    private lateinit var bookingVM: BookingViewModel

    lateinit var photoHelper: PhotoHelper
    var imStr = ""
    var namaFile = ""
    var fileUri = Uri.parse("")

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentPembayaranBinding.inflate(layoutInflater)
        v = b.root
        parent = activity as BookingActivity

        photoHelper = PhotoHelper()
        bookingVM = ViewModelProvider(this).get(BookingViewModel::class.java)

        try {
            val m = StrictMode::class.java.getMethod("disableDeathOnFileUriExposure")
            m.invoke(null)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        b.btnChoose.setOnClickListener {
            requestPermission()
        }

        b.btnKirim.setOnClickListener {
            val alert = AlertDialogHelper(v.context)
            alert.showConfirmationDialog(
                "Konfirmasi!", "Apakah Anda ingin melakukan pembayaran?", "Ya", "Tidak",
                { dialogInterface, i ->
                    bayar()
                },
                { dialogInterface, i ->
                    Toast.makeText(v.context, "Berhasil Membatalkan!", Toast.LENGTH_SHORT).show()
                }
            )
        }

        return v
    }

    fun requestPermission() = runWithPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA ) {
        fileUri = photoHelper.getOutputMediaFileUri()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
        startActivityForResult(intent, photoHelper.getRcCamera())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            photoHelper.getRcCamera() -> {
                when (resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        imStr = photoHelper.getBitMapToString(b.insFoto, fileUri)
                        namaFile = photoHelper.getMyFileName()
                        Toast.makeText(v.context, "Berhasil upload foto", Toast.LENGTH_SHORT).show()
                    }

                    AppCompatActivity.RESULT_CANCELED -> {
                        // kode untuk kondisi kedua jika dibatalkan
                    }
                }
            }
        }
    }

    fun bayar() {
        val data = BookingModel(
            id_booking = arguments?.get("id").toString(),
            nama_hotel = "",
            nama_pelanggan = "",
            hari = "",
            tgl_pemakaian = "",
            status_bayar = "",
            bukti_bayar = imStr
        )

        bookingVM.bayar(data)
            .observe(parent, Observer<Boolean> { succes ->
                dismiss()
                parent.loadData()
            })
    }
}