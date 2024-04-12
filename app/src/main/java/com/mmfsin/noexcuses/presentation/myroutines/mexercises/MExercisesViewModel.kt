package com.mmfsin.noexcuses.presentation.myroutines.mexercises

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetDayByIdUseCase
import com.mmfsin.noexcuses.domain.usecases.GetDayExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MExercisesViewModel @Inject constructor(
    private val getDayByIdUseCase: GetDayByIdUseCase,
    private val getDayExercisesUseCase: GetDayExercisesUseCase
) : BaseViewModel<MExercisesEvent>() {

    fun getDay(dayId: String) {
        executeUseCase(
            { getDayByIdUseCase.execute(GetDayByIdUseCase.Params(dayId)) },
            { result ->
                _event.value = result?.let { MExercisesEvent.GetDay(it) }
                    ?: run { MExercisesEvent.SomethingWentWrong }
            },
            { _event.value = MExercisesEvent.SomethingWentWrong }
        )
    }

    fun getDayExercises(dayId: String) {
        executeUseCase(
            { getDayExercisesUseCase.execute(GetDayExercisesUseCase.Params(dayId)) },
            { result -> _event.value = MExercisesEvent.GetDayExercises(result) },
            { _event.value = MExercisesEvent.SomethingWentWrong }
        )
    }
}