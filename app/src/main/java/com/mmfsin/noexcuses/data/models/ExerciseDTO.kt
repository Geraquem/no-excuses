package com.mmfsin.noexcuses.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class ExerciseDTO : RealmObject {
    @PrimaryKey
    var id: String = ""
    var category: String = ""
    var imageURL: String = ""
    var gifURL: String? = null
    var name: String = ""
    var order: Long = 0
    var description: String = ""
    var muscles: String = ""
    var isFav: Boolean = false
    var muscleWikiURL: String? = null
    var createdByUser: Boolean = false
}
