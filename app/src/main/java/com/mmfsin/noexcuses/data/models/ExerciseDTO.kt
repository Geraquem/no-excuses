package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ExerciseDTO(
    @PrimaryKey
    var id: String = "",
    var category: String = "",
    var imageURL: String = "",
    var name: String = "",
    var order: Long = 0,
    var dataURL: String = "",
) : RealmObject()
