package com.mmfsin.noexcuses.presentation.myroutines.exercises

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChExercisesViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase
) : BaseViewModel<ChExercisesEvent>() {

    fun getExercises(mGroup: String?) {
        mGroup?.let { mGroupName ->
            executeUseCase(
                { getExercisesUseCase.execute(GetExercisesUseCase.Params(mGroupName)) },
                { result ->
                    _event.value = if (result.isNotEmpty()) ChExercisesEvent.GetExercises(result)
                    else ChExercisesEvent.SomethingWentWrong
                },
                { _event.value = ChExercisesEvent.SomethingWentWrong }
            )
        }
    }
}