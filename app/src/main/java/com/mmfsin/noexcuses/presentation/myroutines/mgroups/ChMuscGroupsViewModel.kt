package com.mmfsin.noexcuses.presentation.myroutines.mgroups

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.CheckBodyImageUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMuscularGroupsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChMuscGroupsViewModel @Inject constructor(
    private val getMuscularGroupsUseCase: GetMuscularGroupsUseCase,
    private val getBodyImageUseCase: CheckBodyImageUseCase,
) : BaseViewModel<ChMuscGroupsEvent>() {

    fun getMuscularGroups() {
        executeUseCase(
            { getMuscularGroupsUseCase.execute() },
            { result ->
                _event.value = if (result.isNotEmpty()) ChMuscGroupsEvent.MuscGroups(result)
                else ChMuscGroupsEvent.SWW
            },
            { _event.value = ChMuscGroupsEvent.SWW }
        )
    }

    fun getBodyImage() {
        executeUseCase(
            { getBodyImageUseCase.execute() },
            { result -> _event.value = ChMuscGroupsEvent.BodyImage(result) },
            { _event.value = ChMuscGroupsEvent.SWW }
        )
    }
}