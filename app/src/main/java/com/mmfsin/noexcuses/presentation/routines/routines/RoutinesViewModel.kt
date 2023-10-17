package com.mmfsin.noexcuses.presentation.routines.routines

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.FirstTimeUseCase
import com.mmfsin.noexcuses.domain.usecases.GetRoutinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoutinesViewModel @Inject constructor(
    private val firstTimeUseCase: FirstTimeUseCase,
    private val getRoutinesUseCase: GetRoutinesUseCase
) : BaseViewModel<RoutinesEvent>() {

    fun getFirstTime() {
        executeUseCase(
            { firstTimeUseCase.execute() },
            { result -> _event.value = RoutinesEvent.IsFistTime(result) },
            { _event.value = RoutinesEvent.SomethingWentWrong }
        )
    }

    fun getRoutines() {
        executeUseCase(
            { getRoutinesUseCase.execute() },
            { result -> _event.value = RoutinesEvent.GetRoutines(result) },
            { _event.value = RoutinesEvent.SomethingWentWrong }
        )
    }
}