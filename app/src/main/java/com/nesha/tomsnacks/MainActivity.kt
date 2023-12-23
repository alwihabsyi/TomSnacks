package com.nesha.tomsnacks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nesha.tomsnacks.databinding.ActivityMainBinding
import com.nesha.tomsnacks.utils.toast

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActions()
    }

    private fun setActions() {
        with(binding) {
            ivCashier.setOnClickListener {
                toast("Ini kasir")
            }
            btnPerson.setOnClickListener {
                toast("Ini orang")
            }
            ivDesk.setOnClickListener {
                toast("Ini meja")
            }
            ivRak.setOnClickListener {
                toast("Ini rak")
            }
            ivKardus.setOnClickListener {
                toast("Ini kardus")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}