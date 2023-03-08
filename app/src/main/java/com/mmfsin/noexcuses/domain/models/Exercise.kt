package com.mmfsin.noexcuses.domain.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Exercise(
    @PrimaryKey
    var id: String = "",
    var imageURL: String? = null,
    var category: String? = "",
    var nombre: String = "",
    var order: Long = 0
) : RealmObject()
