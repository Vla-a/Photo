package com.example.photo.repositories


import com.example.photo.data.Photo
import com.example.photo.database.PhotoDao
import com.example.photo.database.PhotoEntity

class PhotoRepository(
    private val photoDao: PhotoDao
) {

    val allGetPhoto = photoDao.getPhotoList()

    fun addPhoto(photo: Photo) {
        photo.entity()?.let { photoDao.addPhoto(it) }
    }

    fun deletePhoto(photo: Photo) {
        photoDao.deletePhoto(photo.entity())
    }

    private fun Photo.entity() =
        PhotoEntity(this.url, this.date, this.circle, this.baptized)
}



