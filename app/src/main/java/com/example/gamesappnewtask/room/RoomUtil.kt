package com.example.gamesappnewtask.room

import android.content.Context
import androidx.room.Room
import com.example.gamesappnewtask.room.db.AppDatabase

class RoomUtil {
    companion object
    {
        fun getAppDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "date-database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}