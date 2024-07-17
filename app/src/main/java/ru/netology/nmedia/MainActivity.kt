package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
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

        val post = Post(
            "Нетология. Университет интернет-профессий",
            "21 мая в 18:36",
            """"Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти
студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть
больше, целиться выше, бежать быстрее. Наша миссия
— помочь встать на путь роста и начать цепочку
перемен"""",
            10,
            5990,
            1132334,
            false
            )

        with(bind) {
            title.text = post.autor
            subtitle.text = post.date
            description.text = post.text
            favoriteValue.text = post.likes.toPresentableString()
            shareValue.text = post.shares.toPresentableString()
            viewsValue.text = post.views.toPresentableString()

            favoriteButton.setOnClickListener {
                post.isLiked = !post.isLiked
                favoriteButton.setImageResource(
                    if (post.isLiked) R.drawable.heart_fill else R.drawable.heart
                )
                if (post.isLiked) post.likes++ else post.likes--
                favoriteValue.text = post.likes.toPresentableString()
            }

            shareButton.setOnClickListener {
                post.shares++
                println(post.shares)
                shareValue.text = post.shares.toPresentableString()
            }
        }
    }
}