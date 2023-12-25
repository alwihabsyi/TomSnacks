package com.nesha.tomsnacks.data.repository

import com.nesha.tomsnacks.data.local.TomSnackDao
import com.nesha.tomsnacks.model.Inventory
import com.nesha.tomsnacks.model.Member
import com.nesha.tomsnacks.model.SalesReport
import com.nesha.tomsnacks.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch

class InventoryRepository(
    private val tomSnackDao: TomSnackDao
) {
    private val inventories = MutableStateFlow<UiState<List<Inventory>>>(UiState.Loading())
    private val members = MutableStateFlow<UiState<List<Member>>>(UiState.Loading())
    private val loginState = MutableStateFlow<UiState<String>>(UiState.Loading())
    private val salesReport = MutableStateFlow<UiState<List<SalesReport>>>(UiState.Loading())

    suspend fun getAllInventory(): Flow<UiState<List<Inventory>>> {
        tomSnackDao.getAllInventory().catch {
            inventories.value = UiState.Error(it.localizedMessage ?: "Terjadi kesalahan")
        }.collect {
            inventories.value = UiState.Success(it)
        }
        return inventories.asStateFlow()
    }

    suspend fun insertInventory(inventory: Inventory) {
        tomSnackDao.insertInventory(inventory)
    }

    suspend fun deleteInventory(inventory: Inventory) {
        tomSnackDao.deleteInventory(inventory)
    }

    suspend fun getAllMembers(): Flow<UiState<List<Member>>> {
        tomSnackDao.getAllMember().catch {
            members.value = UiState.Error(it.localizedMessage ?: "Terjadi Kesalahan")
        }.collect {
            members.value = UiState.Success(it)
        }

        return members
    }

    suspend fun insertMember(member: Member) {
        tomSnackDao.insertMember(member)
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

    suspend fun getAllSales(): Flow<UiState<List<SalesReport>>> {
        tomSnackDao.getAllSales().catch {
            salesReport.value = UiState.Error(it.localizedMessage ?: "Terjadi Kesalahan")
        }.collect {
            salesReport.value = UiState.Success(it)
        }

        return salesReport.asStateFlow()
    }
}