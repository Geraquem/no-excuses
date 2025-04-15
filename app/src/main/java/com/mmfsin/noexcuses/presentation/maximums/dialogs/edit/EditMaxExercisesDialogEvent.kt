package com.mmfsin.noexcuses.presentation.maximums.dialogs.edit

import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.MData

sealed class EditMaxExercisesDialogEvent {
    class GetExercise(val exercise: Exercise) : EditMaxExercisesDialogEvent()
    class GetMData(val data: MData) : EditMaxExercisesDialogEvent()
    object Edited : EditMaxExercisesDialogEvent()
    object SWW : EditMaxExercisesDialogEvent()
}