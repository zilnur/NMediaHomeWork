package ru.netology.nmedia.feedView

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MainRepository = MainRepositoryImpl(application)
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
    var selectedPost = emptyData

    fun editedData(): LiveData<Post> = edited

    fun likeBy(id: Int) = repository.likeBy(id)
    fun shareBy(id: Int) = repository.shareBy(id)
    fun removeBy(id: Int) = repository.removeBy(id)
    fun save() {
        edited.value.let {
            if (it != null && !it.text.isNullOrBlank()) {
                repository.save(it)
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