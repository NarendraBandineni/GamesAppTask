package com.example.gamesappnewtask.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gamesappnewtask.room.data.Date

@Dao
interface DateDao {

    @Query("DELETE FROM date_table")
    fun deleteAll()

    @Insert
    fun insertDates(date: Date)
}