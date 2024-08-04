package ru.netology.nmedia

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val repository: MainRepository = MainRepositoryImpl()
    val data = repository.get()
    fun likeBy(id: Int) = repository.likeBy(id)
    fun shareBy(id: Int) = repository.shareBy(id)
}