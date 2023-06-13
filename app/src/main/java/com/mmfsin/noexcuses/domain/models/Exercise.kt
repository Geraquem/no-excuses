package com.mmfsin.noexcuses.domain.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Exercise(
    @PrimaryKey
    var id: String = "",
    var category: String = "",
    var name: String = "",
    var imageURL: String = "",
    var order: Long = 0,
    var dataURL: String? = "",
    var videoURL: String? = "",
    var weight: String? = "0"
) : RealmObject()
