package com.mmfsin.noexcuses.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class MuscularGroupDTO : RealmObject {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var manImageURL: String = ""
    var womanImageURL: String = ""
    var order: Long = 0
}
