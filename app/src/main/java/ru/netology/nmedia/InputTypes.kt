package ru.netology.nmedia

sealed interface InputTypes {
    data class CreateInput(val post: Post): InputTypes
    data class DeleteInput(val post: Post): InputTypes
    data class EditInput(val post: Post): InputTypes
    data class LikeInput(val post: Post): InputTypes
    data class ShareInput(val post: Post): InputTypes
}