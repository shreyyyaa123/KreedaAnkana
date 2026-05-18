package com.example.kreedaankana

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class MatchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val team1: String,
    val team2: String,
    val score: String,
    val timestamp: Long = System.currentTimeMillis()
)