package com.mmfsin.noexcuses.presentation.maximums.dialogs.edit

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.models.TempMaximumData
import com.mmfsin.noexcuses.domain.usecases.EditMDataUseCase
import com.mmfsin.noexcuses.domain.usecases.GetExerciseUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMDataByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditMDataDialogViewModel @Inject constructor(
    private val getExerciseUseCase: GetExerciseUseCase,
    private val getMDataByIdUseCase: GetMDataByIdUseCase,
    private val editMDataUseCase: EditMDataUseCase
) : BaseViewModel<EditMDataDialogEvent>() {

    fun getExercise(id: String) {
        executeUseCase(
            { getExerciseUseCase.execute(GetExerciseUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { EditMDataDialogEvent.GetExercise(it) }
                    ?: run { EditMDataDialogEvent.SWW }
            },
            { _event.value = EditMDataDialogEvent.SWW }
        )
    }

    fun getMData(mDataId: String) {
        executeUseCase(
            { getMDataByIdUseCase.execute(GetMDataByIdUseCase.Params(mDataId)) },
            { result ->
                _event.value = result?.let { EditMDataDialogEvent.GetMData(it) }
                    ?: run { EditMDataDialogEvent.SWW }
            },
            { _event.value = EditMDataDialogEvent.SWW }
        )
    }

    fun editMData(mDataId: String, tmpData: TempMaximumData) {
        executeUseCase(
            { editMDataUseCase.execute(EditMDataUseCase.Params(mDataId, tmpData)) },
            { _event.value = EditMDataDialogEvent.Edited },
            { _event.value = EditMDataDialogEvent.SWW }
        )
    }
}