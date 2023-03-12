package com.mmfsin.noexcuses.presentation.days

import com.mmfsin.noexcuses.data.repository.DaysRepository
import com.mmfsin.noexcuses.domain.models.Day

class DaysPresenter(val view: DaysView) {

    private val repository by lazy { DaysRepository() }

    fun getDays(phaseId: String) {
        val days = repository.getDays()
        val list = mutableListOf<Day>()
        for (day in days) {
            if (day.phaseId == phaseId) list.add(day)
        }
        view.getDays(list)
    }

    fun deleteDay(day: Day) {
        val result = repository.deleteDay(day)
        if (result) view.dayDeleted()
        else view.sww()
    }
}