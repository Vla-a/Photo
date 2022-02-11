package com.example.photo.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photo.data.Photo
import com.example.photo.repositories.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ImageViewModel(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _list = MutableStateFlow<List<Photo>>(listOf())
    val list = _list.asStateFlow()

    init {
        viewModelScope.launch {
            photoRepository.allGetPhoto.collect { it ->
                _list.value = it.map {
                    Photo(
                        it.url,
                        it.date,
                        it.circle,
                        it.baptized
                    )
                }
            }
        }
    }

    fun addPhotoToDatabase(newPhoto: Photo) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                photoRepository.addPhoto(newPhoto)
            }
        }
    }

    fun deletePhoto(photo: Photo) {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                photoRepository.deletePhoto(photo)
            }
        }
    }

    fun chengeList() {
        val newlist = list.value.map {
            Photo(it.url, it.date, !it.circle, it.baptized)
        }
        _list.value = newlist
    }

}