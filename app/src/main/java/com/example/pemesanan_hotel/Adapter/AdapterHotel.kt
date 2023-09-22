package com.example.pemesanan_hotel.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pemesanan_hotel.View.DataHotelActivity
import com.example.pemesanan_hotel.Helper.AlertDialogHelper
import com.example.pemesanan_hotel.Helper.IntentHelper
import com.example.pemesanan_hotel.Model.HotelModel
import com.example.pemesanan_hotel.R
import com.squareup.picasso.Picasso

class AdapterHotel(private var dataList: List<HotelModel>, val parent: DataHotelActivity) :
    RecyclerView.Adapter<AdapterHotel.HolderDataAdapter>(){
    class HolderDataAdapter(v : View) : RecyclerView.ViewHolder(v) {
        val nm = v.findViewById<TextView>(R.id.itemNama)
        val kmr = v.findViewById<TextView>(R.id.itemKamar)
        val hrg = v.findViewById<TextView>(R.id.itemHarga)
        val alm = v.findViewById<TextView>(R.id.itemAlamat)
        val fas = v.findViewById<TextView>(R.id.itemFasilitas)
        val edt = v.findViewById<Button>(R.id.btn_edit)
        val hps = v.findViewById<Button>(R.id.btn_hapus)
        val lok = v.findViewById<Button>(R.id.btn_lokasi)
        val img = v.findViewById<ImageView>(R.id.itemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderDataAdapter {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_hotel,parent,false)
        return HolderDataAdapter(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: HolderDataAdapter, position: Int) {
        val item = dataList.get(position)
        holder.nm.setText(item.nama_hotel)
        holder.hrg.setText("Harga : Rp."+item.harga)
        holder.kmr.setText("Jumlah : "+item.kamar+" Kamar")
        holder.alm.setText("Alamat : "+item.alamat)
        holder.fas.setText("Fasilitas : "+item.fasilitas)
        Picasso.get().load(item.image).into(holder.img)

        holder.hps.setOnClickListener {
            val alertDialogHelper = AlertDialogHelper(parent)
            alertDialogHelper.showConfirmationDialog(
                "Konfirmasi!", "Apakah Anda ingin menghapus data?", "Ya", "Tidak",
                { dialogInterface, i ->
                    parent.delete(item.id_hotel)
                },
                { dialogInterface, i -> }
            )
        }

        val intentHelper = IntentHelper(parent)

        holder.edt.setOnClickListener {
            intentHelper.startActivity(intentHelper.intentEditHotel(item.id_hotel))
        }

        holder.lok.setOnClickListener {
            intentHelper.startActivity(intentHelper.intentMaps(item.alamat))
        }
    }

    fun setData(newHotelList: List<HotelModel>) {
        dataList = newHotelList
        notifyDataSetChanged()
    }
}