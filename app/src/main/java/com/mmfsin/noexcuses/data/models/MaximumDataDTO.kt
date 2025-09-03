package com.mmfsin.noexcuses.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class MaximumDataDTO : RealmObject {
    @PrimaryKey
    var id: String = ""
    var exerciseId: String = ""
    var weight: Double = 0.0
    var date: String = ""
}
