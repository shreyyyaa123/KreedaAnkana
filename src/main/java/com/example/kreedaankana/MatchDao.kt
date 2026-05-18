package com.example.kreedaankana

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {
    @Insert
    suspend fun insertMatch(match: MatchEntity)

    @Query("SELECT * FROM matches ORDER BY timestamp DESC")
    fun getAllMatches(): Flow<List<MatchEntity>>
}