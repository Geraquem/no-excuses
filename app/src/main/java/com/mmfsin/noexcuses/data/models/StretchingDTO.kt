package com.mmfsin.noexcuses.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class StretchingDTO : RealmObject {
    @PrimaryKey
    var id: String = ""
    var order: Long = 0
    var category: String = ""
    var imageURL: String = ""
    var description: String = ""
}
