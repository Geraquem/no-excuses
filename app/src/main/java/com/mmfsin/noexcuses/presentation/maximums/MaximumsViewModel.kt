package com.mmfsin.noexcuses.presentation.maximums

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetFavExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MaximumsViewModel @Inject constructor(
    private val getFavExercisesUseCase: GetFavExercisesUseCase
) : BaseViewModel<MaximumsEvent>() {

    fun getMaximumData() {
        executeUseCase(
            { getFavExercisesUseCase.execute() },
            { result -> _event.value = MaximumsEvent.GetMaximums(emptyList()) },
            { _event.value = MaximumsEvent.SWW }
        )
    }
}