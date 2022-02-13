package com.example.photo.ui

import android.app.Application
import com.example.photo.database.DatabaseConstructor
import com.example.photo.database.PhotoDatabase
import com.example.photo.ui.photo.ImageViewModel
import com.example.photo.repositories.PhotoRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.dsl.module

@KoinApiExtension
class MySuperApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MySuperApp)
            modules(listOf(viewModels, repository, storageModule))
        }
    }

    private val viewModels = module {
        viewModel { ImageViewModel(get()) }
    }

    private val repository = module { //создаем репозитории
        factory { PhotoRepository(get()) }
    }

    private val storageModule = module {
        single { DatabaseConstructor.create(get()) }  //создаем синглтон базы данных
        factory { get<PhotoDatabase>().photoDao() }

    }

}