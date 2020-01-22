package com.example.myapplication

import androidx.room.*

@Entity(tableName = "speles_table", primaryKeys = arrayOf("laiks", "skatitaji", "vieta"))
data class SpelesEntity(
    var laiks: String,
    var skatitaji: Int,
    var vieta: String
)

@Dao
interface SpelesEntityDao{
    @Query("SELECT * from speles_table")
    fun getAllGames(): List<SpelesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(spele: SpelesEntity)

    @Query("SELECT * FROM speles_table WHERE laiks = :laiks AND skatitaji = :skatitaji AND vieta = :vieta")
    fun loudGame (laiks: String, skatitaji: Int, vieta: String): SpelesEntity
}