package com.mmfsin.noexcuses.presentation.maximums.detail

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetMaximumByExerciseIdDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MaximumDetailViewModel @Inject constructor(
    private val getMaximumByExerciseIdDataUseCase: GetMaximumByExerciseIdDataUseCase
) : BaseViewModel<MaximumDetailEvent>() {

    fun getMaximumData(exerciseId: String) {
        executeUseCase(
            {
                getMaximumByExerciseIdDataUseCase.execute(
                    GetMaximumByExerciseIdDataUseCase.Params(exerciseId)
                )
            },
            { result ->
                _event.value = result?.let {
                    MaximumDetailEvent.GetData(it)
                } ?: run { MaximumDetailEvent.SWW }
            },
            { _event.value = MaximumDetailEvent.SWW }
        )
    }
}