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
    val str = this.toString().removeRange(0,0)
    return if (this > 1000) {
        val suffix = if (this > 1000000) "M" else "K"
        if (str[1] == '0') {
            str[0].toString() + suffix
        } else {
            str[0].toString() + "." + str[1].toString() + suffix
        }
    } else {
        this.toString()
    }
}