package ru.netology.nmedia

import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.PostActivityBinding

typealias onLikeListener = (Post) -> Unit
typealias onShareListener = (Post) -> Unit

class PostViewHolder(
    private val binding: PostActivityBinding,
    private val likeListener: onLikeListener,
    private val shareListener: onShareListener
): RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            description.text = post.text
            title.text = post.autor
            subtitle.text = post.date
            favoriteValue.text = post.likes.toPresentableString()
            viewsValue.text = post.views.toPresentableString()
            shareValue.text = post.shares.toPresentableString()
            favoriteButton.setImageResource(
                if (post.isLiked) R.drawable.heart_fill else R.drawable.heart
            )
            favoriteButton.setOnClickListener{
                likeListener(post)
            }
            shareButton.setOnClickListener {
                shareListener(post)
            }
        }
    }
}