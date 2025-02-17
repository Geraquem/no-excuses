package com.mmfsin.noexcuses.presentation.myroutines.mexercises

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.models.CalendarInfo
import com.mmfsin.noexcuses.domain.usecases.GetDayByIdUseCase
import com.mmfsin.noexcuses.domain.usecases.GetDayExercisesUseCase
import com.mmfsin.noexcuses.domain.usecases.MoveChExerciseUseCase
import com.mmfsin.noexcuses.domain.usecases.RegisterDayInCalendarUseCase
import com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.DefaultExercisesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MExercisesViewModel @Inject constructor(
    private val getDayByIdUseCase: GetDayByIdUseCase,
    private val getDayExercisesUseCase: GetDayExercisesUseCase,
    private val moveChExerciseUseCase: MoveChExerciseUseCase,
    private val registerDayInCalendarUseCase: RegisterDayInCalendarUseCase
) : BaseViewModel<MExercisesEvent>() {

    fun getDay(dayId: String) {
        executeUseCase(
            { getDayByIdUseCase.execute(GetDayByIdUseCase.Params(dayId)) },
            { result ->
                _event.value = result?.let { MExercisesEvent.GetDay(it) }
                    ?: run { MExercisesEvent.SWW }
            },
            { _event.value = MExercisesEvent.SWW }
        )
    }

    fun getDayExercises(dayId: String) {
        executeUseCase(
            { getDayExercisesUseCase.execute(GetDayExercisesUseCase.Params(dayId)) },
            { result -> _event.value = MExercisesEvent.GetDayExercises(result) },
            { _event.value = MExercisesEvent.SWW }
        )
    }

    fun moveChExercise(exercises: List<String>) {
        executeUseCase(
            { moveChExerciseUseCase.execute(MoveChExerciseUseCase.Params(exercises)) },
            { _event.value = MExercisesEvent.ExerciseMoved },
            { _event.value = MExercisesEvent.SWW }
        )
    }

    fun registerDayInCalendar(info: CalendarInfo) {
        executeUseCase(
            { registerDayInCalendarUseCase.execute(RegisterDayInCalendarUseCase.Params(info)) },
            { _event.value = MExercisesEvent.ExercisesRegisteredInCalendar },
            { _event.value = MExercisesEvent.SWW }
        )
    }
}