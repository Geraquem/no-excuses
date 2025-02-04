package com.mmfsin.noexcuses.domain.models

open class DefaultExercise(
    val id: String,
    val exercise: Exercise,
    val dayId: String,
    val desc: String,
    val reps: String,
    val series: String,
    val superSerie: Boolean
)