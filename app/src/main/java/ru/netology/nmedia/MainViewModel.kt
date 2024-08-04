package ru.netology.nmedia

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val repository: MainRepository = MainRepositoryImpl()
    val data = repository.get()
    fun like() = repository.like()
    fun share() = repository.share()
}