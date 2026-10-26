package com.example.assignment3

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/** DAO: Room queries for the courses table. [getAll] re-emits whenever the table changes. */
@Dao
interface CourseDao {
    @Query("SELECT * FROM courses")
    fun getAll(): Flow<List<Course>>

    @Insert
    suspend fun insert(course: Course)

    @Update
    suspend fun update(course: Course)

    @Delete
    suspend fun delete(course: Course)
}
