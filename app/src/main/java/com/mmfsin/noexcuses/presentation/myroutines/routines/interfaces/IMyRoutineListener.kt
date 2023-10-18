package com.mmfsin.noexcuses.presentation.myroutines.routines.interfaces

interface IMyRoutineListener {
    fun onRoutineClick(id: String)
    fun onRoutineLongClick(id: String)

    fun onDayClick(routineId: String, dayId: String)
    fun dayAddedToRoutine()
}