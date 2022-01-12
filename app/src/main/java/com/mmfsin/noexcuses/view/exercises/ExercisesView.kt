package com.mmfsin.noexcuses.view.exercises

import com.mmfsin.noexcuses.view.exercises.model.ExerciseDTO

interface ExercisesView {
    fun showExercises(list: List<ExerciseDTO>)
    fun somethingWentWrong()
}