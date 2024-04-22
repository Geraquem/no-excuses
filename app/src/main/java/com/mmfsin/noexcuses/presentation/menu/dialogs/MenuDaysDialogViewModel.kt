package com.mmfsin.noexcuses.presentation.menu.dialogs

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetMyActualRoutineDaysUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMyActualRoutineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuDaysDialogViewModel @Inject constructor(
    private val getMyActualRoutineUseCase: GetMyActualRoutineUseCase,
    private val getMyActualRoutineDaysUseCase: GetMyActualRoutineDaysUseCase
) : BaseViewModel<MenuDaysDialogEvent>() {

    fun getPinnedRoutine() {
        executeUseCase(
            { getMyActualRoutineUseCase.execute() },
            { result ->
                _event.value = result?.let { MenuDaysDialogEvent.GetRoutine(it) }
                    ?: run { MenuDaysDialogEvent.SWW }
            },
            { _event.value = MenuDaysDialogEvent.SWW }
        )
    }

    fun getPinnedRoutineDays(routineId: String) {
        executeUseCase(
            { getMyActualRoutineDaysUseCase.execute(GetMyActualRoutineDaysUseCase.Params(routineId)) },
            { result -> _event.value = MenuDaysDialogEvent.GetDays(result) },
            { _event.value = MenuDaysDialogEvent.SWW }
        )
    }
}