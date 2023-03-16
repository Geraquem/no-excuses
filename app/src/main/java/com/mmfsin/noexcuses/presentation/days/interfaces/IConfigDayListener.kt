package com.mmfsin.noexcuses.presentation.days.interfaces

import com.mmfsin.noexcuses.domain.models.Day

interface IConfigDayListener {
    fun edit(day: Day)
    fun delete(day: Day)
}