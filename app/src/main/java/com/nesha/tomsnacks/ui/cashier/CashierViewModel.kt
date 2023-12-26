package com.nesha.tomsnacks.ui.cashier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nesha.tomsnacks.data.model.Inventory
import com.nesha.tomsnacks.data.model.Member
import com.nesha.tomsnacks.data.model.SalesReport
import com.nesha.tomsnacks.data.repository.TomSnackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CashierViewModel @Inject constructor(
    val tomSnackRepository: TomSnackRepository
): ViewModel() {
    private val _inventoryState = MutableStateFlow<List<Inventory>>(listOf())
    val inventoryState = _inventoryState.asStateFlow()

    private val _lastNota = MutableStateFlow(0)
    val lastNota = _lastNota.asStateFlow()

    private val _members = MutableStateFlow<List<Member>>(listOf())
    val members = _members.asStateFlow()

    init {
        getLastNota()
        getAllMembers()
        getAllInventory()
    }

    private fun getAllMembers() {
        viewModelScope.launch {
            tomSnackRepository.getAllMembers().collect {
                _members.value = it
            }
        }
    }

    private fun getLastNota() {
        viewModelScope.launch {
            tomSnackRepository.getLastSales().collect {
                _lastNota.value = it
            }
        }
    }

    private fun getAllInventory() {
        viewModelScope.launch {
            tomSnackRepository.getAllInventory().collect {
                _inventoryState.value = it
            }
        }
    }

    fun reportSales(salesReport: SalesReport) {
        viewModelScope.launch {
            tomSnackRepository.insertSales(salesReport)
        }
    }
}