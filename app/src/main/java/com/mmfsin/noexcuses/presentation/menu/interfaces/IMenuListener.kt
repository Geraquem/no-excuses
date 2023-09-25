package com.mmfsin.noexcuses.presentation.menu.interfaces

import com.mmfsin.noexcuses.domain.models.MenuAction

interface IMenuListener {
    fun onItemClick(action: MenuAction)
}