package com.example.photo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [PhotoEntity::class], version = 1)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
}

object DatabaseConstructor {
    fun create(context: Context): PhotoDatabase =
        Room.databaseBuilder(
            context,
            PhotoDatabase::class.java,
            "photo_database"
        ).build()
}