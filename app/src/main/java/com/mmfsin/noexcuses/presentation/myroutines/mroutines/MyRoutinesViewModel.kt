package com.mmfsin.noexcuses.presentation.myroutines.mroutines

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.FirstTimeUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMyRoutinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyRoutinesViewModel @Inject constructor(
    private val firstTimeUseCase: FirstTimeUseCase,
    private val getMyRoutinesUseCase: GetMyRoutinesUseCase
) : BaseViewModel<MyRoutinesEvent>() {

    fun getFirstTime() {
        executeUseCase(
            { firstTimeUseCase.execute() },
            { result -> _event.value = MyRoutinesEvent.IsFistTime(result) },
            { _event.value = MyRoutinesEvent.SWW }
        )
    }

    fun getRoutines() {
        executeUseCase(
            { getMyRoutinesUseCase.execute() },
            { result -> _event.value = MyRoutinesEvent.GetMyRoutines(result) },
            { _event.value = MyRoutinesEvent.SWW }
        )
    }
}