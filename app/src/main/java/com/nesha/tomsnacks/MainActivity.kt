package com.nesha.tomsnacks

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.nesha.tomsnacks.databinding.ActivityMainBinding
import com.nesha.tomsnacks.ui.cashier.CashierActivity
import com.nesha.tomsnacks.ui.inventory.InventoryActivity
import com.nesha.tomsnacks.ui.members.MembersActivity
import com.nesha.tomsnacks.ui.report.ReportActivity
import com.nesha.tomsnacks.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        setActions()
    }

    private fun setActions() {
        with(binding) {
            ivCashier.setOnClickListener {
                startActivity(
                    Intent(this@MainActivity, CashierActivity::class.java)
                )
            }
            btnPerson.setOnClickListener {
                startActivity(
                    Intent(this@MainActivity, MembersActivity::class.java)
                )
            }
            ivDesk.setOnClickListener {
            }
            ivRak.setOnClickListener {
                startActivity(
                    Intent(
                        this@MainActivity, ReportActivity::class.java
                    )
                )
            }
            ivKardus.setOnClickListener {
                startActivity(
                    Intent(this@MainActivity, InventoryActivity::class.java)
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}