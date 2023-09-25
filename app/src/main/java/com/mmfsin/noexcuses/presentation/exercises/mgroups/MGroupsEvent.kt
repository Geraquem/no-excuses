package com.mmfsin.noexcuses.presentation.exercises.mgroups

import com.mmfsin.noexcuses.domain.models.MuscularGroup

sealed class MGroupsEvent {
    class MGroups(val groups: List<MuscularGroup>) : MGroupsEvent()
    object SomethingWentWrong : MGroupsEvent()
}