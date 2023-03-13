package com.mmfsin.noexcuses.presentation.detailexercise

import com.mmfsin.noexcuses.domain.models.CompleteExercise

interface DetailExerciseView {
    fun getExerciseDetail(exercise: CompleteExercise)
    fun sww()
}