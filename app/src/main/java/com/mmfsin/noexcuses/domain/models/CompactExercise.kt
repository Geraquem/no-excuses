package com.mmfsin.noexcuses.domain.models

import com.mmfsin.noexcuses.R

open class CompactExercise(
    val chExerciseId: String?,
    var name: String,
    var category: String,
    var imageURL: String,
    var series: Int?,
    var time: String?,
    var hasNotes: Boolean,
)

fun getCategoryColor(category: String): Int {
    return when (category) {
        "hombro" -> R.color.shoulder_color
        "pecho" -> R.color.chest_color
        "biceps" -> R.color.biceps_color
        "triceps" -> R.color.triceps_color
        "espalda" -> R.color.back_color
        "pierna" -> R.color.legs_color
        "core" -> R.color.core_color
        "cardio" -> R.color.cardio_color
        else -> R.color.white
    }
}
