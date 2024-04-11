package com.mmfsin.noexcuses.presentation.myroutines.mroutines.interfaces

interface IMyRoutineListener {
    fun onRoutineClick(id: String)
    fun onRoutineLongClick(id: String)

    fun onDayClick(routineId: String, dayId: String)
    fun dayAddedToRoutine()

    /** when add or edit */
    fun flowCompleted()
    fun deleteRoutine(id: String)
}