package com.mmfsin.noexcuses.presentation.category

import com.mmfsin.noexcuses.presentation.category.model.CategoryDTO

interface CategoryView {
    fun showCategoryList(list: List<CategoryDTO>)
    fun somethingWentWrong()
}