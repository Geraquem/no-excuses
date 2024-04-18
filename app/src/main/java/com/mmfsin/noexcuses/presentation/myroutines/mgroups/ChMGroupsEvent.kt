package com.mmfsin.noexcuses.presentation.myroutines.mgroups

import com.mmfsin.noexcuses.domain.models.MuscularGroup

sealed class ChMGroupsEvent {
    class MGroups(val groups: List<MuscularGroup>) : ChMGroupsEvent()
    object SWW : ChMGroupsEvent()
}