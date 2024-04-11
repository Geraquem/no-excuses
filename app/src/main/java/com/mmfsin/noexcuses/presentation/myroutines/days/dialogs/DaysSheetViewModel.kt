package com.mmfsin.noexcuses.presentation.myroutines.days.dialogs

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetMyRoutineByIdUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMyRoutineDaysUseCase
import com.mmfsin.noexcuses.presentation.myroutines.days.DaysEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DaysSheetViewModel @Inject constructor(
    private val getMyRoutineByIdUseCase: GetMyRoutineByIdUseCase,
    private val getMyRoutineDaysUseCase: GetMyRoutineDaysUseCase
) : BaseViewModel<DaysSheetEvent>() {

    fun getRoutine(routineId: String) {
        executeUseCase(
            { getMyRoutineByIdUseCase.execute(GetMyRoutineByIdUseCase.Params(routineId)) },
            { result ->
                _event.value = result?.let { DaysSheetEvent.GetRoutine(it) }
                    ?: run { DaysSheetEvent.SWW }
            },
            { _event.value = DaysSheetEvent.SWW }
        )
    }

    fun getDays(routineId: String) {
        executeUseCase(
            { getMyRoutineDaysUseCase.execute(GetMyRoutineDaysUseCase.Params(routineId)) },
            { result -> _event.value = DaysSheetEvent.GetDays(result) },
            { _event.value = DaysSheetEvent.SWW }
        )
    }
}