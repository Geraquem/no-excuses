package com.mmfsin.noexcuses.presentation.calendar.dialogs.exercises

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetCalendarDayExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarDayExercisesViewModel @Inject constructor(
    private val getCalendarDayExercisesUseCase: GetCalendarDayExercisesUseCase
) : BaseViewModel<CalendarDayExercisesEvent>() {

    fun getExercises(dayId: String, routineId: String) {
        executeUseCase(
            {
                getCalendarDayExercisesUseCase.execute(
                    GetCalendarDayExercisesUseCase.Params(
                        routineId = routineId,
                        dayId = dayId,
                    )
                )
            },
            { result-> _event.value = CalendarDayExercisesEvent.GetExercises(result) },
            { _event.value = CalendarDayExercisesEvent.SWW }
        )
    }
}