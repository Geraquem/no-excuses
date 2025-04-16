package com.mmfsin.noexcuses.presentation.maximums.dialogs.delete

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.DeleteMDataByIdUseCase
import com.mmfsin.noexcuses.domain.usecases.DeleteMaximumDataUseCase
import com.mmfsin.noexcuses.domain.usecases.GetExerciseUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMDataByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeleteMDataDialogViewModel @Inject constructor(
    private val getExerciseUseCase: GetExerciseUseCase,
    private val deleteMaximumDataUseCase: DeleteMaximumDataUseCase,
    private val getMDataByIdUseCase: GetMDataByIdUseCase,
    private val deleteMDataByIdUseCase: DeleteMDataByIdUseCase
) : BaseViewModel<DeleteMDataDialogEvent>() {

    fun getExercise(exerciseId: String) {
        executeUseCase(
            { getExerciseUseCase.execute(GetExerciseUseCase.Params(exerciseId)) },
            { result ->
                _event.value = result?.let { DeleteMDataDialogEvent.GetExercise(it) }
                    ?: run { DeleteMDataDialogEvent.SWW }
            },
            { _event.value = DeleteMDataDialogEvent.SWW }
        )
    }

    fun deleteMaximumData(exerciseId: String) {
        executeUseCase(
            { deleteMaximumDataUseCase.execute(DeleteMaximumDataUseCase.Params(exerciseId)) },
            { _event.value = DeleteMDataDialogEvent.Deleted },
            { _event.value = DeleteMDataDialogEvent.SWW }
        )
    }

    fun getMData(mDataId: String) {
        executeUseCase(
            { getMDataByIdUseCase.execute(GetMDataByIdUseCase.Params(mDataId)) },
            { result ->
                _event.value = result?.let { DeleteMDataDialogEvent.GetMData(it) }
                    ?: run { DeleteMDataDialogEvent.SWW }
            },
            { _event.value = DeleteMDataDialogEvent.SWW }
        )
    }

    fun deleteMData(mDataId: String) {
        executeUseCase(
            { deleteMDataByIdUseCase.execute(DeleteMDataByIdUseCase.Params(mDataId)) },
            { _event.value = DeleteMDataDialogEvent.Deleted },
            { _event.value = DeleteMDataDialogEvent.SWW }
        )
    }
}