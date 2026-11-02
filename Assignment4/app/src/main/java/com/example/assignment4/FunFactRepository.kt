package com.example.assignment4

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow

/** Repository: the only layer that talks to Room and Ktor. One instance is held by [FunFactApplication]. */
class FunFactRepository(private val dao: FunFactDao, private val client: HttpClient) {
    val facts: Flow<List<FunFact>> = dao.getAll()

    /** Fetches a random fact from the API and stores it in Room. */
    suspend fun fetchNewFact() {
        val fact: FunFact = client.get("https://uselessfacts.jsph.pl/api/v2/facts/random?language=en").body()
        dao.insert(fact)
    }
}
