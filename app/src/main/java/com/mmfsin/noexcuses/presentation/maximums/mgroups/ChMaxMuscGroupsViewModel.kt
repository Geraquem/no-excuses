package com.mmfsin.noexcuses.presentation.maximums.mgroups

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.CheckBodyImageUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMuscularGroupsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChMaxMuscGroupsViewModel @Inject constructor(
    private val getMuscularGroupsUseCase: GetMuscularGroupsUseCase,
    private val getBodyImageUseCase: CheckBodyImageUseCase,
) : BaseViewModel<ChMaxMuscGroupsEvent>() {

    fun getMuscularGroups() {
        executeUseCase(
            { getMuscularGroupsUseCase.execute() },
            { result ->
                _event.value = if (result.isNotEmpty()) ChMaxMuscGroupsEvent.MuscGroups(result)
                else ChMaxMuscGroupsEvent.SWW
            },
            { _event.value = ChMaxMuscGroupsEvent.SWW }
        )
    }

    fun getBodyImage() {
        executeUseCase(
            { getBodyImageUseCase.execute() },
            { result -> _event.value = ChMaxMuscGroupsEvent.BodyImage(result) },
            { _event.value = ChMaxMuscGroupsEvent.SWW }
        )
    }
}