package com.nesha.tomsnacks.ui.cashier

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import com.nesha.tomsnacks.databinding.ActivityCashierBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CashierActivity : AppCompatActivity() {

    private var _binding: ActivityCashierBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CashierViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCashierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startCamera()
        setupPage()
    }

    private fun setupPage() {
        val nota = viewModel.lastNota.asLiveData()
        nota.observe(this) {
            val notaText = "#Nota $it"
            binding.etNota.setText(notaText)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewBarcode.surfaceProvider)
                }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview
                )
            } catch (exc: Exception) {
                Toast.makeText(
                    this@CashierActivity,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("CameraActivity", "startCamera: ${exc.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}