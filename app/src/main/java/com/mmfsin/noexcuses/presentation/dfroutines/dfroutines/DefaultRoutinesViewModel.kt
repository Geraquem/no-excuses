package com.mmfsin.noexcuses.presentation.dfroutines.dfroutines

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetDefaultRoutinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DefaultRoutinesViewModel @Inject constructor(
    private val getDefaultRoutinesUseCase: GetDefaultRoutinesUseCase
) : BaseViewModel<DefaultRoutinesEvent>() {

    fun getDefaultRoutines() {
        executeUseCase(
            { getDefaultRoutinesUseCase.execute() },
            { result -> _event.value = DefaultRoutinesEvent.GetDefaultRoutines(result) },
            { _event.value = DefaultRoutinesEvent.SomethingWentWrong }
        )
    }
}