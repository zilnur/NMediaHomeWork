package ru.netology.nmedia.addPostView

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.AndroidUtils
import ru.netology.nmedia.databinding.NewPostFragmentBinding
import ru.netology.nmedia.feedView.MainViewModel

class NewPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: MainViewModel by viewModels(
            ownerProducer = ::requireParentFragment
        )
        val binding = NewPostFragmentBinding.inflate(inflater, container, false)
        binding.edit.setText(viewModel.editedData().value?.text)
        binding.ok.setOnClickListener {
            val text = binding.edit.text.toString()
            viewModel.changeContent(text)
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            viewModel.endEdidting()
            findNavController().navigateUp()
        }

        return binding.root
    }
}