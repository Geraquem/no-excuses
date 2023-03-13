package com.mmfsin.noexcuses.presentation.days.interfaces

import com.mmfsin.noexcuses.domain.models.Day

interface IDayListener {
    fun onClick(day: Day)
}