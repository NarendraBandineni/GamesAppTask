package com.example.gamesappnewtask.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gamesappnewtask.room.dao.DateDao
import com.example.gamesappnewtask.room.data.Date

@Database(entities = [Date::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun dateDao(): DateDao
}