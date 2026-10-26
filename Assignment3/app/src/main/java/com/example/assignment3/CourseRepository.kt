package com.example.assignment3

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.flow.Flow

/** Repository: the layer between the ViewModel and storage. */
interface CourseRepository {
    val courses: Flow<List<Course>>
    suspend fun add(course: Course)
    suspend fun update(course: Course)
    suspend fun delete(course: Course)
}

/** Room-backed [CourseRepository]. Only one instance exists app-wide; get it with [getInstance]. */
class RoomCourseRepository private constructor(private val dao: CourseDao) : CourseRepository {
    override val courses: Flow<List<Course>> = dao.getAll()

    override suspend fun add(course: Course) = dao.insert(course)
    override suspend fun update(course: Course) = dao.update(course)
    override suspend fun delete(course: Course) = dao.delete(course)

    companion object {
        @Volatile
        private var instance: RoomCourseRepository? = null

        fun getInstance(context: Context): RoomCourseRepository =
            instance ?: synchronized(this) {
                instance ?: RoomCourseRepository(
                    Room.databaseBuilder(context.applicationContext, CourseDatabase::class.java, "courses.db")
                        .build()
                        .courseDao()
                ).also { instance = it }
            }
    }
}
