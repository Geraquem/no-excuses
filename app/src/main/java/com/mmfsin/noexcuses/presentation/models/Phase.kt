package com.mmfsin.noexcuses.presentation.models

import io.realm.annotations.PrimaryKey

data class Phase(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String? = null
)
