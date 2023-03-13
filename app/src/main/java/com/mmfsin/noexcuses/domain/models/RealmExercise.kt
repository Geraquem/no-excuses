package com.mmfsin.noexcuses.domain.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmExercise(
    @PrimaryKey
    var id: String = "",
    var category: String? = "",
    var nombre: String = "",
    var order: Long = 0
) : RealmObject()
