package com.mmfsin.noexcuses.presentation.exercises

import com.mmfsin.noexcuses.domain.models.Exercise

interface ExercisesView {
    fun getExecises(exercises: List<Exercise>)
    fun sww()
}