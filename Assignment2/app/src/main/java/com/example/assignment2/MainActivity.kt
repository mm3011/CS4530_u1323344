package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(Modifier.fillMaxSize()) {
                    CourseScreen()
                }
            }
        }
    }
}

@Composable
fun CourseScreen(vm: CourseViewModel = viewModel()) {
    val courses by vm.courses.collectAsState()
    val selectedId by vm.selectedId.collectAsState()
    val selected = courses.find { it.id == selectedId }

    // Id of the course being edited, or null when the form adds a new course.
    var editingId by rememberSaveable { mutableStateOf<Int?>(null) }
    var department by rememberSaveable { mutableStateOf("") }
    var number by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }

    fun clearForm() {
        editingId = null
        department = ""
        number = ""
        location = ""
    }

    Column(
        Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(if (editingId == null) "Add Course" else "Edit Course", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(department, { department = it }, Modifier.fillMaxWidth(), label = { Text("Department") }, singleLine = true)
        OutlinedTextField(number, { number = it }, Modifier.fillMaxWidth(), label = { Text("Number") }, singleLine = true)
        OutlinedTextField(location, { location = it }, Modifier.fillMaxWidth(), label = { Text("Location") }, singleLine = true)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    val id = editingId
                    if (id == null) vm.addCourse(department, number, location)
                    else vm.updateCourse(id, department, number, location)
                    clearForm()
                },
                enabled = department.isNotBlank() && number.isNotBlank() && location.isNotBlank(),
            ) { Text("Save") }
            if (editingId != null) {
                OutlinedButton(onClick = ::clearForm) { Text("Cancel") }
            }
        }

        if (selected != null) {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Department: ${selected.department}")
                    Text("Number: ${selected.number}")
                    Text("Location: ${selected.location}")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = {
                            editingId = selected.id
                            department = selected.department
                            number = selected.number
                            location = selected.location
                        }) { Text("Edit") }
                        Button(onClick = {
                            if (editingId == selected.id) clearForm()
                            vm.deleteCourse(selected.id)
                        }) { Text("Delete") }
                        OutlinedButton(onClick = { vm.select(null) }) { Text("Close") }
                    }
                }
            }
        }

        Text("Courses", style = MaterialTheme.typography.titleLarge)
        LazyColumn(Modifier.weight(1f)) {
            items(courses, key = { it.id }) { course ->
                Text(
                    course.name,
                    Modifier
                        .fillMaxWidth()
                        .clickable { vm.select(course.id) }
                        .padding(vertical = 12.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
                HorizontalDivider()
            }
        }
    }
}
