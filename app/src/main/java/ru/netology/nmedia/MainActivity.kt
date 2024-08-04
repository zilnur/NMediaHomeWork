package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewModel: MainViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(bind) {
                description.text = post.text
                title.text = post.autor
                subtitle.text = post.date
                favoriteValue.text = post.likes.toPresentableString()
                viewsValue.text = post.views.toPresentableString()
                shareValue.text = post.shares.toPresentableString()
                favoriteButton.setImageResource(
                    if (post.isLiked) R.drawable.heart_fill else R.drawable.heart
                )
            }
        }
        bind.favoriteButton.setOnClickListener { viewModel.like() }
        bind.shareButton.setOnClickListener { viewModel.share() }
    }
}