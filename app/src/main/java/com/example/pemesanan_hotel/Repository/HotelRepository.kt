package com.example.pemesanan_hotel.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.pemesanan_hotel.Global.BaseUrl
import com.example.pemesanan_hotel.Model.GetHotel
import com.example.pemesanan_hotel.Model.HotelModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HotelRepository(context: Context) {
    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)
    lateinit var baseUrl: BaseUrl

    fun loadData(nama: String): LiveData<List<HotelModel>> {
        baseUrl = BaseUrl()

        val data = MutableLiveData<List<HotelModel>>()
        val request = object : StringRequest(
            Method.POST, baseUrl.show,
            Response.Listener { response ->
                val hotelList = mutableListOf<HotelModel>()
                val jsonArray = JSONArray(response)
                for (x in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(x)
                    val hotel = HotelModel(
                        jsonObject.getString("id_hotel"),
                        jsonObject.getString("nama_hotel"),
                        jsonObject.getString("kamar"),
                        jsonObject.getString("alamat"),
                        jsonObject.getString("fasilitas"),
                        jsonObject.getString("harga"),
                        jsonObject.getString("img"),
                        ""
                    )
                    hotelList.add(hotel)
                }
                data.value = hotelList
            },
            Response.ErrorListener { error ->

            }) {
            override fun getParams(): MutableMap<String, String>? {
                val hm = HashMap<String, String>()
                hm.put("mode", "show_data_hotel")
                hm.put("nama_hotel", nama)

                return hm
            }
        }
        requestQueue.add(request)
        return data
    }

    fun getHotelList() : MutableLiveData<List<GetHotel>> {
        baseUrl = BaseUrl()

        val hotelList = MutableLiveData<List<GetHotel>>()
        val request = object : StringRequest(
            Method.POST, baseUrl.show,
            Response.Listener { response ->
                val hotelData = mutableListOf<GetHotel>()
                val jsonArray = JSONArray(response)
                for (x in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(x)
                    val hotel = GetHotel(jsonObject.getString("nama_hotel"))
                    hotelData.add(hotel)
                }
                hotelList.value = hotelData
            },
            Response.ErrorListener { error ->

            }) {
            override fun getParams(): MutableMap<String, String>? {
                val hm = HashMap<String, String>()
                hm.put("mode", "get_hotel")
                return hm
            }
        }
        requestQueue.add(request)
        return hotelList
    }

    fun delete(id: String): LiveData<Boolean> {
        baseUrl = BaseUrl()
        val result = MutableLiveData<Boolean>()
        val request = object : StringRequest(
            Method.POST, baseUrl.hotel,
            Response.Listener { response ->
                val jsonObject = JSONObject(response)
                val respon = jsonObject.getString("respon")
                result.value = respon == "1"
            },
            Response.ErrorListener { error ->

            }) {
            override fun getParams(): MutableMap<String, String>? {
                val hm = HashMap<String, String>()
                hm.put("mode", "delete")
                hm.put("id_hotel", id)
                return hm
            }
        }
        requestQueue.add(request)
        return result
    }

    fun detail(id: String): LiveData<HotelModel> {
        baseUrl = BaseUrl()
        val data = MutableLiveData<HotelModel>()
        val request = object : StringRequest(
            Method.POST, baseUrl.show,
            Response.Listener { response ->
                val jsonObject = JSONObject(response)
                val nama = jsonObject.getString("nama_hotel")
                val kamar = jsonObject.getString("kamar")
                val alamat = jsonObject.getString("alamat")
                val fasilitas = jsonObject.getString("fasilitas")
                val harga = jsonObject.getString("harga")
                val img = jsonObject.getString("img")

                val hotel = HotelModel(id, nama, kamar, alamat, fasilitas, harga, img, "")
                data.value = hotel
            },
            Response.ErrorListener { error ->
                // Error handling
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val hm = HashMap<String, String>()
                hm.put("mode", "detail")
                hm.put("id_hotel", id)

                return hm
            }
        }
        requestQueue.add(request)
        return data
    }

    fun edit(hotel: HotelModel): LiveData<Boolean> {
        baseUrl = BaseUrl()

        val result = MutableLiveData<Boolean>()
        val request = object : StringRequest(
            Method.POST, baseUrl.hotel,
            Response.Listener { response ->
                val jsonObject = JSONObject(response)
                val respon = jsonObject.getString("respon")
                result.value = respon == "1"
            },
            Response.ErrorListener { error ->
                // Error handling
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val hm = HashMap<String, String>()
                val nmFile =
                    "IMG" + SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date()) + ".jpg"

                hm.put("mode", "edit")
                hm.put("id_hotel", hotel.id_hotel)
                hm.put("nama_hotel", hotel.nama_hotel)
                hm.put("kamar", hotel.kamar)
                hm.put("alamat", hotel.alamat)
                hm.put("fasilitas", hotel.fasilitas)
                hm.put("harga", hotel.harga)
                hm.put("image", hotel.image)
                hm.put("file", nmFile)

                return hm
            }
        }
        requestQueue.add(request)
        return result
    }

    fun insertHotel(hotel: HotelModel): LiveData<Boolean> {
        baseUrl = BaseUrl()

        val result = MutableLiveData<Boolean>()
        val request = object : StringRequest(
            Method.POST, baseUrl.hotel,
            Response.Listener { response ->
                val jsonObject = JSONObject(response)
                val respon = jsonObject.getString("respon")
                result.value = respon == "1"
            },
            Response.ErrorListener { error ->

            }) {
            override fun getParams(): MutableMap<String, String> {
                val hm = HashMap<String, String>()
                val nmFile =
                    "IMG" + SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date()) + ".jpg"

                hm.put("mode", "insert")
                hm.put("nama_hotel", hotel.nama_hotel)
                hm.put("kamar", hotel.kamar)
                hm.put("alamat", hotel.alamat)
                hm.put("fasilitas", hotel.fasilitas)
                hm.put("harga", hotel.harga)
                hm.put("image", hotel.image)
                hm.put("file", nmFile)

                return hm
            }
        }

        requestQueue.add(request)
        return result
    }
}