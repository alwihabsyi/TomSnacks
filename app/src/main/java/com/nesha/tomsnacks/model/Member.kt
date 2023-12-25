package com.nesha.tomsnacks.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Member(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tanggalEntry: String,
    val nama: String,
    val alamat: String,
    val catatan: String
)
