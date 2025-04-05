package com.mmfsin.noexcuses.presentation.stretching

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetStretchingByMGroupUseCase
import com.mmfsin.noexcuses.domain.usecases.GetStretchingDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StretchingViewModel @Inject constructor(
    private val getStretchingDataUseCase: GetStretchingDataUseCase,
    private val getStretchingByMGroupUseCase: GetStretchingByMGroupUseCase
) : BaseViewModel<StretchingEvent>() {

    fun getStretchingData() {
        executeUseCase(
            { getStretchingDataUseCase.execute() },
            { result -> _event.value = StretchingEvent.GetStretchingData(result) },
            { _event.value = StretchingEvent.SWW }
        )
    }

    fun getStretchingByMGroup(category: String) {
        executeUseCase(
            { getStretchingByMGroupUseCase.execute(GetStretchingByMGroupUseCase.Params(category)) },
            { result -> _event.value = StretchingEvent.GetStretchingByMGroup(result) },
            { _event.value = StretchingEvent.SWW }
        )
    }
}