package com.mmfsin.noexcuses.presentation.chooseexercises

import com.mmfsin.noexcuses.data.repository.ExercisesRepository
import com.mmfsin.noexcuses.domain.models.ComboModel
import java.util.*

class ChExercisesPresenter(val view: ChExercisesView) {

    private val repository by lazy { ExercisesRepository() }

    fun getExercisesByMuscularGroup(name: String) {
        if (name.isEmpty()) view.sww()
        else {
            val exercises = repository.getExercisesFromRealm()
                .filter { it.category == name }.sortedBy { it.order }
            view.getExercises(exercises)
        }
    }


    fun saveComoModel(dayId: String, exerciseId: String) {
        val id = UUID.randomUUID().toString()
        val combo = ComboModel(id, dayId, exerciseId)
        val result = repository.saveCombo(combo)
        view.savedExerciseInDay(result)
    }
}