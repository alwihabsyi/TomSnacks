package com.nesha.tomsnacks.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nesha.tomsnacks.model.Inventory
import com.nesha.tomsnacks.model.Member
import com.nesha.tomsnacks.model.SalesReport
import kotlinx.coroutines.flow.Flow

@Dao
interface TomSnackDao {

    // Inventory
    @Query("SELECT * FROM inventory")
    fun getAllInventory(): Flow<List<Inventory>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertInventory(inventory: Inventory)

    @Delete
    suspend fun deleteInventory(inventory: Inventory)

    // Member
    @Query("SELECT * FROM member")
    fun getAllMember(): Flow<List<Member>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMember(member: Member)

    @Delete
    suspend fun deleteMember(member: Member)

    // User
    @Query("SELECT COUNT(id) FROM User WHERE name = :name AND password = :password")
    suspend fun login(name: String, password: String): Flow<Int>

    // Sales Report
    @Query("SELECT * FROM SalesReport")
    fun getAllSales(): Flow<SalesReport>

    @Insert(onConflict =  OnConflictStrategy.ABORT)
    fun insertSales(salesReport: SalesReport)
}