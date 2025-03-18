package com.mmfsin.noexcuses.presentation.exercises.musculargroups

import com.mmfsin.noexcuses.domain.models.MuscularGroup

sealed class MuscGroupsEvent {
    class MuscGroups(val groups: List<MuscularGroup>) : MuscGroupsEvent()
    class BodyImage(val isWomanImage: Boolean) : MuscGroupsEvent()
    object SWW : MuscGroupsEvent()
}