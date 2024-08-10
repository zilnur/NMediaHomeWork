package ru.netology.nmedia.mainView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val repository: MainRepository = MainRepositoryImpl()
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
            if (it != null) {
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