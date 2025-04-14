package com.mmfsin.noexcuses.presentation.maximums.dialogs.delete

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.DeleteMDataByIdUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMDataByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeleteMaxExerciseDialogViewModel @Inject constructor(
    private val getMDataByIdUseCase: GetMDataByIdUseCase,
    private val deleteMDataByIdUseCase: DeleteMDataByIdUseCase
) : BaseViewModel<DeleteMaxExercisesDialogEvent>() {

    fun getMData(mDataId: String) {
        executeUseCase(
            { getMDataByIdUseCase.execute(GetMDataByIdUseCase.Params(mDataId)) },
            { result ->
                _event.value = result?.let { DeleteMaxExercisesDialogEvent.GetMData(it) }
                    ?: run { DeleteMaxExercisesDialogEvent.SWW }
            },
            { _event.value = DeleteMaxExercisesDialogEvent.SWW }
        )
    }

    fun deleteMData(mDataId: String) {
        executeUseCase(
            { deleteMDataByIdUseCase.execute(DeleteMDataByIdUseCase.Params(mDataId)) },
            { _event.value = DeleteMaxExercisesDialogEvent.Deleted },
            { _event.value = DeleteMaxExercisesDialogEvent.SWW }
        )
    }
}