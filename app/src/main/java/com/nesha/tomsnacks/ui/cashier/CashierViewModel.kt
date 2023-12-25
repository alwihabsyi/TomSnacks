package com.nesha.tomsnacks.ui.cashier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nesha.tomsnacks.data.repository.TomSnackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CashierViewModel @Inject constructor(
    val tomSnackRepository: TomSnackRepository
): ViewModel() {
    private val _lastNota = MutableStateFlow(0)
    val lastNota = _lastNota.asStateFlow()

    init {
        getLastNota()
    }

    private fun getLastNota() {
        viewModelScope.launch {
            tomSnackRepository.getLastSales().collect {
                _lastNota.value = it
            }
        }
    }
}