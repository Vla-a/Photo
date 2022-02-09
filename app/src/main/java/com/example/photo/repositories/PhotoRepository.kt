package com.example.photo.repositories


import com.example.photo.data.Photo
import com.example.photo.database.PhotoDao
import com.example.photo.database.PhotoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PhotoRepository(

    private val photoDao: PhotoDao
) {

    fun gePhotoList(): Flow<List<Photo>> =
        photoDao.getPhotoList().map { photoEntities ->
            photoEntities.map { photoEntities ->
                Photo(
                    photoEntities.id,
                    photoEntities.url,
                    photoEntities.date,
                    photoEntities.circle,
                    photoEntities.baptized
                )
            }
        }

    suspend fun addPhoto(photo: Photo) {
         photo.entity()?.let { photoDao.addPhoto(it) }

    }

//   suspend fun deletePhoto(photo: Photo){
//    photoDao.deletePhoto(photo.entity())
//}

//    suspend fun deleteLesson(lesson: Photo) {
//
//        photoDao.deleteLesson(lesson.entity())
//    }

    private fun Photo.entity() =
        PhotoEntity(this.id, this.url, this.date, this.circle, this.baptized)

}



