package com.mmfsin.noexcuses.view.category

import com.mmfsin.noexcuses.view.category.model.CategoryDTO

class CategoryPresenter(val view: CategoryView) : CategoryInteractor.ICategory{

    private val interactor by lazy { CategoryInteractor(this) }

    fun getCategoryList(){
        interactor.getCategoryList()
    }

    override fun ok(list: List<CategoryDTO>) {
        view.showCategoryList(list)
    }

    override fun ko() {
        view.somethingWentWrong()
    }
}