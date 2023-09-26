package com.mmfsin.noexcuses.presentation.routines.mgroups

import com.mmfsin.noexcuses.domain.models.MuscularGroup

sealed class ChMGroupsEvent {
    class MGroups(val groups: List<MuscularGroup>) : ChMGroupsEvent()
    object SomethingWentWrong : ChMGroupsEvent()
}