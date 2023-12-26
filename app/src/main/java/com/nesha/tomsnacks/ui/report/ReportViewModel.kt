package com.nesha.tomsnacks.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nesha.tomsnacks.data.model.SalesReport
import com.nesha.tomsnacks.data.repository.TomSnackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val tomSnackRepository: TomSnackRepository
): ViewModel() {
    private val _allReports = MutableStateFlow<List<SalesReport>>(listOf())
    val allReports = _allReports.asStateFlow()

    init {
        getAllReports()
    }

    private fun getAllReports() {
        viewModelScope.launch {
            tomSnackRepository.getAllSales().collect {
                _allReports.value = it
            }
        }
    }

}