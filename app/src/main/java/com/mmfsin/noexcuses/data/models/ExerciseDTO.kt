package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ExerciseDTO(
    @PrimaryKey
    var id: String = "",
    var category: String = "",
    var imageURL: String = "",
    var gifURL: String? = null,
    var name: String = "",
    var order: Long = 0,
    var description: String = "",
    var muscles: String = "",
    var isFav: Boolean = false,
    var muscleWikiURL: String? = null,
    var createdByUser: Boolean = false
) : RealmObject()
