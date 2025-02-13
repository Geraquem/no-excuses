package com.mmfsin.noexcuses.domain.models


open class ChExercise(
    val id: String,
    val routineId: String,
    val dayId: String,
    val exerciseId: String?,
    val data: List<Data>?,
    val time: Double?,
    var notes: String?,
    val position: Int,
    val superSerie: Boolean
)
