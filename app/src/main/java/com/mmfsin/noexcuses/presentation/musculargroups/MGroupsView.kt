package com.mmfsin.noexcuses.presentation.musculargroups

import com.mmfsin.noexcuses.domain.models.MuscularGroup

interface MGroupsView {
    fun getMuscularGroups(exercises: List<MuscularGroup>)
    fun sww()
}