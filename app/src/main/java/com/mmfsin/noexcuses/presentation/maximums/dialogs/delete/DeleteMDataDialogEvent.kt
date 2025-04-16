package com.mmfsin.noexcuses.presentation.maximums.dialogs.delete

import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.MData

sealed class DeleteMDataDialogEvent {
    class GetExercise(val exercise: Exercise) : DeleteMDataDialogEvent()
    class GetMData(val data: MData) : DeleteMDataDialogEvent()
    object Deleted : DeleteMDataDialogEvent()
    object SWW : DeleteMDataDialogEvent()
}