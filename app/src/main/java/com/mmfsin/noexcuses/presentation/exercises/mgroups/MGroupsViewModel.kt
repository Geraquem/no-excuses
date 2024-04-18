package com.mmfsin.noexcuses.presentation.exercises.mgroups

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetMuscularGroupsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MGroupsViewModel @Inject constructor(
    private val getMuscularGroupsUseCase: GetMuscularGroupsUseCase
) : BaseViewModel<MGroupsEvent>() {

    fun getMuscularGroups() {
        executeUseCase(
            { getMuscularGroupsUseCase.execute() },
            { result ->
                _event.value = if (result.isNotEmpty()) MGroupsEvent.MGroups(result)
                else MGroupsEvent.SWW
            },
            { _event.value = MGroupsEvent.SWW }
        )
    }
}