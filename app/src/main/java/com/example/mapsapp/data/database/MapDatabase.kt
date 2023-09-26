package com.example.mapsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mapsapp.data.database.dao.PointDao
import com.example.mapsapp.data.database.model.PointModel

@Database(entities = [PointModel::class], version = 1, exportSchema = false)
abstract class MapDatabase : RoomDatabase(){

    companion object {

        const val DATABASE_NAME = "map_db"
    }

    abstract fun pointDao(): PointDao

}