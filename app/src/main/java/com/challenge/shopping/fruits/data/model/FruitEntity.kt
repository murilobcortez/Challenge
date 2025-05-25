package com.challenge.shopping.fruits.data.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FruitEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val image: Bitmap?,
    val family: String,
    val order: String,
    val genus: String,
    val price: String
)
