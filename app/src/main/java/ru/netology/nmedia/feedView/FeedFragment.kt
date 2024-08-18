package ru.netology.nmedia.feedView

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.IntArg
import ru.netology.nmedia.R
import ru.netology.nmedia.StringArg
import ru.netology.nmedia.addPostView.NewPostActivityContract
import ru.netology.nmedia.databinding.FeedFragmentBinding
import ru.netology.nmedia.postView.PostsAdapter

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bind = FeedFragmentBinding.inflate(inflater, container, false)

        val viewModel: MainViewModel by viewModels(
            ownerProducer = ::requireParentFragment
        )
        val intent = registerForActivityResult(NewPostActivityContract()) { result ->
            if (result != null) {
                viewModel.changeContent(result)
                viewModel.save()
            } else {
                viewModel.endEdidting()
            }
        }
        val adapter = PostsAdapter { inputType ->
            when (inputType) {
                is InputTypes.LikeInput -> viewModel.likeBy(inputType.post.id)
                is InputTypes.ShareInput -> viewModel.shareBy(inputType.post.id)
                is InputTypes.DeleteInput -> viewModel.removeBy(inputType.post.id)
                is InputTypes.EditInput -> {
                    viewModel.edit(inputType.post)
                    findNavController().navigate(
                        R.id.action_feedFragment2_to_newPostFragment2
                    )
                }
                is InputTypes.NavigateToPostDetails -> {
                    findNavController().navigate(
                        R.id.action_feedFragment2_to_postFragment,
                        Bundle().apply {
                            intArg = inputType.post.id
                        }
                    )
                }
                else -> println()
            }
        }
        bind.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        bind.addButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_feedFragment2_to_newPostFragment2
            )
        }

        return bind.root
    }

    companion object {
        private const val TEXT_KEY = "TEXT_KEY"
        var Bundle.textArg: String? by StringArg
        var Bundle.intArg: Int by IntArg
    }
}