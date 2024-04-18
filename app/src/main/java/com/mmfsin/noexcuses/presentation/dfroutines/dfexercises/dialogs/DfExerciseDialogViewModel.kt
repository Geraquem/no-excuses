package com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.dialogs

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetDefaultExerciseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DfExerciseDialogViewModel @Inject constructor(
    private val getDefaultExerciseUseCase: GetDefaultExerciseUseCase
) : BaseViewModel<DfExerciseDialogEvent>() {

    fun getDefaultExercise(id: String) {
        executeUseCase(
            { getDefaultExerciseUseCase.execute(GetDefaultExerciseUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { DfExerciseDialogEvent.GetDefaultExercise(it) }
                    ?: run { DfExerciseDialogEvent.SWW }
            },
            { _event.value = DfExerciseDialogEvent.SWW }
        )
    }
}