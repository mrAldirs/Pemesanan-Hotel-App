package com.example.pemesanan_hotel.Adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pemesanan_hotel.View.BookingActivity
import com.example.pemesanan_hotel.Helper.AlertDialogHelper
import com.example.pemesanan_hotel.Model.BookingModel
import com.example.pemesanan_hotel.R
import com.example.pemesanan_hotel.View.PembayaranFragment
import com.squareup.picasso.Picasso

class AdapterBooking(private var dataList: List<BookingModel>, val parent: BookingActivity) :
    RecyclerView.Adapter<AdapterBooking.HolderDataAdapter>(){
    class HolderDataAdapter(v : View) : RecyclerView.ViewHolder(v) {
        val nm = v.findViewById<TextView>(R.id.itemNama)
        val htl = v.findViewById<TextView>(R.id.itemHotel)
        val tgl = v.findViewById<TextView>(R.id.itemTanggal)
        val sts = v.findViewById<TextView>(R.id.itemStatus)
        val byr = v.findViewById<Button>(R.id.btn_bayar)
        val hps = v.findViewById<Button>(R.id.btn_hapus)
        val img = v.findViewById<ImageView>(R.id.itemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderDataAdapter {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_booking,parent,false)
        return HolderDataAdapter(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: HolderDataAdapter, position: Int) {
        val item = dataList.get(position)
        holder.nm.setText(item.nama_pelanggan)
        holder.htl.setText("Hotel : "+item.nama_hotel)
        holder.tgl.setText("Tanggal Pemakaian : "+item.tgl_pemakaian)
        holder.sts.setText(item.status_bayar)
        Picasso.get().load(item.bukti_bayar).into(holder.img)

        holder.hps.setOnClickListener {
            val alertDialogHelper = AlertDialogHelper(parent)
            alertDialogHelper.showConfirmationDialog(
                "Konfirmasi!", "Apakah Anda ingin menghapus data?", "Ya", "Tidak",
                { dialogInterface, i ->
                    parent.delete(item.id_booking)
                },
                { dialogInterface, i -> }
            )
        }

        holder.byr.setOnClickListener {
            val dialog = PembayaranFragment()

            val bundle = Bundle()
            bundle.putString("id", item.id_booking)
            dialog.arguments = bundle

            dialog.show(parent.supportFragmentManager, "PembayaranFragment")
        }
    }

    fun setData(newDataList: List<BookingModel>) {
        dataList = newDataList
        notifyDataSetChanged()
    }
}