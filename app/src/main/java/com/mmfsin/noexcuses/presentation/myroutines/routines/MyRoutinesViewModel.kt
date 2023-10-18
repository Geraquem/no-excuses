package com.mmfsin.noexcuses.presentation.myroutines.routines

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.FirstTimeUseCase
import com.mmfsin.noexcuses.domain.usecases.GetRoutinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyRoutinesViewModel @Inject constructor(
    private val firstTimeUseCase: FirstTimeUseCase,
    private val getRoutinesUseCase: GetRoutinesUseCase
) : BaseViewModel<MyRoutinesEvent>() {

    fun getFirstTime() {
        executeUseCase(
            { firstTimeUseCase.execute() },
            { result -> _event.value = MyRoutinesEvent.IsFistTime(result) },
            { _event.value = MyRoutinesEvent.SomethingWentWrong }
        )
    }

    fun getRoutines() {
        executeUseCase(
            { getRoutinesUseCase.execute() },
            { result -> _event.value = MyRoutinesEvent.GetMyRoutines(result) },
            { _event.value = MyRoutinesEvent.SomethingWentWrong }
        )
    }
}