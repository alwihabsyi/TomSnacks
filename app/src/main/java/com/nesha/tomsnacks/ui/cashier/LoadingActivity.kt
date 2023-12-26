package com.nesha.tomsnacks.ui.cashier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nesha.tomsnacks.MainActivity
import com.nesha.tomsnacks.R
import com.nesha.tomsnacks.databinding.ActivityLoadingBinding

class LoadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.animate().apply {
            duration = 5000
            alpha(1f)
        }.withEndAction {
            startActivity(
                Intent(this@LoadingActivity, MainActivity::class.java).also {
                    it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
        }
    }
}