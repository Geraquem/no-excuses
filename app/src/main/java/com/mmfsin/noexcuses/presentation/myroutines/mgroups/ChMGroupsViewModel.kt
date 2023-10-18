package com.mmfsin.noexcuses.presentation.myroutines.mgroups

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetMuscularGroupsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChMGroupsViewModel @Inject constructor(
    private val getMuscularGroupsUseCase: GetMuscularGroupsUseCase
) : BaseViewModel<ChMGroupsEvent>() {

    fun getMuscularGroups() {
        executeUseCase(
            { getMuscularGroupsUseCase.execute() },
            { result ->
                _event.value = if (result.isNotEmpty()) ChMGroupsEvent.MGroups(result)
                else ChMGroupsEvent.SomethingWentWrong
            },
            { _event.value = ChMGroupsEvent.SomethingWentWrong }
        )
    }
}