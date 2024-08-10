package ru.netology.nmedia.mainView

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.AndroidUtils
import ru.netology.nmedia.R
import ru.netology.nmedia.addPostView.NewPostActivityContract
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.postView.PostsAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(bind.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val viewModel: MainViewModel by viewModels()
        val intent = registerForActivityResult(NewPostActivityContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }
        val adapter = PostsAdapter { inputType ->
            when (inputType) {
                is InputTypes.LikeInput -> viewModel.likeBy(inputType.post.id)
                is InputTypes.ShareInput -> viewModel.shareBy(inputType.post.id)
                is InputTypes.DeleteInput -> viewModel.removeBy(inputType.post.id)
                is InputTypes.EditInput -> {
                    viewModel.edit(inputType.post)
                    intent.launch(inputType.post.text)
                }
                is InputTypes.OpenVideoInput -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(inputType.uti))
                    val shareIntent = Intent.createChooser(intent, getString(R.string.app_name))
                    startActivity(shareIntent)
                }
                else -> println()
            }
        }
        bind.list.adapter = adapter
        viewModel.data.observe(this) {
            adapter.submitList(it)
        }
        bind.addButton.setOnClickListener {
            intent.launch(null)
        }
    }
}