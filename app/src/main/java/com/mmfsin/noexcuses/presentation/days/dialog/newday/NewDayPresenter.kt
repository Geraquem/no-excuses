package com.mmfsin.noexcuses.presentation.days.dialog.newday

import com.mmfsin.noexcuses.data.repository.DaysRepository
import com.mmfsin.noexcuses.domain.models.Day
import java.util.*

class NewDayPresenter(val view: NewDayView) {

    private val repository by lazy { DaysRepository() }

    fun save(id: String?, phaseId: String, name: String) {
         val finalId = id ?: run { UUID.randomUUID().toString() }
        val result = repository.addDay(Day(finalId, phaseId, name))
        if (result) view.savedInRealm() else view.sww()
    }
}