package com.mmfsin.noexcuses.presentation.stretching

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetMuscularGroupsUseCase
import com.mmfsin.noexcuses.domain.usecases.GetStretchingByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StretchingViewModel @Inject constructor(
    private val getMuscularGroupsUseCase: GetMuscularGroupsUseCase,
    private val getStretchingByCategoryUseCase: GetStretchingByCategoryUseCase
) : BaseViewModel<StretchingEvent>() {

    fun getMuscularGroups() {
        executeUseCase(
            { getMuscularGroupsUseCase.execute() },
            { result -> _event.value = StretchingEvent.GetMuscularGroups(result) },
            { _event.value = StretchingEvent.SWW }
        )
    }

    fun getStretching(category: String) {
        executeUseCase(
            { getStretchingByCategoryUseCase.execute(GetStretchingByCategoryUseCase.Params(category)) },
            { result -> _event.value = StretchingEvent.GetStretching(result) },
            { _event.value = StretchingEvent.SWW }
        )
    }
}