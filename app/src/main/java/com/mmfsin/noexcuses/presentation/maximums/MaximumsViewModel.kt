package com.mmfsin.noexcuses.presentation.maximums

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetAllMaximumDataUseCase
import com.mmfsin.noexcuses.domain.usecases.GetFavExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MaximumsViewModel @Inject constructor(
    private val getAllMaximumDataUseCase: GetAllMaximumDataUseCase
) : BaseViewModel<MaximumsEvent>() {

    fun getMaximumData() {
        executeUseCase(
            { getAllMaximumDataUseCase.execute() },
            { result -> _event.value = MaximumsEvent.GetMaximums(result) },
            { _event.value = MaximumsEvent.SWW }
        )
    }
}