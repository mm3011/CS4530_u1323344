package com.example.assignment4

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/** DAO: Room queries for the facts table. [getAll] re-emits whenever the table changes. */
@Dao
interface FunFactDao {
    @Query("SELECT * FROM facts ORDER BY id DESC")
    fun getAll(): Flow<List<FunFact>>

    @Insert
    suspend fun insert(fact: FunFact)
}
