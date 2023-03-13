package com.mmfsin.noexcuses.domain.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CompleteExercise(
    @PrimaryKey var id: String = "",
    var category: String? = "",
    var nombre: String = "",
    var order: Long = 0,
    var imageURL: String? = "",
    var dataURL: String? = "",
    var involvedMuscles: String? = ""
) : RealmObject()
