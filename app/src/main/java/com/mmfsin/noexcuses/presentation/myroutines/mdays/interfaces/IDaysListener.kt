package com.mmfsin.noexcuses.presentation.myroutines.mdays.interfaces

interface IDaysListener {
    fun flowCompleted()
    fun openDeleteDayDialog(id: String)

    fun onDayClick(id: String)
    fun onDayLongClick(id: String)
}