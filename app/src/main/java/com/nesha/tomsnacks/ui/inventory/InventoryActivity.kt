package com.nesha.tomsnacks.ui.inventory

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.nesha.tomsnacks.databinding.ActivityInventoryBinding
import com.nesha.tomsnacks.ui.inventory.adapter.InventoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InventoryActivity : AppCompatActivity() {

    private var _binding: ActivityInventoryBinding? = null
    private val binding get() = _binding!!
    private val inventoryAdapter by lazy { InventoryAdapter() }
    private val viewModel by viewModels<InventoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityInventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        observer()
    }

    private fun initRecyclerView() {
        binding.rvInventory.apply {
            adapter = inventoryAdapter
            layoutManager = LinearLayoutManager(this@InventoryActivity)
        }
    }

    private fun observer() {
        val inventories = viewModel.inventoryState.asLiveData()
        inventories.observe(this) {
            inventoryAdapter.differ.submitList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}