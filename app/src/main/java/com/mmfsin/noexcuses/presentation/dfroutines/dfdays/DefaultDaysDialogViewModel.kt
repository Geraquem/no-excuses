package com.mmfsin.noexcuses.presentation.dfroutines.dfdays

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetDefaultRoutineByIdUseCase
import com.mmfsin.noexcuses.domain.usecases.GetDefaultRoutineDaysUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DefaultDaysDialogViewModel @Inject constructor(
    private val getDefaultRoutineByIdUseCase: GetDefaultRoutineByIdUseCase,
    private val getDefaultRoutineDaysUseCase: GetDefaultRoutineDaysUseCase
) : BaseViewModel<DefaultDaysDialogEvent>() {

    fun getDefaultRoutine(routineId: String) {
        executeUseCase(
            { getDefaultRoutineByIdUseCase.execute(GetDefaultRoutineByIdUseCase.Params(routineId)) },
            { result ->
                _event.value = result?.let { DefaultDaysDialogEvent.GetDefaultRoutine(it) }
                    ?: run { DefaultDaysDialogEvent.SWW }
            },
            { _event.value = DefaultDaysDialogEvent.SWW }
        )
    }

    fun getDefaultDays(routineId: String) {
        executeUseCase(
            { getDefaultRoutineDaysUseCase.execute(GetDefaultRoutineDaysUseCase.Params(routineId)) },
            { result -> _event.value = DefaultDaysDialogEvent.GetDefaultDays(result) },
            { _event.value = DefaultDaysDialogEvent.SWW }
        )
    }
}