package com.mmfsin.noexcuses.presentation.dfroutines.dfdays

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetDefaultRoutineByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DefaultDaysDialogViewModel @Inject constructor(
    private val getDefaultRoutineByIdUseCase: GetDefaultRoutineByIdUseCase,
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

//    fun getDays(routineId: String) {
//        executeUseCase(
//            { getMyRoutineDaysUseCase.execute(GetMyRoutineDaysUseCase.Params(routineId)) },
//            { result -> _event.value = DaysDialogEvent.GetDays(result) },
//            { _event.value = DaysDialogEvent.SWW }
//        )
//    }
}