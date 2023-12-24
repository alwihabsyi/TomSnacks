package com.nesha.tomsnacks.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Inventory(
    @PrimaryKey
    val kode: Int,
    val jenisPack: String,
    val isi: Int,
    val stok: Int = 0,
    val hargaBeli: Int,
    val hargaJual: Int,
    val tanggalExp: Date,
    val statusExp: String? = null
)