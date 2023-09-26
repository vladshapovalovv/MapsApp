package com.example.mapsapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.mapsapp.data.database.model.PointModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PointDao {

    @Query("SELECT * FROM ${PointModel.TABLE_NAME}")
    fun get(): Flow<List<PointModel>>

    @Upsert
    suspend fun set(pointModel: PointModel)

}