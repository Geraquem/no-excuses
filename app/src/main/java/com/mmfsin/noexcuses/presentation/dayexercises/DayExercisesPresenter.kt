package com.mmfsin.noexcuses.presentation.dayexercises

import com.mmfsin.noexcuses.data.repository.ExercisesRepository
import com.mmfsin.noexcuses.domain.models.ComboModel
import java.util.*

class DayExercisesPresenter(val view: DayExercisesView) {

    private val repository by lazy { ExercisesRepository() }

    fun getDayExercises(dayId: String) = view.getDayExercises(repository.getDayExercises(dayId))
}