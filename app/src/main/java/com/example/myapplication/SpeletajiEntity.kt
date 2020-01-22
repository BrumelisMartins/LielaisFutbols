package com.example.myapplication

import androidx.room.*


@Entity(tableName = "speletajs_table", primaryKeys = arrayOf("nr", "uzvards", "vards"))
data class SpeletajsEntity(
    var nr: Int,
    var uzvards: String,
    var vards: String,
    var loma: String,
    var komanda: String,
    var varti: Int,
    var piespeles: Int,
    var sodi: Int,
    var tiesnesis: Boolean,
    var sodietoSkaits: Int,
    var gamesPlayed: Int,
    var ielaistieVarti: Int
)

@Dao
interface SpeletajsDao {

    @Query("SELECT * from speletajs_table")
    fun getAllPlayers(): List<SpeletajsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(speeltajs: SpeletajsEntity)

    @Query("SELECT * FROM speletajs_table WHERE nr = :nr AND uzvards = :uzvards AND vards = :vards")
    fun loadUser (nr: Int, uzvards: String, vards: String): SpeletajsEntity
}
