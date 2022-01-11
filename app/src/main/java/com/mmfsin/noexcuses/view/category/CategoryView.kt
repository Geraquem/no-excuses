package com.mmfsin.noexcuses.view.category

import com.mmfsin.noexcuses.view.category.model.CategoryDTO

interface CategoryView {
    fun showCategoryList(list: List<CategoryDTO>)
    fun somethingWentWrong()
}