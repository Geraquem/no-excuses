package com.mmfsin.noexcuses.presentation.routines.days

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetRoutineDaysUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DaysViewModel @Inject constructor(
    private val getRoutineDaysUseCase: GetRoutineDaysUseCase
) : BaseViewModel<DaysEvent>() {

    fun getDays(routineId: String) {
        executeUseCase(
            { getRoutineDaysUseCase.execute(GetRoutineDaysUseCase.Params(routineId)) },
            { result -> _event.value = DaysEvent.GetDays(result) },
            { _event.value = DaysEvent.SomethingWentWrong }
        )
    }
}