package com.mmfsin.noexcuses.presentation.dfroutines.dfexercises

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.models.CalendarInfo
import com.mmfsin.noexcuses.domain.usecases.GetDefaultDayByIdUseCase
import com.mmfsin.noexcuses.domain.usecases.GetDefaultDayExercisesUseCase
import com.mmfsin.noexcuses.domain.usecases.RegisterDayInCalendarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DefaultExercisesViewModel @Inject constructor(
    private val getDefaultDayByIdUseCase: GetDefaultDayByIdUseCase,
    private val getDefaultDayExercisesUseCase: GetDefaultDayExercisesUseCase,
    private val registerDayInCalendarUseCase: RegisterDayInCalendarUseCase
) : BaseViewModel<DefaultExercisesEvent>() {

    fun getDefaultDay(dayId: String) {
        executeUseCase(
            { getDefaultDayByIdUseCase.execute(GetDefaultDayByIdUseCase.Params(dayId)) },
            { result ->
                _event.value = result?.let { DefaultExercisesEvent.GetDefaultDay(it) }
                    ?: run { DefaultExercisesEvent.SWW }
            },
            { _event.value = DefaultExercisesEvent.SWW }
        )
    }

    fun getDefaultDayExercises(routineId: String, dayId: String) {
        executeUseCase(
            {
                getDefaultDayExercisesUseCase.execute(
                    GetDefaultDayExercisesUseCase.Params(routineId, dayId)
                )
            },
            { result -> _event.value = DefaultExercisesEvent.GetDefaultDayExercises(result) },
            { _event.value = DefaultExercisesEvent.SWW }
        )
    }

    fun registerDayInCalendar(info: CalendarInfo) {
        executeUseCase(
            { registerDayInCalendarUseCase.execute(RegisterDayInCalendarUseCase.Params(info)) },
            { _event.value = DefaultExercisesEvent.ExercisesRegisteredInCalendar },
            { _event.value = DefaultExercisesEvent.SWW }
        )
    }
}