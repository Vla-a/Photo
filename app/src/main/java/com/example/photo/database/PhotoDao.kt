package com.example.photo.database


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo_table")
    fun getPhotoList(): Flow<List<PhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhoto(photo: PhotoEntity)

//    @Delete
//    suspend fun deletePhoto(photo: PhotoEntity)
}