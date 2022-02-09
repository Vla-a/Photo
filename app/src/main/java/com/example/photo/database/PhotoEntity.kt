package com.example.photo.database

import androidx.room.Entity


@Entity(tableName = "photo_table",  primaryKeys = ["id", "url"])
class PhotoEntity(

    val id: Long,
    val url: String,
    val date: Long,
    val circle: Boolean,
    val baptized: Boolean
)


