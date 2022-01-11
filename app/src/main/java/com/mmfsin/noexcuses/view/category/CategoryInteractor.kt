package com.mmfsin.noexcuses.view.category

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.noexcuses.view.category.model.CategoryDTO

class CategoryInteractor(val listener: ICategory) {

    fun getCategoryList() {
        val list = mutableListOf<CategoryDTO>()
        Firebase.database.reference.child("logos").get()
            .addOnSuccessListener {
                for (child in it.children) {
                    list.add(CategoryDTO(child.key.toString(), child.value.toString()))
                }
                listener.ok(list)

            }.addOnFailureListener { listener.ko() }
    }

    interface ICategory {
        fun ok(list: List<CategoryDTO>)
        fun ko()
    }
}