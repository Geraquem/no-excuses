package com.mmfsin.noexcuses.data.mappers

import com.mmfsin.noexcuses.data.models.MuscularGroupDTO
import com.mmfsin.noexcuses.domain.models.MuscularGroup

fun MuscularGroupDTO.toMuscularGroup() = MuscularGroup(name, imageURL)

fun List<MuscularGroupDTO>.toMuscularGroupList() = this.map { it.toMuscularGroup() }