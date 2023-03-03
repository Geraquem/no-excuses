package com.mmfsin.noexcuses.presentation.exercises

import com.mmfsin.noexcuses.presentation.exercises.model.ExerciseDTO

interface ExercisesView {
    fun showExercises(list: List<ExerciseDTO>)
    fun somethingWentWrong()
}