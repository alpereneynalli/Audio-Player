package com.player.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.player.roomdb.dao.DownloadedSongDao
import com.player.roomdb.dao.FavoriteSongDao
import com.player.roomdb.entity.DownloadedSongEntity
import com.player.roomdb.entity.FavoriteSongEntity

@Database(
    entities = [FavoriteSongEntity::class, DownloadedSongEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteSongDao(): FavoriteSongDao
    abstract fun downloadedSongDao(): DownloadedSongDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()
                    .also { INSTANCE = it }
            }
        }
    }
}