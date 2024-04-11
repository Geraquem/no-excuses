package com.mmfsin.noexcuses.presentation.myroutines.days

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetMyRoutineByIdUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMyRoutineDaysUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DaysDialogViewModel @Inject constructor(
    private val getMyRoutineByIdUseCase: GetMyRoutineByIdUseCase,
    private val getMyRoutineDaysUseCase: GetMyRoutineDaysUseCase
) : BaseViewModel<DaysDialogEvent>() {

    fun getRoutine(routineId: String) {
        executeUseCase(
            { getMyRoutineByIdUseCase.execute(GetMyRoutineByIdUseCase.Params(routineId)) },
            { result ->
                _event.value = result?.let { DaysDialogEvent.GetRoutine(it) }
                    ?: run { DaysDialogEvent.SWW }
            },
            { _event.value = DaysDialogEvent.SWW }
        )
    }

    fun getDays(routineId: String) {
        executeUseCase(
            { getMyRoutineDaysUseCase.execute(GetMyRoutineDaysUseCase.Params(routineId)) },
            { result -> _event.value = DaysDialogEvent.GetDays(result) },
            { _event.value = DaysDialogEvent.SWW }
        )
    }
}