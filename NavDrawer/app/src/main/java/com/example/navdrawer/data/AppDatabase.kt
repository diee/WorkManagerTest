package com.example.navdrawer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val DATABASE_NAME = "test_db"

@Database(entities = [Person::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun get(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }

    abstract fun personDao(): PersonDao
}