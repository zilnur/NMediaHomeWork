package ru.netology.nmedia.postView

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.feedView.FeedFragment.Companion.intArg
import ru.netology.nmedia.feedView.MainViewModel
import ru.netology.nmedia.model.toPresentableString

class PostFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(layoutInflater, container, false)
        val id = arguments?.intArg ?: 0
        viewModel.data.observe(requireActivity()) { posts ->
            val post = posts.find { it.id == id } ?: return@observe
            binding.post.description.text = post.text
            binding.post.description.visibility = if (post.text != null) {
                View.VISIBLE
            } else {
                View.INVISIBLE
                View.GONE
            }
            binding.post.preview.visibility = if (post.video != null) {
                View.VISIBLE
            } else {
                View.INVISIBLE
                View.GONE
            }
            binding.post.preview.adjustViewBounds = true
            binding.post.videoTitle.text = post.video
            binding.post.title.text = post.autor
            binding.post.subtitle.text = post.date
            binding.post.favoriteButton.isChecked = post.isLiked ?: false
            binding.post.favoriteButton.setText(post.likes.toPresentableString())
            binding.post.viewsButton.setText(post.views.toPresentableString())
            binding.post.shareButton.setText(post.shares.toPresentableString())

            binding.post.favoriteButton.setOnClickListener{
                viewModel.likeBy(post.id)
            }
            binding.post.shareButton.setOnClickListener {
                viewModel.shareBy(post.id)
            }
            binding.post.moreButton.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.popup_menu)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                findNavController().navigateUp()
                                viewModel.removeBy(post.id)
                                true
                            }
                            R.id.edit -> {
                                viewModel.edit(post)
                                findNavController().navigate(R.id.action_postFragment_to_newPostFragment2)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
            binding.post.preview.setOnClickListener {
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigateUp()
        }
        return binding.root
    }
}