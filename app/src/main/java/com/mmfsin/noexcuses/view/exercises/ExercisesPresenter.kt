package com.mmfsin.noexcuses.view.exercises

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.mmfsin.noexcuses.view.category.model.CategoryDTO

class ExercisesPresenter(val view: ExercisesView) : ExercisesInteractor.IExercises {

    private val interactor by lazy { ExercisesInteractor(this) }

    override fun ok(list: List<CategoryDTO>) {
    }

    override fun ko() {
        view.somethingWentWrong()
    }
}