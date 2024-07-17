package ru.netology.nmedia

data class Post(
    val autor: String,
    var date: String,
    var text: String,
    var likes: Int,
    var shares: Int,
    var views: Int,
    var isLiked: Boolean
)

public fun Int.toPresentableString(): String {
    return when {
        this < 1_000 -> this.toString()
        this in 1_000..99_999 -> {
            val thousands = this / 1_000
            val hundreds = (this % 1_000) / 100
            if (hundreds == 0) {
                "${thousands}K"
            } else {
                "${thousands}.${hundreds}K"
            }
        }
        this in 10_000..999_999 -> {
            val thousands = this / 1_000
            "${thousands}K"
        }
        this in 1_000_000..9_999_999 -> {
            val millions = this / 1_000_000
            val thousands = (this % 1_000_000) / 100_000
            if (thousands == 0) {
                "${millions}M"
            } else {
                "${millions}.${thousands}M"
            }
        }
        else -> {
            val millions = this / 1_000_000
            "${millions}M"
        }
    }
}
