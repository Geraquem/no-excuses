package com.mmfsin.noexcuses.domain.models

open class Routine(
    var id: String,
    var title: String,
    var description: String?,
    var days: Int,
    var doingIt: Boolean
)
