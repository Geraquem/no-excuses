package com.mmfsin.noexcuses.presentation.myroutines.mgroups

import com.mmfsin.noexcuses.domain.models.MuscularGroup

sealed class ChMuscGroupsEvent {
    class MuscGroups(val groups: List<MuscularGroup>) : ChMuscGroupsEvent()
    object SWW : ChMuscGroupsEvent()
}