package com.nesha.tomsnacks.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class SalesReport(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Date = Date(),
    val total: Int
)