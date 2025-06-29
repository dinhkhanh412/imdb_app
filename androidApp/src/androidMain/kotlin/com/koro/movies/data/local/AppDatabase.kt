package com.koro.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.koro.movies.data.local.dao.MovieDao
import com.koro.movies.data.local.entity.Converters
import com.koro.movies.data.local.entity.MovieDetailEntity
import com.koro.movies.data.local.entity.MovieEntity
import com.koro.movies.data.local.entity.ProductionCompanyConverter

@Database(
    entities = [MovieEntity::class, MovieDetailEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
