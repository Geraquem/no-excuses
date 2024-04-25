package com.mmfsin.noexcuses.presentation.menu.interfaces

interface IMenuListener {
    fun onMenuDayClick(id: String, createdByUser: Boolean)
    fun onMenuMuscGroupClick(id: String)
}