package com.mmfsin.noexcuses.presentation.days

import com.mmfsin.noexcuses.data.repository.DaysRepository
import com.mmfsin.noexcuses.domain.models.Day

class DaysPresenter(val view: DaysView) {

    private val repository by lazy { DaysRepository() }

    fun getDays(phaseId: String) = view.getDays(repository.getDays(phaseId))

    fun deleteDay(day: Day) {
        val result = repository.deleteDay(day)
        if (result) view.dayDeleted()
        else view.sww()
    }
}