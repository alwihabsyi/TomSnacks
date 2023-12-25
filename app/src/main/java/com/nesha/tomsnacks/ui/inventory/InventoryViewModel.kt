package com.nesha.tomsnacks.ui.inventory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nesha.tomsnacks.data.repository.TomSnackRepository
import com.nesha.tomsnacks.data.model.Inventory
import com.nesha.tomsnacks.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val repository: TomSnackRepository
): ViewModel() {
    private val _inventoryState = MutableStateFlow<List<Inventory>>(listOf())
    val inventoryState = _inventoryState.asStateFlow()

    init {
        getAllInventory()
    }

    private fun getAllInventory() {
        viewModelScope.launch {
            Log.d("TESINVENT", "MASUKKKK")
            repository.getAllInventory().collect {
                Log.d("TESINVENT", it.toString())
                _inventoryState.value = it
            }
        }
    }
}