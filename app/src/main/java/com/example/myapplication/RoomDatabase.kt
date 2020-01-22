package com.example.myapplication


import androidx.room.Database
import androidx.room.RoomDatabase

@Database (entities = [(SpeletajsEntity::class)], version = 1)
abstract class SpeletajsDb : RoomDatabase(){
    abstract fun speletajsDao(): SpeletajsDao
}

@Database(entities = [(SpelesEntity::class)], version = 1)
abstract class SpelesDb : RoomDatabase() {
    abstract fun spelesDao(): SpelesEntityDao
}

@Database(entities = [(KomandasEntity::class)], version = 1)
abstract class KomandasDb : RoomDatabase() {
    abstract fun komandasDao(): KomandasEntityDao
}