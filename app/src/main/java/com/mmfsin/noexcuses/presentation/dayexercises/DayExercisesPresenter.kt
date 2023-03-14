package com.mmfsin.noexcuses.presentation.dayexercises

import com.mmfsin.noexcuses.data.repository.ExercisesRepository
import com.mmfsin.noexcuses.domain.models.ComboModel

class DayExercisesPresenter(val view: DayExercisesView) {

    private val repository by lazy { ExercisesRepository() }

    fun getDayExercises(dayId: String) = view.getDayExercises(repository.getDayExercises(dayId))

    fun getComboModelByExerciseId(exerciseId: String): ComboModel =
        repository.getComModelByExerciseId(exerciseId)

    fun deleteComboModel(comboModel: ComboModel) {
        val result = repository.deleteComboModel(comboModel)
        if (result) view.dayExerciseDeleted()
        else view.sww()
    }
}