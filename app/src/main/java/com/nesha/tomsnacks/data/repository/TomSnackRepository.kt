package com.nesha.tomsnacks.data.repository

import com.nesha.tomsnacks.data.local.TomSnackDao
import com.nesha.tomsnacks.data.model.Inventory
import com.nesha.tomsnacks.data.model.Member
import com.nesha.tomsnacks.data.model.SalesReport
import com.nesha.tomsnacks.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf

class TomSnackRepository(
    private val tomSnackDao: TomSnackDao
) {
    private val loginState = MutableStateFlow<UiState<String>>(UiState.Loading())
    private val salesReport = MutableStateFlow<UiState<List<SalesReport>>>(UiState.Loading())

    fun getAllInventory(): Flow<List<Inventory>> {
        return tomSnackDao.getAllInventory()
    }

    suspend fun insertInventory(inventory: Inventory) {
        tomSnackDao.insertInventory(inventory)
    }

    suspend fun deleteInventory(inventory: Inventory) {
        tomSnackDao.deleteInventory(inventory)
    }

    fun getAllMembers(): Flow<List<Member>> {
        return tomSnackDao.getAllMember()
    }

    suspend fun insertMember(member: Member) {
        tomSnackDao.insertMember(member)
    }

    suspend fun insertSales(salesReport: SalesReport) {
        tomSnackDao.insertSales(salesReport)
    }

    suspend fun deleteMember(member: Member) {
        tomSnackDao.deleteMember(member)
    }

    suspend fun login(name: String, password: String): Flow<UiState<String>> {
        tomSnackDao.login(name, password).catch {
            loginState.value = UiState.Error(it.localizedMessage ?: "Terjadi Kesalahan")
        }.collect {
            if (it != 1) {
                loginState.value = UiState.Error("Username atau password salah")
            } else {
                loginState.value = UiState.Success("Berhasil masuk")
            }
        }

        return loginState.asStateFlow()
    }

    fun getAllSales(): Flow<List<SalesReport>> {
        return tomSnackDao.getAllSales()
    }

    suspend fun getLastSales(): Flow<Int> {
        val sales = tomSnackDao.getLastSaleId()
        val nota = if (sales == null) 0 else sales.id + 1
        return flowOf(nota)
    }

    suspend fun getLastMemberId(): Flow<Int> {
        val member = tomSnackDao.getLastMemberId()
        val id = if (member == null) 1 else member.id + 1
        return flowOf(id)
    }
}