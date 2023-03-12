package com.mmfsin.noexcuses.presentation.days

import com.mmfsin.noexcuses.domain.models.Day

interface DaysView {
    fun getDays(days: List<Day>)
    fun dayDeleted()
    fun sww()
}