package com.mmfsin.noexcuses.domain.mappers

import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.DayWithExercises

fun DayWithExercises.toDay() = Day(
    id = this.day.id,
    phaseId = this.day.phaseId,
    name = this.day.name
)