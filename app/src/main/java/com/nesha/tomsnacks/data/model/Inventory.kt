package com.nesha.tomsnacks.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Inventory(
    @PrimaryKey
    val kode: Int = 0,
    val namaItem: String? = null,
    val jenisPack: String? = null,
    val isi: Int? = 0,
    val stok: Int? = 0,
    val hargaBeli: Int? = null,
    val hargaJual: Int? = null,
    val tanggalExp: String? = null,
    val statusExp: String? = null
)