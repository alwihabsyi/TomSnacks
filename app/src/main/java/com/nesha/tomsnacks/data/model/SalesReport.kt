package com.nesha.tomsnacks.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SalesReport(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val total: Int
)