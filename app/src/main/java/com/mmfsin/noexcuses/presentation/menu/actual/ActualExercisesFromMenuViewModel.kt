package com.mmfsin.noexcuses.presentation.menu.actual

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetActualDayExercisesUseCase
import com.mmfsin.noexcuses.domain.usecases.GetActualDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActualExercisesFromMenuViewModel @Inject constructor(
    private val getActualDayUseCase: GetActualDayUseCase,
    private val getActualDayExercisesUseCase: GetActualDayExercisesUseCase
) : BaseViewModel<ActualExercisesFromMenuEvent>() {

    fun getActualDay(dayId: String, createdByUser: Boolean) {
        executeUseCase(
            { getActualDayUseCase.execute(GetActualDayUseCase.Params(dayId, createdByUser)) },
            { result ->
                _event.value = result?.let { ActualExercisesFromMenuEvent.GetDay(it) }
                    ?: run { ActualExercisesFromMenuEvent.SWW }
            },
            { _event.value = ActualExercisesFromMenuEvent.SWW }
        )
    }

    fun getActualDayExercises(routineId: String, dayId: String, createdByUser: Boolean) {
        executeUseCase(
            {
                getActualDayExercisesUseCase.execute(
                    GetActualDayExercisesUseCase.Params(routineId, dayId, createdByUser)
                )
            },
            { result -> _event.value = ActualExercisesFromMenuEvent.GetDayExercises(result) },
            { _event.value = ActualExercisesFromMenuEvent.SWW }
        )
    }
}