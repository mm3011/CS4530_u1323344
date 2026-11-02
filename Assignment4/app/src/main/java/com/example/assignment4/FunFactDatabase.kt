package com.example.assignment4

import androidx.room.Database
import androidx.room.RoomDatabase

/** Room database holding the facts table. Built once by [FunFactApplication]. */
@Database(entities = [FunFact::class], version = 1)
abstract class FunFactDatabase : RoomDatabase() {
    abstract fun funFactDao(): FunFactDao
}
