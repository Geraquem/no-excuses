package com.mmfsin.noexcuses.presentation.myroutines.mgroups

import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.presentation.exercises.musculargroups.MuscGroupsEvent

sealed class ChMuscGroupsEvent {
    class MuscGroups(val groups: List<MuscularGroup>) : ChMuscGroupsEvent()
    class BodyImage(val isWomanImage: Boolean) : ChMuscGroupsEvent()
    object SWW : ChMuscGroupsEvent()
}