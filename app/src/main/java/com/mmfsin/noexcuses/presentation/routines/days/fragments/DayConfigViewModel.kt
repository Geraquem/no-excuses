package com.mmfsin.noexcuses.presentation.routines.days.fragments

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.AddDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DayConfigViewModel @Inject constructor(
    private val addDayUseCase: AddDayUseCase
) : BaseViewModel<DayConfigEvent>() {

    fun addDay(routineId: String, title: String) {
        executeUseCase(
            { addDayUseCase.execute(AddDayUseCase.Params(routineId, title)) },
            { _event.value = DayConfigEvent.FlowCompleted },
            { _event.value = DayConfigEvent.SomethingWentWrong }
        )
    }

}