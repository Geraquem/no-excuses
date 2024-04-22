package com.mmfsin.noexcuses.presentation.menu

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.CheckVersionUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMuscularGroupsUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMyActualRoutineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val checkVersionUseCase: CheckVersionUseCase,
    private val getMyActualRoutine: GetMyActualRoutineUseCase,
    private val getMuscularGroups: GetMuscularGroupsUseCase
) : BaseViewModel<MenuEvent>() {

    fun checkVersion() {
        executeUseCase(
            { checkVersionUseCase.execute() },
            { _event.value = MenuEvent.Completed },
            { _event.value = MenuEvent.SWW }
        )
    }

    fun getMenuItems() {
        executeUseCase(
            { getMyActualRoutine.execute() },
            { result -> _event.value = MenuEvent.ActualRoutine(result) },
            { _event.value = MenuEvent.SWW }
        )
    }

    fun getMuscularGroups() {
        executeUseCase(
            { getMuscularGroups.execute() },
            { result -> _event.value = MenuEvent.GetMuscularGroups(result) },
            { _event.value = MenuEvent.SWW }
        )
    }
}