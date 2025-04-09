package com.mmfsin.noexcuses.presentation.maximums.exercises

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.models.CalendarInfo
import com.mmfsin.noexcuses.domain.usecases.GetDayByIdUseCase
import com.mmfsin.noexcuses.domain.usecases.GetDayExercisesUseCase
import com.mmfsin.noexcuses.domain.usecases.GetExercisesUseCase
import com.mmfsin.noexcuses.domain.usecases.MoveChExerciseUseCase
import com.mmfsin.noexcuses.domain.usecases.RegisterDayInCalendarUseCase
import com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.DefaultExercisesEvent
import com.mmfsin.noexcuses.presentation.exercises.exercises.ExercisesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MaxExercisesViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase
) : BaseViewModel<ExercisesEvent>() {

    fun getExercises(mGroup: String, newCreated: Boolean = false) {
        executeUseCase(
            { getExercisesUseCase.execute(GetExercisesUseCase.Params(mGroup)) },
            { result ->
                _event.value =
                    if (result.isNotEmpty()) ExercisesEvent.GetExercises(result, newCreated)
                    else ExercisesEvent.SWW
            },
            { _event.value = ExercisesEvent.SWW }
        )
    }
}