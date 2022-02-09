package com.example.photo.data

data class Photo(
    val id: Long,
    val url: String,
    val date: Long,
    val circle: Boolean,
    var baptized: Boolean
    )