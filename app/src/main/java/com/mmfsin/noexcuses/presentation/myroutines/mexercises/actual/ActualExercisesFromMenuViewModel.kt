package com.mmfsin.noexcuses.presentation.myroutines.mexercises.actual

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetActualDayExercisesUseCase
import com.mmfsin.noexcuses.domain.usecases.GetDayByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActualExercisesFromMenuViewModel @Inject constructor(
    private val getDayByIdUseCase: GetDayByIdUseCase,
    private val getActualDayExercisesUseCase: GetActualDayExercisesUseCase
) : BaseViewModel<ActualExercisesFromMenuEvent>() {

    fun getDay(dayId: String) {
        executeUseCase(
            { getDayByIdUseCase.execute(GetDayByIdUseCase.Params(dayId)) },
            { result ->
                _event.value = result?.let { ActualExercisesFromMenuEvent.GetDay(it) }
                    ?: run { ActualExercisesFromMenuEvent.SWW }
            },
            { _event.value = ActualExercisesFromMenuEvent.SWW }
        )
    }

    fun getActualDayExercises(dayId: String, createdByUser: Boolean) {
        executeUseCase(
            {
                getActualDayExercisesUseCase.execute(
                    GetActualDayExercisesUseCase.Params(dayId, createdByUser)
                )
            },
            { result -> _event.value = ActualExercisesFromMenuEvent.GetDayExercises(result) },
            { _event.value = ActualExercisesFromMenuEvent.SWW }
        )
    }
}