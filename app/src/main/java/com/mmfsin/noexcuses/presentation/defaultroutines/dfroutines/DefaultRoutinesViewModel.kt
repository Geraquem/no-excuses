package com.mmfsin.noexcuses.presentation.defaultroutines.dfroutines

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetPredRoutinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DefaultRoutinesViewModel @Inject constructor(
    private val getPredRoutinesUseCase: GetPredRoutinesUseCase
) : BaseViewModel<DefaultRoutinesEvent>() {

    fun getRoutines() {
        executeUseCase(
            { getPredRoutinesUseCase.execute() },
            { result -> _event.value = DefaultRoutinesEvent.GetDefaultRoutines(result) },
            { _event.value = DefaultRoutinesEvent.SomethingWentWrong }
        )
    }
}