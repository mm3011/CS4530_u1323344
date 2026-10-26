package com.example.assignment3

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/** ViewModel: exposes the stored course list and owns the currently selected course. */
class CourseViewModel(private val repository: CourseRepository) : ViewModel() {
    val courses: StateFlow<List<Course>> =
        repository.courses.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedId = MutableStateFlow<Int?>(null)
    val selectedId: StateFlow<Int?> = _selectedId.asStateFlow()

    fun addCourse(department: String, number: String, location: String) {
        viewModelScope.launch {
            repository.add(Course(department = department.trim(), number = number.trim(), location = location.trim()))
        }
    }

    fun updateCourse(id: Int, department: String, number: String, location: String) {
        viewModelScope.launch {
            repository.update(Course(id, department.trim(), number.trim(), location.trim()))
        }
    }

    fun deleteCourse(id: Int) {
        val course = courses.value.find { it.id == id } ?: return
        viewModelScope.launch { repository.delete(course) }
        if (_selectedId.value == id) _selectedId.value = null
    }

    fun select(id: Int?) {
        _selectedId.value = id
    }

    /** Factory: builds the ViewModel with the single app-wide repository instance. */
    class Factory(private val context: Context) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            CourseViewModel(RoomCourseRepository.getInstance(context)) as T
    }
}
