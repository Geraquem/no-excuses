package com.mmfsin.noexcuses.presentation.myroutines.mexercises

import android.util.Log
import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetDayByIdUseCase
import com.mmfsin.noexcuses.domain.usecases.GetDayExercisesUseCase
import com.mmfsin.noexcuses.domain.usecases.MoveChExerciseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MExercisesViewModel @Inject constructor(
    private val getDayByIdUseCase: GetDayByIdUseCase,
    private val getDayExercisesUseCase: GetDayExercisesUseCase,
    private val moveChExerciseUseCase: MoveChExerciseUseCase
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
            { _event.value = MExercisesEvent.AAA },
            { _event.value = MExercisesEvent.SWW }
        )
    }
}