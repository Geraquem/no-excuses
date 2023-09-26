package com.mmfsin.noexcuses.presentation.routines.routines.interfaces

interface IRoutineListener {
    fun onRoutineClick(id: String)
    fun onRoutineLongClick(id: String)

    fun onDayClick(routineId: String, dayId: String)
    fun dayAddedToRoutine()
}