package com.mmfsin.noexcuses.presentation.dfroutines.dfroutines

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetDefaultRoutinesUseCase
import com.mmfsin.noexcuses.domain.usecases.UpdateDefaultRoutinePushPinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DefaultRoutinesViewModel @Inject constructor(
    private val getDefaultRoutinesUseCase: GetDefaultRoutinesUseCase,
    private val updateDefaultRoutinePushPinUseCase: UpdateDefaultRoutinePushPinUseCase,
) : BaseViewModel<DefaultRoutinesEvent>() {

    fun getDefaultRoutines() {
        executeUseCase(
            { getDefaultRoutinesUseCase.execute() },
            { result -> _event.value = DefaultRoutinesEvent.GetDefaultRoutines(result) },
            { _event.value = DefaultRoutinesEvent.SWW }
        )
    }

    fun updateDefaultRoutinePushPin(routineId: String) {
        executeUseCase(
            {
                updateDefaultRoutinePushPinUseCase.execute(
                    UpdateDefaultRoutinePushPinUseCase.Params(routineId)
                )
            },
            { _event.value = DefaultRoutinesEvent.PushPinUpdated(routineId) },
            { _event.value = DefaultRoutinesEvent.SWW }
        )
    }
}