package com.mmfsin.noexcuses.presentation.dfroutines.dfroutines.interfaces

interface IDefaultRoutineListener {
    fun onRoutineClick(routineId: String)
    fun onDayClick(routineId: String, dayId: String)
    fun onRoutinePushPinClick(routineId: String)
}