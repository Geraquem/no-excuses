package com.mmfsin.noexcuses.presentation.exercises

import com.mmfsin.noexcuses.data.repository.ExercisesRepository

class ExercisesPresenter(val view: ExercisesView) {

    private val repository by lazy { ExercisesRepository() }

    fun getExercisesByMuscularGroup(name: String) {
        if (name.isEmpty()) view.sww()
        else {
            val exercises = repository.getExercisesFromRealm()
                .filter { it.category == name }.sortedBy { it.order }
            view.getExecises(exercises)
        }
    }

}