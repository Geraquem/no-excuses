package com.mmfsin.noexcuses.presentation.routines.days

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetDayByIdUseCase
import com.mmfsin.noexcuses.domain.usecases.GetDayExercisesUseCase
import com.mmfsin.noexcuses.domain.usecases.GetRoutineDaysUseCase
import com.mmfsin.noexcuses.presentation.exercises.mgroups.MGroupsEvent
import com.mmfsin.noexcuses.presentation.routines.days.fragments.DayConfigEvent
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
            { result -> _event.value = DaysEvent.GetDayExercises()
//                _event.value = if (result.isNotEmpty()) DaysEvent.MGroups(result)
//                else DaysEvent.SomethingWentWrong
            },
            { _event.value = DaysEvent.SomethingWentWrong }
        )
    }
}