package com.mmfsin.noexcuses.presentation.menu

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.CheckBodyImageUseCase
import com.mmfsin.noexcuses.domain.usecases.CheckVersionUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMuscularGroupsUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMyActualRoutineUseCase
import com.mmfsin.noexcuses.domain.usecases.GetPinnedNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val checkVersionUseCase: CheckVersionUseCase,
    private val getMyActualRoutine: GetMyActualRoutineUseCase,
    private val getMuscularGroups: GetMuscularGroupsUseCase,
    private val getPinnedNoteUseCase: GetPinnedNoteUseCase,
    private val getBodyImageUseCase: CheckBodyImageUseCase,
) : BaseViewModel<MenuEvent>() {

    fun checkVersion() {
        executeUseCase(
            { checkVersionUseCase.execute() },
            { _event.value = MenuEvent.VersionCompleted },
            { _event.value = MenuEvent.SWW }
        )
    }

    fun secondCallPinnedRoutine() {
        executeUseCase(
            { getMyActualRoutine.execute() },
            { result -> _event.value = MenuEvent.SecondCallPinnedRoutine(result) },
            { _event.value = MenuEvent.SWW }
        )
    }

    fun secondCallPinnedNote() {
        executeUseCase(
            { getPinnedNoteUseCase.execute() },
            { result -> _event.value = MenuEvent.SecondCallPinnedNote(result) },
            { _event.value = MenuEvent.SWW }
        )
    }

    fun getMyActualRoutine() {
        executeUseCase(
            { getMyActualRoutine.execute() },
            { result -> _event.value = MenuEvent.ActualRoutine(result) },
            { _event.value = MenuEvent.SWW }
        )
    }

    fun getBodyImage() {
        executeUseCase(
            { getBodyImageUseCase.execute() },
            { result -> _event.value = MenuEvent.BodyImage(result) },
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

    fun getPinnedNote() {
        executeUseCase(
            { getPinnedNoteUseCase.execute() },
            { result -> _event.value = MenuEvent.PinnedNote(result) },
            { _event.value = MenuEvent.SWW }
        )
    }
}