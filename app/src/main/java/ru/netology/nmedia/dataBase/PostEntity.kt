package ru.netology.nmedia.dataBase

import androidx.annotation.ReturnThis
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.model.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val autor: String,
    val date: String,
    val text: String?,
    val video: String?,
    val likes: Int,
    val shares: Int,
    val views: Int,
    val isLiked: Boolean
) {
    fun mapToModel(): Post = Post(id, autor, date, text, video, likes, shares, views, isLiked)

    companion object {
        fun mapFromModel(post: Post) = with(post) {
            PostEntity(id, autor, date, text, video, likes, shares, views, isLiked)
        }
    }
}