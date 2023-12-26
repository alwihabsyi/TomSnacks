package com.nesha.tomsnacks.ui.cashier

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.nesha.tomsnacks.R
import com.nesha.tomsnacks.data.model.Inventory
import com.nesha.tomsnacks.data.model.Member
import com.nesha.tomsnacks.databinding.ActivityCashierBinding
import com.nesha.tomsnacks.ui.inventory.adapter.InventoryAdapter
import com.nesha.tomsnacks.utils.currencyFormat
import com.nesha.tomsnacks.utils.hide
import com.nesha.tomsnacks.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CashierActivity : AppCompatActivity() {

    private var _binding: ActivityCashierBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CashierViewModel>()
    private var membersList = mutableListOf<String>()
    private val inventoryAdapter by lazy { InventoryAdapter() }
    private val selectedItems = arrayListOf<Inventory>()
    private var totalPrice = 0
    private var notaInt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCashierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startCamera()
        setupPage()
        setupInventory()
        setSearchBar()

        binding.cvPayment.setOnClickListener {
            startActivity(
                Intent(this, DetailCashierActivity::class.java).also {
                    it.putExtra("items", selectedItems)
                }
            )
        }

        binding.ivProfile.setOnClickListener {
            finish()
        }
    }

    private fun setSearchBar() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query == "") {
                    setupInventory()
                }else {
                    val searchList = inventoryAdapter.differ.currentList.filter { inventory ->
                        inventory.namaItem?.lowercase() == query?.lowercase()
                    }
                    inventoryAdapter.differ.submitList(searchList)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun setupInventory() {
        binding.rvInventory.apply {
            adapter = inventoryAdapter
            layoutManager = LinearLayoutManager(this@CashierActivity)
        }

        val inventories = viewModel.inventoryState.asLiveData()
        inventories.observe(this) {
            inventoryAdapter.differ.submitList(it)
            inventoryAdapter.onClick = { inventory ->
                when {
                    selectedItems.isEmpty() -> {
                        binding.cvPayment.show()
                        selectedItems.add(inventory)
                        totalPrice += inventory.hargaJual!!
                        binding.tvDiscountPrice.text = totalPrice.currencyFormat()
                        binding.tvPrice.text = (totalPrice - 10000).currencyFormat()
                    }

                    selectedItems.contains(inventory) -> {
                        selectedItems.remove(inventory)
                        totalPrice -= inventory.hargaJual!!
                        binding.tvDiscountPrice.text = totalPrice.currencyFormat()
                        binding.tvPrice.text = (totalPrice - 10000).currencyFormat()
                        if (selectedItems.isEmpty()) {
                            binding.cvPayment.hide()
                        }
                    }

                    selectedItems.isNotEmpty() -> {
                        selectedItems.add(inventory)
                        totalPrice += inventory.hargaJual!!
                        binding.tvDiscountPrice.text = totalPrice.currencyFormat()
                        binding.tvPrice.text = (totalPrice - 10000).currencyFormat()
                    }
                }
            }
        }
    }

    private fun setupPage() {
        val nota = viewModel.lastNota.asLiveData()
        nota.observe(this) {
            notaInt = it
            val notaText = "#Nota $it"
            binding.etNota.setText(notaText)
        }

        val members = viewModel.members.asLiveData()
        val listMember = mutableListOf<Member>()
        members.observe(this) {
            it.forEach { member ->
                val memberString = "${member.id} | ${member.nama} - ${member.alamat}"
                membersList.add(memberString)
                listMember.add(member)
            }
        }

        val arrayAdapter = ArrayAdapter(this, R.layout.item_spinner, membersList)
        binding.acMembers.setAdapter(arrayAdapter)
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