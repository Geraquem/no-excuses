package com.mmfsin.noexcuses.presentation.calendar.interfaces

interface ICalendarDayListener {
    fun toExercises(dayId: String, routineId: String)
    fun deleteDayData(id: String)
}