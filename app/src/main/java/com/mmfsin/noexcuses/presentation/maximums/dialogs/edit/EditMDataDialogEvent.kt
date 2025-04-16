package com.mmfsin.noexcuses.presentation.maximums.dialogs.edit

import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.MData

sealed class EditMDataDialogEvent {
    class GetExercise(val exercise: Exercise) : EditMDataDialogEvent()
    class GetMData(val data: MData) : EditMDataDialogEvent()
    object Edited : EditMDataDialogEvent()
    object SWW : EditMDataDialogEvent()
}