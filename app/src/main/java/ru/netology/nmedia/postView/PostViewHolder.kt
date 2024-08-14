package ru.netology.nmedia.postView

import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.feedView.InputTypes
import ru.netology.nmedia.feedView.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostActivityBinding
import ru.netology.nmedia.feedView.toPresentableString

typealias onListener = (InputTypes) -> Unit

class PostViewHolder(
    private val binding: PostActivityBinding,
    private val listener: onListener
): RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            description.text = post.text
            description.visibility = if (post.text != null) {
                View.VISIBLE
            } else {
                View.INVISIBLE
                View.GONE
            }
            preview.visibility = if (post.video != null) {
                View.VISIBLE
            } else {
                View.INVISIBLE
                View.GONE
            }
            preview.adjustViewBounds = true
            videoTitle.text = post.video
            title.text = post.autor
            subtitle.text = post.date
            favoriteButton.isChecked = post.isLiked
            favoriteButton.setText(post.likes.toPresentableString())
            viewsButton.setText(post.views.toPresentableString())
            shareButton.setText(post.shares.toPresentableString())
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
                    }
                }.show()
            }
            preview.setOnClickListener {
                listener(InputTypes.OpenVideoInput(post.video))
            }
            binding.postFragment.setOnClickListener {
                listener(InputTypes.NavigateToPostDetails(post))
            }
        }
    }
}