package com.example.myapplication

import androidx.room.*

@Entity(tableName = "komandas_table")
data class KomandasEntity(
    @PrimaryKey
    var nosaukums: String,
    var punkti: Int,
    var uzvarasPamatl: Int,
    var zaudesPamatl: Int,
    var uzvarasPapild: Int,
    var zaudesPapild: Int,
    var iegutieVarti: Int,
    var zaudetieVarti: Int
)

@Dao
interface KomandasEntityDao{
    @Query("SELECT * from komandas_table")
    fun getAllGames(): List<KomandasEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(spele: KomandasEntity)

    @Query("SELECT * FROM komandas_table WHERE nosaukums = :nosaukums")
    fun loudTeam (nosaukums: String): KomandasEntity
}
