package com.nesha.tomsnacks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nesha.tomsnacks.data.model.Inventory
import com.nesha.tomsnacks.data.model.Member
import com.nesha.tomsnacks.data.model.SalesReport
import com.nesha.tomsnacks.data.model.User

@Database(entities = [Inventory::class, Member::class, User::class, SalesReport::class], version = 1, exportSchema = false)
abstract class TomSnackDatabase: RoomDatabase() {
    abstract val tomSnackDao: TomSnackDao
}