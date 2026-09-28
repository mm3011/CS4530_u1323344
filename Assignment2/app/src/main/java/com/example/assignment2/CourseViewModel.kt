package com.example.assignment2

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/** ViewModel: owns the course list and the currently selected course. */
class CourseViewModel : ViewModel() {
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    private val _selectedId = MutableStateFlow<Int?>(null)
    val selectedId: StateFlow<Int?> = _selectedId.asStateFlow()

    private var nextId = 0

    fun addCourse(department: String, number: String, location: String) {
        _courses.update { it + Course(nextId++, department.trim(), number.trim(), location.trim()) }
    }

    fun updateCourse(id: Int, department: String, number: String, location: String) {
        _courses.update { list ->
            list.map {
                if (it.id == id) it.copy(department = department.trim(), number = number.trim(), location = location.trim())
                else it
            }
        }
    }

    fun deleteCourse(id: Int) {
        _courses.update { list -> list.filterNot { it.id == id } }
        if (_selectedId.value == id) _selectedId.value = null
    }

    fun select(id: Int?) {
        _selectedId.value = id
    }
}
