package com.mmfsin.noexcuses.presentation.myroutines.days.interfaces

interface IDaysListener {
    fun flowCompleted()
    fun openDeleteDayDialog(id: String)

    fun onDayClick(id: String)
    fun onDayLongClick(id: String)
}