package com.mmfsin.noexcuses.presentation.myroutines.myroutines

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetMyRoutinesUseCase
import com.mmfsin.noexcuses.domain.usecases.UpdateMyRoutinePushPinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyRoutinesViewModel @Inject constructor(
    private val getMyRoutinesUseCase: GetMyRoutinesUseCase,
    private val updateMyRoutinePushPinUseCase: UpdateMyRoutinePushPinUseCase
) : BaseViewModel<MyRoutinesEvent>() {

    fun getRoutines() {
        executeUseCase(
            { getMyRoutinesUseCase.execute() },
            { result -> _event.value = MyRoutinesEvent.GetMyRoutines(result) },
            { _event.value = MyRoutinesEvent.SWW }
        )
    }

    fun updateMyRoutinePushPin(routineId: String) {
        executeUseCase(
            { updateMyRoutinePushPinUseCase.execute(UpdateMyRoutinePushPinUseCase.Params(routineId)) },
            { _event.value = MyRoutinesEvent.PushPinUpdated(routineId) },
            { _event.value = MyRoutinesEvent.SWW }
        )
    }
}