package com.example.pemesanan_hotel.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pemesanan_hotel.Helper.AlertDialogHelper
import com.example.pemesanan_hotel.Helper.IntentHelper
import com.example.pemesanan_hotel.Helper.SharedPreferencesHelper
import com.example.pemesanan_hotel.databinding.FragmentProfilBinding

class ProfilFragment : Fragment() {
    private lateinit var b: FragmentProfilBinding
    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentProfilBinding.inflate(layoutInflater)
        v = b.root

        val alertDialogHelper = AlertDialogHelper(v.context)
        val intent = IntentHelper(v.context)
        val sharedPreferencesHelper = SharedPreferencesHelper(v.context)
        b.btnLogout.setOnClickListener {
            alertDialogHelper.showConfirmationDialog(
                "Peringatan!","Apakah Anda Ingin Keluar Aplikasi?","Ya","Tidak",
                { dialogInterface, i ->
                    sharedPreferencesHelper.remove("nama")
                    intent.intentLogout()
                    requireActivity().finishAffinity()
                },
                { dialogInterface, i ->

                }
            )
        }

        return v
    }
}