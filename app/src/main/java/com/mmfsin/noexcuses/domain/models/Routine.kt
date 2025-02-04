package com.mmfsin.noexcuses.domain.models

open class Routine(
    var id: String,
    var name: String,
    var description: String?,
    var days: Int,
    var doingIt: Boolean,
    val createdByUser: Boolean
)
