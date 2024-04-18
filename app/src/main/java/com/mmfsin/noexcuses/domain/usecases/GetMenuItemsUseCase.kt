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
            MenuItem(
                1,
                R.drawable.iv_routines,
                gs(R.string.menu_routines),
                gs(R.string.menu_routines_desc),
                ROUTINES
            ),
            MenuItem(
                2,
                R.drawable.iv_routines,
                gs(R.string.menu_my_routines),
                gs(R.string.menu_my_routines_desc),
                MY_ROUTINES
            ),
            MenuItem(
                3,
                R.drawable.iv_exercises,
                gs(R.string.menu_exercises),
                gs(R.string.menu_exercises_desc),
                EXERCISES
            ),
            MenuItem(
                4,
                R.drawable.iv_notes,
                gs(R.string.menu_notes),
                gs(R.string.menu_notes_desc),
                NOTES
            )
        )
        return items.sortedBy { it.order }
    }

    private fun gs(str: Int) = context.getString(str)
}