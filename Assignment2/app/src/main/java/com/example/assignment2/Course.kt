package com.example.assignment2

/** Model: a single course. [id] keeps courses distinct even if their fields match. */
data class Course(
    val id: Int,
    val department: String,
    val number: String,
    val location: String,
) {
    val name: String get() = "$department $number"
}
