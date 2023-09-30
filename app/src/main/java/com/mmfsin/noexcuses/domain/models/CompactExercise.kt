package com.mmfsin.noexcuses.domain.models

open class CompactExercise(
    val chExerciseId: String?,
    var name: String,
    var category: String,
    var imageURL: String,
    var weight: Double?,
    var series: Int?,
    var reps: Int?,
)
