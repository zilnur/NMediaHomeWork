package ru.netology.nmedia.postView

import androidx.recyclerview.widget.DiffUtil
import ru.netology.nmedia.mainView.Post

class PostDiffCallBack: DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}