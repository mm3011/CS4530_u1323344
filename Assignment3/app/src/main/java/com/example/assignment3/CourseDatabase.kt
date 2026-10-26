package com.example.assignment3

import androidx.room.Database
import androidx.room.RoomDatabase

/** Room database holding the courses table. Built once by [RoomCourseRepository.getInstance]. */
@Database(entities = [Course::class], version = 1)
abstract class CourseDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}
