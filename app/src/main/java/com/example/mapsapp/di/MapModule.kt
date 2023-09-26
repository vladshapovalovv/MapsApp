package com.example.mapsapp.di

import android.content.Context
import androidx.room.Room
import com.example.mapsapp.data.database.MapDatabase
import com.example.mapsapp.data.database.dao.PointDao
import com.example.mapsapp.data.datasource.PointLocalDataSource
import com.example.mapsapp.data.datasource.PointLocalDataSourceImpl
import com.example.mapsapp.data.repository.PointRepositoryImpl
import com.example.mapsapp.domain.repository.PointRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MapModule {

    companion object {

        @Provides
        @Singleton
        fun provideMapDatabase(context: Context): MapDatabase =
            Room.databaseBuilder(context, MapDatabase::class.java, MapDatabase.DATABASE_NAME)
                .build()

        @Provides
        @Singleton
        fun providePointDao(dataBase: MapDatabase): PointDao =
            dataBase.pointDao()

        @Singleton
        @Provides
        fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)
    }

    @Singleton
    @Binds
    fun bindRepository(impl: PointRepositoryImpl): PointRepository

    @Singleton
    @Binds
    fun bindLocalDataSource(impl: PointLocalDataSourceImpl): PointLocalDataSource
}