package com.mmfsin.noexcuses.domain.models

open class Stretching(
    var imageURL: String?,
    var description: String,
    var order: Long
)

data class Stretch(
    val mGroup: String,
    val stretching: List<Stretching>
)

