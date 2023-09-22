package com.example.pemesanan_hotel.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.pemesanan_hotel.Global.BaseUrl
import com.example.pemesanan_hotel.Model.BookingModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BookingRepository(context: Context) {
    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)
    lateinit var baseUrl: BaseUrl

    fun loadData(): LiveData<List<BookingModel>> {
        baseUrl = BaseUrl()

        val data = MutableLiveData<List<BookingModel>>()
        val request = object : StringRequest(
            Method.POST, baseUrl.show,
            Response.Listener { response ->
                val dataList = mutableListOf<BookingModel>()
                val jsonArray = JSONArray(response)
                for (x in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(x)
                    val item = BookingModel(
                        jsonObject.getString("id_booking"),
                        jsonObject.getString("nama_hotel"),
                        jsonObject.getString("nama_pelanggan"),
                        jsonObject.getString("hari"),
                        jsonObject.getString("tgl_pemakaian"),
                        jsonObject.getString("status_bayar"),
                        jsonObject.getString("img")
                    )
                    dataList.add(item)
                }
                data.value = dataList
            },
            Response.ErrorListener { error ->

            }) {
            override fun getParams(): MutableMap<String, String>? {
                val hm = HashMap<String, String>()
                hm.put("mode", "show_data_booking")

                return hm
            }
        }
        requestQueue.add(request)
        return data
    }

    fun insert(booking: BookingModel): LiveData<Boolean> {
        baseUrl = BaseUrl()

        val result = MutableLiveData<Boolean>()
        val request = object : StringRequest(
            Method.POST, baseUrl.booking,
            Response.Listener { response ->
                val jsonObject = JSONObject(response)
                val respon = jsonObject.getString("respon")
                result.value = respon == "1"
            },
            Response.ErrorListener { error ->

            }) {
            override fun getParams(): MutableMap<String, String> {
                val hm = HashMap<String, String>()
                hm.put("mode", "insert")
                hm.put("nama_hotel", booking.nama_hotel)
                hm.put("nama_pelanggan", booking.nama_pelanggan)
                hm.put("hari", booking.hari)
                hm.put("tgl_pemakaian", booking.tgl_pemakaian)

                return hm
            }
        }

        requestQueue.add(request)
        return result
    }

    fun bayar(booking: BookingModel): LiveData<Boolean> {
        baseUrl = BaseUrl()

        val result = MutableLiveData<Boolean>()
        val request = object : StringRequest(
            Method.POST, baseUrl.booking,
            Response.Listener { response ->
                val jsonObject = JSONObject(response)
                val respon = jsonObject.getString("respon")
                result.value = respon == "1"
            },
            Response.ErrorListener { error ->

            }) {
            override fun getParams(): MutableMap<String, String>? {
                val hm = HashMap<String, String>()
                val nmFile =
                    "IMG" + SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date()) + ".jpg"
                hm.put("mode", "bayar")
                hm.put("id_booking", booking.id_booking)
                hm.put("image", booking.bukti_bayar)
                hm.put("file", nmFile)

                return hm
            }
        }
        requestQueue.add(request)
        return result
    }

    fun delete(id: String): LiveData<Boolean> {
        baseUrl = BaseUrl()
        val result = MutableLiveData<Boolean>()
        val request = object : StringRequest(
            Method.POST, baseUrl.booking,
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
                hm.put("id_booking", id)
                return hm
            }
        }
        requestQueue.add(request)
        return result
    }
}