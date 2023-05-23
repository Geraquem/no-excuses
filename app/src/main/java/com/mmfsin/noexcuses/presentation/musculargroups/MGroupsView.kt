package com.mmfsin.noexcuses.presentation.musculargroups

import com.mmfsin.noexcuses.domain.models.Exercise

interface MGroupsView {
    fun getMGroups(exercises: List<Exercise>)
    fun sww()
}