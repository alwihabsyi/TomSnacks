package com.nesha.tomsnacks.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.nesha.tomsnacks.MainActivity
import com.nesha.tomsnacks.databinding.ActivityLoginBinding
import com.nesha.tomsnacks.utils.toast

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            when {
                username.isEmpty() && password.isEmpty() -> {
                    toast("Username dan password tidak boleh kosong")
                }

                username != "admin" && password != "admin" -> {
                    toast("Username atau password salah")
                }

                username == "admin" && password == "admin" -> {
                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )
                    finish()
                }
            }
        }
    }
}