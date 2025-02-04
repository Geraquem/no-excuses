package com.mmfsin.noexcuses.presentation.menu.interfaces

interface IMenuListener {
    fun onMenuDayClick(routineId: String, dayId: String, createdByUser: Boolean)
    fun onMenuMuscGroupClick(muscularGroupId: String)
}