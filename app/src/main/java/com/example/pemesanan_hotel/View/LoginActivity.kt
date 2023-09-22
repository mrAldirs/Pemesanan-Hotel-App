package com.example.pemesanan_hotel.View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pemesanan_hotel.Helper.SharedPreferencesHelper
import com.example.pemesanan_hotel.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var b: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.hide()

        val sharedPreferencesHelper = SharedPreferencesHelper(this)

        b.btnLogin.setOnClickListener {
            if (b.edtUsername.text.toString().equals("vidierizky") && b.edtPassword.text.toString().equals("12345")) {
                sharedPreferencesHelper.saveString("nama", "Vidie dan Rizky")

                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Berhasil Login!", Toast.LENGTH_SHORT).show()
            } else if (b.edtUsername.text.toString().equals("") || b.edtPassword.text.toString().equals("")) {
                Toast.makeText(this, "Username atau Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Username & Password Anda Salah!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}