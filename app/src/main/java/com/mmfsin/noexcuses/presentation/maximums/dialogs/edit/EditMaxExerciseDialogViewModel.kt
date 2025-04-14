package com.mmfsin.noexcuses.presentation.maximums.dialogs.edit

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetExerciseUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMDataByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditMaxExerciseDialogViewModel @Inject constructor(
    private val getExerciseUseCase: GetExerciseUseCase,
    private val getMDataByIdUseCase: GetMDataByIdUseCase
) : BaseViewModel<EditMaxExercisesDialogEvent>() {

    fun getExercise(id: String) {
        executeUseCase(
            { getExerciseUseCase.execute(GetExerciseUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { EditMaxExercisesDialogEvent.GetExercise(it) }
                    ?: run { EditMaxExercisesDialogEvent.SWW }
            },
            { _event.value = EditMaxExercisesDialogEvent.SWW }
        )
    }

    fun getMData(mDataId: String) {
        executeUseCase(
            { getMDataByIdUseCase.execute(GetMDataByIdUseCase.Params(mDataId)) },
            { result ->
                _event.value = result?.let { EditMaxExercisesDialogEvent.GetMData(it) }
                    ?: run { EditMaxExercisesDialogEvent.SWW }
            },
            { _event.value = EditMaxExercisesDialogEvent.SWW }
        )
    }
}