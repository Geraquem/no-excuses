package com.mmfsin.noexcuses.presentation.maximums.mgroups

import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.presentation.exercises.musculargroups.MuscGroupsEvent

sealed class ChMaxMuscGroupsEvent {
    class MuscGroups(val groups: List<MuscularGroup>) : ChMaxMuscGroupsEvent()
    class BodyImage(val isWomanImage: Boolean) : ChMaxMuscGroupsEvent()
    object SWW : ChMaxMuscGroupsEvent()
}