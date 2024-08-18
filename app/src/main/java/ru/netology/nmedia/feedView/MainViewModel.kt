package ru.netology.nmedia.feedView

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dataBase.AppDataBase
import ru.netology.nmedia.model.Post
import ru.netology.nmedia.repository.MainRepository
import ru.netology.nmedia.repository.MainRepositoryImpl

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MainRepository = MainRepositoryImpl(postDao = AppDataBase.getInstance(application.baseContext).postDao)
    private val emptyData = Post(
        0,
        "",
        "",
        "",
        null,
        0,
        0,
        0,
        false
    )
    private val edited = MutableLiveData(emptyData)
    val data = repository.get()

    fun editedData(): LiveData<Post> = edited

    fun likeBy(id: Int) = repository.likeBy(id)
    fun shareBy(id: Int) = repository.shareBy(id)
    fun removeBy(id: Int) = repository.removeBy(id)
    fun save() {
        edited.value.let {
            if (it != null && !it.text.isNullOrBlank()) {
                if (it.id == 0) {
                    repository.save(it)
                } else {
                    repository.updateTextBy(it.id, it.text)
                }
            }
        }
        edited.value = emptyData
    }
    fun changeContent(content: String) {
        edited.value.let {
            val text = content.trim()
            if (it?.text == text) {
                return
            }
            edited.value = it?.copy(text = text)
        }
    }
    fun edit(post: Post) {
        edited.value = post
    }

    fun endEdidting() {
        edited.value = emptyData
    }
}