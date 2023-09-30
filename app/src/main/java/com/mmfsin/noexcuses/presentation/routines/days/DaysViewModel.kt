package com.mmfsin.noexcuses.presentation.routines.days

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetDayByIdUseCase
import com.mmfsin.noexcuses.domain.usecases.GetDayExercisesUseCase
import com.mmfsin.noexcuses.domain.usecases.GetRoutineDaysUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DaysViewModel @Inject constructor(
    private val getRoutineDaysUseCase: GetRoutineDaysUseCase,
    private val getDayByIdUseCase: GetDayByIdUseCase,
    private val getDayExercisesUseCase: GetDayExercisesUseCase
) : BaseViewModel<DaysEvent>() {

    fun getDays(routineId: String) {
        executeUseCase(
            { getRoutineDaysUseCase.execute(GetRoutineDaysUseCase.Params(routineId)) },
            { result -> _event.value = DaysEvent.GetDays(result) },
            { _event.value = DaysEvent.SomethingWentWrong }
        )
    }

    fun getDay(dayId: String) {
        executeUseCase(
            { getDayByIdUseCase.execute(GetDayByIdUseCase.Params(dayId)) },
            { result ->
                _event.value = result?.let { DaysEvent.GetDay(it) }
                    ?: run { DaysEvent.SomethingWentWrong }
            },
            { _event.value = DaysEvent.SomethingWentWrong }
        )
    }

    fun getDayExercises(dayId: String) {
        executeUseCase(
            { getDayExercisesUseCase.execute(GetDayExercisesUseCase.Params(dayId)) },
            { result -> _event.value = DaysEvent.GetDayExercises(result) },
            { _event.value = DaysEvent.SomethingWentWrong }
        )
    }
}