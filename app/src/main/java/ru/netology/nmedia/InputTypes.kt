package ru.netology.nmedia

sealed interface InputTypes {
    class CreateInput(val post: Post): InputTypes
    class DeleteInput(val post: Post): InputTypes
    class EditInput(val post: Post): InputTypes
    class LikeInput(val post: Post): InputTypes
    class ShareInput(val post: Post): InputTypes
}