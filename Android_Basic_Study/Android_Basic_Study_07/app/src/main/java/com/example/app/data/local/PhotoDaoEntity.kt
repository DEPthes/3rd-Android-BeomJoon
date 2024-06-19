package com.example.app.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarkPhoto")
data class PhotoDaoEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo
    val thumb: String,
    @ColumnInfo
    val tag: List<String>,
    @ColumnInfo
    val country: String,
    @ColumnInfo
    val city: String,
    @ColumnInfo
    val likes: Int,
    @ColumnInfo
    val downloads: Int,
)