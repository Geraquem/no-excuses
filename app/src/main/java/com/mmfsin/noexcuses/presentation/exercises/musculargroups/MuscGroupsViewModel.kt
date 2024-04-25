package com.mmfsin.noexcuses.presentation.exercises.musculargroups

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetMuscularGroupsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MuscGroupsViewModel @Inject constructor(
    private val getMuscularGroupsUseCase: GetMuscularGroupsUseCase
) : BaseViewModel<MuscGroupsEvent>() {

    fun getMuscularGroups() {
        executeUseCase(
            { getMuscularGroupsUseCase.execute() },
            { result ->
                _event.value = if (result.isNotEmpty()) MuscGroupsEvent.MuscGroups(result)
                else MuscGroupsEvent.SWW
            },
            { _event.value = MuscGroupsEvent.SWW }
        )
    }
}