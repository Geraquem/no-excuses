package com.mmfsin.noexcuses.presentation.days

import com.mmfsin.noexcuses.domain.models.DayWithExercises

interface DaysView {
    fun getDays(days: List<DayWithExercises>)
    fun dayDeleted()
    fun sww()
}