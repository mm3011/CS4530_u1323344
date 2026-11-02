package com.example.assignment4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(Modifier.fillMaxSize()) {
                    FunFactScreen()
                }
            }
        }
    }
}

@Composable
fun FunFactScreen(
    vm: FunFactViewModel = viewModel(
        factory = FunFactViewModel.Factory((LocalContext.current.applicationContext as FunFactApplication).repository)
    )
) {
    val facts by vm.facts.collectAsState()
    val query by vm.query.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Button(onClick = vm::fetchNewFact, Modifier.fillMaxWidth()) { Text("Fetch New Fact") }
        OutlinedTextField(query, vm::setQuery, Modifier.fillMaxWidth(), label = { Text("Search") }, singleLine = true)

        Text("Fun Facts", style = MaterialTheme.typography.titleLarge)
        LazyColumn(Modifier.weight(1f)) {
            items(facts, key = { it.id }) { fact ->
                Text(
                    fact.text,
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
                HorizontalDivider()
            }
        }
    }
}
