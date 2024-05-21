package com.example.app.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteItem")
data class FavoriteItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo
    val price: Int,
    @ColumnInfo
    val thumbnail: String,
    @ColumnInfo
    val title: String
)
