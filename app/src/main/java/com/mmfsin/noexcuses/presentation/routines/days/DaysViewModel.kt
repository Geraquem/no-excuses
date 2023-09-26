package com.mmfsin.noexcuses.presentation.routines.days

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetDaysUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DaysViewModel @Inject constructor(
    private val getDaysUseCase: GetDaysUseCase
) : BaseViewModel<DaysEvent>() {

    fun getDays(routineId: String) {
        executeUseCase(
            { getDaysUseCase.execute(GetDaysUseCase.Params(routineId)) },
            { result -> _event.value = DaysEvent.GetDays(result) },
            { _event.value = DaysEvent.SomethingWentWrong }
        )
    }
}