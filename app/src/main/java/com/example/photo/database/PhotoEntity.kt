package com.example.photo.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "photo_table")
class PhotoEntity(

    @PrimaryKey val url: String,
    val date: Long,
    val circle: Boolean,
    val baptized: Boolean
)


