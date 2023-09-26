package com.example.mapsapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PointModel.TABLE_NAME)
data class PointModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val latitude: Double,
    val longitude: Double
) {
    companion object {

        const val TABLE_NAME = "points"
    }
}
