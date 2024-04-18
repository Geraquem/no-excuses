package com.mmfsin.noexcuses.presentation.myroutines.mroutines

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.FirstTimeUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMyRoutinesUseCase
import com.mmfsin.noexcuses.domain.usecases.UpdateMyRoutinePushPinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyRoutinesViewModel @Inject constructor(
    private val firstTimeUseCase: FirstTimeUseCase,
    private val getMyRoutinesUseCase: GetMyRoutinesUseCase,
    private val updateMyRoutinePushPinUseCase: UpdateMyRoutinePushPinUseCase
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

    fun updateMyRoutinePushPin(id: String) {
        executeUseCase(
            { updateMyRoutinePushPinUseCase.execute(UpdateMyRoutinePushPinUseCase.Params(id)) },
            { _event.value = MyRoutinesEvent.PushPinUpdated(myRoutineId = id) },
            { _event.value = MyRoutinesEvent.SWW }
        )
    }
}