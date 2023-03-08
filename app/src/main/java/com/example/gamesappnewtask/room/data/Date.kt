package com.example.gamesappnewtask.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "date_table")
data class Date(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "date") val date: String?,
)
