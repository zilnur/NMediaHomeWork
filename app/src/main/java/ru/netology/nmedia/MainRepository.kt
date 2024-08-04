package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface MainRepository {
    fun get(): LiveData<Post>
    fun like()
}

class MainRepositoryImpl: MainRepository {

    private var post = Post(
        "Нетология. Университет интернет-профессий",
        "21 мая в 18:36",
        """"Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти
студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть
больше, целиться выше, бежать быстрее. Наша миссия
— помочь встать на путь роста и начать цепочку
перемен"""",
        10,
        999990,
        1132334,
        false
    )

    private val data = MutableLiveData(post)
    override fun get() = data
    override fun like() {
        post = post.copy(isLiked = !post.isLiked, likes = if (post.isLiked) post.likes - 1 else post.likes + 1)
        data.value = post
    }
}