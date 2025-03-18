package com.mmfsin.noexcuses.presentation.exercises.musculargroups

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.CheckBodyImageUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMuscularGroupsUseCase
import com.mmfsin.noexcuses.domain.usecases.SwitchBodyImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MuscGroupsViewModel @Inject constructor(
    private val getMuscularGroupsUseCase: GetMuscularGroupsUseCase,
    private val getBodyImageUseCase: CheckBodyImageUseCase
) : BaseViewModel<MuscGroupsEvent>() {

    fun getBodyImage() {
        executeUseCase(
            { getBodyImageUseCase.execute() },
            { result -> _event.value = MuscGroupsEvent.BodyImage(result) },
            { _event.value = MuscGroupsEvent.SWW }
        )
    }

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