package com.mmfsin.noexcuses.intefaces

import com.mmfsin.noexcuses.view.category.model.CategoryDTO

interface IFragment {
    fun openExercises(category: CategoryDTO)
    fun close()
}