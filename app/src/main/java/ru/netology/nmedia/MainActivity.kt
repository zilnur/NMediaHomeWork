package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.databinding.ActivityMainBinding

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
        val adapter = PostsAdapter { inputType ->
            when (inputType) {
                is InputTypes.LikeInput -> viewModel.likeBy(inputType.post.id)
                is InputTypes.ShareInput -> viewModel.shareBy(inputType.post.id)
                is InputTypes.DeleteInput -> viewModel.removeBy(inputType.post.id)
                is InputTypes.EditInput -> viewModel.edit(inputType.post)
                else -> println()
            }
        }
        bind.list.adapter = adapter
        viewModel.data.observe(this) {
            adapter.submitList(it)
        }
        viewModel.editedData().observe(this) {
            bind.editMessage.text = it.text
            if (it.id == 0) {
                bind.editView.visibility = View.INVISIBLE
                bind.editView.visibility = View.GONE
            } else {
                bind.editView.visibility = View.VISIBLE
            }
        }
        bind.save.setOnClickListener {
            with(bind.textEditor) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Content can't be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }
        bind.endEditing.setOnClickListener {
            viewModel.endEdidting()
        }
    }
}