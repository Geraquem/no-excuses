package com.mmfsin.noexcuses.domain.models

data class MenuItem(
    val order: Int,
    val image: Int,
    val title: String,
    val description: String,
    val action: MenuAction,
)
