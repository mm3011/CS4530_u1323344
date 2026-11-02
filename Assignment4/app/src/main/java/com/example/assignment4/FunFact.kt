package com.example.assignment4

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/** Model: a single fun fact, used both to decode the API response and as a row in the facts table. */
@Serializable
@Entity(tableName = "facts")
data class FunFact(
    @Transient @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
)
