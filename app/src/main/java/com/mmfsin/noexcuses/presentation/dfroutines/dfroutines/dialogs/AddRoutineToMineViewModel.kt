package com.mmfsin.noexcuses.presentation.dfroutines.dfroutines.dialogs

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.AddDfRoutineToMineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddRoutineToMineViewModel @Inject constructor(
    private val addDfRoutineToMineUseCase: AddDfRoutineToMineUseCase
) : BaseViewModel<AddRoutineToMineEvent>() {

    fun addRoutineToMine(routineId: String) {
        executeUseCase(
            { addDfRoutineToMineUseCase.execute(AddDfRoutineToMineUseCase.Params(routineId)) },
            { _event.value = AddRoutineToMineEvent.RoutineAddedToMine },
            { _event.value = AddRoutineToMineEvent.SWW }
        )
    }
}