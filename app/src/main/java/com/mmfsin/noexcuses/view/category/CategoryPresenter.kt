package com.mmfsin.noexcuses.view.category

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.mmfsin.noexcuses.view.category.model.CategoryDTO

class CategoryPresenter(val view: CategoryView) : CategoryInteractor.ICategory {

    private val interactor by lazy { CategoryInteractor(this) }

    fun getGroupList(context: Context, activity: FragmentActivity?): List<LinearLayout> {
        val list = mutableListOf<LinearLayout>()
        for (i in 1..6) {
            val name = "group$i"
            val res = context.resources.getIdentifier(name, "id", context.packageName)
            list.add(activity!!.findViewById(res))
        }
        return list
    }

    fun getImagesList(context: Context, activity: FragmentActivity?): List<ImageView> {
        val list = mutableListOf<ImageView>()
        for (i in 1..6) {
            val name = "image$i"
            val res = context.resources.getIdentifier(name, "id", context.packageName)
            list.add(activity!!.findViewById(res))
        }
        return list
    }

    fun  getNamesList(context: Context, activity: FragmentActivity?): List<TextView> {
        val list = mutableListOf<TextView>()
        for (i in 1..6) {
            val name = "name$i"
            val res = context.resources.getIdentifier(name, "id", context.packageName)
            list.add(activity!!.findViewById(res))
        }
        return list
    }

    fun getCategoryList() {
        interactor.getCategoryList()
    }

    override fun ok(list: List<CategoryDTO>) {
        view.showCategoryList(list)
    }

    override fun ko() {
        view.somethingWentWrong()
    }
}