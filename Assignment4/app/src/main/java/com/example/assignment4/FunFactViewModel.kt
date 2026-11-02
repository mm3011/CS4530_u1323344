package com.example.assignment4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/** ViewModel: exposes the stored facts filtered by the current search query. */
class FunFactViewModel(private val repository: FunFactRepository) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    val facts: StateFlow<List<FunFact>> =
        combine(repository.facts, _query) { facts, query ->
            facts.filter { it.text.contains(query.trim(), ignoreCase = true) }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setQuery(query: String) {
        _query.value = query
    }

    fun fetchNewFact() {
        viewModelScope.launch {
            // Ignore network failures so the app doesn't crash when offline.
            runCatching { repository.fetchNewFact() }
        }
    }

    /** Factory: builds the ViewModel with the given repository. */
    class Factory(private val repository: FunFactRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            FunFactViewModel(repository) as T
    }
}
