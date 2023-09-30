package com.mmfsin.noexcuses.domain.models

open class ChExercise(
    val id: String,
    val routineId: String,
    val dayId: String,
    val exerciseId: String?,
    var weight: Double?,
    var series: Int?,
    var reps: Int?,
    var notes: String?
)
