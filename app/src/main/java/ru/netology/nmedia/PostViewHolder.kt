package ru.netology.nmedia

import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.PostActivityBinding

typealias onLikeListener = (Post) -> Unit
typealias onShareListener = (Post) -> Unit
typealias onListener = (InputTypes) -> Unit

class PostViewHolder(
    private val binding: PostActivityBinding,
    private val listener: onListener
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
                listener(InputTypes.LikeInput(post))
            }
            shareButton.setOnClickListener {
                listener(InputTypes.ShareInput(post))
            }
            moreButton.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.popup_menu)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener(InputTypes.DeleteInput(post))
                                true
                            }
                            R.id.edit -> {
                                listener(InputTypes.EditInput(post))
                                true
                            }
                            else -> false
                        }
                        false
                    }
                }.show()
            }
        }
    }
}