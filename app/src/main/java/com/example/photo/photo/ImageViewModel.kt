package com.example.photo.photo

import androidx.lifecycle.*
import com.example.photo.data.Photo
import com.example.photo.repositories.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageViewModel(
    private val photoRepository: PhotoRepository
): ViewModel() {
    val photoListLiveData: LiveData<List<Photo>> =
        photoRepository.gePhotoList().asLiveData()

    fun addPhotoToDatabase(newPhoto: Photo) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                photoRepository.addPhoto(newPhoto)
            }
        }
    }

    fun deletePhoto(photo: Photo) {
        viewModelScope.launch {
//         photoRepository.deletePhoto(photo)

        }
    }
//    fun deleteMessage(lesson: Photo) {
//        viewModelScope.launch {
//           photoRepository.deleteLesson(lesson)
//        }
//    }

}