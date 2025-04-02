package com.mmfsin.noexcuses.presentation.models

data class CreatedExercise(
    val image: String?,
    val category: String,
    val name: String,
    val description: String?,
    val externalURL: String?,
    val muscles: String?
)