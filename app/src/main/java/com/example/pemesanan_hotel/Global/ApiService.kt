package com.example.pemesanan_hotel.Global

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class ApiService(context: Context) {
    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun getRequest(url: String, listener: ResponseListener) {
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                listener.onResponse(response)
            },
            { error ->
                listener.onError(error.toString())
            }
        )
        requestQueue.add(request)
    }

    fun postRequest(url: String, requestBody: JSONObject, listener: ResponseListener) {
        val request = JsonObjectRequest(Request.Method.POST, url, requestBody,
            { response ->
                listener.onResponse(response)
            },
            { error ->
                listener.onError(error.toString())
            }
        )
        requestQueue.add(request)
    }

    fun putRequest(url: String, requestBody: JSONObject, listener: ResponseListener) {
        val request = JsonObjectRequest(Request.Method.PUT, url, requestBody,
            { response ->
                listener.onResponse(response)
            },
            { error ->
                listener.onError(error.toString())
            }
        )
        requestQueue.add(request)
    }

    fun deleteRequest(url: String, listener: ResponseListener) {
        val request = JsonObjectRequest(Request.Method.DELETE, url, null,
            { response ->
                listener.onResponse(response)
            },
            { error ->
                listener.onError(error.toString())
            }
        )
        requestQueue.add(request)
    }

    interface ResponseListener {
        fun onResponse(response: JSONObject)
        fun onError(error: String)
    }
}
