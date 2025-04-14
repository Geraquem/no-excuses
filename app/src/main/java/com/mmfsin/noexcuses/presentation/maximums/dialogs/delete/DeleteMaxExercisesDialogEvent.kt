package com.mmfsin.noexcuses.presentation.maximums.dialogs.delete

import com.mmfsin.noexcuses.domain.models.MData

sealed class DeleteMaxExercisesDialogEvent {
    class GetMData(val data: MData) : DeleteMaxExercisesDialogEvent()
    object Deleted : DeleteMaxExercisesDialogEvent()
    object SWW : DeleteMaxExercisesDialogEvent()
}