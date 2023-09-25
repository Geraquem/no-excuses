package com.mmfsin.noexcuses.domain.models

data class MenuItem(
    val order: Int,
    val name: String,
    val image: Int,
    val action: MenuAction,
)
