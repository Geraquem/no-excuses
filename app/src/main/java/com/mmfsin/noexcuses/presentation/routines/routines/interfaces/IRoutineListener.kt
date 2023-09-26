package com.mmfsin.noexcuses.presentation.routines.routines.interfaces

interface IRoutineListener {
    fun dayAddedToRoutine()
    fun onRoutineClick(id: String)
    fun onRoutineLongClick(id: String)
}