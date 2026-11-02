package com.example.assignment4

import android.app.Application
import androidx.room.Room
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/** Application: holds the app-wide singletons (Room database, Ktor client, repository). */
class FunFactApplication : Application() {
    val database: FunFactDatabase by lazy {
        Room.databaseBuilder(this, FunFactDatabase::class.java, "facts.db").build()
    }

    val client: HttpClient by lazy {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    val repository: FunFactRepository by lazy { FunFactRepository(database.funFactDao(), client) }
}
