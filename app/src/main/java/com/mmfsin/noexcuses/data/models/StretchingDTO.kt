package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class StretchingDTO(
    @PrimaryKey
    var id: String = "",
    var category: String = "",
    var imageURL: String = "",
    var description: String = "",
) : RealmObject()
