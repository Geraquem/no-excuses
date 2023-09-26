package com.mmfsin.noexcuses.domain.usecases

import android.content.Context
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.domain.models.MenuAction.*
import com.mmfsin.noexcuses.domain.models.MenuItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetMenuItemsUseCase @Inject constructor(@ApplicationContext val context: Context) :
    BaseUseCaseNoParams<List<MenuItem>>() {

    override suspend fun execute(): List<MenuItem> {
        val items = listOf(
            MenuItem(1, gs(R.string.menu_routines), R.drawable.iv_routines, ROUTINES),
            MenuItem(2, gs(R.string.menu_exercises), R.drawable.iv_exercises, EXERCISES),
            MenuItem(3, gs(R.string.menu_chronometer), R.drawable.iv_exercises, CHRONOMETER)
        )
        return items.sortedBy { it.order }
    }

    private fun gs(str: Int) = context.getString(str)
}